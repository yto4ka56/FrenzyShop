package com.example.frenzyshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.frenzyshop.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity(), WorkingWithData {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Кнопка для возвращения на страницу авторизации
        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
        }

        // Обработка нажатия кнопки "Создать аккаунт"
        binding.regCreateAccountButton.setOnClickListener {
            val login = binding.regName.text.toString().trim()
            val email = binding.regEmail.text.toString().trim()
            val password = binding.regPassword.text.toString().trim()
            val passwordRepeat = binding.regPasswordRepeat.text.toString().trim()

            if (password != passwordRepeat) {
                Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (login.isEmpty() || email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены! Пожалуйста, введите все данные!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // path -> default - 1 google - 2 vk - 3
            // code -> 1 - user 2 - manager/admin 3 - admin/creator
            // acces -> 0 - ban naher 1 - welcome

            val salt = generateRandomSalt()
            val hash = generateHash(password, salt)

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = FirebaseAuth.getInstance().currentUser
                        if (firebaseUser != null) {
                            val userInfo = hashMapOf(
                                "uid" to firebaseUser.uid,
                                "email" to email,
                                "username" to login,
                                "code" to 1,
                                "path" to 1,
                                "access" to 1
                            )
                            FirebaseDatabase.getInstance("https://frenzyshop-61859-default-rtdb.europe-west1.firebasedatabase.app")
                                .getReference("Users")
                                .child(firebaseUser.uid)
                                .setValue(userInfo)
                                .addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        Log.d("RegistrationActivity", "Регистрация успешна")
                                        Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this@RegistrationActivity, scroll_activity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        val dbErrorMessage = dbTask.exception?.message ?: "Ошибка при добавлении в базу данных!"
                                        Log.e("RegistrationActivity", dbErrorMessage)
                                        Toast.makeText(this, dbErrorMessage, Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                    } else {
                        val errorMessage = task.exception?.message ?: "Ошибка при регистрации!"
                        Log.e("RegistrationActivity", errorMessage)
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
