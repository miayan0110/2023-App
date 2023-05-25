package com.example.ovo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        /* hide origin action bar */
        supportActionBar?.hide()

        /* custom action bar actions */
        // save and return to prev page
        findViewById<ImageButton>(R.id.btn_back_add).setOnClickListener { returnValues() }

        /* send note content back to main */
        findViewById<FloatingActionButton>(R.id.btn_save).setOnClickListener { returnValues() }
    }

    private fun returnValues() {
        val noteTitle = findViewById<EditText>(R.id.note_title)
        val noteContent = findViewById<EditText>(R.id.note_content)

        val b = Bundle()

        if (noteTitle.length()<1 && noteContent.length()>0) b.putString("title", "未命名")
        else b.putString("title", noteTitle.text.toString())

        b.putString("content", noteContent.text.toString())

        setResult(Activity.RESULT_OK, Intent().putExtras(b))
        finish()
    }
}