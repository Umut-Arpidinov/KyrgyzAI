package edu.alatoo.kyrgyzlearning.data.local.db.entities

data class FlashCard(
    val word: String,
    val translations: String,
    val example: List<Example>?,
    val synonyms: List<Synonym>
)
