package com.gradien.rubiksolver.domain.validator

import com.gradien.rubiksolver.domain.model.Cube
import com.gradien.rubiksolver.domain.model.CubeColor

/**
 * Hasil dari proses validasi Cube.
 */
sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val message: String) : ValidationResult()

    val isValid: Boolean get() = this is Valid
}

/**
 * Validator untuk memastikan Cube berada dalam kondisi yang valid dan dapat diselesaikan.
 */
object CubeValidator {

    fun validate(cube: Cube): ValidationResult {
        // 1. Cek jumlah total sticker
        val allStickers = getAllStickers(cube)
        if (allStickers.size != 54) {
            return ValidationResult.Invalid("Total sticker harus 54, ditemukan ${allStickers.size}.")
        }

        // 2. Cek jumlah masing-masing warna harus tepat 9
        val colorCounts = allStickers.groupingBy { it }.eachCount()
        for (color in CubeColor.entries) {
            val count = colorCounts[color] ?: 0
            if (count != 9) {
                return ValidationResult.Invalid("Warna ${color.name} harus berjumlah 9, ditemukan $count.")
            }
        }

        // 3. Cek center sticker (setiap sisi harus memiliki warna pusat yang berbeda)
        val centers = listOf(
            cube.up.stickers[4], cube.down.stickers[4],
            cube.left.stickers[4], cube.right.stickers[4],
            cube.front.stickers[4], cube.back.stickers[4]
        )
        if (centers.distinct().size != 6) {
            return ValidationResult.Invalid("Setiap sisi harus memiliki warna pusat yang unik.")
        }

        // Catatan: Validasi solvabilitas tingkat lanjut (corner/edge parity) 
        // biasanya membutuhkan konversi ke CubieCube (package state).
        // Untuk tahap awal, validasi komposisi warna sudah cukup.

        return ValidationResult.Valid
    }

    private fun getAllStickers(cube: Cube): List<CubeColor> {
        return cube.up.stickers + cube.right.stickers + cube.front.stickers +
                cube.down.stickers + cube.left.stickers + cube.back.stickers
    }
}
