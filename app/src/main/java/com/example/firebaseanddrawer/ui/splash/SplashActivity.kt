package com.example.firebaseanddrawer.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseanddrawer.MainActivity
import com.example.firebaseanddrawer.R
import com.example.firebaseanddrawer.databinding.ActivitySplashBinding
import com.example.firebaseanddrawer.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weAre: ImageView = findViewById<ImageView>(R.id.we_are)

        Handler(Looper.getMainLooper()).postDelayed({
            weAre.setImageResource(R.drawable.psu_logo)
        }, 2000)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 4000)
    }
}