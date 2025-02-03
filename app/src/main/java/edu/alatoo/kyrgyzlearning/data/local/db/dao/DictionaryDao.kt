package edu.alatoo.kyrgyzlearning.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word


@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

}