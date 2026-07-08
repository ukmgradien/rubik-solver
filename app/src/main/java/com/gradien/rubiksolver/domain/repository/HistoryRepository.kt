package com.gradien.rubiksolver.domain.repository

import com.gradien.rubiksolver.domain.model.HistoryEntry
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getAllHistory(): Flow<List<HistoryEntry>>
    suspend fun saveHistory(entry: HistoryEntry)
    suspend fun clearHistory()
}
