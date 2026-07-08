package com.gradien.rubiksolver.domain.model

data class HistoryEntry(
    val id: Long = 0,
    val timestamp: Long,
    val cubeState: String,
    val solution: List<String>
)
