package com.example.firsthw

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.firsthw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var bus = Bus()
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        start()
    }

    private fun start() {
        checkSeats(bus.fullnessType)
        binding.buttonAdd.setOnClickListener {
            checkSeats(bus.addPassenger())
        }

        binding.buttonRemove.setOnClickListener {
            checkSeats(bus.removePassenger())
        }

        binding.buttonReset.setOnClickListener {
            bus = Bus()
            checkSeats(bus.fullnessType)
        }
    }

    @SuppressLint("ResourceAsColor", "ResourceType", "SetTextI18n")
    private fun checkSeats(fullnessType: FullnessType) {
        when (fullnessType) {
            FullnessType.FREE -> {
                changeTextInformation(
                    R.color.green,
                    "${resources.getText(R.string.all_seats_are_free)}"
                )
                binding.buttonRemove.isEnabled = false
                switchButtonReset()
            }
            FullnessType.FILLED -> {
                switchButtonRemove()
                switchButtonReset()

                val remainingSpace = (bus.maxSpace - bus.numberOfSeats).toString()
                changeTextInformation(
                    R.color.blue,
                    "${resources.getText(R.string.there_are_seats_left)}: $remainingSpace"
                )
            }
            FullnessType.CROWDED -> {
                switchButtonRemove()
                changeTextInformation(
                    R.color.red,
                    "${resources.getText(R.string.there_are_too_many_passengers)}"
                )
                binding.buttonReset.isVisible = true
            }
        }
    }

    private fun switchButtonReset() {
        if (binding.buttonReset.isVisible)
            binding.buttonReset.isVisible = false
    }

    private fun switchButtonRemove() {
        if (!binding.buttonRemove.isEnabled)
            binding.buttonRemove.isEnabled = true
    }

    private fun changeTextInformation(color: Int, text: String) {
        binding.textInformationOutput.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )
        binding.textInformationOutput.text = text
        binding.counter.text = bus.numberOfSeats.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
