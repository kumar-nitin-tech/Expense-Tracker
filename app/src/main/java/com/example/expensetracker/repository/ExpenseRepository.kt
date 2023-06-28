package com.example.expensetracker.repository

import androidx.lifecycle.LiveData
import com.example.expensetracker.localdatabase.Expense
import com.example.expensetracker.localdatabase.ExpenseDao

class ExpenseRepository(private val expenseDao:ExpenseDao) {

    val dailyTransaction: LiveData<List<Expense>> = expenseDao.getAllTransaction()

    suspend fun insert(expense: Expense){
        expenseDao.insertExpense(expense)
    }
    suspend fun update(expense: Expense){
        expenseDao.updateExpense(expense)
    }
    suspend fun delete(expense: Expense){
        expenseDao.deleteExpense(expense)
    }
}