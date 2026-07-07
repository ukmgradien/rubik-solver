package com.gradien.rubiksolver.presentation.home

sealed interface HomeEvent {
    data object SolveClicked : HomeEvent
    data object LearnClicked : HomeEvent
    data object HistoryClicked : HomeEvent
    data object SettingsClicked : HomeEvent
}