package com.example.goodjob

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class BadgeActivity : AppCompatActivity() {

    lateinit var dbHelper: UsersDBHelper
    lateinit var sqLiteDatabase: SQLiteDatabase

    lateinit var textView3days: TextView
    lateinit var textView1week: TextView
    lateinit var textView21days: TextView
    lateinit var textView66days: TextView
    lateinit var textView100days: TextView

    lateinit var drawer: ImageView

    lateinit var toast: Toast

    lateinit var str_userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge)

        drawer = findViewById(R.id.activity_badge_drawerImage)

        //변수 연결(배지 텍스트)
        textView3days = findViewById(R.id.activity_badge_3daysText)
        textView1week = findViewById(R.id.activity_badge_1weekText)
        textView21days = findViewById(R.id.activity_badge_21daysText)
        textView66days = findViewById(R.id.activity_badge_66daysText)
        textView100days = findViewById(R.id.activity_badge_100daysText)

        //받아온 ID를 변수로 저장 - str_userID
        val intent = intent
        str_userID = intent.getStringExtra("intent_userID").toString()

        //일기 작성 일수 불러오기
        sqLiteDatabase = dbHelper.readableDatabase
        var cursor: Cursor
        //cursor = sqLiteDatabase.rawQuery("SELECT ... (achievement)")

        //var days = (일기 작성 일수)

        //서랍(이미지뷰) 선택 시
        drawer.setOnClickListener(){
            //
        }

        //텍스트 색상 초기화
        textView3days.setTextColor(Color.LTGRAY)
        textView1week.setTextColor(Color.LTGRAY)
        textView21days.setTextColor(Color.LTGRAY)
        textView66days.setTextColor(Color.LTGRAY)
        textView100days.setTextColor(Color.LTGRAY)

        //badgeUnlock(days)
    }

    //배지 텍스트 색상 변경, 토스트 메시지 설정
    fun badgeUnlock(days: Int) {

        if(days < 3){
            toast = Toast.makeText(getApplicationContext(), "이제 시작이에요! '작심삼일' 달성까지 ${3-days}일 남았어요!",Toast.LENGTH_SHORT)
            textView3days.setText("3일 - 시작을 준비하는 시간.")
        }

        if(days >= 3){
            toast = Toast.makeText(getApplicationContext(), "'칭찬 스타터' 달성까지 ${7-days}일 남았어요.",Toast.LENGTH_SHORT)
            textView3days.setText("3일을 넘긴 당신! 멋져요! 시작할 준비가 되었군요!")
            textView3days.setTextColor(Color.WHITE)
        }

        if(days >= 7){
            toast = Toast.makeText(getApplicationContext(), "당신은 칭찬 스타터! '칭찬 주니어' 달성까지 ${21-days}일 남았어요.",Toast.LENGTH_SHORT)
            textView1week.setText("7일을 넘긴 당신! 일주일간 노력했군요!")
            textView1week.setTextColor(Color.WHITE)
        }

        if(days >= 21){
            toast = Toast.makeText(getApplicationContext(), "당신은 칭찬 주니어! '칭찬 시니어' 달성까지 ${66-days}일 남았어요.",Toast.LENGTH_SHORT)
            textView21days.setText("21일간 열심히 한 멋진 당신! 행동이 습관화가 되었어요!")
            textView21days.setTextColor(Color.WHITE)
        }

        if(days >= 66){
            toast = Toast.makeText(getApplicationContext(), "당신은 칭찬 시니어! '칭찬 마스터' 달성까지 ${100-days}일 남았어요.",Toast.LENGTH_SHORT)
            textView66days.setText("66일간 열심히 한 당신! 습관이 자리를 잡았네요.")
            textView66days.setTextColor(Color.WHITE)
        }

        if(days >= 100){
            toast = Toast.makeText(getApplicationContext(), "당신은 칭찬 마스터!!!",Toast.LENGTH_SHORT)
            textView100days.setText("100일간 노력했네요! 당신은 100일 전보다 훨씬 멋있어졌을 거예요!")
            textView100days.setTextColor(Color.WHITE)
        }

        toast.show();
    }
}