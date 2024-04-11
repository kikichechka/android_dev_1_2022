package com.example.photosights.presentation.fragments.viewfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.photosights.R
import com.example.photosights.databinding.FragmentViewBinding
import com.example.photosights.presentation.fragments.listphoto.ListPhotoFragment
import com.example.photosights.presentation.fragments.mapfragment.MapFragment
import com.google.android.material.tabs.TabLayoutMediator

class ViewFragment : Fragment() {
    private var _binding: FragmentViewBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var adapter: MyViewAdapter
    private lateinit var viewPager: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewPager()

        binding.buttonTakeNewPhoto.setOnClickListener {
            findNavController().navigate(R.id.action_viewFragment_to_makePhotoFragment)
        }
    }

    private fun createViewPager() {
        adapter = MyViewAdapter(this)
        viewPager = binding.containerForFragment
        viewPager.setUserInputEnabled(false)
        viewPager.adapter = adapter

        TabLayoutMediator(binding.bottomNavigation, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.photo)
                }

                1 -> {
                    tab.text = getString(R.string.map)
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private class MyViewAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
            override fun getItemCount(): Int = 2
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> ListPhotoFragment()
                    1 -> MapFragment()
                    else -> ListPhotoFragment()
                }
            }
        }
    }
}
