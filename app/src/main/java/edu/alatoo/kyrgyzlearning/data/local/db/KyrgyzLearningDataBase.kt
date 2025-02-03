package edu.alatoo.kyrgyzlearning.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.alatoo.kyrgyzlearning.data.local.db.dao.DictionaryDao
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader


@Database(
    entities = [Word::class],
    version = 1,
    exportSchema = false
)
abstract class KyrgyzLearningDataBase : RoomDatabase() {

    abstract fun dictionaryDao(): DictionaryDao

    companion object {
        private const val DATABASE_NAME = "KyrgyzLearningDataBase"
        @Volatile
        private var INSTANCE: KyrgyzLearningDataBase? = null

        fun getDataBaseClient(context: Context): KyrgyzLearningDataBase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, KyrgyzLearningDataBase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                return INSTANCE!!
            }
        }

    }
}
