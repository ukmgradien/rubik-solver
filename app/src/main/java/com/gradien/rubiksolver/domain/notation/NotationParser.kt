package com.gradien.rubiksolver.domain.notation

import com.gradien.rubiksolver.domain.model.CubeMove

/**
 * Parser untuk notasi pergerakan Rubik.
 */
object NotationParser {

    /**
     * Mengubah string notasi (misal: "R U R' U2") menjadi daftar CubeMove.
     */
    fun parse(notation: String): List<CubeMove> {
        if (notation.isBlank()) return emptyList()
        
        return notation.trim().split(Regex("\\s+")).map { part ->
            CubeMove.fromNotation(part) 
                ?: throw IllegalArgumentException("Notasi '$part' tidak dikenali.")
        }
    }

    /**
     * Mengubah daftar CubeMove kembali menjadi string notasi.
     */
    fun toString(moves: List<CubeMove>): String {
        return moves.joinToString(" ") { it.notation }
    }
}
