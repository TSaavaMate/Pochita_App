package com.example.Pochita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_bc)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            setContentView(R.layout.activity_main)
        },600)



    }
}