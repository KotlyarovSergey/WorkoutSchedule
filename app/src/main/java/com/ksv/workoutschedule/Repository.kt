package com.ksv.workoutschedule

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log

private const val HISTORY_FILE_NAME = "history.txt"
private const val PREFERENCE_NAME = "preference_name"
private const val SHARED_PREFS_LAST_PLAN_BAR = "shared_prefs_last_plan_bar"
private const val SHARED_PREFS_LAST_PLAN_PRESS = "shared_prefs_last_plan_press"

class Repository(context: Context) {
    private val prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
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

    fun addWorkoutPlanToHistory(plan: WorkoutPlan) {

    }

    fun loadHistory(): List<String> {

        return listOf("")
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
}