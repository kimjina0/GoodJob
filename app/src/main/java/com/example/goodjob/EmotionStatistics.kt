package com.example.goodjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class EmotionStatistics : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion_statistics)

        val userName = intent.getStringExtra("userName")
        Toast.makeText(this, userName + "님 환영합니다!", Toast.LENGTH_SHORT).show()
    }
}