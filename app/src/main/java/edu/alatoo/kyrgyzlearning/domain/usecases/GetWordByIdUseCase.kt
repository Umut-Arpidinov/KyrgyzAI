package edu.alatoo.kyrgyzlearning.domain.usecases

import edu.alatoo.kyrgyzlearning.common.utils.LocalDbResult
import edu.alatoo.kyrgyzlearning.common.utils.dbRequest
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import edu.alatoo.kyrgyzlearning.domain.interfaces.DictionaryRepository

class GetWordByIdUseCase(
    private val dictionaryRepository: DictionaryRepository
) {
    suspend operator fun invoke(id: Int): LocalDbResult<Word?> {
        return dbRequest { dictionaryRepository.getWordById(id)}
    }
}