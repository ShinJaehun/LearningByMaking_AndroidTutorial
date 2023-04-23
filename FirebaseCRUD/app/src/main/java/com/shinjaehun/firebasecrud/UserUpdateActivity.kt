package com.shinjaehun.firebasecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.shinjaehun.firebasecrud.databinding.ActivityUserUpdateBinding

class UserUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserUpdateBinding
    lateinit var sKey: String
    lateinit var sName: String
    lateinit var sAge: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = UserDao()

        if (intent.hasExtra("key") && intent.hasExtra("name") && intent.hasExtra("age")) {
            sKey = intent.getStringExtra("key")!!
            sName = intent.getStringExtra("name")!!
            sAge = intent.getStringExtra("age")!!

            binding.etName.setText(sName)
            binding.etAge.setText(sAge)

            binding.btnUpdate.setOnClickListener {
                val uName = binding.etName.text.toString()
                val uAge = binding.etAge.text.toString()

                val hashMap: HashMap<String, Any> = HashMap()
                hashMap["userName"] = uName
                hashMap["userAge"] = uAge

                dao.userUpdate(sKey, hashMap).addOnSuccessListener {
                    Toast.makeText(this, "수정 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UserUpdateActivity, UserListActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "수정 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}