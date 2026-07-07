package com.gradien.rubiksolver.presentation.manual

import com.gradien.rubiksolver.domain.model.CubeColor

sealed class ManualEntryEvent {
    data class StickerClicked(val index: Int) : ManualEntryEvent()
    data class ColorSelected(val color: CubeColor) : ManualEntryEvent()
    object SolveClicked : ManualEntryEvent()
    object ResetClicked : ManualEntryEvent()
    object NextStep : ManualEntryEvent()
    object PreviousStep : ManualEntryEvent()
}