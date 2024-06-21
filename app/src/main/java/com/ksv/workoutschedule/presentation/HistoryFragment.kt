package com.ksv.workoutschedule.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ksv.workoutschedule.data.HistoryRepository
import com.ksv.workoutschedule.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
//    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyList = loadHistoryList()
        showHistoryList(historyList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadHistoryList(): List<String> {
        val repo = HistoryRepository(requireContext())
        return repo.loadHistory()
    }

    private fun showHistoryList(list: List<String>)
    {
        for (line in list) {
            binding.historyEdittext.append(line)
            binding.historyEdittext.append("\n")
        }
    }

    private fun historyItemTo(){}

}