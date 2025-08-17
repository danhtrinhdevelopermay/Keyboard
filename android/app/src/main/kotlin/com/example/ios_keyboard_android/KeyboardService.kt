package com.example.ios_keyboard_android

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.KeyEvent
import android.widget.TextView
import android.widget.LinearLayout
import android.text.TextUtils
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation

class KeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {
    
    private var keyboardView: KeyboardView? = null
    private var qwertyKeyboard: Keyboard? = null
    private var numbersKeyboard: Keyboard? = null
    private var currentKeyboard: Keyboard? = null
    private var containerView: LinearLayout? = null
    
    // QuickType components
    private var suggestionEngine: SuggestionEngine? = null
    private var hapticFeedback: HapticFeedback? = null
    private var soundManager: SoundManager? = null
    private var textExpansionManager: TextExpansionManager? = null
    private var suggestion1: TextView? = null
    private var suggestion2: TextView? = null
    private var suggestion3: TextView? = null
    
    private var isCapsLock = false
    private var isShift = false
    private var isNumberMode = false
    private var currentWord = StringBuilder()
    
    override fun onCreateInputView(): View? {
        // Initialize QuickType components
        suggestionEngine = SuggestionEngine(this)
        hapticFeedback = HapticFeedback(this)
        soundManager = SoundManager(this)
        textExpansionManager = TextExpansionManager(this)
        
        // Use Spotlight container layout
        containerView = layoutInflater.inflate(R.layout.spotlight_keyboard_container, null) as LinearLayout
        keyboardView = containerView?.findViewById(R.id.keyboardView)
        
        // Initialize suggestion bar views
        suggestion1 = containerView?.findViewById(R.id.suggestion1)
        suggestion2 = containerView?.findViewById(R.id.suggestion2)
        suggestion3 = containerView?.findViewById(R.id.suggestion3)
        
        setupSuggestionClickListeners()
        
        // Initialize keyboards
        qwertyKeyboard = Keyboard(this, R.xml.qwerty)
        numbersKeyboard = Keyboard(this, R.xml.numbers)
        
        // Start with QWERTY
        currentKeyboard = qwertyKeyboard
        keyboardView?.keyboard = currentKeyboard
        keyboardView?.setOnKeyboardActionListener(this)
        
        return containerView
    }
    
    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        // Reset keyboard state when starting new input
        isShift = false
        currentWord.clear()
        updateShiftKeyState()
        clearSuggestions()
    }
    
    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection = currentInputConnection
        
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> {
                handleDelete()
            }
            Keyboard.KEYCODE_SHIFT -> {
                isShift = !isShift
                updateShiftKeyState()
                hapticFeedback?.performKeyPress()
                soundManager?.playKeyClick()
            }
            Keyboard.KEYCODE_DONE -> {
                inputConnection?.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_ENTER
                    )
                )
                currentWord.clear()
                clearSuggestions()
                hapticFeedback?.performKeyPress()
                soundManager?.playReturn()
            }
            32 -> { // Space
                handleSpace()
            }
            // Switch to numbers keyboard
            123 -> { // "123" key code
                switchToNumbersKeyboard()
                hapticFeedback?.performModeSwitch()
                soundManager?.playKeyClick()
            }
            // Switch back to letters keyboard  
            65 -> { // "ABC" key codes - using just 'A' for simplicity
                switchToQwertyKeyboard()
                hapticFeedback?.performModeSwitch()
                soundManager?.playKeyClick()
            }
            else -> {
                handleCharacterInput(primaryCode)
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
    
    // QuickType Methods
    private fun handleCharacterInput(primaryCode: Int) {
        val inputConnection = currentInputConnection
        var code = primaryCode.toChar()
        
        // Apply case transformation if needed
        if (Character.isLetter(code) && isShift) {
            code = Character.toUpperCase(code)
        }
        
        // Add to current word if it's a letter
        if (Character.isLetter(code)) {
            currentWord.append(code.toLowerCase())
            updateSuggestions()
        } else {
            // Non-letter character ends current word
            currentWord.clear()
            clearSuggestions()
        }
        
        inputConnection?.commitText(code.toString(), 1)
        
        // Auto turn off shift after typing
        if (isShift && !isCapsLock) {
            isShift = false
            updateShiftKeyState()
        }
        
        // Play sounds and haptic feedback
        hapticFeedback?.performKeyPress()
        soundManager?.playKeyClick()
    }
    
    private fun handleDelete() {
        val inputConnection = currentInputConnection
        inputConnection?.deleteSurroundingText(1, 0)
        
        // Remove last character from current word
        if (currentWord.isNotEmpty()) {
            currentWord.deleteCharAt(currentWord.length - 1)
            if (currentWord.isNotEmpty()) {
                updateSuggestions()
            } else {
                clearSuggestions()
            }
        }
        
        hapticFeedback?.performDelete()
        soundManager?.playDelete()
    }
    
    private fun handleSpace() {
        val inputConnection = currentInputConnection
        
        // Check for text expansion and auto-correction before committing space
        if (currentWord.isNotEmpty()) {
            val wordText = currentWord.toString()
            
            // First check for text expansion/shortcuts
            val expansion = textExpansionManager?.getExpansion(wordText)
            if (expansion != null) {
                // Replace with expanded text
                inputConnection?.deleteSurroundingText(currentWord.length, 0)
                inputConnection?.commitText("$expansion ", 1)
            } else {
                // Check for auto-correction
                val autoCorrection = suggestionEngine?.getAutoCorrection(wordText)
                if (autoCorrection != null) {
                    // Replace the current word with correction
                    inputConnection?.deleteSurroundingText(currentWord.length, 0)
                    inputConnection?.commitText("$autoCorrection ", 1)
                } else {
                    inputConnection?.commitText(" ", 1)
                }
            }
            currentWord.clear()
            clearSuggestions()
        } else {
            inputConnection?.commitText(" ", 1)
        }
        
        hapticFeedback?.performSpace()
        soundManager?.playSpace()
    }
    
    private fun updateSuggestions() {
        if (currentWord.length < 2) {
            clearSuggestions()
            return
        }
        
        val suggestions = suggestionEngine?.getSuggestions(currentWord.toString()) ?: return
        
        // Update suggestion views with animation
        suggestions.getOrNull(0)?.let { suggestion ->
            suggestion1?.text = suggestion.word
            suggestion1?.visibility = View.VISIBLE
            animateSuggestionIn(suggestion1)
        }
        
        suggestions.getOrNull(1)?.let { suggestion ->
            suggestion2?.text = suggestion.word
            suggestion2?.visibility = View.VISIBLE
            animateSuggestionIn(suggestion2)
        }
        
        suggestions.getOrNull(2)?.let { suggestion ->
            suggestion3?.text = suggestion.word
            suggestion3?.visibility = View.VISIBLE
            animateSuggestionIn(suggestion3)
        }
    }
    
    private fun clearSuggestions() {
        suggestion1?.visibility = View.GONE
        suggestion2?.visibility = View.GONE
        suggestion3?.visibility = View.GONE
    }
    
    private fun animateSuggestionIn(view: TextView?) {
        view?.let {
            val scaleAnimation = ScaleAnimation(
                0.8f, 1.0f, 0.8f, 1.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
            )
            scaleAnimation.duration = 150
            it.startAnimation(scaleAnimation)
        }
    }
    
    private fun setupSuggestionClickListeners() {
        suggestion1?.setOnClickListener { onSuggestionClicked(suggestion1?.text.toString()) }
        suggestion2?.setOnClickListener { onSuggestionClicked(suggestion2?.text.toString()) }
        suggestion3?.setOnClickListener { onSuggestionClicked(suggestion3?.text.toString()) }
    }
    
    private fun onSuggestionClicked(suggestion: String) {
        if (suggestion.isBlank()) return
        
        val inputConnection = currentInputConnection
        
        // Replace current word with suggestion
        if (currentWord.isNotEmpty()) {
            inputConnection?.deleteSurroundingText(currentWord.length, 0)
        }
        
        // Check if suggestion contains emoji
        if (suggestion.contains(" ")) {
            inputConnection?.commitText(suggestion, 1)
        } else {
            inputConnection?.commitText("$suggestion ", 1)
        }
        
        currentWord.clear()
        clearSuggestions()
        
        // Haptic feedback for suggestion selection
        hapticFeedback?.performKeyPress()
        soundManager?.playKeyClick()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        soundManager?.release()
    }
}