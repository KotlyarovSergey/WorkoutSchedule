package com.ksv.workoutschedule.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ksv.workoutschedule.data.HistoryRepository
import com.ksv.workoutschedule.databinding.FragmentHistoryBinding
import com.ksv.workoutschedule.domain.WorkoutPlan
import com.ksv.workoutschedule.entity.HistoryItem
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoryFragment : Fragment() {
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
        binding.progress.visibility = View.INVISIBLE
        binding.cardHistory.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadHistoryList(): List<HistoryItem> {
        val repo = HistoryRepository(requireContext())
        return repo.loadHistory()
    }

    private fun showHistoryList(historyList: List<HistoryItem>) {
        if(historyList.isEmpty()){
            binding.historyEdittext.text = VOID_HISTORY_MSG
        } else {
            for (historyItem in historyList) {
                binding.historyEdittext.append(historyItemToString(historyItem))
                binding.historyEdittext.append("\n")
            }
        }
    }

    private fun historyItemToString(historyItem: HistoryItem): String {
        val date = with(historyItem.workoutDate) { LocalDate.of(year, month, day) }
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateFormatted = date.format(formatter)

        val pressPlan = WorkoutPlan.Plans.PressPlan.entries[historyItem.pressPlanNum]
        val press = pressPlan.number
        val barPlan = WorkoutPlan.Plans.BarPlan.entries[historyItem.barPlanNum]
        val bar = barPlan.type

        val duration = Duration.ofSeconds(historyItem.duration)
        val hours = duration.toHours().toString()
        val minutes = addZero((duration.toMinutes() % 60).toInt())
        val seconds = addZero((duration.seconds % 60).toInt())
        val durationStr = "$hours:$minutes:$seconds"

        return "$dateFormatted: [$press-$bar] - $durationStr"
    }

    private fun addZero(number: Int): String {
        return if (number < 10)
            "0$number"
        else
            number.toString()
    }

    companion object {
        private const val VOID_HISTORY_MSG = "Здесь пока пусто"
    }
}