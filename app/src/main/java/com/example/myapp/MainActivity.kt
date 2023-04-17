package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    lateinit var ed_name: EditText
    lateinit var tv_text: TextView
    lateinit var btn_scissor: RadioButton
    lateinit var btn_stone: RadioButton
    lateinit var btn_paper: RadioButton
    lateinit var tv_name: TextView
    lateinit var tv_winner: TextView
    lateinit var tv_mmora: TextView
    lateinit var tv_cmora: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
    }

    private fun findViews() {
        ed_name = findViewById(R.id.ed_name)
        tv_text = findViewById(R.id.tv_text)
        btn_scissor = findViewById(R.id.btn_scissor)
        btn_stone = findViewById(R.id.btn_stone)
        btn_paper = findViewById(R.id.btn_paper)
        tv_name = findViewById(R.id.tv_name)
        tv_winner = findViewById(R.id.tv_winner)
        tv_mmora = findViewById(R.id.tv_mmora)
        tv_cmora = findViewById(R.id.tv_cmora)
    }

    fun mora(view: View) {
        var start = false
        if (ed_name.length() < 1) {
            tv_text.text = "尚未輸入姓名！"
            start = false
        }
        else {
            start = true
        }

        if (start) {
            var playerName = ed_name.text
            tv_text.text = "Welcome, $playerName!"

            var mmora = when {
                btn_scissor.isChecked -> "剪刀"
                btn_stone.isChecked -> "石頭"
                else -> "布"
            }
            var cmora = when((0..2).random()) {
                0 -> "剪刀"
                1 -> "石頭"
                else -> "布"
            }

            tv_name.text = tv_name.text.toString() + "\n$playerName"
            tv_mmora.text = tv_mmora.text.toString() + "\n$mmora"
            tv_cmora.text = tv_cmora.text.toString() + "\n$cmora"
            when {
                mmora == "剪刀" && cmora == "布" ||
                        mmora == "石頭" && cmora == "剪刀" ||
                        mmora == "布" && cmora == "石頭" -> {
                    tv_winner.text = tv_winner.text.toString() + "\n$playerName"
                    tv_text.text = "恭喜你獲勝了！！！"
                }
                mmora == "剪刀" && cmora == "石頭" ||
                        mmora == "石頭" && cmora == "布" ||
                        mmora == "布" && cmora == "剪刀" -> {
                    tv_winner.text = tv_winner.text.toString() + "\n電腦"
                    tv_text.text = "可惜，電腦獲勝了！"
                }
                else -> {
                    tv_winner.text = tv_winner.text.toString() + "\n平手"
                    tv_text.text = "平局，請再試一次！"
                }
            }

        }
    }
}