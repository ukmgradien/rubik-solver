package com.gradien.rubiksolver.presentation.manual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gradien.rubiksolver.domain.model.CubeColor
import com.gradien.rubiksolver.domain.parser.CubeParser
import com.gradien.rubiksolver.domain.usecase.SolveCubeUseCase
import com.gradien.rubiksolver.domain.notation.NotationParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ManualEntryViewModel(
    private val solveCubeUseCase: SolveCubeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManualEntryUiState())
    val uiState: StateFlow<ManualEntryUiState> = _uiState.asStateFlow()

    fun onEvent(event: ManualEntryEvent) {
        when (event) {
            is ManualEntryEvent.StickerClicked -> {
                updateSticker(event.index)
            }
            is ManualEntryEvent.ColorSelected -> {
                _uiState.update { it.copy(selectedColor = event.color) }
            }
            ManualEntryEvent.SolveClicked -> {
                solve()
            }
            ManualEntryEvent.ResetClicked -> {
                _uiState.update { ManualEntryUiState() }
            }
        }
    }

    private fun updateSticker(index: Int) {
        _uiState.update { currentState ->
            val newList = currentState.facelets.toMutableList()
            newList[index] = currentState.selectedColor
            currentState.copy(facelets = newList, solution = null, errorMessage = null)
        }
    }

    private fun solve() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val faceletString = _uiState.value.facelets.joinToString("") { it.char.toString() }
                val cube = CubeParser.fromString(faceletString)
                val moves = solveCubeUseCase.execute(cube)
                val solutionString = NotationParser.toString(moves)
                
                _uiState.update { it.copy(
                    solution = if (solutionString.isEmpty()) "Cube is already solved!" else solutionString,
                    isLoading = false
                )}
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    errorMessage = e.message ?: "Unknown Error",
                    isLoading = false
                )}
            }
        }
    }
}
