package com.gradien.rubiksolver.domain.solver

import com.gradien.rubiksolver.domain.solver.min2phase.Search as Min2PhaseSearch

/**
 * Bridge ke library min2phase.
 * Sekarang menggunakan implementasi asli dari cs0x7f/min2phase.
 */
object Search {

    private val searchInstance = Min2PhaseSearch()

    /**
     * Mencari solusi dari representasi facelet string.
     * @param facelets String 54 karakter (U, R, F, D, L, B).
     * @return String solusi (misal: "R U R' F2") atau null jika gagal.
     */
    fun solution(facelets: String): String? {
        // Pemanggilan library min2phase yang asli:
        // parameters: facelets, maxDepth, timeOut, timeMin, verbose
        val result = searchInstance.solution(facelets, 21, 1000, 0, 0)
        
        // Library mengembalikan pesan error jika diawali dengan "Error"
        if (result.startsWith("Error")) {
            return null
        }
        
        return result.trim()
    }
}
