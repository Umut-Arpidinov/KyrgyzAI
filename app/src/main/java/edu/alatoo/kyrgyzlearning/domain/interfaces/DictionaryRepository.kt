package edu.alatoo.kyrgyzlearning.domain.interfaces

import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word

interface DictionaryRepository {

    fun saveWord(word: Word)
}