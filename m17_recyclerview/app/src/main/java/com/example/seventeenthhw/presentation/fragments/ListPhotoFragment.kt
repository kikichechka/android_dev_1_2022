package com.example.seventeenthhw.presentation.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.seventeenthhw.R
import com.example.seventeenthhw.data.AstronomyPicture
import com.example.seventeenthhw.data.Mars
import com.example.seventeenthhw.data.StateListPhotoFragment
import com.example.seventeenthhw.databinding.FragmentListPhotoBinding
import com.example.seventeenthhw.presentation.adapters.ListPhotoAstronomyPictureAdapter
import com.example.seventeenthhw.presentation.adapters.ListPhotoMarsAdapter
import com.example.seventeenthhw.presentation.ListPhotoViewModel
import com.example.seventeenthhw.presentation.ListPhotoViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class ListPhotoFragment : Fragment() {
    private var _binding: FragmentListPhotoBinding? = null
    private val binding: FragmentListPhotoBinding
        get() = _binding!!

    @Inject
    lateinit var listPhotoViewModelFactory: ListPhotoViewModelFactory
    private val listPhotoViewModel: ListPhotoViewModel by viewModels { listPhotoViewModelFactory }

    private val listAdapterMars = ListPhotoMarsAdapter { photoMoon -> onItemClickMars(photoMoon) }
    private val listAdapterAstronomy = ListPhotoAstronomyPictureAdapter { astronomyPicture ->
        onItemClickAstronomyPicture(astronomyPicture)
    }

    private val calendar = Calendar.getInstance()

    private lateinit var stateFragment: StateListPhotoFragment

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateFragment =
            savedInstanceState?.getParcelable(SAVE_STATE_NAME, StateListPhotoFragment::class.java)
                ?: StateListPhotoFragment.MARS
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPhotoBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseAdapter()
        installDatePhoto()
        showProgressIndicator()
        changeDatePhoto()
        clickOnButtonMenu()
    }

    private fun chooseAdapter() {
        when (stateFragment) {
            StateListPhotoFragment.MARS -> createListAdapterMars()
            StateListPhotoFragment.ASTRONOMY -> createListAdapterAstronomyPicture()
        }
    }

    private fun showProgressIndicator() {
        listPhotoViewModel.stateLoad.onEach {
            when (it) {
                true -> {
                    hideOrShowListPhotoMarsAdapter(true)
                    binding.buttonMenu.isEnabled = false
                    binding.progress.visibility = View.VISIBLE
                }

                false -> {
                    hideOrShowListPhotoMarsAdapter(false)
                    binding.buttonMenu.isEnabled = true
                    binding.progress.visibility = View.INVISIBLE
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun hideOrShowListPhotoMarsAdapter(state: Boolean) {
        when (stateFragment) {
            StateListPhotoFragment.MARS -> {
                if (state) {
                    binding.recyclerView.visibility = View.INVISIBLE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                }
            }

            StateListPhotoFragment.ASTRONOMY -> return
        }
    }

    private fun changeDatePhoto() {
        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.date -> {
                    showDatePicker()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun clickOnButtonMenu() {
        binding.buttonMenu.setOnClickListener { v: View ->
            showMenuButton(v)
        }
    }

    private fun takeListPhotoAstronomyPicture() {
        listPhotoViewModel.listPhotoAstronomyPicture.onEach {
            listAdapterAstronomy.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun takeListPhotoMars() {
        listPhotoViewModel.listPhotoMars.onEach {
            listAdapterMars.setList(it.photos)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun installDatePhoto() {
        listPhotoViewModel.datePhotos.onEach {
            binding.bottomAppBar.menu.findItem(R.id.date).title = it
            configureCalendar(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun configureCalendar(valueDate: String) {
        val dateValues: List<String> = valueDate.split("-").map { it.trim() }
        calendar.apply {
            set(Calendar.YEAR, dateValues[0].toInt())
            set(Calendar.MONTH, dateValues[1].toInt() - 1)
            set(Calendar.DAY_OF_MONTH, dateValues[2].toInt())
        }
    }

    private fun showDatePicker() {
        val dateDialog = MaterialDatePicker.Builder.datePicker()
            .setSelection(calendar.timeInMillis)
            .setTitleText(resources.getText(R.string.choose_date))
            .build()

        dateDialog.let {
            it.addOnPositiveButtonClickListener { time ->
                calendar.timeInMillis = time
                val date = calendar.time
                listPhotoViewModel.changeDate(date)
            }
            it.show(parentFragmentManager, "DatePicker")
        }
    }

    private fun createListAdapterMars() {
        binding.recyclerView.adapter = listAdapterMars
        takeListPhotoMars()
    }

    private fun createListAdapterAstronomyPicture() {
        binding.recyclerView.adapter = listAdapterAstronomy
        takeListPhotoAstronomyPicture()
    }

    private fun showMenuButton(v: View) {
        val menu = PopupMenu(requireContext(), v)
        requireActivity().menuInflater.inflate(R.menu.menu_button, menu.menu)
        menu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.photo_mars -> {
                    createListAdapterMars()
                    stateFragment = StateListPhotoFragment.MARS
                }

                R.id.photo_apod -> {
                    createListAdapterAstronomyPicture()
                    stateFragment = StateListPhotoFragment.ASTRONOMY
                }
            }
            false
        }
        menu.show()
    }

    private fun onItemClickMars(item: Mars) {
        val bundle = Bundle().apply {
            putParcelable(ItemPhotoMarsFragment.KEY_PARAM, item)
        }
        findNavController().navigate(R.id.action_ListPhotoFragment_to_ItemPhotoMarsFragment, bundle)
    }

    private fun onItemClickAstronomyPicture(item: AstronomyPicture) {
        val bundle = Bundle().apply {
            putParcelable(ItemPhotoAstronomyPictureFragment.KEY_PARAM_ASTRONOMY, item)
        }
        findNavController().navigate(
            R.id.action_ListPhotoFragment_to_ItemPhotoAstronomyPictureFragment,
            bundle
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SAVE_STATE_NAME, stateFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val SAVE_STATE_NAME = "PREFERENCE_NAME"
    }
}
