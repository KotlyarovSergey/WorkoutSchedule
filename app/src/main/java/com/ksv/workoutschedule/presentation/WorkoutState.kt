package com.ksv.workoutschedule.presentation

sealed class WorkoutState {
    data object Idle: WorkoutState()
    data object Training: WorkoutState()
}