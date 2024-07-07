package com.ksv.workoutschedule.presentation.main

sealed class WorkoutState {
    data object Idle: WorkoutState()
    data object Training: WorkoutState()
}