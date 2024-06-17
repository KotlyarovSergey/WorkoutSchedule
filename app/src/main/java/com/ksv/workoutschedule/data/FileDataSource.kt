package com.ksv.workoutschedule.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.widget.Toast
import com.ksv.workoutschedule.entity.HistoryItem
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
            Log.d("ksvlog", "Add to history: $textLine")
        } catch (ex: IOException) {
            Toast.makeText(context, FILE_WRITE_ERROR_MSG, Toast.LENGTH_SHORT).show()
        } finally {
            fos?.close()
        }

    }

    fun getData(): List<HistoryItem> {
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
            fos.write(0)
//            Log.d("ksvlog", "History cleared")
        } catch (ex: IOException) {
            Toast.makeText(context, FILE_WRITE_ERROR_MSG, Toast.LENGTH_SHORT).show()
        } finally {
            fos?.close()
        }
    }

    private fun historyItemToString(historyItem: HistoryItem): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(historyItem.date)
        stringBuilder.append("\t")
        stringBuilder.append(historyItem.pressExercise)
        stringBuilder.append("\t")
        stringBuilder.append(historyItem.barExercise)
        stringBuilder.append("\t")
        stringBuilder.append(historyItem.duration)

        return stringBuilder.toString()
    }

    private fun historyItemFromString(data: String): HistoryItem {
        val p = data.split("\t")
        if (p.size != 4)
            return HistoryItem(0, "", "", 0)

        return try {
            val date = p[0].toLong()
            val pressExercise = p[1]
            val barExercise = p[2]
            val duration = p[3].toLong()
            HistoryItem(date, pressExercise, barExercise, duration)
        } catch (exception: Exception){
            HistoryItem(0, "", "", 0)
        }
    }

    companion object {
        private const val HISTORY_FILE_NAME = "history.txt"
        private const val FILE_WRITE_ERROR_MSG = "Ошибка записи файла истории"
        private const val FILE_READ_ERROR_MSG = "Ошибка чтения файла истории"
    }
}