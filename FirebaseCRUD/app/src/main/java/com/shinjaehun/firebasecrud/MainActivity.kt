package com.shinjaehun.firebasecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.shinjaehun.firebasecrud.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = UserDao()

        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString()

            val user = User("", name, age)
            // 여기서 id를 ""로 넘기고 getList할 때 FB의 임의 key를 id로 받아옴: 이렇게 하는게 맞는건가?
            // FB에는 계속 ""로 남아 있음...

            dao.add(user)?.addOnSuccessListener {
                Toast.makeText(this, "등록성공", Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener {
                Toast.makeText(this, "등록실패", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnList.setOnClickListener {
            val intent = Intent(this@MainActivity, UserListActivity::class.java)
            startActivity(intent)
        }
    }
}