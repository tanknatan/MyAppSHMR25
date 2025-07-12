package com.natan.shamilov.shmr25.feature.history.data.mapper

import com.natan.shamilov.shmr25.common.impl.data.model.TransactionDto
import com.natan.shamilov.shmr25.common.impl.presentation.utils.convertCurrency
import com.natan.shamilov.shmr25.history.impl.domain.model.HistoryItem
import javax.inject.Inject

/**
 * Маппер для преобразования DTO транзакций в доменные объекты истории операций.
 * Используется для конвертации данных, полученных из сети, в формат,
 * подходящий для отображения в пользовательском интерфейсе.
 */
class HistoryMapper @Inject constructor() {

    /**
     * Преобразует DTO транзакции в элемент истории операций.
     *
     * @param dto DTO объект транзакции, полученный из сети
     * @return [HistoryItem] объект, содержащий информацию о транзакции в формате для UI
     */
    fun mapTransactionDtoToHistoryItem(dto: TransactionDto): HistoryItem = with(dto) {
        HistoryItem(
            id = id,
            name = dto.category.name,
            emoji = dto.category.emoji,
            amount = amount.toDouble(),
            currency = dto.account.currency.convertCurrency(),
            comment = comment,
            createdAt = transactionDate,
            categoryId = dto.category.id.toInt(),
            accountId = dto.account.id,
            isIncome = dto.category.isIncome
        )
    }
}
