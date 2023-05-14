package com.example.hw_3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                findViewById<TextView>(R.id.info_name).text = "姓名: ${it.getString("name")}"
                findViewById<TextView>(R.id.info_sex).text = "性別: ${it.getString("sex")}"
                findViewById<TextView>(R.id.info_bmi).text = "BMI值: ${it.getString("bmi")}"
                findViewById<TextView>(R.id.info_healthCon).text = "健康狀態: ${it.getString("healthCon")}"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_toInputInfo).setOnClickListener{
            val intent2Input = Intent(this, SecActivity::class.java)
            startActivityForResult(intent2Input, 1)
        }

        findViewById<Button>(R.id.btn_toBMIInfo).setOnClickListener {
            val intent2Bmi = Intent(this, ThirdActivity::class.java)
            startActivityForResult(intent2Bmi, 2)
        }
    }
}