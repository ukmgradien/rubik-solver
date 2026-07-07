package com.gradien.rubiksolver.presentation.util

import android.content.Context
import android.media.AudioManager

fun playClickSound(context: Context) {
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, -1f)
}