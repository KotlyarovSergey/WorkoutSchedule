package com.ksv.workoutschedule.data

import android.content.Context
import com.ksv.workoutschedule.domain.WorkoutPlan

class WorkoutRepository(private val context: Context) {

    fun saveWorkoutPlan(workoutPlan: WorkoutPlan) {
        val sharedPreferencesDataSource = SharedPreferencesDataSource(context)
        sharedPreferencesDataSource.saveWorkoutPlanToSharedPreference(workoutPlan)
    }

    fun loadWorkoutPlan(): WorkoutPlan {
        val sharedPreferencesDataSource = SharedPreferencesDataSource(context)
        return sharedPreferencesDataSource.loadWorkoutPlanFromSharedPreference()
            ?: WorkoutPlan(
                WorkoutPlan.Plans.PressPlan.THIRD,
                WorkoutPlan.Plans.BarPlan.BROAD
            )
    }
}