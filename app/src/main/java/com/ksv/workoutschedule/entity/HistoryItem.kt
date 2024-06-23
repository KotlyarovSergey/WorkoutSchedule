package com.ksv.workoutschedule.entity

data class HistoryItem(
    val workoutDate: WorkoutDate,
    val pressPlanNum: Int,
    val barPlanNum: Int,
    val duration: Long
)

data class WorkoutDate(
    val year: Int,
    val month: Int,
    val day: Int
)