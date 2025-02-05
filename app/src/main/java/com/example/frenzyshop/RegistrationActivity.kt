package com.example.frenzyshop

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistrationActivity : AppCompatActivity(), WorkingWithData {
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

        // path -> default - 1   google - 2   vk - 3
        // code -> 1 - user    2 - manager/admin   3 - admin/creator
        // acces -> 0 - ban naher  1 - welcome

        button.setOnClickListener {
            val login = userName.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val password = userPassword.text.toString().trim()
            val passwordRepeat = userPasswordRepeat.text.toString().trim()
            val salt = generateRandomSalt()
            val hash = generateHash(userPassword.toString().trim(), salt)
            val code = 1
            val access = 1
            val path = 1
            if (!password.equals(passwordRepeat))
                Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_LONG).show()
            else {
                if (login == "" || email == "" || password == "" || passwordRepeat == "")
                    Toast.makeText(this, "Не все поля заполнены! Пожалуйста, введите все данные!",
                        Toast.LENGTH_LONG).show()
                else {
                    val user = User(login, email, hash, salt, code, access, path)
                }
            }

        }
    }


}