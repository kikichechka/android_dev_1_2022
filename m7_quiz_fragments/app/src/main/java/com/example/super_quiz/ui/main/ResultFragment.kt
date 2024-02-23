package com.example.super_quiz.ui.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.super_quiz.R
import com.example.super_quiz.databinding.FragmentResultBinding
import com.example.super_quiz.quiz.Locale

class ResultFragment : Fragment() {

    companion object{
        const val ARG_KEY_STR_RESULT_FRAGMENT = "ARG_KEY_RESULT_FRAGMENT"
        const val ARG_KEY_LOCATE_RESULT_FRAGMENT = "ARG_KEY_LOCATE_RESULT_FRAGMENT"
    }
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

        binding.textviewResult.text = resString
        binding.buttonStartOver.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(QuestionFragment.ARG_KEY_QUESTION_FRAGMENT, locate)
            }
            findNavController().navigate(R.id.action_ResultFragment_to_QuestionFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
