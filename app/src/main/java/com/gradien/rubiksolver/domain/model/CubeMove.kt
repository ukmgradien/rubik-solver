package com.gradien.rubiksolver.domain.model

/**
 * Representasi gerakan Rubik (Singmaster Notation).
 * Urutan didefinisikan agar sesuai dengan indexing pada MoveTable:
 * (U, U2, U'), (R, R2, R'), (F, F2, F'), (D, D2, D'), (L, L2, L'), (B, B2, B')
 */
enum class CubeMove(val notation: String) {
    U("U"), U2("U2"), U_PRIME("U'"),
    R("R"), R2("R2"), R_PRIME("R'"),
    F("F"), F2("F2"), F_PRIME("F'"),
    D("D"), D2("D2"), D_PRIME("D'"),
    L("L"), L2("L2"), L_PRIME("L'"),
    B("B"), B2("B2"), B_PRIME("B'");

    companion object {
        fun fromNotation(notation: String): CubeMove? {
            return entries.find { it.notation == notation.uppercase() || it.notation == notation }
        }
    }
}
