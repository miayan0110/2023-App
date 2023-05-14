package com.example.hw_3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.math.pow

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)

        val inputName = findViewById<EditText>(R.id.acInfo_inputName)
        val inputHeight = findViewById<EditText>(R.id.acInfo_inputHeight)
        val inputWeight = findViewById<EditText>(R.id.acInfo_inputWeight)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnReturn = findViewById<Button>(R.id.acInfo_btn_return)

        btnReturn.setOnClickListener {
            if (inputName.length()<1 || inputHeight.length()<1 || inputWeight.length()<1) {
                Toast.makeText(this, "請輸入完整資訊", Toast.LENGTH_SHORT).show()
            }
            else {
                var bmi = inputWeight.text.toString().toDouble()/(inputHeight.text.toString().toDouble()/100).pow(2)
                var b = Bundle()
                b.putString("name", inputName.text.toString())
                b.putString("sex", radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString())
                b.putString("bmi", ((bmi*10).toInt()/10.0).toString())
                b.putString("healthCon", when {
                    bmi<18.5 -> "過輕"
                    24<=bmi && bmi<27 -> "過重"
                    27<=bmi && bmi<30 -> "輕度肥胖"
                    30<=bmi && bmi<35 -> "中度肥胖"
                    35<=bmi -> "重度肥胖"
                    else -> "正常"
                })

                setResult(Activity.RESULT_OK, Intent().putExtras(b))
                finish()
            }
        }
    }
}