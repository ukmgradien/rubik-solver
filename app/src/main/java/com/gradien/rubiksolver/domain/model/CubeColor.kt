package com.gradien.rubiksolver.domain.model

/**
 * Representasi warna pada sticker Rubik.
 * Menggunakan karakter standar yang sering digunakan dalam notasi Kociemba (U, R, F, D, L, B).
 */
enum class CubeColor(val char: Char) {
    WHITE('U'),  // Up
    RED('R'),    // Right
    GREEN('F'),  // Front
    YELLOW('D'), // Down
    ORANGE('L'), // Left
    BLUE('B');   // Back

    companion object {
        fun fromChar(char: Char): CubeColor? {
            return entries.find { it.char == char.uppercaseChar() }
        }
    }
}
