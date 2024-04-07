package com.example.photosights.presentation.fragments.listphoto

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.photosights.R
import com.example.photosights.databinding.FragmentListPhotoBinding
import com.example.photosights.data.model.Photo
import com.example.photosights.presentation.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ListPhotoFragment : Fragment() {
    private var _binding: FragmentListPhotoBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ListPhotoViewModel by viewModels { viewModelFactory }
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
        val file = File(item.imageUri)
        if (file.exists()) {
            MaterialAlertDialogBuilder(requireContext())
                .setBackground(Drawable.createFromPath(item.imageUri))
                .show()
        } else{
            Toast.makeText(context, getString(R.string.notification_delete_photo), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
