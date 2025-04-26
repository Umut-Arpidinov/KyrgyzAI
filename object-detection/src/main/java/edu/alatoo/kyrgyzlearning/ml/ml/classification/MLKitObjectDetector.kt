/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.alatoo.kyrgyzlearning.ml.ml.classification

import android.app.Activity
import android.media.Image
import edu.alatoo.kyrgyzlearning.ml.ml.classification.utils.ImageUtils
import edu.alatoo.kyrgyzlearning.ml.ml.classification.utils.VertexUtils.rotateCoordinates
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import kotlinx.coroutines.tasks.asDeferred

/**
 * Analyzes an image using ML Kit.
 */
class MLKitObjectDetector(context: Activity) : ObjectDetector(context) {


   val model = LocalModel.Builder().setAssetFilePath("models/translated_ky_new.tflite").build()



   val builder = CustomObjectDetectorOptions.Builder(model)


  private val options = builder
    .setDetectorMode(CustomObjectDetectorOptions.SINGLE_IMAGE_MODE)
    .enableClassification()
    .build()
  private val detector = ObjectDetection.getClient(options)

  override suspend fun analyze(image: Image, imageRotation: Int): List<DetectedObjectResult> {

    val convertYuv = convertYuv(image)

    val rotatedImage = ImageUtils.rotateBitmap(convertYuv, imageRotation)

    val inputImage = InputImage.fromBitmap(rotatedImage, 0)

    val mlKitDetectedObjects = detector.process(inputImage).asDeferred().await()
    return mlKitDetectedObjects.mapNotNull { obj ->
      val bestLabel = obj.labels.maxByOrNull { label -> label.confidence } ?: return@mapNotNull null
      val coords = obj.boundingBox.exactCenterX().toInt() to obj.boundingBox.exactCenterY().toInt()
      val rotatedCoordinates = coords.rotateCoordinates(rotatedImage.width, rotatedImage.height, imageRotation)
      DetectedObjectResult(bestLabel.confidence, bestLabel.text, rotatedCoordinates)
    }
  }
}