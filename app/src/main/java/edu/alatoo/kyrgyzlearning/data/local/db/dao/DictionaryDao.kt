package edu.alatoo.kyrgyzlearning.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Example
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Translation
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import edu.alatoo.kyrgyzlearning.data.local.db.entities.WordWithDetails


@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWords(words: List<Word>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTranslations(translation: Translation)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExamples(examples: Example)

    @Query("SELECT * FROM dictionary")
    suspend fun getWords(): List<Word>


    @Query("SELECT * FROM dictionary WHERE id = :id LIMIT 1")
    suspend fun getWordById(id: Int): Word?

    @Query("SELECT * FROM examples WHERE wordId = :wordId")
    suspend fun getExampleById(wordId: Int): Example?

    @Query("SELECT * FROM translations WHERE wordId = :wordId")
    suspend fun getTranslationById(wordId: Int): Translation?

    @Query("SELECT * FROM dictionary ORDER BY id DESC LIMIT 5")
    suspend fun getLastFiveWords(): List<Word>

    @Transaction
    @Query("SELECT * FROM dictionary")
    suspend fun getAllWordsWithDetails(): List<WordWithDetails>


}