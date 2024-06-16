package com.ksv.workoutschedule.util

import android.content.Context
import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

private const val HISTORY_FILE_NAME = "history.txt"
private const val PREFERENCE_NAME = "preference_name"
private const val SHARED_PREFS_LAST_PLAN_BAR = "shared_prefs_last_plan_bar"
private const val SHARED_PREFS_LAST_PLAN_PRESS = "shared_prefs_last_plan_press"
private const val FILE_WRITE_ERROR_MSG = "Ошибка записи файла истории"
private const val FILE_READ_ERROR_MSG = "Ошибка чтения файла истории"
private const val EMPTY_HISTORY_MSG = "Здесь пока пусто"

class Repository(private val context: Context) {
    private val prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    fun saveWorkoutPlan(plan: WorkoutPlan) {
        saveWorkoutPlanToSharedPreference(plan)
    }

    fun loadWorkoutPlan(): WorkoutPlan {

        return loadWorkoutPlanFromSharedPreference()
            ?: WorkoutPlan(
                WorkoutPlan.Plans.PressPlan.FIRST,
                WorkoutPlan.Plans.BarPlan.FIRST
            )
    }

    fun addTextToHistory(textToAdd: String) {

        val textLine = "$textToAdd\n"
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(HISTORY_FILE_NAME, MODE_APPEND)
//            fos = context.openFileOutput(HISTORY_FILE_NAME, MODE_PRIVATE)
            fos.write(textLine.toByteArray())
            Log.d("ksvlog", "Add to history: $textToAdd")
        } catch (ex: IOException) {
            Toast.makeText(context, FILE_WRITE_ERROR_MSG, Toast.LENGTH_SHORT).show()
        } finally {
            fos?.close()
        }
    }

    fun clearHistory() {
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(HISTORY_FILE_NAME, MODE_PRIVATE)
//            fos.write("".toByteArray())
            fos.write(0)
        } catch (ex: IOException) {
            Toast.makeText(context, FILE_WRITE_ERROR_MSG, Toast.LENGTH_SHORT).show()
        } finally {
            fos?.close()
        }
    }

    fun loadHistory(): List<String> {
//        val filePath = "/$HISTORY_FILE_NAME"
//        val file = File(filePath)
        val file = File(context.filesDir.absolutePath, HISTORY_FILE_NAME)
        if (!file.exists())
            return listOf(EMPTY_HISTORY_MSG)


        Log.d("ksvlog", "History file exist")
        var fin: FileInputStream? = null
        return try {
            fin = context.openFileInput(HISTORY_FILE_NAME)
            val bytes = ByteArray(fin.available())
            fin.read(bytes)
            val string = String(bytes)
            Log.d("ksvlog", string)
            string.split("\n")
        } catch (ex: IOException) {
            Toast.makeText(context, FILE_READ_ERROR_MSG, Toast.LENGTH_SHORT).show()
            listOf()
        } finally {
            fin?.close()
        }
    }


    private fun saveWorkoutPlanToSharedPreference(plan: WorkoutPlan) {
        val editor = prefs.edit()
        editor.putString(SHARED_PREFS_LAST_PLAN_BAR, plan.bar.name)
        editor.putString(SHARED_PREFS_LAST_PLAN_PRESS, plan.press.name)
        editor.apply()
    }

    private fun loadWorkoutPlanFromSharedPreference(): WorkoutPlan? {
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
}