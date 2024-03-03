package com.example.super_quiz.ui.main

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.super_quiz.R
import com.example.super_quiz.databinding.FragmentHelloBinding
import com.example.super_quiz.quiz.Locale
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
const val DATE_PICKER_SHOW_TAG = "date"

class HelloFragment : Fragment() {

    @SuppressLint("SimpleDateFormat")
    private val dateFormatter = SimpleDateFormat("dd.MM.yy")
    private val calendar = Calendar.getInstance()
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

        binding.buttonDatePicker.setOnClickListener {
            dateDialogBuild(it)
        }
    }

    private fun dateDialogBuild(view: View) {
        MaterialDatePicker.Builder
            .datePicker()
            .setTitleText(resources.getString(R.string.title_enter_date_birth))
            .build().apply {
                addOnPositiveButtonClickListener {
                    calendar.timeInMillis = it
                    creatureSnackBar(view)
                }
            }
            .show(parentFragmentManager, DATE_PICKER_SHOW_TAG)
    }

    private fun creatureSnackBar(view: View) {
        Snackbar.make(view, dateFormatter.format(calendar.time), Snackbar.LENGTH_LONG).let {
            it.view.setBackgroundColor(resources.getColor(R.color.transparent_pink, null))
            it.setTextColor(resources.getColor(R.color.pink, null))
            it.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
