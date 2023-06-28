package com.example.expensetracker.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Login_Activity_Launcher  : AppCompatActivity(){
    private lateinit var editText: EditText
    private lateinit var userImage: ImageView
    private lateinit var uploadButton: FloatingActionButton
    private var imageUri : Uri? = null
    private lateinit var nextButton: Button

    private val contract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            imageUri = it.data?.data
            userImage.setImageURI(imageUri)
            contentResolver.takePersistableUriPermission(imageUri!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_launcher)
        findByViewIdFunc()
        uploadButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            contract.launch(intent)
        }

        nextButton.setOnClickListener {
            nextButtonClick()
        }

    }

    private fun nextButtonClick(){
        val isFilled = checkField()
        if(isFilled){
            val editor = getSharedPreferences("login", MODE_PRIVATE).edit()
            editor.putString("userName",editText.text.toString())
            editor.putString("imageUri", imageUri.toString())
            editor.apply()

            val prefCheck = getSharedPreferences("loginCheck", MODE_PRIVATE).edit()
            prefCheck.putBoolean("firstLogin",true)
            prefCheck.apply()

            val intent = Intent(this,AddIncome::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkField():Boolean{
        if(editText.length() == 0){
            editText.error = "Fill the Name"
            return false
        }
        return true
    }

    private fun findByViewIdFunc(){
        editText = findViewById(R.id.userLoginNameInput)
        nextButton = findViewById(R.id.nextButtonLogin)
        userImage = findViewById(R.id.userPhotoLogin)
        uploadButton = findViewById(R.id.addImageButton)
    }

}