package com.ksv.workoutschedule

class WorkoutPlan(pressPlan: PressPlan, barExercise: BarPlan) {
    constructor() : this(PressPlan.FIRST, BarPlan.FIRST) {

    }

    var exercises = listOf<String>()
        private set

    var press = pressPlan
        private set
    var bar = barExercise
        private set

    init {
        makeListOfExercise()
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
        makeListOfExercise()
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
        makeListOfExercise()
        return exercises
    }

    fun planToHistory(): String {
        // TODO     плохо!!! надо переделать!
        val barShort = when(bar){
            BarPlan.FIRST -> "ш"
            BarPlan.SECOND -> "у"
        }
        return "${press.ordinal + 1}-$barShort"
    }

    private fun makeListOfExercise() {
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

    companion object Plans{
        enum class PressPlan {
            FIRST, SECOND, THIRST
        }
        enum class BarPlan {
            FIRST, SECOND
        }

        private val pressPlan1 =
            listOf("Маятник", "Ситап", "Косые скручивания", "Книжка", "Скручивания с поворотом")
        private val pressPlan2 = listOf("Сотня", "Скамья", "Велосипед", "Планка", "Твист")
        private val pressPlan3 = listOf("Маятник", "Косые скручивания", "Скамья", "Планка", "Сотня")

        const val barPlan1 = "Подтягивания широким хватом"
        const val barPlan2 = "Подтягивания нормальным хватом"
    }


}
