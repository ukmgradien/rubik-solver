package com.gradien.rubiksolver.presentation.manual

import com.gradien.rubiksolver.domain.model.CubeColor
import com.gradien.rubiksolver.domain.model.CubeMove

data class ManualEntryUiState(
    val facelets: List<CubeColor> = List(54) { CubeColor.WHITE },
    val selectedColor: CubeColor = CubeColor.WHITE,
    val steps: List<SolutionStep> = emptyList(),
    val currentStepIndex: Int = 0,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSolved: Boolean = false
)

data class SolutionStep(
    val stepNumber: Int,
    val totalSteps: Int,
    val notation: String,
    val description: String
)

fun CubeMove.toDescription(): String {
    return when (this) {
        CubeMove.U -> "Putar sisi Atas searah jarum jam"
        CubeMove.U2 -> "Putar sisi Atas dua kali"
        CubeMove.U_PRIME -> "Putar sisi Atas berlawanan jarum jam"
        CubeMove.R -> "Putar sisi Kanan searah jarum jam"
        CubeMove.R2 -> "Putar sisi Kanan dua kali"
        CubeMove.R_PRIME -> "Putar sisi Kanan berlawanan jarum jam"
        CubeMove.F -> "Putar sisi Depan searah jarum jam"
        CubeMove.F2 -> "Putar sisi Depan dua kali"
        CubeMove.F_PRIME -> "Putar sisi Depan berlawanan jarum jam"
        CubeMove.D -> "Putar sisi Bawah searah jarum jam"
        CubeMove.D2 -> "Putar sisi Bawah dua kali"
        CubeMove.D_PRIME -> "Putar sisi Bawah berlawanan jarum jam"
        CubeMove.L -> "Putar sisi Kiri searah jarum jam"
        CubeMove.L2 -> "Putar sisi Kiri dua kali"
        CubeMove.L_PRIME -> "Putar sisi Kiri berlawanan jarum jam"
        CubeMove.B -> "Putar sisi Belakang searah jarum jam"
        CubeMove.B2 -> "Putar sisi Belakang dua kali"
        CubeMove.B_PRIME -> "Putar sisi Belakang berlawanan jarum jam"
    }
}