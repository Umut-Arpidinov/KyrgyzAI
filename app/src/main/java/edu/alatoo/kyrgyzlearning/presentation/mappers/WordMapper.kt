package edu.alatoo.kyrgyzlearning.presentation.mappers

import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word


object WordMapper {
    fun mapToEntities(words: List<String>): List<Word> {
        return words.map {
            Word(
                word = it
            )
        }
    }
}