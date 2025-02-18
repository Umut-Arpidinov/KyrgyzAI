package edu.alatoo.kyrgyzlearning.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "synonyms",
    foreignKeys = [ForeignKey(
        entity = Word::class,
        parentColumns = ["id"],
        childColumns = ["wordId"],
        onDelete = CASCADE
    )],
    indices = [Index(value = ["wordId"])]
)
data class Synonym(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "wordId")
    val wordId: Int,  // Links synonyms to a word

    @ColumnInfo(name = "synonym")
    val synonym: String
)