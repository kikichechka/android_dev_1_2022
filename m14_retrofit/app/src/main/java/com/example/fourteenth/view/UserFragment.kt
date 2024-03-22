package com.example.fourteenth.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.fourteenth.databinding.FragmentUserBinding
import com.example.fourteenth.ui.State
import com.example.fourteenth.ui.UserViewModel
import com.example.fourteenth.ui.UsersModels
import kotlinx.coroutines.launch

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding: FragmentUserBinding
        get() {
            return _binding!!
        }
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                trackState(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.user.collect {
                if (it != null) {
                    userTextInfo(it)
                    imageUser(it, view)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonUpdate.setOnClickListener {
            viewModel.getUser()
        }

        if (savedInstanceState == null) {
            binding.buttonUpdate.callOnClick()
        }
    }

    private fun trackState(it: State) {
        when (it) {
            State.Loading -> binding.progress.isVisible = true
            else -> binding.progress.isVisible = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun userTextInfo(usersModels: UsersModels) {
        val gender = usersModels.results.first().gender
        val title = usersModels.results.first().name.title
        val name = usersModels.results.first().name.first
        val lastName = usersModels.results.first().name.last
        val street =
            "${usersModels.results.first().location.street.number} ${usersModels.results.first().location.street.name}"
        val city = usersModels.results.first().location.city
        val country = usersModels.results.first().location.country
        val email = usersModels.results.first().email
        val phone = usersModels.results.first().phone
        binding.textUser.text = """
                    Gender: $gender
                    Name: $title $name $lastName
                    Location: $street, $city, $country
                    Email: $email
                    Phone: $phone
                """.trimIndent()
    }

    private fun imageUser(usersModels: UsersModels, view: View) {
        val imageViewPicture = usersModels.results.first().picture.large
        Glide
            .with(view)
            .load(imageViewPicture)
            .into(binding.imageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
