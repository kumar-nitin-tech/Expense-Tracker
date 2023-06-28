package com.example.expensetracker.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Expense::class], exportSchema = false, version = 1)
abstract class ExpenseDatabase :RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object{

        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "ExpenseDB"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}