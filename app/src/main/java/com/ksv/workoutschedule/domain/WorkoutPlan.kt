package com.ksv.workoutschedule.domain

class WorkoutPlan(pressPlan: PressPlan, barExercise: BarPlan) {
    constructor() : this(PressPlan.FIRST, BarPlan.NORMAL){

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
            PressPlan.SECOND -> PressPlan.THIRD
            PressPlan.THIRD -> PressPlan.FIRST
        }
        bar = when (bar) {
            BarPlan.NORMAL -> BarPlan.BROAD
            BarPlan.BROAD -> BarPlan.NORMAL
        }
        makeListOfExercise()
        return exercises
    }

    fun previous(): List<String> {
        press = when (press) {
            PressPlan.FIRST -> PressPlan.THIRD
            PressPlan.SECOND -> PressPlan.FIRST
            PressPlan.THIRD -> PressPlan.SECOND
        }
        bar = when (bar) {
            BarPlan.NORMAL -> BarPlan.BROAD
            BarPlan.BROAD -> BarPlan.NORMAL
        }
        makeListOfExercise()
        return exercises
    }

    private fun makeListOfExercise() {
        val exerciseList = when (press) {
            PressPlan.FIRST -> pressPlan1.toMutableList()
            PressPlan.SECOND -> pressPlan2.toMutableList()
            PressPlan.THIRD -> pressPlan3.toMutableList()
        }
        when (bar) {
            BarPlan.NORMAL -> exerciseList.add(barPlanNormal)
            BarPlan.BROAD -> exerciseList.add(barPlanBroad)
        }
        exercises = exerciseList.toList()
    }

    companion object Plans{
        enum class PressPlan(val number: Int) {
            FIRST(1), SECOND(2), THIRD(3)
        }
        enum class BarPlan(val type: String) {
            NORMAL("у"), BROAD("ш")
        }

        private val pressPlan1 =
            listOf("Маятник", "Ситап", "Косые скручивания", "Книжка", "Скручивания с поворотом")
        private val pressPlan2 = listOf("Сотня", "Скамья", "Велосипед", "Планка", "Твист")
        private val pressPlan3 = listOf("Маятник", "Косые скручивания", "Скамья", "Планка", "Сотня")

        const val barPlanNormal = "Подтягивания нормальным хватом"
        const val barPlanBroad = "Подтягивания широким хватом"
    }


}
