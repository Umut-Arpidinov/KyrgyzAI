package edu.alatoo.kyrgyzlearning.data.local.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary", indices = [Index(value = ["word"], unique = true)])
data class Word(
    @ColumnInfo(name = "word")
    val word: String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}