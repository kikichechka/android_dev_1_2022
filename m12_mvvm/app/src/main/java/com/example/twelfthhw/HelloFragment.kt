package com.example.twelfthhw

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.twelfthhw.databinding.FragmentHelloBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class HelloFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private var _binding: FragmentHelloBinding? = null
    private val binding: FragmentHelloBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelloBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textChangedListener()
        displayResultRequest()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun textChangedListener() {
        binding.enteredText.addTextChangedListener { editable ->
            if (editable == null || editable.count() < 3) {
                displayHelperText()
                blockingButtonSearch()
            } else {
                displayButtonSearch()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun displayButtonSearch() {
        binding.textInputLayout.let {
            it.endIconMode = TextInputLayout.END_ICON_CUSTOM
            it.endIconDrawable =
                resources.getDrawable(R.drawable.baseline_search_24, null)
            searchText()
            it.helperText = null
        }
    }

    private fun displayHelperText() {
        binding.textInputLayout.helperText =
            resources.getString(R.string.warning_minimum_characters)
    }

    private fun blockingButtonSearch() {
        binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
    }

    private fun searchText() {
        binding.textInputLayout.setEndIconOnClickListener {
            viewModel.searchString(binding.enteredText.text.toString())
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        binding.enteredText.clearFocus()
        hideWindowKeyboard()
    }

    private fun displayResultRequest() {
        subscribeOnChangeSearch()
        subscribeOnChangeState()
    }

    private fun subscribeOnChangeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    State.Loading -> {
                        binding.progress.isVisible = true
                        blockingButtonSearch()
                    }

                    is State.Success -> {
                        displayButtonSearch()
                        binding.progress.isVisible = false
                    }
                }
            }
        }
    }

    private fun subscribeOnChangeSearch() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchString.collect {
                binding.textView.text = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

fun Fragment.hideWindowKeyboard() = WindowCompat
    .getInsetsController(requireActivity().window, requireView())
    .hide(WindowInsetsCompat.Type.ime())
