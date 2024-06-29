package com.ksv.workoutschedule.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import com.ksv.workoutschedule.entity.HistoryItem
import com.ksv.workoutschedule.entity.WorkoutDate
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class FileDataSource(private val context: Context) {

    fun addData(historyItem: HistoryItem) {

        val textLine = historyItemToString(historyItem) + "\n"
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(HISTORY_FILE_NAME, Context.MODE_APPEND)
            fos.write(textLine.toByteArray())
        } catch (ex: IOException) {
            Toast.makeText(context, FILE_WRITE_ERROR_MSG, Toast.LENGTH_SHORT).show()
        } finally {
            fos?.close()
        }

    }

    fun getData(): List<HistoryItem> {
//            delay(5000)
        val historyItemList = mutableListOf<HistoryItem>()

        val file = File(context.filesDir.absolutePath, HISTORY_FILE_NAME)
        if (!file.exists())
            return historyItemList

        var fis: FileInputStream? = null
        try {
            fis = context.openFileInput(HISTORY_FILE_NAME)
            val inputStreamReader = InputStreamReader(fis)
            val bufferedReader = BufferedReader(inputStreamReader)

//            var line: String?
//            while (bufferedReader.readLine().also { line = it } != null){
//                historyItemList.add(historyItemFromString(line!!))
//            }

            bufferedReader.lines().forEach {line ->
                historyItemList.add(historyItemFromString(line!!))
            }
            return historyItemList

        } catch (ex: IOException) {
            Toast.makeText(context, FILE_READ_ERROR_MSG, Toast.LENGTH_SHORT).show()
            return emptyList()
        } finally {
            fis?.close()
        }
    }

    fun clearData() {
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(HISTORY_FILE_NAME, MODE_PRIVATE)
            fos.write("".toByteArray())
//            Log.d("ksvlog", "History cleared")
        } catch (ex: IOException) {
            Toast.makeText(context, FILE_WRITE_ERROR_MSG, Toast.LENGTH_SHORT).show()
        } finally {
            fos?.close()
        }
    }

    private fun historyItemToString(historyItem: HistoryItem): String {
        val stringBuilder = StringBuilder()
//        stringBuilder.append(historyItem.workoutDate)
        stringBuilder.append(historyItem.workoutDate.year)
        stringBuilder.append("\t")
        stringBuilder.append(historyItem.workoutDate.month)
        stringBuilder.append("\t")
        stringBuilder.append(historyItem.workoutDate.day)
        stringBuilder.append("\t")
        stringBuilder.append(historyItem.pressPlanNum)
        stringBuilder.append("\t")
        stringBuilder.append(historyItem.barPlanNum)
        stringBuilder.append("\t")
        stringBuilder.append(historyItem.duration)

        return stringBuilder.toString()
    }

    private fun historyItemFromString(data: String): HistoryItem {
        val p = data.split("\t")
        if (p.size != HISTORY_ITEM_FIELDS_COUNT)
            return HistoryItem(WorkoutDate(0,1,1), 0, 0, 0)

        return try {
            val year = p[0].toInt()
            val month = p[1].toInt()
            val day = p[2].toInt()
            val pressExercise = p[3].toInt()
            val barExercise = p[4].toInt()
            val duration = p[5].toLong()
            HistoryItem(WorkoutDate(year, month, day), pressExercise, barExercise, duration)
        } catch (exception: Exception){
            HistoryItem(WorkoutDate(0,0,0), 0, 0, 0)
        }
    }

    companion object {
        private const val HISTORY_FILE_NAME = "history.txt"
        private const val HISTORY_ITEM_FIELDS_COUNT = 6
        private const val FILE_WRITE_ERROR_MSG = "Ошибка записи файла истории"
        private const val FILE_READ_ERROR_MSG = "Ошибка чтения файла истории"
    }
}