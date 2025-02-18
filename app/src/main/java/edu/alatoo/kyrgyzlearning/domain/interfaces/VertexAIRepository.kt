package edu.alatoo.kyrgyzlearning.domain.interfaces

import edu.alatoo.kyrgyzlearning.common.utils.ApiResult


interface VertexAIRepository {
   suspend fun getTranslations(word: String): ApiResult<String?>
   suspend fun getExamples(word: String): ApiResult<String?>
}