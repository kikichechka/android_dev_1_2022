package com.example.photosights.presentation.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.photosights.R
import com.example.photosights.presentation.StateMakePhotoFragment
import com.example.photosights.databinding.FragmentMakePhotoBinding
import com.example.photosights.presentation.MakePhotoViewModel
import com.example.photosights.presentation.MakePhotoViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class MakePhotoFragment : Fragment() {

    @Inject
    lateinit var makePhotoViewModelFactory: MakePhotoViewModelFactory
    private val viewModel: MakePhotoViewModel by viewModels { makePhotoViewModelFactory }

    private var imageCapture: ImageCapture? = null
    private lateinit var executor: Executor
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                startCamera()
            }
        }

    @SuppressLint("WeekBasedYear")
    private val name =
        SimpleDateFormat(FILE_NAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis())

    private var _binding: FragmentMakePhotoBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMakePhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executor = ContextCompat.getMainExecutor(requireContext())

        checkPermission()

        clickButtonMakeNewPhoto()
        clickButtonClearPhoto()
        clickButtonSavePhoto()

        reactToState()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, VALUE_TYPE)
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            requireContext().contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        lifecycleScope.launch {
            imageCapture.takePicture(
                outputOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Glide.with(requireActivity())
                            .load(outputFileResults.savedUri)
                            .fitCenter()
                            .into(binding.image)

                        viewModel.insertPhoto(requireContext())
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.Error), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }

    private fun checkPermission() {
        val isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            startCamera()
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            imageCapture = ImageCapture.Builder().build()

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture
            )
        }, executor)
    }

    private fun clickButtonMakeNewPhoto() {
        binding.buttonMakeNewPhoto.setOnClickListener {
            binding.image.setImageDrawable(null)
            takePhoto()
            viewModel.takePicture()
        }
    }

    private fun clickButtonClearPhoto() {
        binding.buttonClear.setOnClickListener {
            viewModel.deletePhoto(requireContext())
            viewModel.remakePhoto()
        }
    }

    private fun clickButtonSavePhoto() {
        binding.buttonCheck.setOnClickListener {
            findNavController().navigate(R.id.action_makePhotoFragment_to_listPhotoFragment)
        }
    }

    private fun reactToState() {
        viewModel.stateFragment.onEach {
            when (it) {
                StateMakePhotoFragment.MAKE_PHOTO -> {
                    showPreview()
                    hideImage()
                }

                StateMakePhotoFragment.READY_PHOTO -> {
                    showImage()
                    hidePreview()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showPreview() {
        binding.apply {
            viewFinder.visibility = View.VISIBLE
            buttonMakeNewPhoto.visibility = View.VISIBLE
        }
    }

    private fun hideImage() {
        binding.apply {
            buttonCheck.visibility = View.INVISIBLE
            buttonClear.visibility = View.INVISIBLE
            image.visibility = View.INVISIBLE
        }
    }

    private fun hidePreview() {
        binding.apply {
            viewFinder.visibility = View.INVISIBLE
            buttonMakeNewPhoto.visibility = View.INVISIBLE
        }
    }

    private fun showImage() {
        binding.apply {
            buttonCheck.visibility = View.VISIBLE
            buttonClear.visibility = View.VISIBLE
            image.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FILE_NAME_FORMAT = "dd.MM.YYYY HH:mm:ss"
        private const val VALUE_TYPE = "image/jpeg"
        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(android.Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }
}
