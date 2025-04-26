package edu.alatoo.kyrgyzlearning.data.local.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class WordWithDetails(
    @Embedded val word: Word,

    @Relation(parentColumn = "id", entityColumn = "wordId")
    val translations: List<Translation>,

    @Relation(parentColumn = "id", entityColumn = "wordId")
    val examples: List<Example>,

    @Relation(parentColumn = "id", entityColumn = "wordId")
    val synonyms: List<Synonym>

) {
    val translationStrings: String
        get() = translations.joinToString(separator = ", ") { it.translation }

}
