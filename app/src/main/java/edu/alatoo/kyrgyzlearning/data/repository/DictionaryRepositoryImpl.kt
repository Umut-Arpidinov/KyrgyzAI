package edu.alatoo.kyrgyzlearning.data.repository

import edu.alatoo.kyrgyzlearning.common.utils.LocalDbResult
import edu.alatoo.kyrgyzlearning.common.utils.dbRequest
import edu.alatoo.kyrgyzlearning.data.local.db.dao.DictionaryDao
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Example
import edu.alatoo.kyrgyzlearning.data.local.db.entities.FlashCard
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Translation
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import edu.alatoo.kyrgyzlearning.domain.interfaces.DictionaryRepository

class DictionaryRepositoryImpl(
    private val dao: DictionaryDao
) : DictionaryRepository {

    override fun saveWord(word: Word) {
        TODO("Not yet implemented")
    }

    override suspend fun saveWords(words: List<Word>) {
        dao.insertWords(words)
    }


    override suspend fun getWords(): List<Word> {
        return dao.getWords()
    }

    override suspend fun getWordById(id: Int): Word? {
        return dao.getWordById(id)
    }

    override suspend fun saveTranslations(translation: Translation): LocalDbResult<Unit> {
        return dbRequest { dao.insertTranslations(translation) }
    }

    override suspend fun saveExamples(example: Example): LocalDbResult<Unit> {
        return dbRequest { dao.insertExamples(example) }
    }

    override suspend fun getTranslationsById(wordId: Int): LocalDbResult<Translation?> {
        return dbRequest { dao.getTranslationById(wordId) }
    }

    override suspend fun getExamplesById(wordId: Int): LocalDbResult<Example?> {
        return dbRequest { dao.getExampleById(wordId) }
    }

    override suspend fun getLastFiveWords(): LocalDbResult<List<Word>> {
        return dbRequest { dao.getLastFiveWords() }
    }

    override suspend fun getWordWithDetails(): LocalDbResult<List<FlashCard>> {
        return dbRequest {
            dao.getAllWordsWithDetails().map {
                FlashCard(
                    word = it.word.word,
                    translations = it.translationStrings,
                    example = it.examples,
                    synonyms = it.synonyms
                )
            }
        }
    }
}