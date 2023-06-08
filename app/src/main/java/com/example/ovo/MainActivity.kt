package com.example.ovo

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {
    private val rotateOpen: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}

    var notes = ArrayList<Note>()
    lateinit var recyclerViewGrid: RecyclerView
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var gridAdapter:MyAdapter

    lateinit var toggle: ActionBarDrawerToggle

    private var isFabMenuOpen = false
    private var appOpenTimes = 0
    private var isLogin = false

    private var userName = "User Name"
    private var userEmail = "useremail@gmail.com"

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            val title = "${it.getString("title")}"
            val content = "${it.getString("content")}"
            userName = "${it.getString("userName")}"
            userEmail = "${it.getString("userEmail")}"

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
            else if (requestCode == 3) {
                findViewById<TextView>(R.id.nav_user_name).text = userName
                findViewById<TextView>(R.id.nav_user_email).text = userEmail
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
        supportActionBar?.setDisplayShowTitleEnabled(false) // hide project title

        /* navigation drawer */
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_my_account -> {
                    /* if not login, login */
                    if (!isLogin) {
                        val loginD = createDialog(R.layout.login_dialog)
                        val nameField = loginD.customView.findViewById<TextInputLayout>(R.id.login_edit_name)
                        val emailField = loginD.customView.findViewById<TextInputLayout>(R.id.login_edit_email)
                        val passwordField = loginD.customView.findViewById<TextInputLayout>(R.id.login_edit_password)
                        val loginName = loginD.customView.findViewById<TextInputEditText>(R.id.login_name)
                        val loginEmail = loginD.customView.findViewById<TextInputEditText>(R.id.login_email)
                        val loginPassword = loginD.customView.findViewById<TextInputEditText>(R.id.login_password)

                        loginD.customView.findViewById<Button>(R.id.btn_login).setOnClickListener {
                            if (loginName.text!!.isEmpty()) nameField.error = "還沒有輸入您的大名"
                            else nameField.error = null
                            if (loginEmail.text!!.isEmpty()) emailField.error = "還沒有輸入您的email"
                            else emailField.error = null
                            if (loginPassword.text!!.length < 8) passwordField.error = "請輸入至少8位數的密碼"
                            else passwordField.error = null

                            if (loginName.text!!.isNotEmpty() && loginEmail.text!!.isNotEmpty() && loginPassword.text!!.length >= 8) {
                                userName = loginName.text.toString()
                                userEmail = loginEmail.text.toString()
                                loginD.myDialog.dismiss()

                                findViewById<TextView>(R.id.nav_user_name).text = userName
                                findViewById<TextView>(R.id.nav_user_email).text = userEmail
                                isLogin = true
                                Toast.makeText(this@MainActivity, "歡迎回來ovo", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }/* else edit account detail */
                    else {
                        val intentEditAccount = Intent(this, EditAccountActivity::class.java)
                        val b = Bundle()
                        b.putString("userName", userName)
                        b.putString("userEmail", userEmail)
                        startActivityForResult(intentEditAccount.putExtras(b), 3)
                    }
                }
                R.id.nav_setting -> Toast.makeText(this@MainActivity, "setting", Toast.LENGTH_SHORT).show()
            }
            true
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
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