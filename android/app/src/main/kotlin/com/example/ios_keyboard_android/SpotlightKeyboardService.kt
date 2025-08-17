package com.example.ios_keyboard_android

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.text.Editable
import android.text.TextWatcher

class SpotlightKeyboardService : InputMethodService() {
    
    private var containerView: FrameLayout? = null
    private var searchInput: EditText? = null
    
    // QuickType components
    private var suggestionEngine: SuggestionEngine? = null
    private var hapticFeedback: HapticFeedback? = null
    private var soundManager: SoundManager? = null
    private var textExpansionManager: TextExpansionManager? = null
    
    // Key references
    private val keyMap = mutableMapOf<String, TextView>()
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
        containerView = layoutInflater.inflate(R.layout.spotlight_keyboard_container, null) as FrameLayout
        searchInput = containerView?.findViewById(R.id.searchInput)
        
        setupKeyboardKeys()
        setupControlButtons()
        setupSearchInput()
        
        return containerView
    }
    
    private fun setupKeyboardKeys() {
        // Setup letter keys
        val letters = arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
                             "a", "s", "d", "f", "g", "h", "j", "k", "l",
                             "z", "x", "c", "v", "b", "n", "m")
        
        letters.forEach { letter ->
            val keyId = resources.getIdentifier("key_$letter", "id", packageName)
            val keyView = containerView?.findViewById<TextView>(keyId)
            keyView?.let {
                it.text = letter
                it.setOnClickListener { onKeyPressed(letter) }
                keyMap[letter] = it
            }
        }
        
        // Setup special keys
        setupSpecialKey("key_shift", "⇧") { toggleShift() }
        setupSpecialKey("key_backspace", "⌫") { onBackspace() }
        setupSpecialKey("key_numbers", "123") { toggleNumbers() }
        setupSpecialKey("key_search", "search") { performSearch() }
        setupSpecialKey("key_space", "space") { onKeyPressed(" ") }
    }
    
    private fun setupSpecialKey(keyId: String, text: String, action: () -> Unit) {
        val id = resources.getIdentifier(keyId, "id", packageName)
        val keyView = containerView?.findViewById<TextView>(id)
        keyView?.let {
            it.text = text
            it.setOnClickListener { 
                hapticFeedback?.performKeyPress()
                soundManager?.playKeyClick()
                action()
            }
        }
    }
    
    private fun setupControlButtons() {
        // Emoji button
        val emojiButton = containerView?.findViewById<ImageView>(R.id.emojiButton)
        emojiButton?.setOnClickListener {
            hapticFeedback?.performKeyPress()
            // Could open emoji picker
        }
        
        // Microphone button
        val micButton = containerView?.findViewById<ImageView>(R.id.micButton)
        micButton?.setOnClickListener {
            hapticFeedback?.performKeyPress()
            // Could start voice input
        }
    }
    
    private fun setupSearchInput() {
        searchInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let { 
                    currentWord.clear()
                    currentWord.append(it.toString())
                    // Could trigger search suggestions here
                }
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun onKeyPressed(char: String) {
        hapticFeedback?.performKeyPress()
        soundManager?.playKeyClick()
        
        val processedChar = if (char != " " && isShift && char.matches(Regex("[a-z]"))) {
            char.uppercase()
        } else {
            char
        }
        
        // Add to search input
        val currentText = searchInput?.text.toString()
        searchInput?.setText(currentText + processedChar)
        searchInput?.setSelection(searchInput?.text?.length ?: 0)
        
        // Also send to current input connection for typing in other apps
        currentInputConnection?.let { ic ->
            if (char == " ") {
                // Check for text expansion before typing space
                val expandedText = textExpansionManager?.getExpansion(currentWord.toString())
                if (expandedText != null && expandedText != currentWord.toString()) {
                    // Delete the current word and replace with expansion
                    ic.deleteSurroundingText(currentWord.length, 0)
                    ic.commitText(expandedText + " ", 1)
                    currentWord.clear()
                } else {
                    ic.commitText(" ", 1)
                }
            } else {
                ic.commitText(processedChar, 1)
                if (char != " ") {
                    currentWord.append(char)
                } else {
                    currentWord.clear()
                }
            }
        }
        
        // Reset shift after typing letter
        if (isShift && char.matches(Regex("[a-zA-Z]"))) {
            isShift = false
            updateShiftState()
        }
    }
    
    private fun onBackspace() {
        hapticFeedback?.performDelete()
        soundManager?.playDelete()
        
        // Remove from search input
        val currentText = searchInput?.text.toString()
        if (currentText.isNotEmpty()) {
            searchInput?.setText(currentText.dropLast(1))
            searchInput?.setSelection(searchInput?.text?.length ?: 0)
        }
        
        // Also delete from current input connection
        currentInputConnection?.deleteSurroundingText(1, 0)
        
        // Update current word
        if (currentWord.isNotEmpty()) {
            currentWord.deleteCharAt(currentWord.length - 1)
        }
    }
    
    private fun toggleShift() {
        isShift = !isShift
        updateShiftState()
        updateKeyboardCase()
    }
    
    private fun updateShiftState() {
        val shiftKeyId = resources.getIdentifier("key_shift", "id", packageName)
        val shiftKey = containerView?.findViewById<TextView>(shiftKeyId)
        shiftKey?.let {
            if (isShift) {
                it.setBackgroundResource(R.drawable.spotlight_key_background)
                it.alpha = 1.0f
            } else {
                it.setBackgroundResource(R.drawable.spotlight_special_key_background)
                it.alpha = 0.7f
            }
        }
    }
    
    private fun updateKeyboardCase() {
        keyMap.values.forEach { keyView ->
            val char = keyView.text.toString()
            if (char.matches(Regex("[a-z]"))) {
                keyView.text = if (isShift) char.uppercase() else char.lowercase()
            }
        }
    }
    
    private fun toggleNumbers() {
        // Switch to numbers layout
        isNumberMode = !isNumberMode
        // Implementation for number layout would go here
    }
    
    private fun performSearch() {
        val query = searchInput?.text.toString().trim()
        if (query.isNotEmpty()) {
            // Perform search action - could launch search intent
            currentInputConnection?.commitText(query, 1)
        }
    }
    
    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        isShift = false
        currentWord.clear()
        updateShiftState()
    }
}