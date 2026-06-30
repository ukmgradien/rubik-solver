package com.gradien.rubiksolver.presentation.home

sealed class HomeNavigationEvent {
    object NavigateToManualEntry : HomeNavigationEvent()
}
