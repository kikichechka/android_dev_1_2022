package com.example.seventeenthhw.presentation.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.seventeenthhw.R
import com.example.seventeenthhw.data.model.Mars
import com.example.seventeenthhw.databinding.FragmentItemPhotoMarsBinding

class ItemPhotoMarsFragment : Fragment() {
    private var param: Mars? = null

    private var _binding: FragmentItemPhotoMarsBinding? = null
    private val binding
        get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getParcelable(KEY_PARAM, Mars::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemPhotoMarsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showItemMars()
    }

    @SuppressLint("SetTextI18n")
    private fun showItemMars() {
        if (param != null) {
            Glide
                .with(requireContext())
                .load(param?.imgSrc)
                .into(binding.image)

            with(binding) {
                rover.text = "${resources.getString(R.string.rover)}: ${param?.rover?.name}"
                camera.text = "${resources.getString(R.string.camera)}: ${param?.camera?.name}"
                sol.text = "${resources.getString(R.string.sol)}: ${param?.sol}"
                date.text = "${resources.getString(R.string.date)}: ${param?.earthDate}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_PARAM = "KEY_PARAM"
    }
}
