package com.ksv.workoutschedule.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.ksv.workoutschedule.data.HistoryRepository
import com.ksv.workoutschedule.data.WorkoutRepository
import com.ksv.workoutschedule.entity.HistoryItem
import com.ksv.workoutschedule.domain.WorkoutPlan
import com.ksv.workoutschedule.entity.WorkoutDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val _state = MutableStateFlow<WorkoutState>(WorkoutState.Idle)
    val state = _state.asStateFlow()
    val exercisesList = MutableStateFlow("")
    val trainingPlan = MutableStateFlow(PLAN_NAME_PREFIX)

    private var needToLoadData = true
    private var workoutPlan = WorkoutPlan()
    private var startTime = LocalDateTime.now()

    fun openWorkoutFragment(){
        if(needToLoadData) {
            val workoutRepository = WorkoutRepository(context)
            workoutPlan = workoutRepository.loadWorkoutPlan()
            needToLoadData = false
            nextWorkoutPlan()
        }
    }

    fun startWorkout() {
        startTime = LocalDateTime.now()
        _state.value = WorkoutState.Training
    }

    fun finishWorkout() {
        saveCurrentWorkout()
        addToHistory()
        _state.value = WorkoutState.Idle
        nextWorkoutPlan()
    }

    private fun saveCurrentWorkout(){
        val workoutRepository = WorkoutRepository(context)
        workoutRepository.saveWorkoutPlan(workoutPlan)
    }

    private fun addToHistory(){
        val hisRepository = HistoryRepository(context)
        val localDate = LocalDate.now()
        val workoutDate = WorkoutDate(localDate.year, localDate.monthValue, localDate.dayOfMonth)
        val pressExNum =  workoutPlan.press.ordinal
        val barExNum = workoutPlan.bar.ordinal
//        val duration = Duration.between(startTime, LocalDateTime.now()).seconds + (3000..4200).random()
        val duration = Duration.between(startTime, LocalDateTime.now()).seconds

        hisRepository.addItemToHistory(HistoryItem(workoutDate, pressExNum, barExNum, duration))
    }



    fun brakeWorkout() {
        _state.value = WorkoutState.Idle
    }

    fun nextWorkoutPlan() {
        workoutPlan.next()
        exercisesList.value = listToNumbericString(workoutPlan.exercises)
        trainingPlan.value = "$PLAN_NAME_PREFIX${workoutPlan.press.number}"
    }

    fun previousWorkoutPlan() {
        workoutPlan.previous()
        exercisesList.value = listToNumbericString(workoutPlan.exercises)
        trainingPlan.value = "$PLAN_NAME_PREFIX${workoutPlan.press.number}"
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