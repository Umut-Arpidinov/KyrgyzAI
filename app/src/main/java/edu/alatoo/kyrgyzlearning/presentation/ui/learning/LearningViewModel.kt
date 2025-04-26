package edu.alatoo.kyrgyzlearning.presentation.ui.learning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.alatoo.kyrgyzlearning.common.base.BaseViewModel
import edu.alatoo.kyrgyzlearning.data.local.db.entities.FlashCard
import edu.alatoo.kyrgyzlearning.domain.interfaces.DictionaryRepository

class LearningViewModel(
    val dictionaryRepository: DictionaryRepository
) : BaseViewModel() {


    private val _wordWithDetails: MutableLiveData<List<FlashCard>> = MutableLiveData()
    val wordWithDetails: LiveData<List<FlashCard>> get() = _wordWithDetails

    init {
        getWordWithDetails()
    }

    private fun getWordWithDetails() {
        dbRequest(
            source = { dictionaryRepository.getWordWithDetails() },
            ) {
            _wordWithDetails.value = it
        }
    }
}