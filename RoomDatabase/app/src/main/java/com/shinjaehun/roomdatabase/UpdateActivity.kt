package com.shinjaehun.roomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        var upNameEdit: EditText = findViewById(R.id.etName)
        var upAgeEdit: EditText = findViewById(R.id.etAge)
        val updateBtn: Button = findViewById(R.id.btnEdit)

        var uId: Int = intent.getIntExtra("uId", 0)
        var uName: String? = intent.getStringExtra("uName")
        var uAge: String? = intent.getStringExtra("uAge")

        upNameEdit.setText(uName)
        upAgeEdit.setText(uAge)

        updateBtn.setOnClickListener {
            var iName = upNameEdit.text.toString()
            var iAge = upAgeEdit.text.toString()

            var user : User = User(uId, iName, iAge)

            var db: AppDatabase? = AppDatabase.getDatabase(applicationContext)
            db?.userDao()?.updateUser(user)

            var intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}