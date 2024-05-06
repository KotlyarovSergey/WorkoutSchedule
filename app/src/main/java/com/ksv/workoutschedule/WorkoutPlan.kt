package com.ksv.workoutschedule

val pressPlan1 =
    listOf<String>("Маятник", "Ситап", "Косые скручивания", "Книжка", "Скручивания с поворотом")
val pressPlan2 = listOf<String>("Сотня", "Скамья", "Велосипед", "Планка", "Твист")
val pressPlan3 = listOf<String>("Маятник", "Косые скручивания", "Скамья", "Планка", "Сотня")

const val barPlan1 = "Подтягивания широким хватом"
const val barPlan2 = "Подтягивания нормальным хватом"

class WorkoutPlan(pressPlan: PressPlan, barExercise: BarPlan) {
    var exercises: List<String> = listOf()
        private set

    var press = pressPlan
        private set
    var bar = barExercise
        private set

    init {
        generateCurrentExercise()
    }

    companion object Plans {
        enum class PressPlan {
            FIRST, SECOND, THIRST
        }

        enum class BarPlan {
            FIRST, SECOND
        }
    }

    fun next(): List<String> {
        press = when (press) {
            PressPlan.FIRST -> PressPlan.SECOND
            PressPlan.SECOND -> PressPlan.THIRST
            PressPlan.THIRST -> PressPlan.FIRST
        }

        bar = when (bar) {
            BarPlan.FIRST -> BarPlan.SECOND
            BarPlan.SECOND -> BarPlan.FIRST
        }

        generateCurrentExercise()

        return exercises
    }

    fun previous(): List<String> {
        press = when (press) {
            PressPlan.FIRST -> PressPlan.THIRST
            PressPlan.SECOND -> PressPlan.FIRST
            PressPlan.THIRST -> PressPlan.SECOND
        }

        bar = when (bar) {
            BarPlan.FIRST -> BarPlan.SECOND
            BarPlan.SECOND -> BarPlan.FIRST
        }

        generateCurrentExercise()

        return exercises
    }

    fun planToHistory(): String {
        // TODO реализовать!
        return ""
    }

    private fun generateCurrentExercise() {
        val exerciseList = when (press) {
            PressPlan.FIRST -> pressPlan1.toMutableList()
            PressPlan.SECOND -> pressPlan2.toMutableList()
            PressPlan.THIRST -> pressPlan3.toMutableList()
        }

        when (bar) {
            BarPlan.FIRST -> exerciseList.add(barPlan1)
            BarPlan.SECOND -> exerciseList.add(barPlan2)
        }

        exercises = exerciseList.toList()
    }
}
