package com.shinjaehun.viewmodelsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shinjaehun.viewmodelsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private val mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // version 1
        val mainViewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)

        binding.tvScore.text = mainViewModel.score.toString()

        binding.btnPlus.setOnClickListener {
            mainViewModel.scorePlus()
            binding.tvScore.text = mainViewModel.score.toString()
        }

        binding.btnMinus.setOnClickListener {
            mainViewModel.scoreMinus()
            binding.tvScore.text = mainViewModel.score.toString()
        }

        // version 2
//        val mainViewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
//
//        binding.tvScore.text = mainViewModel.score.value.toString()
//
//        binding.btnPlus.setOnClickListener {
//            mainViewModel.scorePlus()
//            binding.tvScore.text = mainViewModel.score.value.toString()
//        }
//
//        binding.btnMinus.setOnClickListener {
//            mainViewModel.scoreMinus()
//            binding.tvScore.text = mainViewModel.score.value.toString()
//        }

        // version 3 : observe 사용하기
//        val mainViewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
//        mainViewModel.score.observe(this, Observer {
//            binding.tvScore.text = it.toString()
//        })
//
//        binding.btnPlus.setOnClickListener {
//            mainViewModel.scorePlus()
//        }
//
//        binding.btnMinus.setOnClickListener {
//            mainViewModel.scoreMinus()
//        }
    }
}