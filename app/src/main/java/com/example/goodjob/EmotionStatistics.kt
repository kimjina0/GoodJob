package com.example.goodjob

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.goodjob.databinding.ActivityEmotionStatisticsBinding
import com.google.android.material.navigation.NavigationView

class EmotionStatistics : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityEmotionStatisticsBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var userName: String
    private lateinit var userID: String
    private lateinit var spf: SharedPreferences
    private var toast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmotionStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 변수 연결
        drawerLayout = binding.activityEmotionStatisticsDL
        navigationView = binding.activityEmotionStatisticsNV
        toolbar = binding.activityEmotionStatisticsTb
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
    }

    // 네비게이션 드로어 메뉴 클릭 리스너
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navi_menu_item_calendar -> {
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)
            }

            R.id.navi_menu_item_emotion_statistics -> Toast.makeText(
                this,
                "현재 화면 입니다",
                Toast.LENGTH_SHORT
            ).show()

            R.id.navi_menu_item_badge -> {
                val intent = Intent(this, BadgeActivity::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawers() // 기능을 수행하고 네비게이션을 닫아준다.
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
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

    // Activity 종료 시, Toast message 취소
    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }
}