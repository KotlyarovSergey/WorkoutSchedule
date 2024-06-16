package com.ksv.workoutschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ksv.workoutschedule.R
import com.ksv.workoutschedule.databinding.FragmentHistoryBinding
import com.ksv.workoutschedule.util.Repository

class HistoryFragment : Fragment() {
//    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater)

        val historyList = loadHistoryList()
        showHistoryList(historyList)

        return binding.root
//        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadHistoryList(): List<String> {
        val repo = Repository(requireContext())
        return repo.loadHistory()
    }

    private fun showHistoryList(list: List<String>)
    {
        for (line in list) {
            binding.historyEdittext.append(line)
            binding.historyEdittext.append("\n")
        }
    }

}