package com.gradien.rubiksolver.domain.model

data class Cube(

    val up: CubeFace,

    val down: CubeFace,

    val left: CubeFace,

    val right: CubeFace,

    val front: CubeFace,

    val back: CubeFace

)