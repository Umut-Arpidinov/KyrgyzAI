package edu.alatoo.kyrgyzlearning.presentation.ui.home

import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI
import edu.alatoo.kyrgyzlearning.common.base.BaseViewModel
import edu.alatoo.kyrgyzlearning.domain.interfaces.DictionaryRepository
import timber.log.Timber

class HomeViewModel(
    private val dictionaryRepository: DictionaryRepository
) : BaseViewModel() {


    val generativeModel = Firebase.vertexAI.generativeModel("gemini-1.5-flash")

    val list = listOf("Китеп", "Апа")





    suspend fun generateUsage() {
        val prompt = "Give me  usages in a sentence in for kyrgyz word китеп in a kyrgyz language with translations to russian and english"

        val response = generativeModel.generateContent(prompt)

        Timber.tag("Vertex").d(response.text)

    }


}