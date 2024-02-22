package com.example.super_quiz.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.super_quiz.R
import com.example.super_quiz.databinding.FragmentHelloBinding

class HelloFragment : Fragment() {

    companion object {
        fun newInstance() = HelloFragment()
    }

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
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, QuestionFragment.newInstance())
                .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
