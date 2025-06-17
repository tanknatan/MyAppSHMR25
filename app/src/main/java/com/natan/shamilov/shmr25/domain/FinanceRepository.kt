package com.natan.shamilov.shmr25.domain


import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.entity.Income

interface FinanceRepository {

    fun getExpensesList(): List<Expense>

    fun getIncomesList(): List<Income>

    fun getAccount(): Account

    fun getCategoriesList(): List<Category>
}