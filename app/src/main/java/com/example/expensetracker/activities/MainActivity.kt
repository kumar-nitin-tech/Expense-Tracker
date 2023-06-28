package com.example.expensetracker.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.expensetracker.R
import com.example.expensetracker.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var username:String
    private lateinit var imageValue:String
    private lateinit var homeFragment: Fragment
    private lateinit var addButton:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addButton = findViewById(R.id.addButton)
        defaultFragment()
        setCurrentFragment(homeFragment)
        dialog()
    }

    private fun setCurrentFragment(fragment:Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.Fragment,fragment)
        commit()
    }

    private fun defaultFragment(){
        val pref = getSharedPreferences("login", MODE_PRIVATE)
        username = pref.getString("userName","").toString()
        imageValue = pref.getString("imageUri","").toString()

        homeFragment  = HomeFragment()
        homeFragment.arguments = Bundle().apply {
            putString("username",username)
            putString("imageUri",imageValue)
        }
    }

    private fun dialog(){
        addButton.setOnClickListener {
            showButtonDialog()
        }
    }

    private fun showButtonDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_bottom_dialog)

        val addExpense: LinearLayout = dialog.findViewById(R.id.expenseLayout)
        val addIncome: LinearLayout = dialog.findViewById(R.id.incomeLayout)

        addExpense.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, AddExpense::class.java)
            startActivity(intent)
        }
        addIncome.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, AddIncome::class.java)
            startActivity(intent)
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}