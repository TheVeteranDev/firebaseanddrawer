package com.example.firebaseanddrawer.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseanddrawer.MainActivity
import com.example.firebaseanddrawer.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton = binding.loginButton

        // Only setting color programmatically because setting it in the XML was not working
        loginButton.setBackgroundColor(Color.parseColor("#002050"))

        // Set an on click listener to get the email and password value and attempt a login
        loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            signInWithEmailAndPassword(email, password)
        }
    }

    // Login function for firebase
    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Successful login sends user to the main activity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // Display toast if authentication fails
                    Toast.makeText(this, "Invalid email and/or password", Toast.LENGTH_LONG).show()
                }
            }
    }
}