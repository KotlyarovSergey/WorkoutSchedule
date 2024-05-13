package com.ksv.workoutschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ksv.workoutschedule.R

class HistoryFragment : Fragment() {
//    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        mainViewModel.openHistory()

        return inflater.inflate(R.layout.fragment_history, container, false)
    }


}