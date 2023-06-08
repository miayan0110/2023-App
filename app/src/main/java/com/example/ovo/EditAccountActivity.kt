package com.example.ovo

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import de.hdodenhof.circleimageview.CircleImageView

class EditAccountActivity : AppCompatActivity() {
    private lateinit var name: String
    private lateinit var email: String

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            if(requestCode == 11) {
                val image = data?.extras?.get("data") ?: return
                findViewById<CircleImageView>(R.id.my_photo).setImageBitmap(image as Bitmap)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        /* hide origin action bar */
        supportActionBar?.hide()

        val editName = findViewById<EditText>(R.id.account_name)
        val editEmail = findViewById<EditText>(R.id.account_email)

        /* get origin data of account */
        intent?.extras?.let {
            name = it.getString("userName")!!
            email = it.getString("userEmail")!!
            editName.setText(name)
            editEmail.setText(email)
        }

        /* custom action bar actions */
        // return to prev page but don't save
        findViewById<ImageButton>(R.id.btn_back_account).setOnClickListener { finish() }
        // save and return to prev page
        findViewById<Button>(R.id.btn_account_save).setOnClickListener { returnValues(editName, editEmail) }


        /* edit user image */
        findViewById<ImageButton>(R.id.edit_image).setOnClickListener {
            Toast.makeText(this@EditAccountActivity, "user photo", Toast.LENGTH_SHORT).show()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(intent, 11)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this@EditAccountActivity, R.string.no_camera, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun returnValues(userName: EditText, userEmail: EditText) {
        val b = Bundle()

        if (userName.length() < 1) b.putString("userName", name)
        else b.putString("userName", userName.text.toString())

        if (userEmail.length() < 1) b.putString("userEmail", email)
        else b.putString("userEmail", userEmail.text.toString())

        setResult(Activity.RESULT_OK, Intent().putExtras(b))
        finish()
    }
}