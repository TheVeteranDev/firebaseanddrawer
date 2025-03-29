package com.example.firebaseanddrawer

import android.app.Application
import com.google.firebase.FirebaseApp

class FirebaseAndDrawerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase here to make it available everywhere
        FirebaseApp.initializeApp(this)
    }
}