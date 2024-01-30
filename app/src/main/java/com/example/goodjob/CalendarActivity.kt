package com.example.goodjob

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.goodjob.databinding.ActivityCalendarBinding

class CalendarActivity: AppCompatActivity() {

    lateinit var dbHelper: UsersDBHelper
    lateinit var sqLiteDatabase: SQLiteDatabase
    lateinit var drawer: ImageView
    lateinit var calendarView: CalendarView
    lateinit var binding: ActivityCalendarBinding

    lateinit var str_userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawer = findViewById(R.id.activity_calendar_drawerImage)
        calendarView = findViewById(R.id.activity_calendar_calendarView)

        //받아온 ID를 변수로 저장 - str_userID
        val intent = intent
        str_userID = intent.getStringExtra("intent_userID").toString()
        // 받아온 사용자 이름 출력
        val userName = intent.getStringExtra("userName")
        Toast.makeText(this, userName + "님 환영합니다!", Toast.LENGTH_SHORT).show()

        sqLiteDatabase = dbHelper.readableDatabase

        var cursor: Cursor
//        cursor = sqLiteDatabase.rawQuery("SELECT ... (일기 데이터 부분)")

//        다이어리 작성 유무에 따라 캘린더에 시각화.(기분 아이콘)
//            다이어리 DB, 날짜 확인
//            if (그 날짜에 작성된 일기가 있다면)...


//        서랍(이미지뷰) 선택 시
        drawer.setOnClickListener(){
            //
        }

//        날짜 선택 시, 각종 정보 전달 + 다이어리 화면으로 전환. (이를 받은 다이어리 액티비티가 각 DB에 접근.)
        calendarView.setOnDateChangeListener{ view, year, month, dayOfMonth ->

            //var intent = Intent(this, DiaryActivity::class.java)

            //전달될 정보
            //intent.putExtra("userID", userID)
            intent.putExtra("year", year)
            intent.putExtra("month", month)
            intent.putExtra("dayOfMonth", dayOfMonth)

            //화면 전환
            startActivity(intent)
        }
    }
}