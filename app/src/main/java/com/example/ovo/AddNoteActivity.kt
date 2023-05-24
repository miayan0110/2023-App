package com.example.ovo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val noteTitle = findViewById<EditText>(R.id.note_title)
        val noteContent = findViewById<EditText>(R.id.note_content)

        /* send note content back to main */
        findViewById<FloatingActionButton>(R.id.btn_save).setOnClickListener {
            val b = Bundle()

            if (noteTitle.length()<1 && noteContent.length()>0) b.putString("title", "未命名")
            else b.putString("title", noteTitle.text.toString())

            b.putString("content", noteContent.text.toString())

            setResult(Activity.RESULT_OK, Intent().putExtras(b))
            finish()
        }
    }
}