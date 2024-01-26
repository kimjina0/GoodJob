package com.example.goodjob

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale

class SignUpDBHelper(context: Context?) :
    SQLiteOpenHelper(context, "SignUp.db", null, 1) {
    override fun onCreate(signUpDB: SQLiteDatabase?) {
        signUpDB!!.execSQL("CREATE TABLE " +
                "User (user_pk INTEGER PRIMARY KEY AUTOINCREMENT," +
                " user_name TEXT NOT NULL, user_ID TEXT NOT NULL," +
                " user_password TEXT NOT NULL, start_date TEXT NOT NULL);")
        Log.i("SignUpDB", "CREATE TABLE")
    }

    override fun onUpgrade(signUpDB: SQLiteDatabase?, p1: Int, p2: Int) {
        signUpDB!!.execSQL("DROP TABLE IF EXISTS User")
    }

    // User Table에 데이터 삽입
    fun insertData(userName: String, userID: String, userPassword: String): Boolean {
        val signUpDB = this.writableDatabase
        val values = ContentValues().apply {
            put("user_name", userName)
            put("user_ID", userID)
            put("user_password", userPassword)
            put("start_date", SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).toString())
        }
        val result = signUpDB.insert("User", null, values)
        signUpDB.close()
        Log.i("SignUpDB", "INSERT DATA")

        return result != -1L
    }

    // 사용자 ID 중복 검사 함수
    fun checkUserID(userID: String): Boolean {
        val signUpDB = this.readableDatabase
        val cursor = signUpDB.query(
            "User", // 테이블
            arrayOf("user_ID"), // 리턴 받고자 하는 칼럼 값의 배열
            "user_ID = $userID", // WHERE 조건
            arrayOf(userID), // WHERE 조건에 해당하는 값의 배열
            null, // GROUP BY 조건
            null, // HAVING BY 조건
            null // ORDER BY 조건
        )
        cursor.close()
        signUpDB.close()
        Log.i("SignUpDB", "CHECK DATA")
        // 반환된 cursor 값이 존재하면 아이디 중복(true), 존재하지 않으면 아이디 생성 가능(false)
        return cursor.count > 0
    }
}