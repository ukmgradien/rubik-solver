package com.gradien.rubiksolver.domain.engine

import com.gradien.rubiksolver.domain.model.Cube
import com.gradien.rubiksolver.domain.model.CubeColor
import com.gradien.rubiksolver.domain.model.CubeFace
import com.gradien.rubiksolver.domain.model.CubeMove

/**
 * Engine simulasi rotasi Rubik menggunakan metode Facelet Permutation.
 * Menggunakan pemetaan indeks 0-53 yang standar.
 * Urutan Facelets: U, R, F, D, L, B (9 karakter per sisi)
 */
object RotationEngine {

    // Indices for each face
    private const val U = 0
    private const val R = 9
    private const val F = 18
    private const val D = 27
    private const val L = 36
    private const val B = 45

    /**
     * Melakukan rotasi pada Cube.
     */
    fun rotate(cube: Cube, move: CubeMove): Cube {
        val facelets = cubeToFacelets(cube).toCharArray()
        val moveIdx = move.ordinal / 3
        val repeatCount = (move.ordinal % 3) + 1

        var currentFacelets = facelets
        repeat(repeatCount) {
            currentFacelets = applyBasicMove(currentFacelets, moveIdx)
        }

        return faceletsToCube(String(currentFacelets))
    }

    private fun applyBasicMove(f: CharArray, moveIdx: Int): CharArray {
        val next = f.copyOf()
        when (moveIdx) {
            0 -> rotateU(next, f)
            1 -> rotateR(next, f)
            2 -> rotateF(next, f)
            3 -> rotateD(next, f)
            4 -> rotateL(next, f)
            5 -> rotateB(next, f)
        }
        return next
    }

    private fun rotateFace(next: CharArray, current: CharArray, faceOffset: Int) {
        next[faceOffset + 0] = current[faceOffset + 6]
        next[faceOffset + 1] = current[faceOffset + 3]
        next[faceOffset + 2] = current[faceOffset + 0]
        next[faceOffset + 3] = current[faceOffset + 7]
        next[faceOffset + 4] = current[faceOffset + 4]
        next[faceOffset + 5] = current[faceOffset + 1]
        next[faceOffset + 6] = current[faceOffset + 8]
        next[faceOffset + 7] = current[faceOffset + 5]
        next[faceOffset + 8] = current[faceOffset + 2]
    }

    private fun rotateU(next: CharArray, f: CharArray) {
        rotateFace(next, f, U)
        // Cycle: F(0,1,2) -> L(0,1,2) -> B(0,1,2) -> R(0,1,2) -> F(0,1,2)
        // Target <- Source
        next[L + 0] = f[F + 0]; next[L + 1] = f[F + 1]; next[L + 2] = f[F + 2]
        next[B + 0] = f[L + 0]; next[B + 1] = f[L + 1]; next[B + 2] = f[L + 2]
        next[R + 0] = f[B + 0]; next[R + 1] = f[B + 1]; next[R + 2] = f[B + 2]
        next[F + 0] = f[R + 0]; next[F + 1] = f[R + 1]; next[F + 2] = f[R + 2]
    }

    private fun rotateR(next: CharArray, f: CharArray) {
        rotateFace(next, f, R)
        // Cycle: U(2,5,8) -> B(6,3,0) -> D(2,5,8) -> F(2,5,8) -> U(2,5,8)
        // Target <- Source
        next[B + 6] = f[U + 2]; next[B + 3] = f[U + 5]; next[B + 0] = f[U + 8]
        next[D + 2] = f[B + 6]; next[D + 5] = f[B + 3]; next[D + 8] = f[B + 0]
        next[F + 2] = f[D + 2]; next[F + 5] = f[D + 5]; next[F + 8] = f[D + 8]
        next[U + 2] = f[F + 2]; next[U + 5] = f[F + 5]; next[U + 8] = f[F + 8]
    }

    private fun rotateF(next: CharArray, f: CharArray) {
        rotateFace(next, f, F)
        // Cycle: U(6,7,8) -> R(0,3,6) -> D(2,1,0) -> L(8,5,2) -> U(6,7,8)
        next[R + 0] = f[U + 6]; next[R + 3] = f[U + 7]; next[R + 6] = f[U + 8]
        next[D + 2] = f[R + 0]; next[D + 1] = f[R + 3]; next[D + 0] = f[R + 6]
        next[L + 8] = f[D + 2]; next[L + 5] = f[D + 1]; next[L + 2] = f[D + 0]
        next[U + 6] = f[L + 8]; next[U + 7] = f[L + 5]; next[U + 8] = f[L + 2]
    }

    private fun rotateD(next: CharArray, f: CharArray) {
        rotateFace(next, f, D)
        // Cycle: F(6,7,8) -> R(6,7,8) -> B(6,7,8) -> L(6,7,8) -> F(6,7,8)
        next[R + 6] = f[F + 6]; next[R + 7] = f[F + 7]; next[R + 8] = f[F + 8]
        next[B + 6] = f[R + 6]; next[B + 7] = f[R + 7]; next[B + 8] = f[R + 8]
        next[L + 6] = f[B + 6]; next[L + 7] = f[B + 7]; next[L + 8] = f[B + 8]
        next[F + 6] = f[L + 6]; next[F + 7] = f[L + 7]; next[F + 8] = f[L + 8]
    }

    private fun rotateL(next: CharArray, f: CharArray) {
        rotateFace(next, f, L)
        // Cycle: U(0,3,6) -> F(0,3,6) -> D(0,3,6) -> B(8,5,2) -> U(0,3,6)
        next[F + 0] = f[U + 0]; next[F + 3] = f[U + 3]; next[F + 6] = f[U + 6]
        next[D + 0] = f[F + 0]; next[D + 3] = f[F + 3]; next[D + 6] = f[F + 6]
        next[B + 8] = f[D + 0]; next[B + 5] = f[D + 3]; next[B + 2] = f[D + 6]
        next[U + 0] = f[B + 8]; next[U + 3] = f[B + 5]; next[U + 6] = f[B + 2]
    }

    private fun rotateB(next: CharArray, f: CharArray) {
        rotateFace(next, f, B)
        // Cycle: U(2,1,0) -> L(0,3,6) -> D(6,7,8) -> R(8,5,2) -> U(2,1,0)
        next[L + 0] = f[U + 2]; next[L + 3] = f[U + 1]; next[L + 6] = f[U + 0]
        next[D + 6] = f[L + 0]; next[D + 7] = f[L + 3]; next[D + 8] = f[L + 6]
        next[R + 8] = f[D + 6]; next[R + 5] = f[D + 7]; next[R + 2] = f[D + 8]
        next[U + 2] = f[R + 8]; next[U + 1] = f[R + 5]; next[U + 0] = f[R + 2]
    }

    private fun cubeToFacelets(cube: Cube): String {
        return (cube.up.stickers + cube.right.stickers + cube.front.stickers +
                cube.down.stickers + cube.left.stickers + cube.back.stickers)
            .joinToString("") { it.char.toString() }
    }

    private fun faceletsToCube(s: String): Cube {
        val c = s.map { char -> CubeColor.fromChar(char)!! }
        return Cube(
            up = CubeFace(c.subList(0, 9)),
            right = CubeFace(c.subList(9, 18)),
            front = CubeFace(c.subList(18, 27)),
            down = CubeFace(c.subList(27, 36)),
            left = CubeFace(c.subList(36, 45)),
            back = CubeFace(c.subList(45, 54))
        )
    }
}
