package com.gradien.rubiksolver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.gradien.rubiksolver.data.local.AppDatabase
import com.gradien.rubiksolver.data.repository.HistoryRepositoryImpl
import com.gradien.rubiksolver.domain.solver.Min2PhaseSolver
import com.gradien.rubiksolver.domain.usecase.SolveCubeUseCase
import com.gradien.rubiksolver.presentation.Screen
import com.gradien.rubiksolver.presentation.history.HistoryScreen
import com.gradien.rubiksolver.presentation.history.HistoryViewModel
import com.gradien.rubiksolver.presentation.history.SolutionDetailScreen
import com.gradien.rubiksolver.presentation.home.HomeNavigationEvent
import com.gradien.rubiksolver.presentation.home.HomeRoute
import com.gradien.rubiksolver.presentation.home.HomeViewModel
import com.gradien.rubiksolver.presentation.manual.ManualEntryRoute
import com.gradien.rubiksolver.presentation.manual.ManualEntryViewModel
import com.gradien.rubiksolver.presentation.settings.SettingsScreen
import com.gradien.rubiksolver.ui.theme.RubikSolverTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val solver = Min2PhaseSolver()
        val solveCubeUseCase = SolveCubeUseCase(solver)

        val database = AppDatabase.getDatabase(this)
        val historyRepository = HistoryRepositoryImpl(database.historyDao())

        val homeViewModel = HomeViewModel()
        val manualViewModel = ManualEntryViewModel(solveCubeUseCase, historyRepository)
        val historyViewModel = HistoryViewModel(historyRepository)

        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            RubikSolverTheme(darkTheme = isDarkMode) {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

                LaunchedEffect(Unit) {
                    homeViewModel.navigationEvent.collect { event ->
                        when (event) {
                            HomeNavigationEvent.NavigateToManualEntry -> {
                                currentScreen = Screen.ManualEntry
                            }
                            HomeNavigationEvent.NavigateToSettings -> {
                                currentScreen = Screen.Settings
                            }
                            HomeNavigationEvent.NavigateToHistory -> {
                                currentScreen = Screen.History
                            }
                        }
                    }
                }

                when (val screen = currentScreen) {
                    Screen.Home -> {
                        HomeRoute(viewModel = homeViewModel)
                    }
                    Screen.ManualEntry -> {
                        ManualEntryRoute(
                            viewModel = manualViewModel,
                            onBack = { currentScreen = Screen.Home }
                        )
                    }
                    Screen.Settings -> {
                        SettingsScreen(
                            isDarkMode = isDarkMode,
                            onToggleDarkMode = { isDarkMode = it },
                            onBack = { currentScreen = Screen.Home }
                        )
                    }
                    Screen.History -> {
                        HistoryScreen(
                            viewModel = historyViewModel,
                            onBack = { currentScreen = Screen.Home },
                            onEntryClick = { entry ->
                                currentScreen = Screen.SolutionDetail(entry)
                            }
                        )
                    }
                    is Screen.SolutionDetail -> {
                        SolutionDetailScreen(
                            entry = screen.entry,
                            onBack = { currentScreen = Screen.History }
                        )
                    }
                }
            }
        }
    }
}