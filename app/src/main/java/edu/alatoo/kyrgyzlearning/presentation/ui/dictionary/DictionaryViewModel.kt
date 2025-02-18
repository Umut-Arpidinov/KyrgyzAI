package edu.alatoo.kyrgyzlearning.presentation.ui.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.alatoo.kyrgyzlearning.common.base.BaseViewModel
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import edu.alatoo.kyrgyzlearning.domain.usecases.GetWordsFromDbUseCase

class DictionaryViewModel(
    private val getWordsFromDbUseCase: GetWordsFromDbUseCase
) : BaseViewModel() {

    private val _words: MutableLiveData<List<Word>> = MutableLiveData()
    val words: LiveData<List<Word>> get() = _words


    fun getWordsFromDb() {
        dbRequest(
            source = { getWordsFromDbUseCase.invoke() }
        ) {
            _words.value = it
        }
    }
}