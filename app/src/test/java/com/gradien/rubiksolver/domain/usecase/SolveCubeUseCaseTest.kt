package com.gradien.rubiksolver.domain.usecase

import com.gradien.rubiksolver.domain.factory.CubeFactory
import com.gradien.rubiksolver.domain.model.CubeMove
import com.gradien.rubiksolver.domain.solver.Min2PhaseSolver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SolveCubeUseCaseTest {

    private val solver = Min2PhaseSolver()
    private val solveCubeUseCase = SolveCubeUseCase(solver)

    @Test
    fun `test solve solved cube returns empty list`() {
        // GIVEN: Cube yang sudah terpecahkan
        val solvedCube = CubeFactory.solvedCube()
        
        // WHEN: Menjalankan UseCase
        val moves = solveCubeUseCase.execute(solvedCube)
        
        // THEN: Tidak ada langkah yang diperlukan
        assertTrue("Solved cube harus mengembalikan daftar kosong", moves.isEmpty())
    }

    @Test
    fun `test solve scrambled cube returns moves`() {
        // GIVEN: Cube yang diacak menggunakan RotationEngine agar valid
        val solvedCube = CubeFactory.solvedCube()
        val scrambledCube = com.gradien.rubiksolver.domain.engine.RotationEngine.rotate(solvedCube, CubeMove.R)

        // WHEN: Menjalankan UseCase
        val moves = solveCubeUseCase.execute(scrambledCube)
        
        // THEN: Harus mengembalikan list move yang valid (solusinya R')
        assertTrue("Scrambled cube harus menghasilkan setidaknya satu langkah", moves.isNotEmpty())
        assertEquals(CubeMove.R_PRIME, moves.first())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test solve invalid length throws exception`() {
        // GIVEN: String dengan panjang tidak valid (kurang dari 54)
        val invalidString = "UUU"
        
        // WHEN & THEN: CubeFactory akan melempar exception saat parsing
        CubeFactory.fromString(invalidString)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test solve invalid color counts throws exception`() {
        // GIVEN: 54 karakter tapi jumlah warna salah (misal: 10 'U' dan 8 'B')
        val invalidColorCounts = "UUUUUUUUUU" + "RRRRRRRRR" + "FFFFFFFFF" + "DDDDDDDDD" + "LLLLLLLLL" + "BBBBBBBB"
        val cube = CubeFactory.fromString(invalidColorCounts)
        
        // WHEN: Validator dipanggil di dalam UseCase
        // THEN: Harus melempar IllegalArgumentException
        solveCubeUseCase.execute(cube)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test solve duplicate center colors throws exception`() {
        // GIVEN: Center (index 4 di setiap face) ada yang sama warnanya
        // Index center di string 54 karakter: 4 (U), 13 (R), 22 (F), 31 (D), 40 (L), 49 (B)
        val stringChars = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB".toCharArray()
        stringChars[13] = 'U' // Center Right diubah jadi U (sama dengan center Up)
        // Kita sesuaikan warna lain agar total tetap 9 per warna supaya lolos cek pertama
        stringChars[0] = 'R' 
        
        val cube = CubeFactory.fromString(String(stringChars))
        
        // WHEN: UseCase dijalankan
        // THEN: Validator mendeteksi duplicate center
        solveCubeUseCase.execute(cube)
    }
}
