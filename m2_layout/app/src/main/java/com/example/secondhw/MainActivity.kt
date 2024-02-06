package com.example.secondhw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.secondhw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.customView.setMessageTopText(resources.getString(R.string.top_text))
        binding.customView.setMessageBottomText(resources.getString(R.string.bottom_text))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}