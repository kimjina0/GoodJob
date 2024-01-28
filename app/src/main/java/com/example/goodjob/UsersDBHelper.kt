package com.example.goodjob

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UsersDBHelper(context: Context?) :
    SQLiteOpenHelper(context, "Users.db", null, 1) {
    override fun onCreate(signUpDB: SQLiteDatabase?) {
        signUpDB?.execSQL("CREATE TABLE " +
                "Users (user_pk INTEGER PRIMARY KEY AUTOINCREMENT," +
                " user_name TEXT NOT NULL, user_ID TEXT NOT NULL," +
                " user_password TEXT NOT NULL, start_date TEXT NOT NULL);")
    }

    override fun onUpgrade(signUpDB: SQLiteDatabase?, p1: Int, p2: Int) {
        signUpDB!!.execSQL("DROP TABLE IF EXISTS User")
    }

    // Users Table 에 데이터 삽입
    fun insertData(userName: String, userID: String, userPassword: String): Boolean {
        val signUpDB = this.writableDatabase
        val date = Date() // 가입 날짜 저장 하기 위한 변수
        val values = ContentValues().apply {
            put("user_name", userName)
            put("user_ID", userID)
            put("user_password", userPassword)
            put("start_date", SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(date))
        }
        val result = signUpDB?.insert("Users", null, values)
        signUpDB.close()
        // insert() : 오류 발생 시 -1L 리턴
        return result != -1L
    }

    // 사용자 ID 중복 검사 함수
    fun checkUserID(userID: String): Boolean {
        val signUpDB = this.readableDatabase
        val cursor = signUpDB.query(
            "Users", // 테이블
            arrayOf("user_ID"), // 리턴 받고자 하는 칼럼 값의 배열
            "user_ID = ?", // WHERE 조건
            arrayOf(userID), // WHERE 조건에 해당 하는 값의 배열
            null, // GROUP BY 조건
            null, // HAVING BY 조건
            null // ORDER BY 조건
        )
        // 반환된 cursor 값이 존재 하면 아이디 중복(true), 존재 하지 않으면 아이디 생성 가능(false)
        return cursor.count > 0
    }

    fun login(userID: String, userPassword: String): String {
        val loginDB = this.readableDatabase
        val cursor = loginDB.query(
            "Users",
            arrayOf("user_name"),
            "user_ID = ? AND user_password = ?",
            arrayOf(userID, userPassword),
            null,
            null,
            null
        )
        return cursor.getString(0)
    }
}