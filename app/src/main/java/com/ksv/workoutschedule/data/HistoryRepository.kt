package com.ksv.workoutschedule.data

import android.content.Context
import com.ksv.workoutschedule.entity.HistoryItem

class HistoryRepository(context: Context) {
    private val fileDataSource = FileDataSource(context)

    fun addItemToHistory(historyItem: HistoryItem){
        fileDataSource.addData(historyItem)
    }

    fun clearHistory() {
        fileDataSource.clearData()
    }

    fun loadHistory(): List<HistoryItem> {
        return fileDataSource.getData()
    }

}