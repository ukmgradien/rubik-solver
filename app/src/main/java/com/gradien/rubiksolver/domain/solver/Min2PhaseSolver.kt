package com.gradien.rubiksolver.domain.solver

import com.gradien.rubiksolver.domain.model.Cube
import com.gradien.rubiksolver.domain.model.CubeMove
import com.gradien.rubiksolver.domain.notation.NotationParser
import com.gradien.rubiksolver.domain.parser.CubeParser

/**
 * Implementasi [CubeSolver] yang menggunakan library min2phase (direpresentasikan oleh Search).
 * Class ini bertugas melakukan orkestrasi antara:
 * 1. Konversi Cube -> String
 * 2. Pemanggilan library solver
 * 3. Konversi String Solution -> List<CubeMove>
 */
class Min2PhaseSolver : CubeSolver {

    override fun solve(cube: Cube): List<CubeMove> {
        // 1. Cube -> String (Representasi yang dimengerti min2phase)
        val facelets = CubeParser.toString(cube)

        // 2. Panggil library min2phase via Search bridge
        val solutionString = Search.solution(facelets) ?: ""

        // 3. String Solution -> List<CubeMove>
        return NotationParser.parse(solutionString)
    }
}
