package edu.alatoo.kyrgyzlearning.presentation.ui.objectDetection

import AppRenderer
import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.ar.core.CameraConfig
import com.google.ar.core.CameraConfigFilter
import com.google.ar.core.Config
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.google.ar.core.exceptions.UnavailableApkTooOldException
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException
import com.google.ar.core.exceptions.UnavailableSdkTooOldException
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.common.helpers.CameraPermissionHelper
import edu.alatoo.kyrgyzlearning.databinding.FragmentRootBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.showSimpleDialog
import edu.alatoo.kyrgyzlearning.presentation.ui.apprender.ARCoreSessionLifecycleHelper
import edu.alatoo.kyrgyzlearning.presentation.ui.apprender.ObjectDetectionView
import edu.alatoo.kyrgyzlearning.presentation.ui.objectDetection.saveWords.SaveWordsBottomSheet
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import timber.log.Timber

class ObjectDetectionFragment : BaseFragment<ObjectDetectionViewModel, FragmentRootBinding>(
    FragmentRootBinding::inflate
) {


    override val viewModel: ObjectDetectionViewModel by activityViewModel()


    lateinit var arCoreSessionHelper: ARCoreSessionLifecycleHelper
    lateinit var renderer: AppRenderer
    lateinit var view: ObjectDetectionView


    private val saveWordsBottomSheet = SaveWordsBottomSheet()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arCoreSessionHelper = ARCoreSessionLifecycleHelper(requireActivity())
        // When session creation or session.resume fails, we display a message and log detailed information.
        arCoreSessionHelper.exceptionCallback = { exception ->
            val message = when (exception) {
                is UnavailableArcoreNotInstalledException,
                is UnavailableUserDeclinedInstallationException -> "Please install ARCore"

                is UnavailableApkTooOldException -> "Please update ARCore"
                is UnavailableSdkTooOldException -> "Please update this app"
                is UnavailableDeviceNotCompatibleException -> "This device does not support AR"
                is CameraNotAvailableException -> "Camera not available. Try restarting the app."
                else -> "Failed to create AR session: $exception"
            }
            Timber.e(exception, message)
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }

        arCoreSessionHelper.beforeSessionResume = { session ->
            session.configure(
                session.config.apply {
                    // To get the best image of the object in question, enable autofocus.
                    focusMode = Config.FocusMode.AUTO
                    if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                        depthMode = Config.DepthMode.AUTOMATIC
                    }
                }
            )
            val filter = CameraConfigFilter(session)
                .setFacingDirection(CameraConfig.FacingDirection.BACK)
            val configs = session.getSupportedCameraConfigs(filter)
            val sort = compareByDescending<CameraConfig> { it.imageSize.width }
                .thenByDescending { it.imageSize.height }
            session.cameraConfig = configs.sortedWith(sort)[0]
        }
        lifecycle.addObserver(arCoreSessionHelper)
        renderer = AppRenderer(this)
        lifecycle.addObserver(renderer)
        view = ObjectDetectionView(requireContext(), renderer)
        lifecycle.addObserver(view)
        renderer.bindView(view)
        return view.binding.root
    }

    override fun initialize() {
        super.initialize()
        requestCameraPermission()
    }


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Разрешение получено, можно продолжить работу
            } else {
                Toast.makeText(
                    activity,
                    "Camera permission is needed to run this application",
                    Toast.LENGTH_LONG
                ).show()
                if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(activity)) {
                    // Разрешение отклонено с "Don't ask again"
                    CameraPermissionHelper.launchPermissionSettings(activity)
                }
            }
        }

    private fun requestCameraPermission() {
        if (!CameraPermissionHelper.hasCameraPermission(activity)) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }


    override fun initClicks() {
        super.initClicks()
        view.binding.apply {
            ivClose.setOnClickListener {
                findNavController().navigateUp()
            }
            ivSave.setOnClickListener {
                renderer.triggerSave()
            }
        }

        renderer.onSaveWordListener {
            if (it.isNotEmpty()) {
                viewModel.saveWords(it.toList())
                saveWordsBottomSheet.showBottomSheet(childFragmentManager)
            }
        }
    }


    override fun observeViewModel() {
        super.observeViewModel()
    }


}