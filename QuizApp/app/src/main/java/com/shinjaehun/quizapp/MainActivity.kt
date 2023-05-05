package com.shinjaehun.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.shinjaehun.quizapp.databinding.ActivityMainBinding
import kotlinx.coroutines.selects.select

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var currentPosition: Int = 1
    private var selectedOption: Int = 0
    private var score: Int = 0

    private lateinit var questionList: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionList = QuestionData.getQuestion()

        getQuestionData()

        // 중복을 피하기 위해서 View.OnClickListener를 상속받고, 야래 구현
        binding.tvOption1.setOnClickListener(this)
        binding.tvOption2.setOnClickListener(this)
        binding.tvOption3.setOnClickListener(this)
        binding.tvOption4.setOnClickListener(this)

        binding.btnSubmit.setOnClickListener {
            // 정답 체크
            if (selectedOption != 0) {
                val question = questionList[currentPosition-1]

                // 오답 먼저 처리
                if (selectedOption != question.correct_answer) {
                    setColor(selectedOption, R.drawable.wrong_option_background)
                    callDialog("오답", "정답은 ${question.correct_answer}")
                } else {
                    score++
                }
                // 정답이든 오답이든 정답 위치에 correct option 처리
                setColor(question.correct_answer, R.drawable.correct_option_background)

                // 퀴즈가 모두 끝났을 때
                if (currentPosition == questionList.size) {
                    binding.btnSubmit.text = getString(R.string.submit, "끝")
                } else {
                    binding.btnSubmit.text = getString(R.string.submit, "다음")
                }
            } else {
                // 정답 선택 없이 그냥 다음 버튼을 클릭하면
                currentPosition++

                when {
                    currentPosition <= questionList.size -> {
                        // 다음 문제 세팅
                        getQuestionData()
                    }

                    else -> {
                        // 결과 액티비티로
//                        Toast.makeText(this, "끝", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)
                        intent.putExtra("score", score)
                        intent.putExtra("totalSize", questionList.size)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            // 선택한 값 초기화
            selectedOption = 0
       } // setOnclickListener
    } // onCreate

    private fun callDialog(alertTitle: String, correctAnswer: String) {
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("정답: $correctAnswer")
            .setPositiveButton("OK") {
                dialogInterface, i -> dialogInterface.dismiss() // 창 닫기
            }
            .setCancelable(false)
            .show()

    }

    private fun setColor(opt: Int, color: Int) {
        when(opt) {
            1 -> binding.tvOption1.background = ContextCompat.getDrawable(this, color)
            2 -> binding.tvOption2.background = ContextCompat.getDrawable(this, color)
            3 -> binding.tvOption3.background = ContextCompat.getDrawable(this, color)
            4 -> binding.tvOption4.background = ContextCompat.getDrawable(this, color)
        }
    }

    private fun getQuestionData() {

        setOptionStyle()

        val question = questionList[currentPosition - 1]

        binding.pb.progress = currentPosition
        binding.pb.max = questionList.size

        binding.tvProgress.text = getString(R.string.count_label, currentPosition, questionList.size)
        binding.tvQuestion.text = question.question
        binding.tvOption1.text = question.option_one
        binding.tvOption2.text = question.option_two
        binding.tvOption3.text = question.option_three
        binding.tvOption4.text = question.option_four

        setSubmitBtn("제출")
    }

    private fun setSubmitBtn(name: String) {
        binding.btnSubmit.text = getString(R.string.submit, name)
    }

    private fun setOptionStyle(){
        var optionList: ArrayList<TextView> = arrayListOf()
        optionList.add(binding.tvOption1)
        optionList.add(binding.tvOption2)
        optionList.add(binding.tvOption3)
        optionList.add(binding.tvOption4)

        for(op in optionList){
            op.setTextColor(Color.parseColor("#555151"))
            op.background = ContextCompat.getDrawable(this, R.drawable.option_background)
            op.typeface = Typeface.DEFAULT
        }
    }

    private fun selectedOptionStyle(view: TextView, opt: Int){
        //초기화
        setOptionStyle()

        selectedOption = opt // 여기서 selectedOption 설정됨!
        view.setTextColor(Color.parseColor("#5F00FF"))
        view.background = ContextCompat.getDrawable(this, R.drawable.selected_option_background)
        view.typeface = Typeface.DEFAULT_BOLD
    }

    // 4개 TextView의 onClick 이벤트를 한 군데 모으기
    override fun onClick(view: View) {
        when(view.id){
            R.id.tvOption1 -> selectedOptionStyle(binding.tvOption1, 1)
            R.id.tvOption2 -> selectedOptionStyle(binding.tvOption2, 2)
            R.id.tvOption3 -> selectedOptionStyle(binding.tvOption3, 3)
            R.id.tvOption4 -> selectedOptionStyle(binding.tvOption4, 4)
        }
    }
}