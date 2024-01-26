package com.example.goodjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testButton = findViewById<Button>(R.id.button)
        testButton.setOnClickListener {
            Toast.makeText(this, "Test Button", Toast.LENGTH_SHORT).show()
            // Register Activity Test
            setContentView(R.layout.activity_signup)
        }
    }
}