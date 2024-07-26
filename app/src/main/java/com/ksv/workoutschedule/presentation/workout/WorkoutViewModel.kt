package com.ksv.workoutschedule.presentation.workout

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ksv.workoutschedule.R
import com.ksv.workoutschedule.data.HistoryRepository
import com.ksv.workoutschedule.data.WorkoutRepository
import com.ksv.workoutschedule.entity.HistoryItem
import com.ksv.workoutschedule.domain.WorkoutPlan
import com.ksv.workoutschedule.entity.WorkoutDate
import com.ksv.workoutschedule.presentation.main.WorkoutState
import com.ksv.workoutschedule.util.TimeConverter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow<WorkoutState>(WorkoutState.Idle)
    val state = _state.asStateFlow()
    val exercisesList = MutableStateFlow("")
    val timerText = MutableStateFlow("")
    val trainingPlan = MutableStateFlow("")

    private var needToLoadData = true
    private var workoutPlan = WorkoutPlan()
    private var startTime = LocalDateTime.now()

    fun openWorkoutFragment() {
        if (needToLoadData) {
            val context = getApplication<Application>().applicationContext
            val workoutRepository = WorkoutRepository(context)
            workoutPlan = workoutRepository.loadWorkoutPlan()
            needToLoadData = false
            nextWorkoutPlanClick()
        }
    }

    fun nextWorkoutPlanClick() {
        workoutPlan.next()
        displayCurrentPlanOnUI()
    }

    fun previousWorkoutPlanClick() {
        workoutPlan.previous()
        displayCurrentPlanOnUI()
    }

    fun startFinishWorkoutClick(context: Context){
        when(state.value){
            WorkoutState.Idle -> {
                startWorkout()
            }
            WorkoutState.Training -> {
                showAlertDialog(context)
            }
        }
    }


    private fun startWorkout() {
        startTime = LocalDateTime.now()
        _state.value = WorkoutState.Training
        viewModelScope.launch {
            timerTick()
        }
    }

    private fun showAlertDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle(R.string.alert_dialog_title)
            .setMessage(R.string.alert_dialog_message)
            .setPositiveButton(R.string.alert_dialog_yes) { _, _ ->
                finishWorkout()
            }
            .setNegativeButton(R.string.alert_dialog_no){ _, _ ->
                // do nothing
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun finishWorkout(){
        saveCurrentWorkout()
        addToHistory()
        _state.value = WorkoutState.Idle
        nextWorkoutPlanClick()
    }

    private fun displayCurrentPlanOnUI(){
        exercisesList.value = listToNumberedString(workoutPlan.exercises)
        val context = getApplication<Application>().applicationContext
        val prefix = context.getString(R.string.plan_name_prefix)
        trainingPlan.value = "$prefix${workoutPlan.press.number}"
    }

    private suspend fun timerTick() {
        while (state.value == WorkoutState.Training) {
            val duration = Duration.between(startTime, LocalDateTime.now())
            timerText.value = TimeConverter.durationToText(duration)
            delay(500)
        }
    }

    private fun saveCurrentWorkout() {
        val context = getApplication<Application>().applicationContext
        val workoutRepository = WorkoutRepository(context)
        workoutRepository.saveWorkoutPlan(workoutPlan)
    }

    private fun addToHistory() {
        val context = getApplication<Application>().applicationContext
        val hisRepository = HistoryRepository(context)
        val localDate = LocalDate.now()
        val workoutDate = WorkoutDate(localDate.year, localDate.monthValue, localDate.dayOfMonth)
        val pressExNum = workoutPlan.press.ordinal
        val barExNum = workoutPlan.bar.ordinal
//        val duration = Duration.between(startTime, LocalDateTime.now()).seconds + (3000..4200).random()
        val duration = Duration.between(startTime, LocalDateTime.now()).seconds
        viewModelScope.launch {
            hisRepository.addItemToHistory(HistoryItem(workoutDate, pressExNum, barExNum, duration))
        }
    }

    private fun listToNumberedString(list: List<String>): String {
        val builder = StringBuilder()
        for ((i, e) in list.withIndex()) {
            builder.append("${i + 1}. $e\n")
        }
        if (builder.isNotEmpty())
            builder.setLength(builder.length - 1)
        return builder.toString()
    }

}