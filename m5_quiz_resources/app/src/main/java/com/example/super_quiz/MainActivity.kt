package com.example.super_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.super_quiz.databinding.MainActivityBinding
import com.example.super_quiz.ui.main.HelloFragment

class MainActivity : AppCompatActivity() {
    private var _binding: MainActivityBinding? = null
    private val binding: MainActivityBinding
    get() {
        return _binding!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HelloFragment.newInstance())
                .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}