package com.gradien.rubiksolver.domain.parser

import com.gradien.rubiksolver.domain.model.Cube
import com.gradien.rubiksolver.domain.model.CubeColor
import com.gradien.rubiksolver.domain.model.CubeFace

/**
 * Parser untuk mengubah representasi String menjadi objek Cube.
 * Format string yang diharapkan adalah 54 karakter (U, R, F, D, L, B).
 * Urutan: 9 karakter Up, 9 Right, 9 Front, 9 Down, 9 Left, 9 Back.
 */
object CubeParser {

    fun fromString(input: String): Cube {
        val cleanedInput = input.filter { !it.isWhitespace() }
        require(cleanedInput.length == 54) { "Input string harus memiliki tepat 54 karakter warna." }

        val colors = cleanedInput.map { char ->
            CubeColor.fromChar(char) ?: throw IllegalArgumentException("Karakter '$char' bukan warna Rubik yang valid.")
        }

        return Cube(
            up = CubeFace(colors.subList(0, 9)),
            right = CubeFace(colors.subList(9, 18)),
            front = CubeFace(colors.subList(18, 27)),
            down = CubeFace(colors.subList(27, 36)),
            left = CubeFace(colors.subList(36, 45)),
            back = CubeFace(colors.subList(45, 54))
        )
    }

    fun toString(cube: Cube): String {
        return buildString {
            append(cube.up.stickers.joinToString("") { it.char.toString() })
            append(cube.right.stickers.joinToString("") { it.char.toString() })
            append(cube.front.stickers.joinToString("") { it.char.toString() })
            append(cube.down.stickers.joinToString("") { it.char.toString() })
            append(cube.left.stickers.joinToString("") { it.char.toString() })
            append(cube.back.stickers.joinToString("") { it.char.toString() })
        }
    }
}
