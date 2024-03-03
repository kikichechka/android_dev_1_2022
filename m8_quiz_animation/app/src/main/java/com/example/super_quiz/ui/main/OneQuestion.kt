package com.example.super_quiz.ui.main

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import com.example.super_quiz.R
import com.example.super_quiz.databinding.OneQuestionBinding

class OneQuestion(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    val binding: OneQuestionBinding

    init {
        val inflatedView = inflate(context, R.layout.one_question, this)
        binding = OneQuestionBinding.bind(inflatedView)
    }

    fun setMessageQuestion(message: String) {
        binding.question.text = message
    }

    fun setTextAnswer(answer: List<String>) {
        binding.oneRadioButton.text = answer.getOrNull(0)
        binding.twoRadioButton.text = answer.getOrNull(1)
        binding.threeRadioButton.text = answer.getOrNull(2)
        binding.fourRadioButton.text = answer.getOrNull(3)
    }

    fun checkAnswer(): Boolean {
        return binding.oneRadioButton.isChecked ||
                binding.twoRadioButton.isChecked ||
                binding.threeRadioButton.isChecked ||
                binding.fourRadioButton.isChecked
    }

    fun increaseAnimate(myDuration: Long) {
        animate().apply {
            duration = myDuration
            alpha(1f)
            interpolator = AccelerateDecelerateInterpolator()
        }
    }
}
