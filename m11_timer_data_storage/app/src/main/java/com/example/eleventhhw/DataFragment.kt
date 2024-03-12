package com.example.eleventhhw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eleventhhw.databinding.FragmentDataBinding

class DataFragment : Fragment() {

    private val repository = Repository()

    private var _binding: FragmentDataBinding? = null
    private val binding:FragmentDataBinding
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

        displayText()

        binding.buttonSave.setOnClickListener {
            repository.saveText(binding.editText.text.toString())
            clearEditText()
            displayText()
        }

        binding.buttonClear.setOnClickListener {
            repository.clearText()
            clearEditText()
            displayText()
        }
    }

    private fun displayText() {
        binding.textView.text = repository.getText(requireContext())
    }

    private fun clearEditText() {
        binding.editText.let {
            it.text?.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
