package edu.alatoo.kyrgyzlearning.domain.interfaces

import edu.alatoo.kyrgyzlearning.common.utils.LocalDbResult
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Example
import edu.alatoo.kyrgyzlearning.data.local.db.entities.FlashCard
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Translation
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word

interface DictionaryRepository {

    fun saveWord(word: Word)

    suspend fun saveWords(words: List<Word>)

    suspend fun getWords(): List<Word>

    suspend fun getWordById(id: Int): Word?

    suspend fun saveExamples(example: Example): LocalDbResult<Unit>

    suspend fun saveTranslations(translation: Translation): LocalDbResult<Unit>

    suspend fun getExamplesById(wordId: Int): LocalDbResult<Example?>

    suspend fun getTranslationsById(wordId: Int): LocalDbResult<Translation?>

    suspend fun getLastFiveWords(): LocalDbResult<List<Word>>

    suspend fun getWordWithDetails(): LocalDbResult<List<FlashCard>>

}