package com.natan.shamilov.shmr25.app.data.api.mapper

import com.natan.shamilov.shmr25.app.data.api.model.AccountDto
import com.natan.shamilov.shmr25.app.data.api.model.CategoryDto
import com.natan.shamilov.shmr25.app.data.api.model.TransactionDto
import com.natan.shamilov.shmr25.common.Category
import com.natan.shamilov.shmr25.feature.account.domain.entity.Account
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income
import javax.inject.Inject

/**
 * Маппер для преобразования DTO моделей в доменные сущности.
 * Ответственность: Преобразование объектов передачи данных (DTO) из слоя данных
 * в доменные модели для использования в бизнес-логике приложения.
 */
class FinanceMapper @Inject constructor() {

    /**
     * Преобразует DTO счета в доменную модель счета.
     *
     * @param dto DTO модель счета
     * @return доменная модель счета
     */
    fun mapAccountDtoToDomain(dto: AccountDto): Account = with(dto) {
        Account(
            id = id,
            name = name,
            balance = balance.toDouble(),
            currency = currency
        )
    }

    /**
     * Преобразует DTO категории в доменную модель категории.
     *
     * @param dto DTO модель категории
     * @return доменная модель категории
     */
    fun mapCategoryDtoToDomain(dto: CategoryDto): Category = with(dto) {
        Category(
            id = id,
            name = name,
            emoji = emoji,
            isIncome = isIncome
        )
    }

    /**
     * Преобразует DTO транзакции в доменную модель расхода.
     * Используется для транзакций с типом "расход".
     *
     * @param dto DTO модель транзакции
     * @return доменная модель расхода
     */
    fun mapTransactionDtoToExpense(dto: TransactionDto): Expense = with(dto) {
        Expense(
            id = id,
            category = mapCategoryDtoToDomain(category),
            amount = amount.toDouble(),
            comment = comment,
            createdAt = transactionDate
        )
    }

    /**
     * Преобразует DTO транзакции в доменную модель дохода.
     * Используется для транзакций с типом "доход".
     *
     * @param dto DTO модель транзакции
     * @return доменная модель дохода
     */
    fun mapTransactionDtoToIncome(dto: TransactionDto): Income = with(dto) {
        Income(
            id = id,
            category = mapCategoryDtoToDomain(category),
            amount = amount.toDouble(),
            comment = comment,
            createdAt = transactionDate
        )
    }
}
