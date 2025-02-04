package com.example.frenzyshop

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.example.frenzyshop.databinding.ActivityAuthorizationBinding
import org.w3c.dom.Text

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding

    private lateinit var inAnimation: Animation
    private lateinit var outAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authorization)
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

        val passwordText: EditText = findViewById(R.id.sign_in_password)
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
        }
    }
}