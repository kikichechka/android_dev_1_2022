package com.example.twelfthhw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.twelfthhw.databinding.FragmentHelloBinding
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it is State.Loading) {
                    hideWindowKeyboard()
                }
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
