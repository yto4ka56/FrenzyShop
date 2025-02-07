package com.example.frenzyshop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.frenzyshop.SplashActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, AuthorizationActivity::class.java))
            finish()
        }
        else {
            val intent = Intent(this@MainActivity, scroll_activity :: class.java)
            startActivity(intent)
            finish()
        }
    }

}