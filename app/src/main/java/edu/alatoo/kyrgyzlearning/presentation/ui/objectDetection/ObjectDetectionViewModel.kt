package edu.alatoo.kyrgyzlearning.presentation.ui.objectDetection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.alatoo.kyrgyzlearning.common.base.BaseViewModel
import edu.alatoo.kyrgyzlearning.domain.usecases.SaveWordsUseCase
import edu.alatoo.kyrgyzlearning.presentation.mappers.WordMapper

class ObjectDetectionViewModel(
    private val saveWordsUseCase: SaveWordsUseCase
) : BaseViewModel() {


    private val _wordsToSave: MutableLiveData<List<String>> = MutableLiveData()
    val wordsToSave: LiveData<List<String>> get() = _wordsToSave


    fun saveWords(words: List<String>) {
        _wordsToSave.value = words
    }

    fun saveWordsToDb(words: List<String>, onWordsSaved: () -> Unit) {
        val wordEntities = WordMapper.mapToEntities(words)
        dbRequest(
            source = { saveWordsUseCase.invoke(wordEntities) },
            onError = {
            }
        ) {
            onWordsSaved.invoke()
        }
    }

}