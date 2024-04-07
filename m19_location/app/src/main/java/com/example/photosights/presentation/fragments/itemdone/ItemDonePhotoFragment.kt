package com.example.photosights.presentation.fragments.itemdone

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.photosights.App
import com.example.photosights.R
import com.example.photosights.databinding.FragmentItemDonePhotoBinding
import com.example.photosights.presentation.MainActivity
import com.example.photosights.presentation.ViewModelFactory
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ItemDonePhotoFragment : Fragment() {
    private var _binding: FragmentItemDonePhotoBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ItemDonePhotoViewModel by viewModels { viewModelFactory }

    private val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDonePhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData(requireContext())
        viewModel.data.onEach {
            Glide.with(requireActivity())
                .load(it.imageUri)
                .fitCenter()
                .into(binding.image)
        }.launchIn(lifecycleScope)

        clickButtonSavePhoto()
        clickButtonClearPhoto()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createNotification() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        val notification = NotificationCompat.Builder(requireContext(), App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_add_photo_alternate_24)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_saving_new_sights))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
        } else {
            launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun clickButtonClearPhoto() {
        binding.buttonClear.setOnClickListener {
            viewModel.deleteData(requireContext())
            findNavController().navigate(R.id.action_itemDonePhotoFragment_to_makePhotoFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun clickButtonSavePhoto() {
        binding.buttonCheck.setOnClickListener {
            findNavController().navigate(R.id.action_itemDonePhotoFragment_to_listPhotoFragment)
            createNotification()
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1000
    }
}
