package com.ksv.workoutschedule

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel() : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Normal)
    val state = _state.asStateFlow()
    val exercisesList = MutableStateFlow("")
    val trainingPlan = MutableStateFlow(PLAN_NAME_PREFIX)

    private var needToLoadData = true
    private var workoutPlan = WorkoutPlan()

    fun loadSavedData(context: Context) {
        if(needToLoadData) {
            val repository = Repository(context)
            workoutPlan = repository.loadWorkoutPlan()
            needToLoadData = false
            nextWorkoutPlan()
        }
    }

    fun openHistory() {
        _state.value = State.History
    }

    fun openSettings() {
        _state.value = State.Settings
    }

    fun startWorkout(context: Context) {
        _state.value = State.Workout
    }

    fun finishWorkout(context: Context) {
        val repository = Repository(context)
        repository.saveWorkoutPlan(workoutPlan)
        _state.value = State.Normal

        nextWorkoutPlan()
    }

    fun brakeWorkout(context: Context) {

        _state.value = State.Normal
    }

    fun nextWorkoutPlan() {
        workoutPlan.next()
        exercisesList.value = listToNumbericString(workoutPlan.exercises)
        trainingPlan.value = "$PLAN_NAME_PREFIX${workoutPlan.press.ordinal + 1}"
    }

    fun previousWorkoutPlan() {
        workoutPlan.previous()
        exercisesList.value = listToNumbericString(workoutPlan.exercises)
        trainingPlan.value = "$PLAN_NAME_PREFIX${workoutPlan.press.ordinal + 1}"
    }


    private fun listToNumbericString(list: List<String>): String {
        val builder = StringBuilder()
        for ((i, e) in list.withIndex()) {
            builder.append("${i + 1}. $e\n")
        }
        if (builder.isNotEmpty())
            builder.setLength(builder.length - 1)
        return builder.toString()

    }

    companion object {
        const val PLAN_NAME_PREFIX = "Группа упражнений №"
    }
}