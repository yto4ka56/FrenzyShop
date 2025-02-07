package com.example.frenzyshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.example.frenzyshop.databinding.ActivityAuthorizationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding

    private lateinit var inAnimation: Animation
    private lateinit var outAnimation: Animation

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthorizationBinding.inflate(getLayoutInflater())
        setContentView(binding.root)
        inAnimation = AnimationUtils.loadAnimation(this, R.anim.alfa_in)
        outAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_out)
        val textView: TextView = findViewById(R.id.text_registration)
        textView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Код Сани -> кнопка войти обработчик //
        binding.signIn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (binding.signInEmail.getText().toString().isEmpty() || binding.signInPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Поля входа не могут быть пустыми!", Toast.LENGTH_SHORT).show()
                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.signInEmail.getText().toString()
                        , binding.signInPassword.getText().toString())
                        .addOnCompleteListener(object: OnCompleteListener<AuthResult> {
                            override fun onComplete(p0: Task<AuthResult?>) {
                                if (p0.isSuccessful()) {
                                    Log.d("AuthorizationActivity", "Вход успешен") // добавлено логирование
                                    val intent = Intent(this@AuthorizationActivity, scroll_activity :: class.java)
                                    startActivity(intent)
                                    finish() // добавлено завершение активности после входа
                                } else {
                                    val errorMessage = p0.exception?.message ?: "Ошибка при входе!" // добавлено обработка ошибок
                                    Log.e("AuthorizationActivity", errorMessage) // добавлено логирование
                                    Toast.makeText(this@AuthorizationActivity, errorMessage, Toast.LENGTH_LONG).show() // добавлено вывод сообщения об ошибке
                                }
                            }
                        })
                }
            }
        })

        /*  val passwordText: EditText = findViewById(R.id.sign_in_password)
          val errorText: TextView = findViewById(R.id.password_error_text)
          var isShowed = false
          passwordText.doOnTextChanged { text, _, _, _ ->
              val length = text?.length ?: 0
              if ((length < 8 && length != 0)) {
                  errorText.startAnimation(inAnimation)
                  errorText.visibility = TextView.VISIBLE
                  //errorText.visibility = TextView.VISIBLE

              } else {
                 //errorText.visibility = TextView.GONE
                //  isShowed = false
              }
          }*/
    }
}
