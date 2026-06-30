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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gradien.rubiksolver.domain.model.CubeColor

@Composable
fun ManualEntryScreen(
    uiState: ManualEntryUiState,
    onEvent: (ManualEntryEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Manual Color Entry",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Cube Layout (2D Net) with Horizontal Scroll to prevent cutting off
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

        // Solution or Error Message
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
        } else if (uiState.solution != null) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Solution:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = uiState.solution, fontSize = 16.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Color Palette
        ColorPalette(
            selectedColor = uiState.selectedColor,
            onColorSelect = { onEvent(ManualEntryEvent.ColorSelected(it)) }
        )

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { onEvent(ManualEntryEvent.ResetClicked) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
            ) {
                Text("Reset")
            }
            Button(
                onClick = { onEvent(ManualEntryEvent.SolveClicked) },
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text("Solve")
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
    // Standard layout: 
    //    U
    //  L F R B
    //    D
    
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Face U (Up)
        FaceGrid(facelets.subList(0, 9), 0, "U", onStickerClick)
        
        Row {
            // Face L (Left)
            FaceGrid(facelets.subList(36, 45), 36, "L", onStickerClick)
            // Face F (Front)
            FaceGrid(facelets.subList(18, 27), 18, "F", onStickerClick)
            // Face R (Right)
            FaceGrid(facelets.subList(9, 18), 9, "R", onStickerClick)
            // Face B (Back)
            FaceGrid(facelets.subList(45, 54), 45, "B", onStickerClick)
        }
        
        // Face D (Down)
        FaceGrid(facelets.subList(27, 36), 27, "D", onStickerClick)
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
            .border(1.dp, Color.Black)
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
            .size(32.dp) // Adjusted size
            .padding(1.dp)
            .background(composeColor)
            .border(0.5.dp, Color.Gray)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                fontSize = 12.sp,
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
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .padding(4.dp)
                    .background(color.toComposeColor(), RoundedCornerShape(4.dp))
                    .border(
                        width = if (selectedColor == color) 3.dp else 1.dp,
                        color = if (selectedColor == color) Color.Black else Color.Transparent,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { onColorSelect(color) }
            )
        }
    }
}

fun CubeColor.toComposeColor(): Color {
    return when (this) {
        CubeColor.WHITE -> Color.White
        CubeColor.RED -> Color.Red
        CubeColor.GREEN -> Color.Green
        CubeColor.YELLOW -> Color.Yellow
        CubeColor.ORANGE -> Color(0xFFFFA500)
        CubeColor.BLUE -> Color.Blue
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ManualEntryPreview() {
    com.gradien.rubiksolver.ui.theme.RubikSolverTheme {
        ManualEntryScreen(
            uiState = ManualEntryUiState(
                solution = "U R2 F L B' D2"
            ),
            onEvent = {}
        )
    }
}
