package com.ksv.workoutschedule.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ksv.workoutschedule.data.HistoryRepository
import com.ksv.workoutschedule.data.WorkoutRepository
import com.ksv.workoutschedule.entity.HistoryItem
import com.ksv.workoutschedule.domain.WorkoutPlan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WorkoutViewModel() : ViewModel() {
    private val _state = MutableStateFlow<WorkoutState>(WorkoutState.Idle)
    val state = _state.asStateFlow()
    val exercisesList = MutableStateFlow("")
    val trainingPlan = MutableStateFlow(PLAN_NAME_PREFIX)

    private var needToLoadData = true
    private var workoutPlan = WorkoutPlan()


//    fun loadSavedData(context: Context) {
//        if(needToLoadData) {
//            val workoutRepository = WorkoutRepository(context)
//            workoutPlan = workoutRepository.loadWorkoutPlan()
//            needToLoadData = false
//            nextWorkoutPlan()
//        }
//    }

    fun openWorkoutFragment(context: Context){
        if(needToLoadData) {
            val workoutRepository = WorkoutRepository(context)
            workoutPlan = workoutRepository.loadWorkoutPlan()
            needToLoadData = false
            nextWorkoutPlan()
        }

    }

    fun startWorkout() {
        _state.value = WorkoutState.Training
    }

    fun finishWorkout(context: Context) {
        // 1. сохранить упраженение
        saveCurrentWorkout(context)

        // 2. добавить в историю
        addToHistory(context)

        // 3. закончить упражнение
        _state.value = WorkoutState.Idle

        // 3. перейти к следующему
        nextWorkoutPlan()
    }

    private fun saveCurrentWorkout(context: Context){
        val workoutRepository = WorkoutRepository(context)
        workoutRepository.saveWorkoutPlan(workoutPlan)
    }

    private fun addToHistory(context: Context){
        val stringToHistory = stringToHistory()

        val hisRepository = HistoryRepository(context)
        val mills = System.currentTimeMillis()
        val pressEx =  "p: ${workoutPlan.press.ordinal + 1}"
        val barEx = workoutPlan.bar.name
        val duration = (50..65).random() * 60L
        val hi = HistoryItem(mills, pressEx, barEx, duration)
        hisRepository.addItemToHistory(hi)
//        Log.d("ksvlog", "$hi")
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
        private const val PLAN_NAME_PREFIX = "Группа упражнений №"
    }
}