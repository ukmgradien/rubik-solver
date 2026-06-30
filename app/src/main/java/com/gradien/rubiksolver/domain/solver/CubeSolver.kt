package com.gradien.rubiksolver.domain.solver

import com.gradien.rubiksolver.domain.model.Cube
import com.gradien.rubiksolver.domain.model.CubeMove

/**
 * Interface untuk solver Rubik.
 * Memungkinkan penggantian implementasi solver (misal: Min2Phase, Kociemba, dsb)
 * tanpa mengubah layer lain.
 */
interface CubeSolver {

    /**
     * Mencari daftar pergerakan untuk menyelesaikan Cube.
     * @param cube Objek domain Cube.
     * @return List dari CubeMove.
     */
    fun solve(cube: Cube): List<CubeMove>

}
