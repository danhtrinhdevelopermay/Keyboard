package com.example.ios_keyboard_android

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

class SwipeGestureDetector(
    private val onSwipeDetected: (direction: SwipeDirection, startKey: String, path: List<Char>) -> Unit
) : GestureDetector.SimpleOnGestureListener() {
    
    enum class SwipeDirection {
        LEFT, RIGHT, UP, DOWN, DIAGONAL
    }
    
    private val swipePath = mutableListOf<Char>()
    private var startKey: String = ""
    private val minSwipeDistance = 100
    private val minSwipeVelocity = 100
    
    override fun onDown(e: MotionEvent): Boolean {
        swipePath.clear()
        // Record starting position/key
        return true
    }
    
    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (e1 == null) return false
        
        val diffX = e2.x - e1.x
        val diffY = e2.y - e1.y
        
        if (abs(diffX) > abs(diffY)) {
            // Horizontal swipe
            if (abs(diffX) > minSwipeDistance && abs(velocityX) > minSwipeVelocity) {
                val direction = if (diffX > 0) SwipeDirection.RIGHT else SwipeDirection.LEFT
                onSwipeDetected(direction, startKey, swipePath)
                return true
            }
        } else {
            // Vertical swipe
            if (abs(diffY) > minSwipeDistance && abs(velocityY) > minSwipeVelocity) {
                val direction = if (diffY > 0) SwipeDirection.DOWN else SwipeDirection.UP
                onSwipeDetected(direction, startKey, swipePath)
                return true
            }
        }
        return false
    }
    
    fun addKeyToPath(key: Char) {
        swipePath.add(key)
    }
    
    fun setStartKey(key: String) {
        startKey = key
    }
}