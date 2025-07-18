package com.natan.shamilov.shmr25.common.impl.data.mapper

import com.natan.shamilov.shmr25.common.impl.data.model.CreateTransactionResponse
import com.natan.shamilov.shmr25.common.impl.data.model.TransactionDto
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.common.impl.domain.storage.entity.TransactionDbModel
import com.natan.shamilov.shmr25.common.impl.domain.storage.entity.TransactionWithRelations
import com.natan.shamilov.shmr25.common.impl.presentation.utils.convertCurrency
import com.natan.shamilov.shmr25.common.impl.presentation.utils.formatToIsoUtc
import javax.inject.Inject

class TransactionMapper @Inject constructor() {

    fun mapTransactionDtoToTransaction(dto: TransactionDto): Transaction = with(dto) {
        Transaction(
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

    fun mapTransactionResponseToDbModel(response: CreateTransactionResponse): TransactionDbModel =
        with(response) {
            TransactionDbModel(
                id = id.toInt(),
                amount = amount.toDouble(),
                comment = comment,
                createdAt = createdAt,
                accountId = accountid,
                categoryId = categoryId
            )
        }

    fun mapTransactionDbModelToResponse(dbModel: TransactionDbModel): CreateTransactionResponse =
        with(dbModel) {
            CreateTransactionResponse(
                id = id.toLong(),
                amount = amount.toString(),
                comment = comment,
                createdAt = createdAt,
                accountid = accountId,
                categoryId = categoryId,
                updatedAt = formatToIsoUtc(updatedAt),
            )
        }

    fun mapTransactionDbToTransaction(db: TransactionWithRelations): Transaction =
        with(db) {
            Transaction(
                id = transaction.id.toLong(),
                accountId = account.id,
                categoryId = category.id,
                name = category.name,
                emoji = category.emoji,
                amount = transaction.amount,
                currency = account.currency.convertCurrency(),
                comment = transaction.comment,
                createdAt = transaction.createdAt,
                isIncome = category.isIncome
            )
        }

    fun mapTransactionToDbModel(transaction: Transaction): TransactionDbModel =
        with(transaction) {
            TransactionDbModel(
                id = id.toInt(),
                amount = amount.toDouble(),
                comment = comment,
                createdAt = createdAt,
                accountId = accountId,
                categoryId = categoryId
            )
        }

    fun mapTransactionDtoToDbModel(dto: TransactionDto): TransactionDbModel =
        with(dto) {
            TransactionDbModel(
                id = id.toInt(),
                amount = amount.toDouble(),
                comment = comment,
                accountId = account.id,
                categoryId = category.id.toInt(),
                createdAt = createdAt,
            )
        }
}
