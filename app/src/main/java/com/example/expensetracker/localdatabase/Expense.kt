package com.example.expensetracker.localdatabase

import android.icu.text.SimpleDateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Year
import java.util.Calendar

@Entity(tableName = "ExpenseTable")
data class Expense(
    @ColumnInfo(name = "Transaction") val amount: Int,
    @ColumnInfo(name = "Category") val category: String,
    @ColumnInfo(name = "TimeStamp") val timeStamp:String
){
    @PrimaryKey(autoGenerate = true) var id = 0

    var month = Calendar.getInstance().get(Calendar.MONTH)+1
    var year = Calendar.getInstance().get(Calendar.YEAR)
}