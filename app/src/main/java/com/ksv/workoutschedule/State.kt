package com.ksv.workoutschedule

sealed class State {
    data object Normal: State()
    data object Workout: State()
    data object History: State()
    data object Settings: State()
}