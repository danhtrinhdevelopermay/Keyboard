package com.example.ios_keyboard_android

import android.content.Context
import android.content.SharedPreferences

class TextExpansionManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences("text_expansions", Context.MODE_PRIVATE)
    
    // Default iOS-style text expansions/shortcuts
    private val defaultExpansions = mapOf(
        "omw" to "On my way!",
        "brb" to "Be right back",
        "ttyl" to "Talk to you later",
        "lol" to "😂",
        "ty" to "Thank you",
        "yw" to "You're welcome",
        "np" to "No problem",
        "gm" to "Good morning",
        "gn" to "Good night",
        "hbd" to "Happy birthday!",
        "fyi" to "For your information",
        "asap" to "As soon as possible",
        "eta" to "Estimated time of arrival",
        "idk" to "I don't know",
        "imo" to "In my opinion",
        "btw" to "By the way",
        "tbh" to "To be honest",
        "smh" to "Shaking my head",
        "rn" to "Right now",
        "nvm" to "Never mind",
        
        // Vietnamese shortcuts
        "k" to "không",
        "ko" to "không", 
        "dc" to "được",
        "vs" to "với",
        "nx" to "nữa",
        "ns" to "nói",
        "xl" to "xin lỗi",
        "cm" to "cảm ơn",
        "tks" to "thanks",
        "ok" to "👌",
        "gg" to "Good game",
        "gl" to "Good luck",
        "gz" to "Congratulations",
        
        // Email/formal shortcuts
        "addr" to "address",
        "fwd" to "forward",
        "cc" to "carbon copy",
        "bcc" to "blind carbon copy",
        "re" to "regarding",
        
        // Date/time shortcuts
        "tm" to "tomorrow",
        "td" to "today",
        "yd" to "yesterday",
        "wknd" to "weekend",
        "mon" to "Monday",
        "tue" to "Tuesday", 
        "wed" to "Wednesday",
        "thu" to "Thursday",
        "fri" to "Friday",
        "sat" to "Saturday",
        "sun" to "Sunday"
    )
    
    init {
        // Load default expansions if not already set
        if (!prefs.contains("defaults_loaded")) {
            val editor = prefs.edit()
            defaultExpansions.forEach { (shortcut, expansion) ->
                editor.putString(shortcut, expansion)
            }
            editor.putBoolean("defaults_loaded", true)
            editor.apply()
        }
    }
    
    fun getExpansion(shortcut: String): String? {
        return prefs.getString(shortcut.lowercase(), null)
    }
    
    fun addExpansion(shortcut: String, expansion: String) {
        prefs.edit()
            .putString(shortcut.lowercase(), expansion)
            .apply()
    }
    
    fun removeExpansion(shortcut: String) {
        prefs.edit()
            .remove(shortcut.lowercase())
            .apply()
    }
    
    fun getAllExpansions(): Map<String, String> {
        val allEntries = prefs.all
        val expansions = mutableMapOf<String, String>()
        
        allEntries.forEach { (key, value) ->
            if (key != "defaults_loaded" && value is String) {
                expansions[key] = value
            }
        }
        
        return expansions
    }
    
    fun hasExpansion(shortcut: String): Boolean {
        return prefs.contains(shortcut.lowercase())
    }
}