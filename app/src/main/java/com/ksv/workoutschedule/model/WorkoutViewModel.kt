package com.ksv.workoutschedule.model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ksv.workoutschedule.util.Repository
import com.ksv.workoutschedule.util.WorkoutPlan
import com.ksv.workoutschedule.util.WorkoutState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WorkoutViewModel() : ViewModel() {
//    private val _state = MutableStateFlow<State>(State.Normal)
    private val _state = MutableStateFlow<WorkoutState>(WorkoutState.Idle)
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

    fun openWorkoutFragment(context: Context){
//        Log.d("ksvlog", "WVM: Open MainFragment. State: ${state.value}")
        if(needToLoadData) {
            val repository = Repository(context)
            workoutPlan = repository.loadWorkoutPlan()
            needToLoadData = false
            nextWorkoutPlan()
        }

    }

    fun startWorkout() {
        _state.value = WorkoutState.Training
    }

    fun finishWorkout(context: Context) {
        val repository = Repository(context)
        repository.saveWorkoutPlan(workoutPlan)
        val stringToHistory = stringToHistory()
        repository.addTextToHistory(stringToHistory)
        _state.value = WorkoutState.Idle
        nextWorkoutPlan()
    }

    fun brakeWorkout() {
        _state.value = WorkoutState.Idle
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

    private fun stringToHistory():String{
        return workoutPlan.planToHistory()
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