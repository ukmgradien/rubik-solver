package com.gradien.rubiksolver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.gradien.rubiksolver.domain.solver.Min2PhaseSolver
import com.gradien.rubiksolver.domain.usecase.SolveCubeUseCase
import com.gradien.rubiksolver.presentation.Screen
import com.gradien.rubiksolver.presentation.home.HomeNavigationEvent
import com.gradien.rubiksolver.presentation.home.HomeRoute
import com.gradien.rubiksolver.presentation.home.HomeViewModel
import com.gradien.rubiksolver.presentation.manual.ManualEntryRoute
import com.gradien.rubiksolver.presentation.manual.ManualEntryViewModel
import com.gradien.rubiksolver.ui.theme.RubikSolverTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Simple DI manual untuk keperluan tugas ini
        val solver = Min2PhaseSolver()
        val solveCubeUseCase = SolveCubeUseCase(solver)
        
        val homeViewModel = HomeViewModel()
        val manualViewModel = ManualEntryViewModel(solveCubeUseCase)

        setContent {
            RubikSolverTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

                // Handle Navigation Event dari Home
                LaunchedEffect(Unit) {
                    homeViewModel.navigationEvent.collect { event ->
                        when (event) {
                            HomeNavigationEvent.NavigateToManualEntry -> {
                                currentScreen = Screen.ManualEntry
                            }
                        }
                    }
                }

                when (currentScreen) {
                    Screen.Home -> {
                        HomeRoute(viewModel = homeViewModel)
                    }
                    Screen.ManualEntry -> {
                        ManualEntryRoute(viewModel = manualViewModel)
                    }
                }
            }
        }
    }
}
