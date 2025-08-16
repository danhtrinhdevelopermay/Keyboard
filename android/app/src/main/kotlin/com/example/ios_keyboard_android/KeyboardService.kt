package com.example.ios_keyboard_android

import android.inputmethodservice.InputMethodService
import android.view.View
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor

class KeyboardService : InputMethodService() {
    private var flutterEngine: FlutterEngine? = null
    private var flutterView: FlutterView? = null

    override fun onCreateInputView(): View? {
        // Initialize Flutter engine
        flutterEngine = FlutterEngine(this)
        flutterEngine?.dartExecutor?.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // Create Flutter view
        flutterView = FlutterView(this)
        flutterView?.attachToFlutterEngine(flutterEngine!!)

        return flutterView
    }

    override fun onDestroy() {
        flutterView?.detachFromFlutterEngine()
        flutterEngine?.destroy()
        super.onDestroy()
    }
}