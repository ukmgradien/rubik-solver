package com.gradien.rubiksolver.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun HomeRoute(
    viewModel: HomeViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}