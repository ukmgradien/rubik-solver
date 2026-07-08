package com.gradien.rubiksolver.data.repository

import android.util.Log
import com.gradien.rubiksolver.data.local.dao.HistoryDao
import com.gradien.rubiksolver.data.local.entity.HistoryEntity
import com.gradien.rubiksolver.domain.model.HistoryEntry
import com.gradien.rubiksolver.domain.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class HistoryRepositoryImpl(private val historyDao: HistoryDao) : HistoryRepository {

    override fun getAllHistory(): Flow<List<HistoryEntry>> {
        return historyDao.getAllHistory()
            .onEach { Log.d("HistoryRepo", "Fetched ${it.size} entities from DB") }
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun saveHistory(entry: HistoryEntry) {
        withContext(Dispatchers.IO) {
            try {
                Log.d("HistoryRepo", "Saving entry to DB: ${entry.cubeState}")
                historyDao.insert(entry.toEntity())
                Log.d("HistoryRepo", "Save successful")
            } catch (e: Exception) {
                Log.e("HistoryRepo", "Error saving history", e)
                throw e
            }
        }
    }

    override suspend fun clearHistory() {
        withContext(Dispatchers.IO) {
            historyDao.deleteAll()
        }
    }

    private fun HistoryEntity.toDomain(): HistoryEntry {
        return HistoryEntry(
            id = id,
            timestamp = timestamp,
            cubeState = cubeState ?: "",
            solution = if (solution == null || solution.isEmpty()) emptyList() else solution.split(",")
        )
    }

    private fun HistoryEntry.toEntity(): HistoryEntity {
        return HistoryEntity(
            timestamp,
            cubeState,
            solution.joinToString(",")
        )
    }
}
