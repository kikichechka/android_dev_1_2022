package com.example.tenthhw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tenthhw.databinding.FragmentTimerBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class TimerFragment : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val viewModel: TimerViewModel by viewModels()
    private val binding: FragmentTimerBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeCount()
        subscribeProgress()
        subscribeState()
        sliderListener()
        buttonClick()
    }

    private fun sliderListener() {
        binding.slider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                viewModel.changeCount(value.toInt())
            }
        }
    }

    private fun buttonClick() {
        binding.buttonStart.setOnClickListener {
            if (binding.slider.value > 0) {
                viewModel.click()
            } else {
                Snackbar.make(
                    binding.buttonStart,
                    resources.getString(R.string.install_timer),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun subscribeCount() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.count.collect {
                binding.counterTimer.text = it.toString()
            }
        }
    }

    private fun subscribeProgress() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.progress.collect {
                binding.progressBar.progress = it
            }
        }
    }

    private fun subscribeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    State.STARTED -> {
                        binding.buttonStart.isCheckable = true
                        binding.buttonStart.text = resources.getString(R.string.start)
                    }

                    State.STOPPED -> {
                        binding.buttonStart.isClickable = true
                        binding.buttonStart.text = resources.getString(R.string.stop)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
