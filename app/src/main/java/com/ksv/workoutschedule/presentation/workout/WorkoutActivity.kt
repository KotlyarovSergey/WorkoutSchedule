package com.ksv.workoutschedule.presentation.workout

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ksv.workoutschedule.R
import com.ksv.workoutschedule.databinding.ActivityMainBinding
import com.ksv.workoutschedule.databinding.ActivityWorkoutBinding

class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}