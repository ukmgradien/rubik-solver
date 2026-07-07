package com.gradien.rubiksolver.presentation

sealed class Screen {
    object Home : Screen()
    object ManualEntry : Screen()
    object Settings : Screen()
}