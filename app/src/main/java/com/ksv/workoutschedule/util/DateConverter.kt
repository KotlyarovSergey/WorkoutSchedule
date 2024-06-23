package com.ksv.workoutschedule.util

import java.security.Timestamp
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime

class DateConverter {
    companion object {
        fun dateToInt(date: LocalDate): Int {
            val year = date.year shl 16
            val month = date.monthValue shl 8
            val day = date.dayOfMonth

            return year + month + day
        }

        fun dateFromInt(number: Int): LocalDate {
            val dd = number % 0x100
            val mm = number % 0x10000 shr 8
            val yyyy = number shr 16

            return try {
                LocalDate.of(yyyy, mm, dd)
            } catch (ex: DateTimeException) {
                return LocalDate.of(1726, 1, 1)
            }
        }

        fun timeToInt():Int{
            val start = LocalDateTime.now()
            // delay
            val stop = LocalDateTime.now()


//            val ts = Timestamp()
            return 0
        }
    }
}