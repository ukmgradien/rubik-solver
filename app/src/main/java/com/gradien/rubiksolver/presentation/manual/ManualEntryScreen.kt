package com.gradien.rubiksolver.presentation.manual

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gradien.rubiksolver.domain.model.CubeColor

@Composable
fun ManualEntryScreen(
    uiState: ManualEntryUiState,
    onEvent: (ManualEntryEvent) -> Unit,
    onBack: () -> Unit = {}
) {
    val accentColor = MaterialTheme.colorScheme.primary
    val bgColor = MaterialTheme.colorScheme.background

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onBack,
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = accentColor
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(text = "← Kembali", fontSize = 13.sp)
            }
            Text(
                text = "Masukkan Warna Rubik",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = accentColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(80.dp))
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            CubeNetLayout(
                facelets = uiState.facelets,
                onStickerClick = { index -> onEvent(ManualEntryEvent.StickerClicked(index)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.errorMessage != null) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = "⚠ ${uiState.errorMessage}",
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp
                )
            }
        } else if (uiState.isSolved) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .border(1.dp, accentColor, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = "✅ Rubik sudah tersolved!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        } else if (uiState.steps.isNotEmpty()) {
            val step = uiState.steps[uiState.currentStepIndex]

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .border(1.dp, accentColor, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "LANGKAH ${step.stepNumber} DARI ${step.totalSteps}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = accentColor.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = step.notation,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider(
                        color = accentColor.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = step.description,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = { onEvent(ManualEntryEvent.PreviousStep) },
                    enabled = uiState.currentStepIndex > 0,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = accentColor),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
                ) {
                    Text("← Sebelumnya")
                }
                Button(
                    onClick = { onEvent(ManualEntryEvent.NextStep) },
                    enabled = uiState.currentStepIndex < uiState.steps.size - 1,
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor, contentColor = bgColor)
                ) {
                    Text("Selanjutnya →")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ColorPalette(
            selectedColor = uiState.selectedColor,
            onColorSelect = { onEvent(ManualEntryEvent.ColorSelected(it)) }
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = { onEvent(ManualEntryEvent.ResetClicked) },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
            ) {
                Text("Reset")
            }
            Button(
                onClick = { onEvent(ManualEntryEvent.SolveClicked) },
                enabled = !uiState.isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = accentColor, contentColor = bgColor)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = bgColor
                    )
                } else {
                    Text("Solve", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun CubeNetLayout(
    facelets: List<CubeColor>,
    onStickerClick: (Int) -> Unit
) {
    val stickerSize = 36.dp
    val faceWidth = stickerSize * 3 + 4.dp

    Column(horizontalAlignment = Alignment.Start) {
        Row {
            Spacer(modifier = Modifier.width(faceWidth))
            FaceGrid(facelets.subList(0, 9), 0, "U", onStickerClick)
        }
        Row {
            FaceGrid(facelets.subList(36, 45), 36, "L", onStickerClick)
            FaceGrid(facelets.subList(18, 27), 18, "F", onStickerClick)
            FaceGrid(facelets.subList(9, 18), 9, "R", onStickerClick)
            FaceGrid(facelets.subList(45, 54), 45, "B", onStickerClick)
        }
        Row {
            Spacer(modifier = Modifier.width(faceWidth))
            FaceGrid(facelets.subList(27, 36), 27, "D", onStickerClick)
        }
    }
}

@Composable
fun FaceGrid(
    stickers: List<CubeColor>,
    offset: Int,
    label: String,
    onStickerClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(2.dp)
            .border(1.dp, Color(0xFF444444))
    ) {
        for (row in 0 until 3) {
            Row {
                for (col in 0 until 3) {
                    val index = row * 3 + col
                    val isCenter = index == 4
                    StickerBox(
                        color = stickers[index],
                        label = if (isCenter) label else "",
                        onClick = { onStickerClick(offset + index) }
                    )
                }
            }
        }
    }
}

@Composable
fun StickerBox(color: CubeColor, label: String, onClick: () -> Unit) {
    val composeColor = color.toComposeColor()
    val contentColor = if (composeColor.luminance() > 0.5f) Color.Black else Color.White

    Box(
        modifier = Modifier
            .size(36.dp)
            .padding(1.dp)
            .background(composeColor, RoundedCornerShape(4.dp))
            .border(0.5.dp, Color(0xFF333333), RoundedCornerShape(4.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

@Composable
fun ColorPalette(
    selectedColor: CubeColor,
    onColorSelect: (CubeColor) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        CubeColor.entries.forEach { color ->
            val isSelected = selectedColor == color
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp)
                    .background(color.toComposeColor(), RoundedCornerShape(8.dp))
                    .border(
                        width = if (isSelected) 2.dp else 0.dp,
                        color = if (isSelected) Color.White else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onColorSelect(color) }
            )
        }
    }
}

fun CubeColor.toComposeColor(): Color {
    return when (this) {
        CubeColor.WHITE -> Color.White
        CubeColor.RED -> Color(0xFFFF3D3D)
        CubeColor.GREEN -> Color(0xFF00C853)
        CubeColor.YELLOW -> Color(0xFFFFD600)
        CubeColor.ORANGE -> Color(0xFFFF6D00)
        CubeColor.BLUE -> Color(0xFF2979FF)
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ManualEntryPreview() {
    com.gradien.rubiksolver.ui.theme.RubikSolverTheme {
        ManualEntryScreen(
            uiState = ManualEntryUiState(
                steps = listOf(
                    SolutionStep(1, 5, "R", "Putar sisi Kanan searah jarum jam"),
                    SolutionStep(2, 5, "U2", "Putar sisi Atas dua kali"),
                    SolutionStep(3, 5, "F'", "Putar sisi Depan berlawanan jarum jam"),
                ),
                currentStepIndex = 0
            ),
            onEvent = {}
        )
    }
}