package com.gradien.rubiksolver.presentation.manual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.gradien.rubiksolver.domain.model.HistoryEntry
import com.gradien.rubiksolver.domain.parser.CubeParser
import com.gradien.rubiksolver.domain.repository.HistoryRepository
import com.gradien.rubiksolver.domain.usecase.SolveCubeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ManualEntryViewModel(
    private val solveCubeUseCase: SolveCubeUseCase,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManualEntryUiState())
    val uiState: StateFlow<ManualEntryUiState> = _uiState.asStateFlow()

    fun onEvent(event: ManualEntryEvent) {
        when (event) {
            is ManualEntryEvent.StickerClicked -> updateSticker(event.index)
            is ManualEntryEvent.ColorSelected -> {
                _uiState.update { it.copy(selectedColor = event.color) }
            }
            ManualEntryEvent.SolveClicked -> solve()
            ManualEntryEvent.ResetClicked -> {
                _uiState.update { ManualEntryUiState() }
            }
            ManualEntryEvent.NextStep -> {
                _uiState.update {
                    if (it.currentStepIndex < it.steps.size - 1)
                        it.copy(currentStepIndex = it.currentStepIndex + 1)
                    else it
                }
            }
            ManualEntryEvent.PreviousStep -> {
                _uiState.update {
                    if (it.currentStepIndex > 0)
                        it.copy(currentStepIndex = it.currentStepIndex - 1)
                    else it
                }
            }
        }
    }

    private fun saveToHistory(cubeState: String, solution: List<String>) {
        viewModelScope.launch {
            try {
                Log.d("ManualVM", "Initiating save to history...")
                historyRepository.saveHistory(
                    HistoryEntry(
                        timestamp = System.currentTimeMillis(),
                        cubeState = cubeState,
                        solution = solution
                    )
                )
                Log.d("ManualVM", "Save history call completed")
            } catch (e: Exception) {
                Log.e("ManualVM", "Failed to save history", e)
            }
        }
    }

    private fun updateSticker(index: Int) {
        _uiState.update { currentState ->
            val newList = currentState.facelets.toMutableList()
            newList[index] = currentState.selectedColor
            currentState.copy(
                facelets = newList,
                steps = emptyList(),
                currentStepIndex = 0,
                errorMessage = null,
                isSolved = false
            )
        }
    }

    private fun solve() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val faceletString = _uiState.value.facelets.joinToString("") { it.char.toString() }
                val cube = CubeParser.fromString(faceletString)
                val moves = solveCubeUseCase.execute(cube)

                if (moves.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            isSolved = true,
                            steps = emptyList(),
                            isLoading = false
                        )
                    }
                    // Even if moves are empty, save to history to show it was solved
                    saveToHistory(faceletString, emptyList())
                } else {
                    val steps = moves.mapIndexed { index, move ->
                        SolutionStep(
                            stepNumber = index + 1,
                            totalSteps = moves.size,
                            notation = move.notation,
                            description = move.toDescription()
                        )
                    }
                    _uiState.update {
                        it.copy(
                            steps = steps,
                            currentStepIndex = 0,
                            isSolved = false,
                            isLoading = false
                        )
                    }
                    saveToHistory(faceletString, moves.map { it.notation })
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.message ?: "Unknown Error",
                        isLoading = false
                    )
                }
            }
        }
    }
}