package com.ksv.workoutschedule.presentation.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ksv.workoutschedule.R
import com.ksv.workoutschedule.databinding.FragmentWorkoutBinding
import com.ksv.workoutschedule.presentation.main.WorkoutState

class WorkoutFragment : Fragment() {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    private val workoutViewModel: WorkoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater)
        workoutViewModel.openWorkoutFragment()
        binding.viewModel = workoutViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(workoutViewModel.state.value == WorkoutState.Training) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder
                        .setTitle(getString(R.string.alert_dialog_break_title))
                        .setMessage(getString(R.string.alert_dialog_break_message))
                        .setIcon(R.drawable.baseline_back_hand_24)
                        .setPositiveButton(getString(R.string.alert_dialog_yes)) { _, _ ->
                            //parentFragmentManager.popBackStack()
                            requireActivity().finish()
                        }
                        .setNegativeButton(getString(R.string.alert_dialog_no)) { _, _ -> }
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    //parentFragmentManager.popBackStack()
                    requireActivity().finish()
                }
            }
        })
    }
}