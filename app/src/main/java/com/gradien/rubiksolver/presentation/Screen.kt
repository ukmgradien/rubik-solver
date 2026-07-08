package com.gradien.rubiksolver.presentation

sealed class Screen {
    object Home : Screen()
    object ManualEntry : Screen()
    object Settings : Screen()
    object History : Screen()
    data class SolutionDetail(val entry: com.gradien.rubiksolver.domain.model.HistoryEntry) : Screen()
}