package com.shinjaehun.roomdatabase

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        val nameEdit: EditText = findViewById(R.id.etName)
        val ageEdit: EditText = findViewById(R.id.etAge)
        val saveBtn: Button = findViewById(R.id.btnSave)

        saveBtn.setOnClickListener {
            val sName = nameEdit.text.toString()
            val sAge = ageEdit.text.toString()

            insertUser(sName, sAge)

        }
    }

    private fun insertUser(name: String, age: String) {
        val user = User(null, name, age)
        var db: AppDatabase? = AppDatabase.getDatabase(applicationContext)
        db?.userDao()?.insertUser(user)
        // 상태값을 돌려준다!
        setResult(Activity.RESULT_OK)
        finish()
    }
}