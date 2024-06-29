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
        var ind = press.ordinal
        var size = PressPlan.entries.size
        var next = (ind + 1) % size
        press = PressPlan.entries[next]

        ind = bar.ordinal
        size = BarPlan.entries.size
        next = (ind + 1) % size
        bar = BarPlan.entries[next]

        makeListOfExercise()
        return exercises
    }

    fun previous(): List<String> {
        var ind = press.ordinal
        var size = PressPlan.entries.size
        var prev = (ind + size - 1) % size
        press = PressPlan.entries[prev]

        ind = bar.ordinal
        size = BarPlan.entries.size
        prev = (ind + size - 1) % size
        bar = BarPlan.entries[prev]

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
            NORMAL("н"), BROAD("ш")
        }

        private val pressPlan1 =
            listOf("Маятник", "Ситап", "Косые скручивания", "Книжка", "Скручивания с поворотом")
        private val pressPlan2 = listOf("Сотня", "Скамья", "Велосипед", "Планка", "Твист")
        private val pressPlan3 = listOf("Маятник", "Косые скручивания", "Скамья", "Планка", "Сотня")

        const val barPlanNormal = "Подтягивания нормальным хватом"
        const val barPlanBroad = "Подтягивания широким хватом"
    }


}
