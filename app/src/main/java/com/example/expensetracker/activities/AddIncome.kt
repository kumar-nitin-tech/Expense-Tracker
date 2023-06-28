package com.example.expensetracker.activities

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import com.example.expensetracker.fragment.HomeFragment
import com.example.expensetracker.localdatabase.Expense
import com.example.expensetracker.viewmodel.ExpenseViewModel
import java.util.Calendar


class AddIncome: AppCompatActivity() {
    private lateinit var saveButton: Button
    private lateinit var amountEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var viewModel: ExpenseViewModel
    private var id = -1
    private var type:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[ExpenseViewModel::class.java]

        saveButton = findViewById(R.id.saveIncomeButton)
        amountEditText = findViewById(R.id.IncomeAmountInput)
        categoryEditText =findViewById(R.id.IncomeCategoryInput)
        val textIncome = findViewById<TextView>(R.id.addIncomeText)

        type = intent.getStringExtra("EditType")
        if(type.equals("Edit")){
            val amount = intent.getStringExtra("IncomeAmount")
            val category = intent.getStringExtra("IncomeCategory")
            amountEditText.setText(amount)
            categoryEditText.setText(category)
            id = intent.getIntExtra("IncomeId",-1)
            saveButton.text = "Update"
            textIncome.text = "Update Income"
        }
        else{
            saveButton.text = "Save"
            textIncome.text = "Add Income"
        }
        saveButton.setOnClickListener{
            onClickSave()
        }

    }


    private fun onClickSave(){
        val check = checkField()
        if(type.equals("Edit")){
                if (check){
                    val sdf = SimpleDateFormat("dd MMMM yyyy | HH:mm")
                    val calendar = Calendar.getInstance()
                    val updatedExpense = Expense(amountEditText.text.toString().toInt(), categoryEditText.text.toString(),
                        sdf.format(calendar.time).toString()
                    )
                    updatedExpense.id = id
                    viewModel.update(updatedExpense)
                }
            }
        else{
            if(check) {
                val sdf = SimpleDateFormat("dd MMMM yyyy | HH:mm")
                val calendar = Calendar.getInstance()
                viewModel.insert(
                    Expense(
                        amountEditText.text.toString().toInt(),
                        categoryEditText.text.toString(),
                        sdf.format(calendar.time).toString()
                    )
                )
            }
        }
        if(check) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
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