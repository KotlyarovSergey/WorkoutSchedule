package com.ksv.workoutschedule.data

import android.content.Context
import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.widget.Toast
import com.ksv.workoutschedule.entity.HistoryItem
import com.ksv.workoutschedule.util.WorkoutPlan
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class Repository(context: Context) {
    private val prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    private val fileDataSource = FileDataSource(context)

    fun saveWorkoutPlan(plan: WorkoutPlan) {
        saveWorkoutPlanToSharedPreference(plan)
    }

    fun loadWorkoutPlan(): WorkoutPlan {

        return loadWorkoutPlanFromSharedPreference()
            ?: WorkoutPlan(
                WorkoutPlan.Plans.PressPlan.FIRST,
                WorkoutPlan.Plans.BarPlan.FIRST
            )
    }

    // TODO     разделить на 2 репозитория!! один для тренировки, другой для истории

    fun addItemToHistory(historyItem: HistoryItem){
//        val fileDataSource = FileDataSource(context)
        fileDataSource.addData(historyItem)
    }

    fun clearHistory() {
//        val fileDataSource = FileDataSource(context)
        fileDataSource.clearData()
    }

    fun loadHistory(): List<String> {
        val result = mutableListOf<String>()

//        val fileDataSource = FileDataSource(context)
        val historyList = fileDataSource.getData()
        historyList.forEach{
            result.add(it.toString())
        }
        return result
    }


    private fun saveWorkoutPlanToSharedPreference(plan: WorkoutPlan) {
        val editor = prefs.edit()
        editor.putString(SHARED_PREFS_LAST_PLAN_BAR, plan.bar.name)
        editor.putString(SHARED_PREFS_LAST_PLAN_PRESS, plan.press.name)
        editor.apply()
    }

    private fun loadWorkoutPlanFromSharedPreference(): WorkoutPlan? {
        val barPlanName = prefs.getString(SHARED_PREFS_LAST_PLAN_BAR, null)
        val pressPlanName = prefs.getString(SHARED_PREFS_LAST_PLAN_PRESS, null)

        val barPlan = if (barPlanName != null)
            WorkoutPlan.Plans.BarPlan.valueOf(barPlanName)
        else
            WorkoutPlan.Plans.BarPlan.FIRST

        val pressPlan = if (pressPlanName != null)
            WorkoutPlan.Plans.PressPlan.valueOf(pressPlanName)
        else
            WorkoutPlan.Plans.PressPlan.FIRST

        return WorkoutPlan(pressPlan, barPlan)
    }

    companion object{

        private const val HISTORY_FILE_NAME = "history.txt"
        private const val PREFERENCE_NAME = "preference_name"
        private const val SHARED_PREFS_LAST_PLAN_BAR = "shared_prefs_last_plan_bar"
        private const val SHARED_PREFS_LAST_PLAN_PRESS = "shared_prefs_last_plan_press"
        private const val FILE_WRITE_ERROR_MSG = "Ошибка записи файла истории"
        private const val FILE_READ_ERROR_MSG = "Ошибка чтения файла истории"
        private const val EMPTY_HISTORY_MSG = "Здесь пока пусто"

    }
}