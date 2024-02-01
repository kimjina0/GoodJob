package com.example.goodjob

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UsersDBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Users.db"
        private const val TABLE_NAME = "Users"
        private const val COLUMN_ID = "column_id"
        private const val COLUMN_USER_NAME = "user_name"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_USER_PASSWORD = "user_password"
        private const val COLUMN_START_DATE = "start_date"
    }
    override fun onCreate(signUpDB: SQLiteDatabase?) {
        val sqlCreateTable = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USER_NAME TEXT NOT NULL," +
                "$COLUMN_USER_ID TEXT NOT NULL," +
                "$COLUMN_USER_PASSWORD TEXT NOT NULL," +
                "$COLUMN_START_DATE TEXT NOT NULL);")
        signUpDB?.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(signUpDB: SQLiteDatabase?, p1: Int, p2: Int) {
        val sqlDropTable = ("DROP TABLE IF EXISTS $TABLE_NAME")
        signUpDB!!.execSQL(sqlDropTable)
    }

    // Users Table 에 데이터 삽입
    fun insertData(userName: String, userID: String, userPassword: String): Boolean {
        val signUpDB = this.writableDatabase
        val date = Date() // 가입 날짜 저장 하기 위한 변수
        val values = ContentValues().apply {
            put(COLUMN_USER_NAME, userName)
            put(COLUMN_USER_ID, userID)
            put(COLUMN_USER_PASSWORD, userPassword)
            put(COLUMN_START_DATE, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date))
        }
        val result = signUpDB?.insert(TABLE_NAME, null, values)
        signUpDB.close()
        // insert() : 오류 발생 시 -1L 리턴
        return result != -1L
    }

    // 사용자 ID 중복 검사 함수
    fun checkUserID(userID: String): Boolean {
        val signUpDB = this.readableDatabase
        val cursor = signUpDB.query(
            TABLE_NAME, // 테이블
            arrayOf(COLUMN_USER_ID), // 리턴 받고자 하는 칼럼 값의 배열
            "$COLUMN_USER_ID = ?", // WHERE 조건
            arrayOf(userID), // WHERE 조건에 해당 하는 값의 배열
            null, // GROUP BY 조건
            null, // HAVING BY 조건
            null // ORDER BY 조건
        )
        val cursorCount = cursor.count
        cursor.close()
        signUpDB.close()
        // 반환된 cursor 값이 존재 하면 아이디 중복(true), 존재 하지 않으면 아이디 생성 가능(false)
        return cursorCount > 0
    }

    // 로그인 메소드
    fun login(userID: String, userPassword: String) : String {
        val loginDB = this.readableDatabase
        val cursor = loginDB.query(
            TABLE_NAME,
            arrayOf(COLUMN_USER_NAME),
            "$COLUMN_USER_ID = ? AND $COLUMN_USER_PASSWORD = ?",
            arrayOf(userID, userPassword),
            null,
            null,
            null
        )
        return if (cursor.count == 0) {
            cursor.close()
            loginDB.close()
            "UNKNOWN"
        } else {
            cursor.moveToFirst()
            val userName = cursor.getString(0)
            cursor.close()
            loginDB.close()
            userName
        }
    }
}