package edu.alatoo.kyrgyzlearning.presentation.ui.dictionary.wordDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.alatoo.kyrgyzlearning.common.base.BaseViewModel
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Example
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Translation
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import edu.alatoo.kyrgyzlearning.domain.interfaces.DictionaryRepository
import edu.alatoo.kyrgyzlearning.domain.interfaces.VertexAIRepository
import edu.alatoo.kyrgyzlearning.domain.usecases.GetWordByIdUseCase

class WordDetailViewModel(
    private val getWordByIdUseCase: GetWordByIdUseCase,
    private val vertexAIRepository: VertexAIRepository,
    private val dictionaryRepository: DictionaryRepository
) : BaseViewModel() {

    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word> get() = _word

    private val _translations = MutableLiveData<String?>()
    val translations: LiveData<String?> get() = _translations

    private val _examples = MutableLiveData<String?>()
    val examples: LiveData<String?> get() = _examples

    fun getWordById(id: Int) {
        dbRequest(
            source = { getWordByIdUseCase(id) }
        ) {
            _word.value = it
            it?.id?.let { wordId ->
                fetchAndSaveWordDetails(wordId, it.word)  // ✅ Auto-fetch if needed
            }
        }
    }

    private fun fetchAndSaveWordDetails(wordId: Int, word: String) {
        // Check if translation exists
        dbRequest(
            source = { dictionaryRepository.getTranslationsById(wordId) }
        ) { translation ->
            if (translation != null) {
                _translations.value = translation.translation // ✅ Show DB data
            } else {
                fetchAndSaveTranslation(wordId, word) // ✅ Fetch if not in DB
            }
        }

        // Check if example exists
        dbRequest(
            source = { dictionaryRepository.getExamplesById(wordId) }
        ) { example ->
            if (example != null) {
                _examples.value = example.exampleSentence // ✅ Show DB data
            } else {
                fetchAndSaveExample(wordId, word) // ✅ Fetch if not in DB
            }
        }
    }

    private fun fetchAndSaveTranslation(wordId: Int, word: String) {
        networkRequest(
            source = { vertexAIRepository.getTranslations(word) }
        ) { aiTranslation ->
            _translations.value = aiTranslation
            aiTranslation?.let {
                dbRequest(
                    source = { dictionaryRepository.saveTranslations(Translation(wordId= wordId, translation = it))}
                ) {} // ✅ Auto-save translation to Room
            }
        }
    }

    private fun fetchAndSaveExample(wordId: Int, word: String) {
        networkRequest(
            source = { vertexAIRepository.getExamples(word) }
        ) { aiExample ->
            _examples.value = aiExample
            aiExample?.let {
                dbRequest(
                    source = { dictionaryRepository.saveExamples(Example(wordId=wordId, exampleSentence =it)) }
                ) {} // ✅ Auto-save example to Room
            }
        }
    }
}
