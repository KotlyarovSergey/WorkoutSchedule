package com.ksv.workoutschedule.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ksv.workoutschedule.data.HistoryRepository
import com.ksv.workoutschedule.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var confirmType = ConfirmType.NONE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = TITLE

        binding.yesAnswerTv.setOnClickListener {
            when(confirmType){
                ConfirmType.CLEAR_HISTORY -> {
                    clearHistory()
                    binding.clearHistorySwitch.isChecked = false
                }
                ConfirmType.NONE -> TODO()
            }
            binding.confirmCard.visibility = View.GONE
            setEnable(true)
        }
        binding.noAnswerTv.setOnClickListener {
            when(confirmType){
                ConfirmType.CLEAR_HISTORY -> {
                    binding.clearHistorySwitch.isChecked = false
                }
                ConfirmType.NONE -> TODO()
            }
            binding.confirmCard.visibility = View.GONE
            setEnable(true)
        }

        binding.clearHistorySwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                confirmType = ConfirmType.CLEAR_HISTORY
                setEnable(false)
                binding.confirmCard.visibility = View.VISIBLE
            }
        }


    }

    private fun setEnable(isEnable: Boolean){
        binding.clearHistorySwitch.isClickable = isEnable
        binding.clearHistorySwitch.isEnabled = isEnable

    }

    private fun clearHistory(){
        val hr = HistoryRepository(requireContext())
        lifecycleScope.launch {
            hr.clearHistory()
        }
    }

    companion object{
        private const val TITLE = "Настройки"
        enum class ConfirmType{
            CLEAR_HISTORY, NONE
        }
    }
}