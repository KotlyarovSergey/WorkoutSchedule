package com.ksv.workoutschedule.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ksv.workoutschedule.model.WorkoutViewModel
import com.ksv.workoutschedule.databinding.FragmentWorkoutBinding

class WorkoutFragment : Fragment() {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    private val workoutViewModel: WorkoutViewModel by viewModels()
//    private val mainViewModel: MainViewModel by viewModels(
//        ownerProducer = {Ma}
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater)
        workoutViewModel.openWorkoutFragment(requireContext())
        binding.viewModel = workoutViewModel
        binding.lifecycleOwner = viewLifecycleOwner
//        mainViewModel.openWorkoutFragment()


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}