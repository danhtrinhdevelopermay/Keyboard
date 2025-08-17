package com.example.ios_keyboard_android

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.os.Build

class HapticFeedback(private val context: Context) {
    
    private val vibrator: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }
    
    fun performKeyPress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // iOS-style light tap
            vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(10)
        }
    }
    
    fun performDelete() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Slightly stronger for delete
            vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(15)
        }
    }
    
    fun performSpace() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Very light for space
            vibrator.vibrate(VibrationEffect.createOneShot(8, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(8)
        }
    }
    
    fun performModeSwitch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Double tap for mode switch
            val pattern = longArrayOf(0, 10, 20, 10)
            val amplitudes = intArrayOf(0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, amplitudes, -1))
        } else {
            @Suppress("DEPRECATION")
            val pattern = longArrayOf(0, 10, 20, 10)
            vibrator.vibrate(pattern, -1)
        }
    }
}