package com.example.ios_keyboard_android

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupUI()
    }
    
    private fun setupUI() {
        findViewById<TextView>(R.id.title).text = "iOS Keyboard for Android"
        findViewById<TextView>(R.id.description).text = 
            "Để sử dụng bàn phím iOS trên Android:\n\n" +
            "1. Nhấn 'Kích hoạt bàn phím' để mở cài đặt\n" +
            "2. Bật 'iOS Keyboard' trong danh sách\n" +
            "3. Nhấn 'Chọn bàn phím' để đặt làm mặc định\n" +
            "4. Chọn 'iOS Keyboard' từ danh sách"
        
        findViewById<Button>(R.id.enableKeyboard).apply {
            text = "Kích hoạt bàn phím"
            setOnClickListener {
                openKeyboardSettings()
            }
        }
        
        findViewById<Button>(R.id.selectKeyboard).apply {
            text = "Chọn bàn phím"
            setOnClickListener {
                openInputMethodPicker()
            }
        }
    }
    
    private fun openKeyboardSettings() {
        val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
        startActivity(intent)
    }
    
    private fun openInputMethodPicker() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showInputMethodPicker()
    }
}