package com.example.seventeenthhw.presentation.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.seventeenthhw.data.model.AstronomyPicture
import com.example.seventeenthhw.databinding.FragmentItemPhotoAstronomyPictureBinding

class ItemPhotoAstronomyPictureFragment : Fragment() {
    private var param: AstronomyPicture? = null

    private var _binding: FragmentItemPhotoAstronomyPictureBinding? = null
    private val binding
        get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            if (it != null) {
                param = it.getParcelable(KEY_PARAM_ASTRONOMY, AstronomyPicture::class.java)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemPhotoAstronomyPictureBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showItemAstronomyPicture()
    }

    private fun showItemAstronomyPicture() {
        if (param != null) {
            Glide
                .with(requireContext())
                .load(param?.url)
                .into(binding.image)

            with(binding) {
                title.text = param?.title
                date.text = param?.date
                explanation.text = param?.explanation
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_PARAM_ASTRONOMY = "KEY_PARAM"
    }
}
