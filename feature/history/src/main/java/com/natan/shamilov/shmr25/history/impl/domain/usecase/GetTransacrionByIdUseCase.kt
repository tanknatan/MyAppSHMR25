package com.natan.shamilov.shmr25.history.impl.domain.usecase

import com.natan.shamilov.shmr25.history.impl.domain.repository.HistoryRepository
import com.natan.shamilov.shmr25.history.impl.domain.model.HistoryItem
import javax.inject.Inject

class GetTransacrionByIdUseCase @Inject constructor(
    private val repository: HistoryRepository,
) {
    suspend operator fun invoke(id: Int): HistoryItem? = repository.getHistoryItemById(id)
}