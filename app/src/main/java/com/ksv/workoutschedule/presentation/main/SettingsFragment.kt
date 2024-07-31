package com.ksv.workoutschedule.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ksv.workoutschedule.R
import com.ksv.workoutschedule.data.HistoryRepository
import com.ksv.workoutschedule.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

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
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.setting_fragment_title)

        clearHistoryOnClickListener()
    }

    override fun onStart() {
        super.onStart()
        binding.clearHistorySwitch.isChecked = false
    }

    private fun clearHistoryOnClickListener() {
        binding.clearHistorySwitch.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder
                .setTitle(getString(R.string.alert_dialog_clear_history_title))
                .setMessage(getString(R.string.alert_dialog_clear_history_message))
                .setPositiveButton(getString(R.string.alert_dialog_yes)) { _, _ ->
                    binding.clearHistorySwitch.isChecked = false
                    clearHistory()
                }
                .setNegativeButton(getString(R.string.alert_dialog_no)) { _, _ ->
                    binding.clearHistorySwitch.isChecked = false
                }
                .setOnCancelListener { _ ->
                    binding.clearHistorySwitch.isChecked = false
                }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun clearHistory() {
        val historyRepository = HistoryRepository(requireContext())
        lifecycleScope.launch {
            historyRepository.clearHistory()
            Toast.makeText(
                requireContext(),
                getString(R.string.clear_history_toast_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}