package com.example.fifteenthhw.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fifteenthhw.App
import com.example.fifteenthhw.R
import com.example.fifteenthhw.ui.DataViewModel
import com.example.fifteenthhw.databinding.FragmentDataBinding
import com.example.fifteenthhw.ui.State
import kotlinx.coroutines.launch

class DataFragment : Fragment() {
    private val dataViewModel: DataViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (requireContext().applicationContext as App).db.wordDao()
                return DataViewModel(wordDao) as T
            }
        }
    }
    private var _binding: FragmentDataBinding? = null
    private val binding: FragmentDataBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeOnState()
        subscribeOnChangeData()

        binding.buttonSave.setOnClickListener {
            dataViewModel.onSave(binding.enteredText.text.toString())
        }

        binding.buttonClear.setOnClickListener {
            dataViewModel.onDelete()
        }

        binding.enteredText.doOnTextChanged { text, _, _, _ ->
            dataViewModel.changeState(text)
        }
    }

    private fun subscribeOnChangeData() {
        lifecycleScope.launch {
            dataViewModel.getAllWithLimit.collect { list ->
                binding.dataFromBase.text = list.joinToString("\n")
            }
        }
    }

    private fun subscribeOnState() {
        lifecycleScope.launch {
            dataViewModel.state.collect {
                when (it) {
                    State.ERROR -> {
                        binding.buttonSave.isEnabled = false
                        binding.outlinedTextField.isErrorEnabled = true
                        binding.outlinedTextField.error = resources.getString(R.string.error_text)
                    }

                    State.SUCCESS -> {
                        binding.buttonSave.isEnabled = true
                        binding.outlinedTextField.isErrorEnabled = false
                    }

                    State.START -> binding.buttonSave.isEnabled = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
