package com.ksv.workoutschedule.util

import java.time.Duration

class TimeConverter {
    companion object{
        fun durationToText(duration: Duration): String{
            val hours = duration.toHours().toString()
            val minutes = addZero((duration.toMinutes() % 60).toInt())
            val seconds = addZero((duration.seconds % 60).toInt())
            return "$hours:$minutes:$seconds"
        }

        private fun addZero(number: Int): String {
            return if (number < 10)
                "0$number"
            else
                number.toString()
        }
    }
}