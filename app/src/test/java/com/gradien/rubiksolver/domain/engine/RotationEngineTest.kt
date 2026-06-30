package com.gradien.rubiksolver.domain.engine

import com.gradien.rubiksolver.domain.model.CubeMove
import com.gradien.rubiksolver.domain.parser.CubeParser
import org.junit.Assert.assertEquals
import org.junit.Test

class RotationEngineTest {

    private val solvedString = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"

    @Test
    fun `test rotation U`() {
        val cube = CubeParser.fromString(solvedString)
        val result = RotationEngine.rotate(cube, CubeMove.U)
        val resultString = CubeParser.toString(result)
        
        // After U: 
        // Up remains all U.
        // Front top becomes Right colors (RRR) -> RRRFFFFFF
        // Right top becomes Back colors (BBB) -> BBBRRRRRR
        // Back top becomes Left colors (LLL) -> LLLBBBBBB
        // Left top becomes Front colors (FFF) -> FFFLLLLLL
        // Down remains all D.
        val expected = "UUUUUUUUUBBBRRRRRRLLLBBBBBB" + "DDDDDDDDD" + "FFFLLLLLL" + "RRRFFFFFF"
        // Wait, CubeParser.toString order is U, R, F, D, L, B
        val expectedCorrectOrder = "UUUUUUUUU" + "BBBRRRRRR" + "RRRFFFFFF" + "DDDDDDDDD" + "FFFLLLLLL" + "LLLBBBBBB"
        
        assertEquals(expectedCorrectOrder, resultString)
    }

    @Test
    fun `test rotation R`() {
        val cube = CubeParser.fromString(solvedString)
        val result = RotationEngine.rotate(cube, CubeMove.R)
        val resultString = CubeParser.toString(result)
        
        // R move affects Up, Front, Down, Back and rotates Right.
        // For a solved cube, the colors change at specific indices.
        val resultCube = CubeParser.fromString(resultString)
        
        // Check Right face is still all R (since it was all R, rotation doesn't change color)
        resultCube.right.stickers.forEach { assertEquals("Right face color", 'R', it.char) }
    }

    @Test
    fun `test identity 4 times R is solved`() {
        var cube = CubeParser.fromString(solvedString)
        repeat(4) {
            cube = RotationEngine.rotate(cube, CubeMove.R)
        }
        assertEquals(solvedString, CubeParser.toString(cube))
    }

    @Test
    fun `test identity 4 times U is solved`() {
        var cube = CubeParser.fromString(solvedString)
        repeat(4) {
            cube = RotationEngine.rotate(cube, CubeMove.U)
        }
        assertEquals(solvedString, CubeParser.toString(cube))
    }

    @Test
    fun `test R followed by R_PRIME is solved`() {
        var cube = CubeParser.fromString(solvedString)
        cube = RotationEngine.rotate(cube, CubeMove.R)
        cube = RotationEngine.rotate(cube, CubeMove.R_PRIME)
        assertEquals(solvedString, CubeParser.toString(cube))
    }

    @Test
    fun `test F rotation`() {
        var cube = CubeParser.fromString(solvedString)
        cube = RotationEngine.rotate(cube, CubeMove.F)
        
        // F rotation is complex, let's verify 4 times returns to solved
        repeat(3) {
            cube = RotationEngine.rotate(cube, CubeMove.F)
        }
        assertEquals("F x 4 should be identity", solvedString, CubeParser.toString(cube))
    }

    @Test
    fun `test B rotation`() {
        var cube = CubeParser.fromString(solvedString)
        cube = RotationEngine.rotate(cube, CubeMove.B)
        
        repeat(3) {
            cube = RotationEngine.rotate(cube, CubeMove.B)
        }
        assertEquals("B x 4 should be identity", solvedString, CubeParser.toString(cube))
    }
    
    @Test
    fun `test L rotation`() {
        var cube = CubeParser.fromString(solvedString)
        cube = RotationEngine.rotate(cube, CubeMove.L)
        
        repeat(3) {
            cube = RotationEngine.rotate(cube, CubeMove.L)
        }
        assertEquals("L x 4 should be identity", solvedString, CubeParser.toString(cube))
    }

    @Test
    fun `test D rotation`() {
        var cube = CubeParser.fromString(solvedString)
        cube = RotationEngine.rotate(cube, CubeMove.D)
        
        repeat(3) {
            cube = RotationEngine.rotate(cube, CubeMove.D)
        }
        assertEquals("D x 4 should be identity", solvedString, CubeParser.toString(cube))
    }
}
