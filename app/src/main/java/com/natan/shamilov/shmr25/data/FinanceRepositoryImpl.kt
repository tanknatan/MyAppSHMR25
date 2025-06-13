package com.natan.shamilov.shmr25.data


import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.entity.Income
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor() : FinanceRepository {

    override fun getExpensesList(): List<Expense> {
        return listOf(
            Expense(
                id = 1,
                category = Category(
                    id = 1,
                    name = "Шоппинг",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 100000,
                createdAt = "25.05.2025"
            ),
            Expense(
                id = 2,
                category = Category(
                    id = 1,
                    name = "Шоппинг",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 100000,
                createdAt = "25.05.2025",
                comment = "Продукты",
            ),
            Expense(
                id = 3,
                category = Category(
                    id = 1,
                    name = "Шоппинг",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 100000,
                createdAt = "25.05.2025"
            ),
            Expense(
                id = 4,
                category = Category(
                    id = 1,
                    name = "Шоппинг",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 100000,
                createdAt = "25.05.2025"
            ),
            Expense(
                id = 5,
                category = Category(
                    id = 1,
                    name = "Шоппинг",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 100000,
                createdAt = "25.05.2025"
            ),
            Expense(
                id = 6,
                category = Category(
                    id = 1,
                    name = "Шоппинг",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 100000,
                createdAt = "25.05.2025",
                comment = "Техника"
            ),
            Expense(
                id = 7,
                category = Category(
                    id = 1,
                    name = "Шоппинг",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 100000,
                createdAt = "25.05.2025"
            ),
            Expense(
                id = 8,
                category = Category(
                    id = 1,
                    name = "Шоппинг",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 100000,
                createdAt = "25.05.2025"
            ),
            Expense(
                id = 9,
                category = Category(
                    id = 1,
                    name = "Шоппинг",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 100000,
                createdAt = "25.05.2025"
            ),
        )
    }

    override fun getIncomesList(): List<Income> {
        return listOf(
            Income(
                id = 1,
                category = Category(
                    id = 1,
                    name = "Зарплата",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 165000,
            ),            Income(
                id = 2,
                category = Category(
                    id = 1,
                    name = "Зарплата",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 165000,
            ),            Income(
                id = 3,
                category = Category(
                    id = 1,
                    name = "Зарплата",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 165000,
            ),            Income(
                id = 4,
                category = Category(
                    id = 1,
                    name = "Зарплата",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 165000,
            ),            Income(
                id = 5,
                category = Category(
                    id = 1,
                    name = "Зарплата",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 165000,
            ),            Income(
                id = 6,
                category = Category(
                    id = 1,
                    name = "Зарплата",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 165000,
            ),            Income(
                id = 7,
                category = Category(
                    id = 1,
                    name = "Зарплата",
                    emoji = "\uD83D\uDCB0",
                    isIncome = true
                ),
                amount = 165000,
            ),


            )
    }

    override fun getAccount(): Account {
        return Account(
            id = 1,
            name = "Основной счет",
            balance = 170000,
            currency = "₽"
        )
    }

    override fun getCategoriesList(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
            Category(
                id = 2,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
            Category(
                id = 3,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
            Category(
                id = 4,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
            Category(
                id = 5,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
            Category(
                id = 6,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
            Category(
                id = 7,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
            Category(
                id = 8,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
            Category(
                id = 9,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
            Category(
                id = 10,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
            Category(
                id = 11,
                name = "Аренда квартиры",
                emoji = "\uD83D\uDCB0",
                isIncome = true
            ),
        )
    }
}