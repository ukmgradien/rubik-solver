package com.gradien.rubiksolver.domain.factory

import com.gradien.rubiksolver.domain.model.Cube
import com.gradien.rubiksolver.domain.parser.CubeParser

/**
 * Factory untuk membuat objek [Cube].
 * Memudahkan pembuatan Cube dalam berbagai kondisi (Solved, Custom, dsb).
 */
object CubeFactory {

    /**
     * Membuat Cube dalam kondisi sudah terpecahkan (Solved).
     */
    fun solvedCube(): Cube {
        return CubeParser.fromString("UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB")
    }

    /**
     * Membuat Cube dari representasi String (54 karakter).
     */
    fun fromString(input: String): Cube {
        return CubeParser.fromString(input)
    }
}
