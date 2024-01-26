package com.example.goodjob

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodjob.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    // ViewBinding 사용
    private lateinit var binding: ActivitySignupBinding
    private lateinit var userName: EditText
    private lateinit var userID: EditText
    private lateinit var userPassword: EditText
    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var signUpDBHelper: SignUpDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 변수 연결
        userName = binding.activitySignupEtUserName
        userID = binding.activitySignupEtUserID
        userPassword = binding.activitySignupEtUserPassword
        signUpBtn = binding.activitySignupBtnSignUp
        loginBtn = binding.activitySignupBtnLogin

        signUpDBHelper = SignUpDBHelper(this)

        signUpBtn.setOnClickListener {
            val name = userName.text.toString()
            val id = userID.text.toString()
            val password = userPassword.text.toString()
            val isExistID = signUpDBHelper.checkUserID(id)
            // ID 유효성 검사
            if (isExistID) {
                Toast.makeText(this, "이미 존재하는 ID입니다.", Toast.LENGTH_SHORT).show()
            } else {
                val result = signUpDBHelper.insertData(name, id, password)
                if (result) {
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}