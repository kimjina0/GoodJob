package com.example.goodjob

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.goodjob.databinding.ActivityCalendarBinding
import com.google.android.material.navigation.NavigationView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

class CalendarActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var dbHelper: UsersDBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var calendarView: MaterialCalendarView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var userName: String
    private lateinit var userID: String
    private lateinit var spf: SharedPreferences
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 변수 연결
        drawerLayout = binding.activityCalendarDL
        navigationView = binding.activityCalendarNV
        toolbar = binding.activityCalendarTb
        calendarView = binding.activityCalendarCalendarView
        calendarView.setSelectedDate(CalendarDay.today())
        spf = getSharedPreferences("user_info", MODE_PRIVATE)

        // 네비게이션 드로어 사용 설정
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        // 네비게이션 드로어 헤더에 받아온 사용자 이름 설정
        userName = spf.getString("userName", "UNKNOWN")!!
        val header = navigationView.getHeaderView(0)
        header.findViewById<TextView>(R.id.navi_header_tvUserName).text = userName
        // 네비게이션 드로어 메뉴 선택 리스너 등록
        navigationView.setNavigationItemSelectedListener(this)
        // 받아온 사용자 이름 출력
        Toast.makeText(this, userName + "님 환영합니다!", Toast.LENGTH_SHORT).show()

        //받아온 ID를 변수로 저장
        userID = spf.getString("userID", "UNKNOWN")!!

//        sqLiteDatabase = dbHelper.readableDatabase

//        var cursor: Cursor
//        cursor = sqLiteDatabase.rawQuery("SELECT ... (일기 데이터 부분)")

//        다이어리 작성 유무에 따라 캘린더에 시각화.(기분 아이콘)
//            다이어리 DB, 날짜 확인
//            if (그 날짜에 작성된 일기가 있다면)...

//        날짜 선택 시, 각종 정보 전달 + 다이어리 화면으로 전환. (이를 받은 다이어리 액티비티가 각 DB에 접근.)
//        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
//
//            //var intent = Intent(this, DiaryActivity::class.java)
//
//            //전달될 정보
//            //intent.putExtra("userID", userID)
//            intent.putExtra("year", year)
//            intent.putExtra("month", month)
//            intent.putExtra("dayOfMonth", dayOfMonth)
//
//            //화면 전환
//            startActivity(intent)
//        }
    }

    // 네비게이션 드로어 관련 메소드
    // 네비게이션 드로어 메뉴 클릭 리스너
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navi_menu_item_calendar -> makeToast("현재 화면 입니다")

            R.id.navi_menu_item_emotion_statistics -> {
                val intent = Intent(this, EmotionStatistics::class.java)
                startActivity(intent)
            }

            R.id.navi_menu_item_badge -> {
                val intent = Intent(this, BadgeActivity::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawers() // 기능을 수행하고 네비게이션을 닫아준다.
        return true
    }

    // 툴바의 메뉴 버튼 클릭 -> 네비게이션 드로어 열어줌
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            drawerLayout.openDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)
    }

    // 뒤로 가기 버튼 클릭 처리
    override fun onBackPressed() { // 네비게이션 드로어가 열려 있으면 닫음
        drawerLayout.closeDrawers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
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
}