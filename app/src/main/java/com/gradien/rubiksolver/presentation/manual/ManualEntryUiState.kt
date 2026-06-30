package com.gradien.rubiksolver.presentation.manual

import com.gradien.rubiksolver.domain.model.CubeColor

/**
 * State untuk layar input warna manual.
 * @property facelets List dari 54 warna sticker (U, R, F, D, L, B).
 * @property selectedColor Warna yang sedang aktif dipilih dari palet.
 * @property solution Solusi string dari solver.
 * @property errorMessage Pesan error jika validasi gagal.
 */
data class ManualEntryUiState(
    val facelets: List<CubeColor> = List(54) { CubeColor.WHITE },
    val selectedColor: CubeColor = CubeColor.WHITE,
    val solution: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
