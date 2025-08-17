package com.example.ios_keyboard_android

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.KeyEvent

class KeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {
    
    private var keyboardView: KeyboardView? = null
    private var qwertyKeyboard: Keyboard? = null
    private var numbersKeyboard: Keyboard? = null
    private var currentKeyboard: Keyboard? = null
    
    private var isCapsLock = false
    private var isShift = false
    private var isNumberMode = false
    
    override fun onCreateInputView(): View? {
        keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView
        
        // Initialize keyboards
        qwertyKeyboard = Keyboard(this, R.xml.qwerty)
        numbersKeyboard = Keyboard(this, R.xml.numbers)
        
        // Start with QWERTY
        currentKeyboard = qwertyKeyboard
        keyboardView?.keyboard = currentKeyboard
        keyboardView?.setOnKeyboardActionListener(this)
        return keyboardView
    }
    
    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        // Reset shift state when starting new input
        isShift = false
        updateShiftKeyState()
    }
    
    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection = currentInputConnection
        
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> {
                inputConnection?.deleteSurroundingText(1, 0)
            }
            Keyboard.KEYCODE_SHIFT -> {
                isShift = !isShift
                updateShiftKeyState()
            }
            Keyboard.KEYCODE_DONE -> {
                inputConnection?.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_ENTER
                    )
                )
            }
            32 -> { // Space
                inputConnection?.commitText(" ", 1)
            }
            // Switch to numbers keyboard
            123 -> { // "123" key code
                switchToNumbersKeyboard()
            }
            // Switch back to letters keyboard  
            65 -> { // "ABC" key codes - using just 'A' for simplicity
                switchToQwertyKeyboard()
            }
            else -> {
                var code = primaryCode.toChar()
                if (Character.isLetter(code) && isShift) {
                    code = Character.toUpperCase(code)
                }
                inputConnection?.commitText(code.toString(), 1)
                
                // Auto turn off shift after typing
                if (isShift && !isCapsLock) {
                    isShift = false
                    updateShiftKeyState()
                }
            }
        }
    }
    
    private fun switchToNumbersKeyboard() {
        isNumberMode = true
        currentKeyboard = numbersKeyboard
        keyboardView?.keyboard = currentKeyboard
        keyboardView?.invalidateAllKeys()
    }
    
    private fun switchToQwertyKeyboard() {
        isNumberMode = false
        currentKeyboard = qwertyKeyboard
        keyboardView?.keyboard = currentKeyboard
        updateShiftKeyState()
    }
    
    private fun updateShiftKeyState() {
        currentKeyboard?.isShifted = isShift
        keyboardView?.invalidateAllKeys()
    }
    
    override fun onPress(primaryCode: Int) {
        // Optional: Add haptic feedback or sound
    }
    
    override fun onRelease(primaryCode: Int) {
        // Optional: Handle key release
    }
    
    override fun onText(text: CharSequence?) {
        currentInputConnection?.commitText(text, 1)
    }
    
    override fun swipeDown() {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeUp() {}
}