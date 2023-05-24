package com.example.ovo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        var noteTitle = findViewById<EditText>(R.id.edit_note_title)
        var noteContent = findViewById<EditText>(R.id.edit_note_content)
        var position = 0    /* position of clicked note */

        /* get origin contents of note */
        intent?.extras?.let {
            noteTitle.setText(it.getString("title"))
            noteContent.setText(it.getString("content"))
            position = it.getInt("pos")
        }

        /* send edited note content back to main */
        findViewById<FloatingActionButton>(R.id.btn_edit_save).setOnClickListener {
            val b = Bundle()

            if (noteTitle.length() < 1 && noteContent.length() > 0) b.putString("title", "標題")
            else b.putString("title", noteTitle.text.toString())

            b.putString("content", noteContent.text.toString())
            b.putInt("pos", position)

            setResult(Activity.RESULT_OK, Intent().putExtras(b))
            finish()
        }
    }
}