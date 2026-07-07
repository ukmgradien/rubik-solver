package com.gradien.rubiksolver.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gradien.rubiksolver.presentation.util.playClickSound
import com.gradien.rubiksolver.ui.theme.RubikSolverTheme

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit
) {
    val accentColor = MaterialTheme.colorScheme.primary
    val bgColor = MaterialTheme.colorScheme.background
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = uiState.title,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = accentColor,
            textAlign = TextAlign.Center,
            letterSpacing = 2.sp
        )

        Text(
            text = uiState.description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
        )

        HomeButton(
            text = "Solve Cube",
            isPrimary = true,
            accentColor = accentColor,
            bgColor = bgColor,
            onClick = {
                playClickSound(context)
                onEvent(HomeEvent.SolveClicked)
            }
        )

        HomeButton(
            text = "Learn",
            isPrimary = false,
            accentColor = accentColor,
            bgColor = bgColor,
            onClick = {
                playClickSound(context)
                onEvent(HomeEvent.LearnClicked)
            },
            modifier = Modifier.padding(top = 12.dp)
        )

        HomeButton(
            text = "History",
            isPrimary = false,
            accentColor = accentColor,
            bgColor = bgColor,
            onClick = {
                playClickSound(context)
                onEvent(HomeEvent.HistoryClicked)
            },
            modifier = Modifier.padding(top = 12.dp)
        )

        HomeButton(
            text = "Settings",
            isPrimary = false,
            accentColor = accentColor,
            bgColor = bgColor,
            onClick = {
                playClickSound(context)
                onEvent(HomeEvent.SettingsClicked)
            },
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
fun HomeButton(
    text: String,
    isPrimary: Boolean,
    accentColor: androidx.compose.ui.graphics.Color,
    bgColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isPrimary) {
        Button(
            onClick = onClick,
            modifier = modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor,
                contentColor = bgColor
            )
        ) {
            Text(text, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(8.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = accentColor)
        ) {
            Text(text, fontSize = 15.sp)
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