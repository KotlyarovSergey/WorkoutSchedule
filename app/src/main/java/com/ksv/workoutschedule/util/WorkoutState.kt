package com.ksv.workoutschedule.util

sealed class WorkoutState {
    data object Idle: WorkoutState()
    data object Training: WorkoutState()
}