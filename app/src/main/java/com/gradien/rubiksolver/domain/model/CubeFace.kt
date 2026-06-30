package com.gradien.rubiksolver.domain.model

/**
 * Representasi satu sisi Rubik (3x3).
 * [stickers] berisi 9 elemen [CubeColor], diurutkan dari baris atas ke bawah, kiri ke kanan.
 */
data class CubeFace(
    val stickers: List<CubeColor>
) {
    init {
        require(stickers.size == 9) { "Satu sisi Rubik harus memiliki tepat 9 sticker." }
    }
}
