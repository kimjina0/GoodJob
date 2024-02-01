package com.example.goodjob

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.goodjob.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryBinding
    private lateinit var dbHelper: DiaryDBHelper
    private lateinit var emojiDBHelper: EmojiDBHelper
    private lateinit var spf: SharedPreferences
    private lateinit var imageView: ImageView
    private lateinit var textViewDate: TextView
    private lateinit var weatherEditText: EditText
    private lateinit var editTitle: EditText
    private lateinit var editText: EditText
    private lateinit var editText2: EditText
    private lateinit var saveButton: Button
    private var moodName: String = "NULL"
    private var alertDialog: AlertDialog? = null
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 변수 연결
        imageView = binding.activityDiaryImage
        textViewDate = binding.activityDiaryTvDate
        weatherEditText = binding.activityDiaryEdtWeather
        editTitle = binding.activityDiaryEdtTitle
        editText = binding.activityDiaryEdtText
        editText2 = binding.activityDiaryEdtText2
        saveButton = binding.activityDiaryBtnSave
        spf = getSharedPreferences("user_info", MODE_PRIVATE)

        // dbHelper 초기화
        dbHelper = DiaryDBHelper(this)
        emojiDBHelper = EmojiDBHelper(this)

        imageView.setOnClickListener {
            showExpressionDialog()
        }

        // userID 가져 오기
        val userID = spf.getString("userID", "UNKNOWN")

        // Calendar Activity 로부터 사용자 가 선택한 날짜 받아옴
        val date = intent.getStringExtra("date")
        textViewDate.text = date
        // Diary 저장 버튼 클릭
        saveButton.setOnClickListener {
            // 입력된 내용 가져옴
            val dateForDiaryDB = intent.getStringExtra("dateForDiaryDB")
            val weather = weatherEditText.text.toString()
            val title = editTitle.text.toString()
            val content1 = editText.text.toString()
            val content2 = editText2.text.toString()
            // 데이터베이스에 저장
            val success = dbHelper.saveDiary(
                userID,
                dateForDiaryDB,
                weather,
                title,
                content1,
                content2,
            )

            // 데이터베이스에 저장 여부 메세지 표시
            if (success) {
                makeToast("저장되었습니다.")
            } else {
                makeToast("저장 실패")
            }

        }
        val yearForEmojiDB = intent.getStringExtra("yearForEmojiDB")
        val monthForEmojiDB = intent.getStringExtra("monthForEmojiDB")
        var isSuccess = false
        if (userID != null && yearForEmojiDB != null && monthForEmojiDB != null)
            isSuccess = emojiDBHelper.insertData(userID, moodName, yearForEmojiDB, monthForEmojiDB)
        if (isSuccess)
            makeToast("성공")
        else
            makeToast("실패")
    }

    private fun showExpressionDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.mood_dialog, null)
        builder.setView(dialogView)

        val alertDialog = builder.create()
        alertDialog.show()
    }

    // 기분 이미지 클릭 처리
    fun onExpressionClick(view: View) {
        val expressionImageView = view as ImageView
        // 선택된 기분 이미지를 가져옴
        val selectedExpression = expressionImageView.drawable

        // 선택된 기분사진 설정함
        imageView.setImageDrawable(selectedExpression)

        // 대화 상자를 닫습니다.
        alertDialog?.dismiss()

        // 마지막으로 선택된 이미지의 리소스 ID 저장
        moodName = view.resources.getResourceEntryName(view.id)
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
