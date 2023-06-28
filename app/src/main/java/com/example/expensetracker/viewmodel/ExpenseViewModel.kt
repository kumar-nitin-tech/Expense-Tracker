package com.example.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.localdatabase.Expense
import com.example.expensetracker.localdatabase.ExpenseDatabase
import com.example.expensetracker.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ExpenseViewModel(application: Application): AndroidViewModel(application) {
    private val expenseRepository: ExpenseRepository
    val dailyTransaction: LiveData<List<Expense>>

    init {
        val dao = ExpenseDatabase.getDatabase(application).expenseDao()
        expenseRepository = ExpenseRepository(dao)
        dailyTransaction = expenseRepository.dailyTransaction
    }
    fun insert(expense: Expense) = viewModelScope.launch(Dispatchers.IO) {
        expenseRepository.insert(expense)
    }
    fun update(expense: Expense) = viewModelScope.launch(Dispatchers.IO) {
        expenseRepository.update(expense)
    }
    fun delete(expense: Expense) = viewModelScope.launch(Dispatchers.IO) {
        expenseRepository.delete(expense)
    }
}