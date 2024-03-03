package com.example.super_quiz.ui.main

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.super_quiz.R
import com.example.super_quiz.databinding.FragmentResultBinding
import com.example.super_quiz.quiz.Locale

class ResultFragment : Fragment() {

    companion object {
        const val ARG_KEY_STR_RESULT_FRAGMENT = "ARG_KEY_RESULT_FRAGMENT"
        const val ARG_KEY_LOCATE_RESULT_FRAGMENT = "ARG_KEY_LOCATE_RESULT_FRAGMENT"
    }

    private val translateY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -400f, 400f)
    private val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 0f, 360f)

    private lateinit var resString: String
    private lateinit var locate: Locale
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resString = it.getString(ARG_KEY_STR_RESULT_FRAGMENT)!!
            locate = it.getParcelable(ARG_KEY_LOCATE_RESULT_FRAGMENT, Locale::class.java)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewResult.apply {
            text = resString
            animationText(this)
        }

        binding.buttonStartOver.apply {
            setOnClickListener {
                clickButton()
            }
            animationButton(this)
        }
    }

    @SuppressLint("Recycle")
    private fun animationButton(view: Button) {

        ObjectAnimator.ofPropertyValuesHolder(
            view,
            rotation,
            translateY
        ).apply {
            duration = 2000
            interpolator = AccelerateInterpolator()
            repeatCount = ObjectAnimator.RESTART
            repeatMode = ObjectAnimator.REVERSE
            start()
        }
    }

    private fun animationText(view: TextView) {
        ObjectAnimator.ofArgb(
            view, "textColor",
            Color.BLUE,
            Color.GREEN
        )
            .apply {
                duration = 4000
                interpolator = AccelerateInterpolator()
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                start()
            }
    }

    private fun clickButton() {
        val bundle = Bundle().apply {
            putParcelable(QuestionFragment.ARG_KEY_QUESTION_FRAGMENT, locate)
        }
        findNavController().navigate(R.id.action_ResultFragment_to_QuestionFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
