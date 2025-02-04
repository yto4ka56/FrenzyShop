package com.example.frenzyshop

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userName: EditText = findViewById(R.id.reg_name)
        val userEmail: EditText = findViewById(R.id.reg_email)
        val userPassword: EditText = findViewById(R.id.reg_password)
        val userPasswordRepeat: EditText = findViewById(R.id.reg_password_repeat)
        val button: Button = findViewById(R.id.reg_create_account_button)
    }
}