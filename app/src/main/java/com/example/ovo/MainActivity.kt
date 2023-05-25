package com.example.ovo

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    var notes = ArrayList<Note>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            val title = "${it.getString("title")}"
            val content = "${it.getString("content")}"
            val position = it.getInt("pos")

            /* add new note */
            if(requestCode == 1 && (title.isNotEmpty() || content.isNotEmpty())) {
                notes.add(Note(title, content))
                updateScreen()
                showNotes()
            }
            /* edit note */
            else if(requestCode == 2) {
                notes[position].title = title
                notes[position].content = content
                updateScreen()
                showNotes()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* hide origin action bar */
        supportActionBar?.hide()

        /* custom action bar actions */
        //  show menu
        findViewById<ImageButton>(R.id.btn_menu_main).setOnClickListener {
            Toast.makeText(this,"menu is clicked!",Toast.LENGTH_SHORT).show()
        }

        /* click floating button to add new note */
        findViewById<FloatingActionButton>(R.id.btn_newNote).setOnClickListener{
            val intentAddNote = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intentAddNote, 1)
        }

        updateScreen()
        showNotes()
    }

    private fun updateScreen() {
        val gridview = findViewById<GridView>(R.id.gridview)

        /* short click note to edit */
        gridview.setOnItemClickListener { parent, view, position, id ->
//            Toast.makeText(this, "click ${notes[position].title}", Toast.LENGTH_SHORT).show()
            val intentEditNote = Intent(this, EditNoteActivity::class.java)
            val b = Bundle()
            b.putString("title", notes[position].title)
            b.putString("content", notes[position].content)
            b.putInt("pos", position)

            startActivityForResult(intentEditNote.putExtras(b), 2)
        }

        /* long click note to delete */
        gridview.setOnItemLongClickListener { parent, view, position, id ->
//            Toast.makeText(this, "long press ${notes[position].title}", Toast.LENGTH_SHORT).show()
            deleteNote(position)
            return@setOnItemLongClickListener(true)
        }
    }

    private fun showNotes() {
        val gridview = findViewById<GridView>(R.id.gridview)

        if(notes.isEmpty()) findViewById<TextView>(R.id.tv_nothing).text = "這裡什麼都沒有:D"
        else {
            findViewById<TextView>(R.id.tv_nothing).text = ""
        }
        gridview.numColumns = 2
        gridview.adapter = MyAdapter(this, notes, R.layout.adapter)
    }

    private fun deleteNote(pos:Int) {
        val builder = AlertDialog.Builder(this)
        val customView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        builder.setView(customView)
        val myDialog = builder.create()
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))    /* transparent background */

        myDialog.show()

        /* delete note */
        customView.findViewById<Button>(R.id.btn_yes).setOnClickListener {
            notes.removeAt(pos)
            showNotes()
            myDialog.dismiss()
        }
        /* cancel note deletion */
        customView.findViewById<Button>(R.id.btn_no).setOnClickListener {
            myDialog.dismiss()
        }
    }
}

data class Note (
    var title: String,
    var content: String)