package com.example.expensetracker.activities

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import com.example.expensetracker.fragment.HomeFragment
import com.example.expensetracker.localdatabase.Expense
import com.example.expensetracker.viewmodel.ExpenseViewModel
import java.sql.Date
import java.util.Calendar

class AddExpense:AppCompatActivity() {
    private lateinit var savButton: Button
    private lateinit var amountEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var viewModel: ExpenseViewModel
    private var id = -1
    private var type:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[ExpenseViewModel::class.java]

        savButton = findViewById(R.id.saveExpenseButton)
        amountEditText = findViewById(R.id.ExpenseAmountInput)
        categoryEditText =findViewById(R.id.ExpenseCategoryInput)
        val textExpense = findViewById<TextView>(R.id.addExpenseText)

        type = intent.getStringExtra("ExpenseType")
        if(type.equals("Edit")){
            val amount = intent.getStringExtra("ExpenseAmount")
            val category = intent.getStringExtra("ExpenseCategory")
            amountEditText.setText(amount)
            categoryEditText.setText(category)
            id = intent.getIntExtra("ExpenseId",-1)
            savButton.text = "Update"
            textExpense.text = "Update Expense"
        }
        else{
            savButton.text = "Save"
            textExpense.text = "Add Expense"

        }

        savButton.setOnClickListener{
            savOrUpdateButton()
        }
    }

    private fun savOrUpdateButton(){
        val check = checkField()
        if(type.equals("Edit")) {
            if (check) {
                val sdf = SimpleDateFormat("dd MMMM yyyy | HH:mm")
                val calendar = Calendar.getInstance()
                val updatedExpense = Expense(
                    0-amountEditText.text.toString().toInt(), categoryEditText.text.toString(),
                    sdf.format(calendar.time).toString()
                )
                updatedExpense.id = id
                viewModel.update(updatedExpense)
            }
        }
        else {
            if (check) {
                val sdf = SimpleDateFormat("dd MMMM yyyy | HH:mm")
                val calendar = Calendar.getInstance()
                viewModel.insert(
                    Expense(
                        0 - amountEditText.text.toString().toInt(),
                        categoryEditText.text.toString(),
                        sdf.format(calendar.time).toString()
                    )
                )
            }
        }

            val savOrUpdate = Intent(this, MainActivity::class.java)
            startActivity(savOrUpdate)
            finish()
    }

    private fun checkField():Boolean{
        if(amountEditText.length() == 0){
            amountEditText.error = "Fill the Name"
            return false
        }
        else if(categoryEditText.length() == 0){
            categoryEditText.error = "Fill the Name"
            return false
        }
        return true
    }
}