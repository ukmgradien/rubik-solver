package com.gradien.rubiksolver.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gradien.rubiksolver.ui.theme.RubikSolverTheme

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = uiState.title,
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = uiState.description,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onEvent(HomeEvent.SolveClicked)
            }
        ) {
            Text("Solve Cube")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = {
                onEvent(HomeEvent.LearnClicked)
            }
        ) {
            Text("Learn")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = {
                onEvent(HomeEvent.HistoryClicked)
            }
        ) {
            Text("History")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = {
                onEvent(HomeEvent.SettingsClicked)
            }
        ) {
            Text("Settings")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {

    RubikSolverTheme {

        Surface {

            HomeScreen(
                uiState = HomeUiState(),
                onEvent = {}
            )

        }

    }

}