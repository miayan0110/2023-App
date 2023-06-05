package com.example.ovo

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val rotateOpen: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}

    var notes = ArrayList<Note>()
    lateinit var recyclerViewGrid: RecyclerView
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var gridAdapter:MyAdapter

    private var isFabMenuOpen = false
    private var appOpenTimes = 0

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            val title = "${it.getString("title")}"
            val content = "${it.getString("content")}"

            /* add new note */
            if(requestCode == 1 && (title.isNotEmpty() || content.isNotEmpty())) {
                notes.add(Note(title, content))
                checkIfNoteIsEmpty()
                updateScreen()
            }
            /* edit note */
            else if(requestCode == 2) {
                val position = it.getInt("pos")
                notes[position].title = title
                notes[position].content = content
                checkIfNoteIsEmpty()
                updateScreen()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appOpenTimes++
        if (appOpenTimes == 1) createDialog(R.layout.help_dialog)

//        for (i in 1..10) {
//            notes.add(Note("title$i", "content$i"))
//        }

        val btnMore = findViewById<FloatingActionButton>(R.id.btn_more)
        val btnAdd = findViewById<FloatingActionButton>(R.id.btn_add)
        val btnHelp = findViewById<FloatingActionButton>(R.id.btn_help)

        /* hide items */
        supportActionBar?.hide()

        /* custom action bar actions */
        //  show menu
        findViewById<ImageButton>(R.id.btn_menu_main).setOnClickListener {
            Toast.makeText(this,"menu is clicked!",Toast.LENGTH_SHORT).show()
        }

        /* floating button implementation */
        // btn more
        btnMore.setOnClickListener{
            if (!isFabMenuOpen) {
                btnMore.startAnimation(rotateOpen)
                btnAdd.startAnimation(fromBottom)
                btnHelp.startAnimation(fromBottom)
                btnAdd.visibility = View.VISIBLE
                btnHelp.visibility = View.VISIBLE
                btnAdd.isClickable = true
                btnHelp.isClickable = true
            }
            else {
                btnMore.startAnimation(rotateClose)
                btnAdd.startAnimation(toBottom)
                btnHelp.startAnimation(toBottom)
                btnAdd.visibility = View.GONE
                btnHelp.visibility = View.GONE
                btnAdd.isClickable = false
                btnHelp.isClickable = false
            }
            isFabMenuOpen = !isFabMenuOpen
        }
        btnMore.setOnLongClickListener {
            Toast.makeText(this, "more", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener(true)
        }

        // btn new
        btnAdd.setOnClickListener{
            val intentAddNote = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intentAddNote, 1)
        }
        btnAdd.setOnLongClickListener {
            Toast.makeText(this, "add new note", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener(true)
        }

        // btn help
        btnHelp.setOnClickListener{
            createDialog(R.layout.help_dialog)
        }
        btnHelp.setOnLongClickListener {
            Toast.makeText(this, "help", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener(true)
        }

        checkIfNoteIsEmpty()
        updateScreen()
    }

    private fun checkIfNoteIsEmpty() {
        if(notes.isEmpty()) findViewById<TextView>(R.id.tv_nothing).text = "這裡什麼都沒有:D"
        else {
            findViewById<TextView>(R.id.tv_nothing).text = ""
        }
    }

    private fun updateScreen() {
        recyclerViewGrid = findViewById<RecyclerView>(R.id.rv)

        gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        recyclerViewGrid.layoutManager = gridLayoutManager

        gridAdapter = MyAdapter(notes, R.layout.adapter)
        recyclerViewGrid.adapter = gridAdapter

        gridAdapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
//                Toast.makeText(this@MainActivity, "you clicked ${notes[position].title}", Toast.LENGTH_SHORT).show()
                val b = Bundle()
                b.putString("title", notes[position].title)
                b.putString("content", notes[position].content)
                b.putInt("pos", position)
                startActivityForResult(Intent(this@MainActivity, EditNoteActivity::class.java).putExtras(b), 2)
            }

            override fun onItemLongClick(position: Int) {
                val dialog = createDialog(R.layout.alert_dialog)

                /* delete note */
                dialog.customView.findViewById<Button>(R.id.btn_yes).setOnClickListener {
                    notes.removeAt(position)
                    gridAdapter.notifyItemRemoved(position)
                    dialog.myDialog.dismiss()
                }
                /* cancel note deletion */
                dialog.customView.findViewById<Button>(R.id.btn_no).setOnClickListener {
                    dialog.myDialog.dismiss()
                }
            }
        })
    }

    fun createDialog(layout: Int) : DialogComponents {
        val builder = AlertDialog.Builder(this@MainActivity)
        val customView = LayoutInflater.from(this@MainActivity).inflate(layout, null)
        builder.setView(customView)
        val myDialog = builder.create()
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))    /* transparent background */

        myDialog.show()

        return DialogComponents(customView, myDialog)
    }
}

data class Note (
    var title: String,
    var content: String,
)

data class DialogComponents (
    var customView: View,
    var myDialog: AlertDialog
        )