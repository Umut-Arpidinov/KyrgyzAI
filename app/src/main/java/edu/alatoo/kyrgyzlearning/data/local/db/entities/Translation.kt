package edu.alatoo.kyrgyzlearning.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "translations",
    foreignKeys = [ForeignKey(
        entity = Word::class,
        parentColumns = ["id"],
        childColumns = ["wordId"],
        onDelete = CASCADE
    )],
    indices = [Index(value = ["wordId"])]
)
data class Translation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "wordId")
    val wordId: Int,  // Links translation to a word

    @ColumnInfo(name = "translation")
    val translation: String
)
