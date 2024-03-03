package com.example.super_quiz.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.super_quiz.R
import com.example.super_quiz.databinding.FragmentHelloBinding
import com.example.super_quiz.quiz.Locale

class HelloFragment : Fragment() {

    private var _binding: FragmentHelloBinding? = null
    private val binding: FragmentHelloBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelloBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonContinue.setOnClickListener {
            val locate = if (binding.radioButtonRu.isChecked) Locale.Ru else Locale.En
            val bundle = Bundle().apply {
                putParcelable(QuestionFragment.ARG_KEY_QUESTION_FRAGMENT, locate)
            }
            findNavController().navigate(R.id.action_HelloFragment_to_QuestionFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
