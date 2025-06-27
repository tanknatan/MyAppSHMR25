package com.natan.shamilov.shmr25.presentation.feature.history.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.entity.Income
import com.natan.shamilov.shmr25.presentation.feature.history.domain.model.HistoryData
import com.natan.shamilov.shmr25.presentation.feature.history.domain.repository.HistoryRepository
import javax.inject.Inject

class GetHistoryByPeriodUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(startDate: String, endDate: String): Result<HistoryData> {
        return try {
            // Получаем расходы и доходы параллельно
            val expensesResult = repository.getExpensesByPeriod(startDate, endDate)
            val incomesResult = repository.getIncomesByPeriod(startDate, endDate)

            // Проверяем результаты
            when {
                expensesResult is Result.Error -> expensesResult
                incomesResult is Result.Error -> incomesResult
                expensesResult is Result.Success && incomesResult is Result.Success -> {
                    Result.Success(
                        HistoryData(
                            expenses = expensesResult.data,
                            incomes = incomesResult.data,
                            totalExpenses = expensesResult.data.sumOf { it.amount },
                            totalIncomes = incomesResult.data.sumOf { it.amount }
                        )
                    )
                }
                else -> Result.Error(Exception("Unexpected result state"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
} 