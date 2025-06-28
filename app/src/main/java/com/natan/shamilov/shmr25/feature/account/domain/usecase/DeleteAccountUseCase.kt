package com.natan.shamilov.shmr25.feature.account.domain.usecase

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.feature.account.domain.repository.AccountRepository
import javax.inject.Inject

/**
 * Use case для удаления счета.
 * Ответственность: Реализация бизнес-логики удаления существующего счета.
 * Делегирует фактическое удаление репозиторию.
 *
 * @property repository Репозиторий для работы со счетами
 */
class DeleteAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    /**
     * Выполняет удаление счета
     * @param id идентификатор удаляемого счета
     * @return результат операции удаления
     */
    suspend operator fun invoke(id: Int): Result<Unit> = repository.deleteAccount(id)
}
