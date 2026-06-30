package com.gradien.rubiksolver.domain.usecase

import com.gradien.rubiksolver.domain.model.Cube
import com.gradien.rubiksolver.domain.model.CubeMove
import com.gradien.rubiksolver.domain.solver.CubeSolver
import com.gradien.rubiksolver.domain.validator.CubeValidator
import com.gradien.rubiksolver.domain.validator.ValidationResult

/**
 * UseCase untuk menyelesaikan Rubik.
 * Ini adalah pintu masuk utama dari layer Presentation/UI ke layer Domain.
 * Alur: Validasi -> Solve.
 */
class SolveCubeUseCase(
    private val solver: CubeSolver
) {

    /**
     * Menjalankan proses pencarian solusi.
     * @throws IllegalArgumentException jika Cube tidak valid.
     */
    fun execute(cube: Cube): List<CubeMove> {
        // 1. Validasi
        val validationResult = CubeValidator.validate(cube)
        if (validationResult is ValidationResult.Invalid) {
            throw IllegalArgumentException(validationResult.message)
        }

        // 2. Solve
        return solver.solve(cube)
    }
}
