package com.example.secondhw

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.secondhw.databinding.CustomViewBinding

class CustomView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {
    private val binding: CustomViewBinding
    init {
        val inflatedView = inflate(context, R.layout.custom_view, this)
        binding = CustomViewBinding.bind(inflatedView)
    }

    fun setMessageTopText(text: String) {
        binding.topText.text = text
    }

    fun setMessageBottomText(text: String) {
        binding.bottomText.text = text
    }
}