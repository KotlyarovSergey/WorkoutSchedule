package com.ksv.workoutschedule.data

import android.content.Context
import com.ksv.workoutschedule.domain.WorkoutPlan

class SharedPreferencesDataSource(context: Context) {
    private val prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)


    fun saveWorkoutPlanToSharedPreference(plan: WorkoutPlan) {
        val editor = prefs.edit()
        editor.putString(SHARED_PREFS_LAST_PLAN_BAR, plan.bar.name)
        editor.putString(SHARED_PREFS_LAST_PLAN_PRESS, plan.press.name)
        editor.apply()
    }

    fun loadWorkoutPlanFromSharedPreference(): WorkoutPlan? {
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
        private const val PREFERENCE_NAME = "preference_name"
        private const val SHARED_PREFS_LAST_PLAN_BAR = "shared_prefs_last_plan_bar"
        private const val SHARED_PREFS_LAST_PLAN_PRESS = "shared_prefs_last_plan_press"
    }
}