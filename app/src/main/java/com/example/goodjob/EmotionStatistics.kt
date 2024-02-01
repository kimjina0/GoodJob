package com.example.goodjob

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.goodjob.databinding.ActivityEmotionStatisticsBinding
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EmotionStatistics : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityEmotionStatisticsBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var userName: String
    private lateinit var userID: String
    private lateinit var monthTV: TextView
    private lateinit var nextBtn: Button
    private lateinit var previousBtn: Button
    private lateinit var diaryDBHelper: DiaryDBHelper
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
        monthTV = binding.activityEmotionStatisticsTvMonth
        nextBtn = binding.activityEmotionStatisticsBtnNext
        previousBtn = binding.activityEmotionStatisticsBtnPrevious
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

        // 현재 Month 로 설정
        val monthFormat = SimpleDateFormat("M월", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        var month = monthFormat.format(calendar.time)
        monthTV.text = month

        // 다음 버튼 클릭 시, 다음 달로 이동
        nextBtn.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            month = monthFormat.format(calendar.time)
            monthTV.text = month
        }

        // 이전 버튼 클릭 시, 이전 달로 이동
        previousBtn.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            month = monthFormat.format(calendar.time)
            monthTV.text = month
        }

        // 이모지 가져 오기
        userID = spf.getString("userID", "UNKNOWN")!!
        diaryDBHelper = DiaryDBHelper(this)
        if (diaryDBHelper.getDiaryCount(userID) == 0)
            Toast.makeText(this, "작성된 일기가 없습니다.", Toast.LENGTH_SHORT).show()
        else {
            val rank = getRanking(diaryDBHelper)
            val drawableID = getDrawableID(rank[0].moodName)
            binding.activityEmotionStatisticsIvFirst.setImageResource(drawableID)
        }
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

    // 이모지 순위 반환 메소드
    private fun getRanking(diaryDBHelper: DiaryDBHelper): Array<Mood> {
        val angryCount = diaryDBHelper.getMoodCount(userID, "mood_angry")
        val basicCount = diaryDBHelper.getMoodCount(userID, "mood_basic")
        val bestCount = diaryDBHelper.getMoodCount(userID, "mood_best")
        val dejectedCount = diaryDBHelper.getMoodCount(userID, "mood_dejected")
        val depressionCount = diaryDBHelper.getMoodCount(userID, "mood_depression")
        val happyCount = diaryDBHelper.getMoodCount(userID, "mood_happy")
        val proudCount = diaryDBHelper.getMoodCount(userID, "mood_proud")
        val sadCount = diaryDBHelper.getMoodCount(userID, "mood_sad")
        val sickCount = diaryDBHelper.getMoodCount(userID, "mood_sick")

        val ranking = arrayOf(
            Mood("mood_angry", angryCount),
            Mood("mood_basic", basicCount),
            Mood("mood_best", bestCount),
            Mood("mood_dejected", dejectedCount),
            Mood("mood_depression", depressionCount),
            Mood("mood_happy", happyCount),
            Mood("mood_proud", proudCount),
            Mood("mood_sad", sadCount),
            Mood("mood_sick", sickCount),
        )
        ranking.sortByDescending { it.moodCount }
        return ranking
    }

    private fun getDrawableID(moodName: String): Int {
        var id: Int = -1
        when (moodName) {
            "mood_angry" -> id = R.drawable.emoji_angry
            "mood_basic" -> id = R.drawable.emoji_basic
            "mood_best" -> id = R.drawable.emoji_best
            "mood_dejected" -> id = R.drawable.emoji_dejected
            "mood_depression" -> id = R.drawable.emoji_depression
            "mood_happy" -> id = R.drawable.emoji_happy
            "mood_proud" -> id = R.drawable.emoji_proud
            "mood_sad" -> id =  R.drawable.emoji_sad
            "mood_sick" -> id =  R.drawable.emoji_sick
        }
        return id
    }

    data class Mood(val moodName: String, val moodCount: Int)
}