package edu.alatoo.kyrgyzlearning.ml.ml.render

import com.google.ar.core.Pose
import edu.alatoo.kyrgyzlearning.common.samplerender.Mesh
import edu.alatoo.kyrgyzlearning.common.samplerender.SampleRender
import edu.alatoo.kyrgyzlearning.common.samplerender.Shader
import edu.alatoo.kyrgyzlearning.common.samplerender.VertexBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Draws a label. See [draw].
 */
class LabelRender {
  companion object {
    private const val TAG = "LabelRender"
    val COORDS_BUFFER_SIZE = 2 * 4 * 4

    /**
     * Vertex buffer data for the mesh quad.
     */
    val NDC_QUAD_COORDS_BUFFER =
      ByteBuffer.allocateDirect(COORDS_BUFFER_SIZE).order(
        ByteOrder.nativeOrder()
      ).asFloatBuffer().apply {
        put(
          floatArrayOf(
            /*0:*/
            -1.0f, -1.0f,
            /*1:*/
            1.0f, -1.0f,
            /*2:*/
            -1.0f, 1.0f,
            /*3:*/
            1.0f, 1.0f,
          )
        )
      }

    /**
     * Vertex buffer data for texture coordinates.
     */
    val SQUARE_TEX_COORDS_BUFFER =
      ByteBuffer.allocateDirect(COORDS_BUFFER_SIZE).order(
        ByteOrder.nativeOrder()
      ).asFloatBuffer().apply {
        put(
          floatArrayOf(
            /*0:*/
            0f, 0f,
            /*1:*/
            1f, 0f,
            /*2:*/
            0f, 1f,
            /*3:*/
            1f, 1f,
          )
        )
      }
  }

  val cache = TextTextureCache()

  lateinit var mesh: Mesh
  lateinit var shader: Shader


  fun onSurfaceCreated(render: SampleRender) {
    shader = Shader.createFromAssets(render, "shaders/label.vert", "shaders/label.frag", null)
      .setBlend(
        Shader.BlendFactor.ONE, // ALPHA (src)
        Shader.BlendFactor.ONE_MINUS_SRC_ALPHA // ALPHA (dest)
      )
      .setDepthTest(false)
      .setDepthWrite(false)

    val vertexBuffers = arrayOf(
      VertexBuffer(render, 2, NDC_QUAD_COORDS_BUFFER),
      VertexBuffer(render, 2, SQUARE_TEX_COORDS_BUFFER),
    )
    mesh = Mesh(render, Mesh.PrimitiveMode.TRIANGLE_STRIP, null, vertexBuffers)
  }

  val labelOrigin = FloatArray(3)

  /**
   * Draws a label quad with text [label] at [pose]. The label will rotate to face [cameraPose] around the Y-axis.
   */
  fun draw(
    render: SampleRender,
    viewProjectionMatrix: FloatArray,
    pose: Pose,
    cameraPose: Pose,
    label: String
  ) {
    labelOrigin[0] = pose.tx()
    labelOrigin[1] = pose.ty()
    labelOrigin[2] = pose.tz()

    // Get the texture for this label
    val texture = cache.get(render, label)

    // Calculate aspect ratio (width/height) of the texture
    val aspectRatio = texture.getWidth().toFloat() / texture.getHeight().toFloat()

    // Scale factor can be adjusted based on your needs
    val baseScale = 0.1f

    shader
      .setMat4("u_ViewProjection", viewProjectionMatrix)
      .setVec3("u_LabelOrigin", labelOrigin)
      .setVec3("u_CameraPos", cameraPose.translation)
      .setFloat("u_AspectRatio", aspectRatio)  // Pass aspect ratio to shader
      .setFloat("u_BaseScale", baseScale)      // Pass base scale to shader
      .setTexture("uTexture", texture)
    render.draw(mesh, shader)
  }
}