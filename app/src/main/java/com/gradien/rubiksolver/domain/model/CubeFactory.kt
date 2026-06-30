package com.gradien.rubiksolver.domain.model

object CubeFactory {

    fun solvedCube(): Cube {

        return Cube(

            up = CubeFace(
                MutableList(9) { CubeColor.WHITE }
            ),

            down = CubeFace(
                MutableList(9) { CubeColor.YELLOW }
            ),

            left = CubeFace(
                MutableList(9) { CubeColor.ORANGE }
            ),

            right = CubeFace(
                MutableList(9) { CubeColor.RED }
            ),

            front = CubeFace(
                MutableList(9) { CubeColor.GREEN }
            ),

            back = CubeFace(
                MutableList(9) { CubeColor.BLUE }
            )

        )
    }

}