package edu.alatoo.kyrgyzlearning.data.repository

import com.google.firebase.vertexai.GenerativeModel
import edu.alatoo.kyrgyzlearning.common.utils.ApiResult
import edu.alatoo.kyrgyzlearning.common.utils.apiRequest
import edu.alatoo.kyrgyzlearning.domain.interfaces.VertexAIRepository

class VertexAIRepositoryImpl(
    private val vertexModel: GenerativeModel
): VertexAIRepository {

    override suspend fun getTranslations(word: String): ApiResult<String?> {
        val prompt = """
        Translate the word "$word" into Russian and English.
        Return only the translated words, one per line, without any labels or formatting.
    """.trimIndent()

        return apiRequest { vertexModel.generateContent(prompt).text }
    }


    override suspend fun getExamples(word: String): ApiResult<String?> {
        return apiRequest {
            val prompt = """
            Generate three example sentences using the word "$word" in Kyrgyz. 
            Each sentence should be translated into Russian and English.
            
            Return the output in the following format:
            
            [Kyrgyz Sentence]  
            [Russian Translation]  
            [English Translation]  
            
            Example:
            
            Мен китеп окуп жатам.  
            Я читаю книгу.  
            I am reading a book.  
            
            Now generate three examples for "$word" following this format.
            Do not include any label title or formatting just clear examples 
        """.trimIndent()

            vertexModel.generateContent(prompt).text
        }
    }

}