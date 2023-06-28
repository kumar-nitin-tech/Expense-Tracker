package com.example.expensetracker.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.activities.AddExpense
import com.example.expensetracker.activities.AddIncome
import com.example.expensetracker.adapter.ButtonClicked
import com.example.expensetracker.adapter.DailyTransactionAdapter
import com.example.expensetracker.adapter.ItemClicked
import com.example.expensetracker.localdatabase.Expense
import com.example.expensetracker.viewmodel.ExpenseViewModel

class HomeFragment:Fragment(), ButtonClicked, ItemClicked {
    private lateinit var username:String
    private lateinit var imageUri:String
    private lateinit var userPhoto: ImageView
    private  var changeImage: Uri? = null
    private lateinit var balanceAmt: TextView
    private lateinit var totalExpense: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ExpenseViewModel

    private val contract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){
            changeImage= it.data?.data
            userPhoto.setImageURI(changeImage)
            activity?.contentResolver?.takePersistableUriPermission(changeImage!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val changePref = activity?.getSharedPreferences("login", Context.MODE_PRIVATE)?.edit()
            changePref?.putString("imageUri",changeImage.toString())
            changePref?.apply()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = arguments?.getString("username").toString()
        imageUri = arguments?.getString("imageUri").toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_home, container, false)
        val textView = rootView.findViewById<TextView>(R.id.userName)
        userPhoto = rootView.findViewById(R.id.userPhoto)
        balanceAmt = rootView.findViewById(R.id.balanceAmt)
        totalExpense = rootView.findViewById(R.id.expense)

        textView.text = username
        if(imageUri.isNotEmpty()){
            userPhoto.setImageURI(imageUri.toUri())
        }
        userPhoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            contract.launch(intent)
        }

        recyclerView = rootView.findViewById(R.id.dailyTransaction)
        viewModel = ViewModelProvider(
            requireActivity(),ViewModelProvider.AndroidViewModelFactory.getInstance(
                requireActivity().application))[ExpenseViewModel::class.java]
        recyclerFunction()
        updateDashboard()
        return rootView
    }

    private fun updateDashboard(){
        viewModel.dailyTransaction.observe(requireActivity()){ list ->
            val totalAmount = list.filter { it.amount>0 }.sumOf { it.amount }
            val expense = list.filter { it.amount<=0.00 }.sumOf { it.amount }
            balanceAmt.text = "₹ $totalAmount"
            totalExpense.text = "₹ ${-1*expense}"
        }
    }

    private fun recyclerFunction(){
        recyclerView.layoutManager = LinearLayoutManager(context)
        val recyclerViewAdapter = DailyTransactionAdapter(this,this)
        recyclerView.adapter = recyclerViewAdapter

        viewModel.dailyTransaction.observe(requireActivity()) { list ->
            list.let {
                recyclerViewAdapter.setExpense(it)
            }
        }
    }
    override fun onClicked(expense: Expense) {
        viewModel.delete(expense)
    }

    override fun onItemClicked(expense: Expense) {
        val edit = "Edit"
        if(expense.amount > 0){
            val intent = Intent(requireActivity(),AddIncome::class.java)
            intent.putExtra("EditType",edit)
            intent.putExtra("IncomeAmount",expense.amount.toString())
            intent.putExtra("IncomeCategory",expense.category)
            intent .putExtra("IncomeId",expense.id)
            startActivity(intent)
        }
        else{
            val intent = Intent(requireActivity(),AddExpense::class.java)
                .putExtra("ExpenseType",edit)
                .putExtra("ExpenseAmount",expense.amount.toString())
                .putExtra("ExpenseCategory",expense.category)
                .putExtra("ExpenseTimeStamp",expense.timeStamp)
                .putExtra("ExpenseId",expense.id)
            startActivity(intent)
        }
    }
}