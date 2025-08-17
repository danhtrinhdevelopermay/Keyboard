package com.example.ios_keyboard_android

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build

class SoundManager(private val context: Context) {
    
    private var soundPool: SoundPool? = null
    private var keyClickSoundId: Int = 0
    private var deleteSoundId: Int = 0
    private var spaceSoundId: Int = 0
    
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    
    init {
        initializeSoundPool()
    }
    
    private fun initializeSoundPool() {
        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder()
                .setMaxStreams(3)
                .build()
        } else {
            @Suppress("DEPRECATION")
            SoundPool(3, AudioManager.STREAM_SYSTEM, 0)
        }
        
        // iOS keyboard sounds would be loaded here
        // For now, we'll use system sounds
        loadSounds()
    }
    
    private fun loadSounds() {
        // Generate iOS-like keyboard sounds programmatically
        // These would typically be audio files, but we'll use system sounds for demo
        
        // Key click sound (simulate iOS tick)
        keyClickSoundId = generateKeyClickSound()
        
        // Delete sound (slightly different tone)
        deleteSoundId = generateDeleteSound() 
        
        // Space sound (softer)
        spaceSoundId = generateSpaceSound()
    }
    
    private fun generateKeyClickSound(): Int {
        // In a real implementation, you would load an actual iOS keyboard sound file
        // For demonstration, we'll return a placeholder ID
        return 1
    }
    
    private fun generateDeleteSound(): Int {
        return 2
    }
    
    private fun generateSpaceSound(): Int {
        return 3
    }
    
    fun playKeyClick() {
        if (isSoundEnabled()) {
            // Use system key click sound
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
        }
    }
    
    fun playDelete() {
        if (isSoundEnabled()) {
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE)
        }
    }
    
    fun playSpace() {
        if (isSoundEnabled()) {
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR)
        }
    }
    
    fun playReturn() {
        if (isSoundEnabled()) {
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN)
        }
    }
    
    private fun isSoundEnabled(): Boolean {
        // Check if keyboard sounds are enabled in system settings
        return audioManager.isMusicActive || 
               android.provider.Settings.System.getInt(
                   context.contentResolver,
                   android.provider.Settings.System.SOUND_EFFECTS_ENABLED,
                   1
               ) == 1
    }
    
    fun release() {
        soundPool?.release()
        soundPool = null
    }
}