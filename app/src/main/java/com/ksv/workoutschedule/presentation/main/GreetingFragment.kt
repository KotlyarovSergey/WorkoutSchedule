package com.ksv.workoutschedule.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ksv.workoutschedule.databinding.FragmentGreetingBinding
import com.ksv.workoutschedule.presentation.workout.WorkoutActivity

class GreetingFragment : Fragment() {
    private var _binding: FragmentGreetingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = TITLE

        binding.workoutButton.setOnClickListener {
            val intent = Intent(requireActivity(), WorkoutActivity::class.java)
            startActivity(intent)
        }
    }




    companion object {
        private const val TITLE = "План тренировок"
    }
}