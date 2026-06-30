package com.gradien.rubiksolver.domain.notation

import com.gradien.rubiksolver.domain.model.CubeMove

object CubeNotation {

    private val notationMap = mapOf(
        "R" to CubeMove.R,
        "R'" to CubeMove.R_PRIME,
        "R2" to CubeMove.R2,

        "L" to CubeMove.L,
        "L'" to CubeMove.L_PRIME,
        "L2" to CubeMove.L2,

        "U" to CubeMove.U,
        "U'" to CubeMove.U_PRIME,
        "U2" to CubeMove.U2,

        "D" to CubeMove.D,
        "D'" to CubeMove.D_PRIME,
        "D2" to CubeMove.D2,

        "F" to CubeMove.F,
        "F'" to CubeMove.F_PRIME,
        "F2" to CubeMove.F2,

        "B" to CubeMove.B,
        "B'" to CubeMove.B_PRIME,
        "B2" to CubeMove.B2
    )

    /**
     * Mengubah satu notasi menjadi CubeMove.
     *
     * Contoh:
     * "R" -> CubeMove.R
     */
    fun parseMove(notation: String): CubeMove {
        return notationMap[notation.trim()]
            ?: throw IllegalArgumentException("Invalid notation: $notation")
    }

    /**
     * Mengubah algoritma menjadi list CubeMove.
     *
     * Contoh:
     * "R U R' U'"
     */
    fun parseAlgorithm(algorithm: String): List<CubeMove> {
        if (algorithm.isBlank()) {
            return emptyList()
        }

        return algorithm
            .trim()
            .split(Regex("\\s+"))
            .map(::parseMove)
    }

    /**
     * Mengubah CubeMove menjadi notasi Rubik.
     */
    fun toNotation(move: CubeMove): String {
        return notationMap.entries
            .first { it.value == move }
            .key
    }

    /**
     * Mengubah list CubeMove menjadi algoritma.
     */
    fun toAlgorithm(moves: List<CubeMove>): String {
        return moves.joinToString(" ") {
            toNotation(it)
        }
    }

    /**
     * Mengecek apakah notasi valid.
     */
    fun isValidMove(notation: String): Boolean {
        return notationMap.containsKey(notation.trim())
    }

    /**
     * Mengecek apakah seluruh algoritma valid.
     */
    fun isValidAlgorithm(algorithm: String): Boolean {
        if (algorithm.isBlank()) {
            return false
        }

        return algorithm
            .trim()
            .split(Regex("\\s+"))
            .all(::isValidMove)
    }
}