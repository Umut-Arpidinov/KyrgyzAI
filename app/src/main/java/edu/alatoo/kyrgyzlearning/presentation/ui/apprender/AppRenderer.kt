import android.opengl.Matrix
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.ar.core.Anchor
import com.google.ar.core.Coordinates2d
import com.google.ar.core.Frame
import com.google.ar.core.TrackingState
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.google.ar.core.exceptions.NotYetAvailableException
import edu.alatoo.kyrgyzlearning.common.helpers.DisplayRotationHelper
import edu.alatoo.kyrgyzlearning.common.samplerender.SampleRender
import edu.alatoo.kyrgyzlearning.common.samplerender.arcore.BackgroundRenderer
import edu.alatoo.kyrgyzlearning.ml.ml.classification.DetectedObjectResult
import edu.alatoo.kyrgyzlearning.ml.ml.classification.MLKitObjectDetector
import edu.alatoo.kyrgyzlearning.ml.ml.render.LabelRender
import edu.alatoo.kyrgyzlearning.ml.ml.render.PointCloudRender
import edu.alatoo.kyrgyzlearning.presentation.ui.apprender.ObjectDetectionView
import edu.alatoo.kyrgyzlearning.presentation.ui.objectDetection.ObjectDetectionFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Collections

class AppRenderer(
    val fragment: ObjectDetectionFragment
) : DefaultLifecycleObserver, SampleRender.Renderer,CoroutineScope by MainScope() {

    companion object {
        val TAG = "KyrgyzArRenderer"
    }

    lateinit var view: ObjectDetectionView
    val displayRotationHelper = DisplayRotationHelper(fragment.requireContext())
    lateinit var backgroundRenderer: BackgroundRenderer
    val pointCloudRender = PointCloudRender()
    val labelRenderer = LabelRender()
    val viewMatrix = FloatArray(16)
    val projectionMatrix = FloatArray(16)
    val viewProjectionMatrix = FloatArray(16)
    val arLabeledAnchors = Collections.synchronizedList(mutableListOf<ARLabeledAnchor>())
    var scanButtonWasPressed = false
    val mlKitAnalyzer = MLKitObjectDetector(fragment.requireActivity())

    override fun onResume(owner: LifecycleOwner) {
        displayRotationHelper.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        displayRotationHelper.onPause()
    }

    fun bindView(view: ObjectDetectionView) {
        this.view = view
        view.binding.scanButton.setOnClickListener {
            scanButtonWasPressed = true
            view.setScanningActive(true)
            hideSnackBar()
        }

        view.binding.clearButton.setOnClickListener {
            arLabeledAnchors.clear()
            view.binding.clearButton.isEnabled = false
            hideSnackBar()
        }
    }


    override fun onSurfaceCreated(render: SampleRender) {
        backgroundRenderer = BackgroundRenderer(render).apply {
            setUseDepthVisualization(render, false)
        }
        pointCloudRender.onSurfaceCreated(render)
        labelRenderer.onSurfaceCreated(render)
    }

    override fun onSurfaceChanged(render: SampleRender, width: Int, height: Int) {
        displayRotationHelper.onSurfaceChanged(width, height)
    }

    var objectResults: List<DetectedObjectResult>? = null


    override fun onDrawFrame(render: SampleRender) {
        val session = fragment.arCoreSessionHelper.sessionCache ?: return
        if (session == null) {
            Timber.tag(TAG).e("Arcore session is null")
        }
        session.setCameraTextureNames(intArrayOf(backgroundRenderer.cameraColorTexture.textureId))
        // Notify ARCore session that the view size changed so that the perspective matrix and
        // the video background can be properly adjusted.
        displayRotationHelper.updateSessionIfNeeded(session)

        val frame = try {
            session.update()
        } catch (e: CameraNotAvailableException) {
            Timber.tag(TAG).e(e, "Camera not available during onDrawFrame")
            showSnackBar("Camera not available. Try restarting the app.")
            return
        }

        backgroundRenderer.updateDisplayGeometry(frame)
        backgroundRenderer.drawBackground(render)

        // Get camera and projection matrices.
        val camera = frame.camera
        camera.getViewMatrix(viewMatrix, 0)
        camera.getProjectionMatrix(projectionMatrix, 0, 0.01f, 100.0f)
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        // Handle tracking failures.
        if (camera.trackingState != TrackingState.TRACKING) {
            Timber.tag(TAG).w("Camera tracking state is NOT TRACKING")
            return
        }
        // Draw point cloud.
        frame.acquirePointCloud().use { pointCloud ->
            pointCloudRender.drawPointCloud(render, pointCloud, viewProjectionMatrix)
        }

        // Frame.acquireCameraImage must be used on the GL thread.
        // Check if the button was pressed last frame to start processing the camera image.
        if (scanButtonWasPressed) {
            scanButtonWasPressed = false
            val cameraImage = frame.tryAcquireCameraImage()
            if (cameraImage != null) {
                // Call our ML model on an IO thread.
                launch(Dispatchers.IO) {
                    val cameraId = session.cameraConfig.cameraId
                    val imageRotation =
                        displayRotationHelper.getCameraSensorToDisplayRotation(cameraId)
                    objectResults = mlKitAnalyzer.analyze(cameraImage, imageRotation)
                    cameraImage.close()
                }
            }
        }

        /** If results were completed this frame, create [Anchor]s from model results. */
        val objects = objectResults
        Timber.tag(TAG).d("$mlKitAnalyzer got objects: $objects")
        if (objects != null) {
            objectResults = null
            Timber.tag(TAG).i("$mlKitAnalyzer got objects: $objects")
            val anchors = objects.mapNotNull { obj ->
                val (atX, atY) = obj.centerCoordinate
                val anchor =
                    createAnchor(atX.toFloat(), atY.toFloat(), frame) ?: return@mapNotNull null
                Timber.tag(TAG).i("Created anchor ${anchor.pose} from hit test")

                ARLabeledAnchor(anchor, obj.label)
            }
            arLabeledAnchors.addAll(anchors)
            Timber.tag("_____").d("$arLabeledAnchors")
            view.post {
                view.binding.clearButton.isEnabled = arLabeledAnchors.isNotEmpty()
                view.setScanningActive(false)
                when {
                    objects.isEmpty() ->
                        showSnackBar(
                            "Default ML Kit classification model returned no results. "

                        )

                    anchors.size != objects.size ->
                        showSnackBar(
                            "Objects were classified, but could not be attached to an anchor. " +
                                    "Try moving your device around to obtain a better understanding of the environment."
                        )
                }
            }
        }

        // Draw labels at their anchor position.

        for (arDetectedObject in arLabeledAnchors) {
            val anchor = arDetectedObject.anchor
            if (anchor.trackingState != TrackingState.TRACKING) continue
            labelRenderer.draw(
                render,
                viewProjectionMatrix,
                anchor.pose,
                camera.pose,
                arDetectedObject.label
            )
        }
    }


    private fun showSnackBar(message: String): Unit =
        view.snackBarHelper.showError(fragment.requireActivity(), message)

    private fun hideSnackBar() = view.snackBarHelper.hide(fragment.requireActivity())


    /**
     * Utility method for [Frame.acquireCameraImage] that maps [NotYetAvailableException] to `null`.
     */
    fun Frame.tryAcquireCameraImage() = try {
        acquireCameraImage()
    } catch (e: NotYetAvailableException) {
        null
    } catch (e: Throwable) {
        throw e
    }

    /**
     * Temporary arrays to prevent allocations in [createAnchor].
     */
    private val convertFloats = FloatArray(4)
    private val convertFloatsOut = FloatArray(4)

    /** Create an anchor using (x, y) coordinates in the [Coordinates2d.IMAGE_PIXELS] coordinate space. */
    fun createAnchor(xImage: Float, yImage: Float, frame: Frame): Anchor? {
        // IMAGE_PIXELS -> VIEW
        convertFloats[0] = xImage
        convertFloats[1] = yImage
        frame.transformCoordinates2d(
            Coordinates2d.IMAGE_PIXELS,
            convertFloats,
            Coordinates2d.VIEW,
            convertFloatsOut
        )

        // Conduct a hit test using the VIEW coordinates
        val hits = frame.hitTest(convertFloatsOut[0], convertFloatsOut[1])
        val result = hits.getOrNull(0) ?: return null
        return result.trackable.createAnchor(result.hitPose)
    }


    fun triggerSave() {
        val wordsToSave = getOnlyLabels()
        onSaveWordsListener?.invoke(wordsToSave)
    }


    private var onSaveWordsListener: ((Set<String>) -> Unit)? = null

    fun onSaveWordListener(listener: (Set<String>) -> Unit) {
        onSaveWordsListener = listener
    }

    private fun getOnlyLabels(): Set<String> = arLabeledAnchors.map { it.label }.toSet()



}

data class ARLabeledAnchor(val anchor: Anchor, val label: String)