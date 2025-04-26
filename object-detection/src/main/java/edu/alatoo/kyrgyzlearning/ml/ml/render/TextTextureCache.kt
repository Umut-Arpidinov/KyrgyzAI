package edu.alatoo.kyrgyzlearning.ml.ml.render

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import edu.alatoo.kyrgyzlearning.common.samplerender.SampleRender
import edu.alatoo.kyrgyzlearning.common.samplerender.Texture

/**
 * Generates and caches GL textures for label names.
 */
class TextTextureCache {
  companion object {
    private const val TAG = "TextTextureCache"
  }

  private val cacheMap = mutableMapOf<String, Texture>()

  /**
   * Get a texture for a given string. If that string hasn't been used yet, create a texture for it
   * and cache the result.
   */
  fun get(render: SampleRender, string: String): Texture {
    return cacheMap.computeIfAbsent(string) {
      generateTexture(render, string)
    }
  }

  private fun generateTexture(render: SampleRender, string: String): Texture {
    val texture = Texture(render, Texture.Target.TEXTURE_2D, Texture.WrapMode.CLAMP_TO_EDGE)

    val bitmap = generateBitmapFromString(string)
    // Use the new method to load bitmap into texture
    texture.loadFromBitmap(bitmap, Texture.ColorFormat.LINEAR)

    // Recycle bitmap after use
    bitmap.recycle()

    return texture
  }

  val textPaint = Paint().apply {
    textSize = 26f
    setARGB(0xff, 0xea, 0x43, 0x35)
    style = Paint.Style.FILL
    isAntiAlias = true
    textAlign = Paint.Align.CENTER
    typeface = Typeface.DEFAULT_BOLD
    strokeWidth = 2f
  }

  val strokePaint = Paint(textPaint).apply {
    setARGB(0xff, 0x00, 0x00, 0x00)
    style = Paint.Style.STROKE
  }

  private fun generateBitmapFromString(string: String): Bitmap {
    // Measure text dimensions
    val bounds = Rect()
    textPaint.getTextBounds(string, 0, string.length, bounds)

    // Add padding around the text to prevent cutoff
    val padding = 24  // Add significant padding to avoid text cutoff
    val w = bounds.width() + padding * 2
    val h = bounds.height() + padding * 2

    // Make sure dimensions are power of 2 for better texture performance (optional)
    // val texWidth = nextPowerOfTwo(w)
    // val texHeight = nextPowerOfTwo(h)

    return Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888).apply {
      eraseColor(0)  // Transparent background

      Canvas(this).apply {
        // Calculate proper text positioning to center in the bitmap
        val centerX = w / 2f
        val centerY = h / 2f + (bounds.height() / 2f) - bounds.bottom

        // Draw the text with a black outline first
        drawText(string, centerX, centerY, strokePaint)

        // Then draw the colored text
        drawText(string, centerX, centerY, textPaint)
      }
    }
  }

  // Helper function to get next power of 2 (optional)
  private fun nextPowerOfTwo(n: Int): Int {
    var value = n
    value--
    value = value or (value shr 1)
    value = value or (value shr 2)
    value = value or (value shr 4)
    value = value or (value shr 8)
    value = value or (value shr 16)
    value++
    return value
  }
}