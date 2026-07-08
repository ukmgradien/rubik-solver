package com.gradien.rubiksolver.presentation.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gradien.rubiksolver.domain.model.HistoryEntry
import com.gradien.rubiksolver.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    val historyEntries: StateFlow<List<HistoryEntry>> = repository.getAllHistory()
        .onEach { Log.d("HistoryVM", "Received ${it.size} history entries in Flow") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onClearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}
