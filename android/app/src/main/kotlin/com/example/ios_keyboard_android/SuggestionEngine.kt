package com.example.ios_keyboard_android

import android.content.Context

data class Suggestion(
    val word: String,
    val confidence: Float,
    val isCorrection: Boolean = false
)

class SuggestionEngine(private val context: Context) {
    
    // Từ điển cơ bản tiếng Việt và tiếng Anh
    private val dictionary = mapOf(
        // Tiếng Việt thông dụng
        "ch" to listOf("chào", "chủ", "chị"),
        "xa" to listOf("xanh", "xám", "xã"),
        "th" to listOf("thì", "thế", "thật"),
        "ng" to listOf("ngày", "người", "nghe"),
        "tr" to listOf("trai", "trên", "trong"),
        "qu" to listOf("quá", "qua", "quê"),
        "gi" to listOf("giá", "giờ", "gì"),
        "ph" to listOf("phải", "phố", "phim"),
        "kh" to listOf("không", "khi", "khá"),
        "nh" to listOf("nhà", "như", "nhé"),
        
        // Tiếng Anh thông dụng
        "th" to listOf("the", "that", "this"),
        "wh" to listOf("what", "when", "where"),
        "you" to listOf("your", "you're", "yours"),
        "the" to listOf("there", "they", "their"),
        "and" to listOf("android", "answer", "another"),
        "for" to listOf("from", "first", "free"),
        "app" to listOf("apple", "application", "appreciate"),
        "key" to listOf("keyboard", "keep", "keychain"),
        "ios" to listOf("iPhone", "iPad", "iCloud")
    )
    
    // Auto-correction từ thông dụng
    private val autoCorrections = mapOf(
        "teh" to "the",
        "adn" to "and", 
        "wiht" to "with",
        "yuor" to "your",
        "recieve" to "receive",
        "seperate" to "separate",
        "occured" to "occurred",
        "accomodate" to "accommodate",
        "definately" to "definitely",
        "neccessary" to "necessary",
        
        // Tiếng Việt
        "khong" to "không",
        "dang" to "đang",
        "duoc" to "được", 
        "nhat" to "nhật",
        "viet" to "việt",
        "truoc" to "trước",
        "sau" to "sau",
        "nhieu" to "nhiều"
    )
    
    // Emoji suggestions
    private val emojiSuggestions = mapOf(
        "happy" to "😊",
        "sad" to "😢",
        "love" to "❤️",
        "like" to "👍",
        "ok" to "👌",
        "good" to "😊",
        "bad" to "😞",
        "thanks" to "🙏",
        "yes" to "✅",
        "no" to "❌",
        "food" to "🍕",
        "coffee" to "☕",
        "home" to "🏠",
        "work" to "💼",
        "car" to "🚗",
        "phone" to "📱",
        
        // Tiếng Việt
        "vui" to "😊",
        "buồn" to "😢",
        "yêu" to "❤️",
        "thích" to "👍",
        "ăn" to "🍕",
        "uống" to "☕",
        "nhà" to "🏠",
        "làm" to "💼",
        "xe" to "🚗",
        "điện" to "📱"
    )
    
    fun getSuggestions(currentWord: String): List<Suggestion> {
        if (currentWord.length < 2) return emptyList()
        
        val suggestions = mutableListOf<Suggestion>()
        val lowerWord = currentWord.lowercase()
        
        // 1. Auto-correction suggestion (highest priority)
        autoCorrections[lowerWord]?.let { correction ->
            suggestions.add(Suggestion(correction, 1.0f, true))
        }
        
        // 2. Dictionary matches
        dictionary.keys.filter { it.startsWith(lowerWord.take(2)) }
            .forEach { prefix ->
                dictionary[prefix]?.forEach { word ->
                    if (word.startsWith(lowerWord) && word != lowerWord) {
                        val confidence = calculateConfidence(currentWord, word)
                        suggestions.add(Suggestion(word, confidence))
                    }
                }
            }
        
        // 3. Emoji suggestions
        emojiSuggestions.keys.filter { it.contains(lowerWord) }
            .forEach { key ->
                emojiSuggestions[key]?.let { emoji ->
                    suggestions.add(Suggestion("$currentWord $emoji", 0.8f))
                }
            }
        
        // Sort by confidence and return top 3
        return suggestions
            .sortedByDescending { it.confidence }
            .distinctBy { it.word }
            .take(3)
    }
    
    private fun calculateConfidence(input: String, suggestion: String): Float {
        val lengthDiff = kotlin.math.abs(input.length - suggestion.length)
        val startMatch = if (suggestion.startsWith(input, ignoreCase = true)) 0.9f else 0.5f
        return startMatch - (lengthDiff * 0.1f)
    }
    
    fun getAutoCorrection(word: String): String? {
        return autoCorrections[word.lowercase()]
    }
}