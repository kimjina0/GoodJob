package com.example.goodjob

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.goodjob.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userID: EditText
    private lateinit var userPassword: EditText
    private lateinit var spf: SharedPreferences
    private lateinit var loginBtn: Button
    private lateinit var signUpBtn: Button
    private lateinit var loginDBHelper: UsersDBHelper
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 변수 연결
        userID = binding.activityLoginEdtID
        userPassword = binding.activityLoginEdtPW
        loginDBHelper = UsersDBHelper(this)
        spf = getSharedPreferences("user_info", MODE_PRIVATE)

        // 로그인
        loginBtn = binding.activityLoginBtnLogin
        loginBtn.setOnClickListener {
            // 입력 검사
            val id = userID.text.toString()
            val password = userPassword.text.toString()
            // isBlank() : 문자열 길이가 0 이거나, 공백(white space)으로만 이루어진 경우 true 리턴
            val isIDBlank = id.isBlank()
            val isPasswordBlank = password.isBlank()
            if (isIDBlank)
                makeToast("ID를 입력하세요.")
            else if (isPasswordBlank)
                makeToast("비밀번호를 입력하세요.")
            // 로그인
            else {
                val name = loginDBHelper.login(id, password)
                if (name == "UNKNOWN")
                    makeToast("사용자를 찾을 수 없습니다.\n입력하신 정보를 다시 한 번 확인해주세요.")
                else {
                    userID.text.clear()
                    userPassword.text.clear()
                    val intent = Intent(this, CalendarActivity::class.java)
                    val editor = spf.edit()
                    editor.putString("userName", name).apply()
                    editor.putString("userID", id).apply()
                    startActivity(intent)
                }
            }
        }

        // 회원 가입 버튼 리스너
        signUpBtn = binding.activityLoginBtnReg
        signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    // Activity 종료 시, Toast message 취소
    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }

    // Toast message 생성 함수
    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    // 화면 터치 시 키보드 내리기
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
}

