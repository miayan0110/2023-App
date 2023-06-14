package com.example.ovo

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.properties.Delegates

class EditAccountActivity : AppCompatActivity() {
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var birth: String
    private lateinit var phone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        /* hide origin action bar */
        supportActionBar?.hide()

        val editName = findViewById<TextInputEditText>(R.id.account_name)
        val editEmail = findViewById<TextInputEditText>(R.id.account_email)
        val editMale = findViewById<RadioButton>(R.id.account_male)
        val editFemale = findViewById<RadioButton>(R.id.account_female)
        val editBirth = findViewById<TextInputEditText>(R.id.account_birthday)
        val editPhone = findViewById<TextInputEditText>(R.id.account_phone)

        /* get origin data of account */
        intent?.extras?.let {
            name = it.getString("userName")!!
            email = it.getString("userEmail")!!
            birth = it.getString("userBirth")!!
            phone = it.getString("userPhone")!!
            editName.setText(name)
            editEmail.setText(email)
            when (it.getInt("userSex")) {
                0 -> editFemale.isChecked = true
                else -> editMale.isChecked = true
            }
            editBirth.setText(birth)
            editPhone.setText(phone)
        }

        /* custom action bar actions */
        // return to prev page but don't save
        findViewById<ImageButton>(R.id.btn_back_account).setOnClickListener { finish() }
        // save and return to prev page
        findViewById<Button>(R.id.btn_account_save).setOnClickListener { returnValues(editName, editEmail, editMale, editBirth, editPhone) }
    }

    private fun returnValues(userName: TextInputEditText, userEmail: TextInputEditText, isMale: RadioButton, userBirth: TextInputEditText, userPhone: TextInputEditText) {
        val b = Bundle()

        if (userName.length() < 1) b.putString("userName", name)
        else b.putString("userName", userName.text.toString())

        if (userEmail.length() < 1) b.putString("userEmail", email)
        else b.putString("userEmail", userEmail.text.toString())

        var sex = when {
            isMale.isChecked -> 1
            else -> 0
        }
        b.putInt("userSex", sex)
        b.putString("userBirth", userBirth.text.toString())
        b.putString("userPhone", userPhone.text.toString())

        setResult(Activity.RESULT_OK, Intent().putExtras(b))
        finish()
    }
}