package com.example.photosights.presentation.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.photosights.R
import com.example.photosights.databinding.FragmentListPhotoBinding
import com.example.photosights.model.Photo
import com.example.photosights.presentation.ListPhotoAdapter
import com.example.photosights.presentation.ListPhotoViewModel
import com.example.photosights.presentation.ListPhotoViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ListPhotoFragment : Fragment() {
    private var _binding: FragmentListPhotoBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var listPhotoViewModelFactory: ListPhotoViewModelFactory
    private val viewModel: ListPhotoViewModel by viewModels { listPhotoViewModelFactory }
    private val listPhotoAdapter = ListPhotoAdapter(
        onLongClick = { uri, view -> onLongClickPhoto(uri, view) },
        onClick = { photo -> onClickPhoto(photo) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPhotoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonTakeNewPhoto.setOnClickListener {
            findNavController().navigate(R.id.action_listPhotoFragment_to_photoFragment)
        }

        binding.scrollView.adapter = listPhotoAdapter

        viewModel.listPhotos.onEach {
            listPhotoAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onLongClickPhoto(uri: String, view: View) {
        val menu = PopupMenu(requireContext(), view)
        requireActivity().menuInflater.inflate(R.menu.menu_item_photo, menu.menu)
        menu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.delete -> {
                    viewModel.deletePhoto(uri)
                }
            }
            false
        }
        menu.show()
    }

    private fun onClickPhoto(item: Photo) {
        MaterialAlertDialogBuilder(requireContext())
            .setBackground(Drawable.createFromPath(item.imageUri))
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
