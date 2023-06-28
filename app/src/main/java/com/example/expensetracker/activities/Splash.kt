package com.example.expensetracker.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val prefCheck = getSharedPreferences("loginCheck", MODE_PRIVATE)
            val check = prefCheck.getBoolean("firstLogin", false)
            if(check){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this,Login_Activity_Launcher::class.java)
                startActivity(intent)
            }
            finish()

        },500)
    }
}