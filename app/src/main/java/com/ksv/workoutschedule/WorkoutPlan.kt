package com.ksv.workoutschedule

import java.lang.StringBuilder

val pressPlan1 =
    listOf<String>("Маятник", "Ситап", "Косые скручивания", "Книжка", "Скручивания с поворотом")
val pressPlan2 = listOf<String>("Сотня", "Скамья", "Велосипед", "Планка", "Твист")
val pressPlan3 = listOf<String>("Маятник", "Косые скручивания", "Скамья", "Планка", "Сотня")

const val barPlan1 = "Подтягивания широким хватом"
const val barPlan2 = "Подтягивания нормальным хватом"

class WorkoutPlan(pressPlan: PressPlans, barExercise: BarPlans) {
//    var currentExercises: String = ""
    var currentExercises: List<String> = listOf()
        private set

    var currentPress = pressPlan
        private set
    var currentBar = barExercise
        private set


    init {
        generateCurrentExercise()
    }

    companion object PressExercises {
        enum class PressPlans {
            FIRST, SECOND, THIRST
        }
        enum class BarPlans {
            FIRST, SECOND
        }
    }


//    fun next(): String {
    fun next(): List<String> {
        currentPress = when (currentPress) {
            PressPlans.FIRST -> PressPlans.SECOND
            PressPlans.SECOND -> PressPlans.THIRST
            PressPlans.THIRST -> PressPlans.FIRST
        }

        currentBar = when (currentBar) {
            BarPlans.FIRST -> BarPlans.SECOND
            BarPlans.SECOND -> BarPlans.FIRST
        }

        generateCurrentExercise()

        return currentExercises
    }

//    fun previous(): String {
    fun previous(): List<String> {
        currentPress = when (currentPress) {
            PressPlans.FIRST -> PressPlans.THIRST
            PressPlans.SECOND -> PressPlans.FIRST
            PressPlans.THIRST -> PressPlans.SECOND
        }

        currentBar = when (currentBar) {
            BarPlans.FIRST -> BarPlans.SECOND
            BarPlans.SECOND -> BarPlans.FIRST
        }

        generateCurrentExercise()

        return currentExercises
    }


    private fun generateCurrentExercise() {
        val exerciseList = when (currentPress) {
            PressPlans.FIRST -> pressPlan1.toMutableList()
            PressPlans.SECOND -> pressPlan2.toMutableList()
            PressPlans.THIRST -> pressPlan3.toMutableList()
        }

        when (currentBar) {
            BarPlans.FIRST -> exerciseList.add(barPlan1)
            BarPlans.SECOND -> exerciseList.add(barPlan2)
        }

//        currentExercises = listToNumbericString(exerciseList)
        currentExercises = exerciseList.toList()
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


}
