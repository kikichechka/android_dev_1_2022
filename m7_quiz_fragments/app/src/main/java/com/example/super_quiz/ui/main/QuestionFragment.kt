package com.example.super_quiz.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.super_quiz.R
import com.example.super_quiz.databinding.QuestionFragmentBinding
import com.example.super_quiz.quiz.Locale
import com.example.super_quiz.quiz.Quiz
import com.example.super_quiz.quiz.QuizStorage

class QuestionFragment : Fragment() {

    companion object {
        const val ARG_KEY_QUESTION_FRAGMENT = "ARG_KEY_QUESTION_FRAGMENT"
        private const val ONE = 0
        private const val TWO = 1
        private const val THREE = 2
        private const val FOUR = 3
    }

    private lateinit var quiz: Quiz
    private lateinit var locate: Locale
    private val listViewQuestion: MutableList<OneQuestion> = mutableListOf()
    private val listAnswer = mutableMapOf<Int, Int>()

    private var _binding: QuestionFragmentBinding? = null
    private val binding: QuestionFragmentBinding
        get() {
            return _binding!!
        }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            locate = it.getParcelable(ARG_KEY_QUESTION_FRAGMENT, Locale::class.java)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuestionFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quiz = QuizStorage.getQuiz(locate)

        fillQuestions()
        checkAllAnswer()
        clickBack()
    }

    private fun fillQuestions() {
        quiz.questions.forEachIndexed { index, q ->
            val viewOneQuestion = OneQuestion(requireContext(), null)
            binding.containerForQuestions.addView(viewOneQuestion, index)
            viewOneQuestion.let {
                it.setMessageQuestion(q.question)
                it.setTextAnswer(q.answers)
                it.binding.radioGroup.setOnCheckedChangeListener { _, button ->
                    addListAnswers(index, generateNumAnswer(button))
                    checkAllAnswer()
                }
            }
            listViewQuestion.add(viewOneQuestion)
        }
    }

    private fun generateNumAnswer(button: Int): Int {
        return when (button) {
            R.id.oneRadioButton -> ONE
            R.id.twoRadioButton -> TWO
            R.id.threeRadioButton -> THREE
            R.id.fourRadioButton -> FOUR
            else -> ONE
        }
    }

    private fun addListAnswers(id: Int, numAnswer: Int) {
        listAnswer[id] = numAnswer
    }

    private fun checkAllAnswer() {
        var count = 0
        listViewQuestion.forEach {
            if (it.checkAnswer())
                count++
        }
        if (count == 3) {
            clickSave()
        }
    }

    private fun pressButtonSave() {
        binding.buttonSave.setOnClickListener {
            val str = QuizStorage.answer(quiz, listAnswer.values.toList())
            val bundle = Bundle().apply {
                putString(ResultFragment.ARG_KEY_STR_RESULT_FRAGMENT, str)
                putParcelable(ResultFragment.ARG_KEY_LOCATE_RESULT_FRAGMENT, locate)
            }
            findNavController().navigate(R.id.action_QuestionFragment_to_ResultFragment, bundle)
        }
    }

    private fun clickBack() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_QuestionFragment_back)
        }
    }

    private fun clickSave() {
        binding.buttonSave.isEnabled = true
        pressButtonSave()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
