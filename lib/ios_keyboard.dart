import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class IOSKeyboard extends StatefulWidget {
  const IOSKeyboard({super.key});

  @override
  State<IOSKeyboard> createState() => _IOSKeyboardState();
}

class _IOSKeyboardState extends State<IOSKeyboard> {
  bool isShiftPressed = false;
  bool isNumberMode = false;
  
  // Row 1: QWERTYUIOP
  final List<String> row1 = ['q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'];
  
  // Row 2: ASDFGHJKL
  final List<String> row2 = ['a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'];
  
  // Row 3: ZXCVBNM
  final List<String> row3 = ['z', 'x', 'c', 'v', 'b', 'n', 'm'];
  
  // Number row
  final List<String> numberRow = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'];

  void _onKeyTap(String key) {
    HapticFeedback.lightImpact();
    
    if (key == 'shift') {
      setState(() {
        isShiftPressed = !isShiftPressed;
      });
    } else if (key == '123') {
      setState(() {
        isNumberMode = !isNumberMode;
      });
    } else if (key == 'backspace') {
      // Handle backspace
      SystemChannels.textInput.invokeMethod('TextInput.sendKeyEvent', {
        'type': 'keydown',
        'keyCode': 67, // KEYCODE_DEL
      });
    } else if (key == 'space') {
      SystemChannels.textInput.invokeMethod('TextInput.addText', ' ');
    } else if (key == 'return') {
      SystemChannels.textInput.invokeMethod('TextInput.addText', '\n');
    } else {
      String inputChar = isShiftPressed ? key.toUpperCase() : key;
      SystemChannels.textInput.invokeMethod('TextInput.addText', inputChar);
      if (isShiftPressed) {
        setState(() {
          isShiftPressed = false;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        color: Color(0xFFD1D5DB),
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(12),
          topRight: Radius.circular(12),
        ),
      ),
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            // Drag indicator
            Container(
              width: 36,
              height: 3,
              decoration: BoxDecoration(
                color: Colors.black26,
                borderRadius: BorderRadius.circular(1.5),
              ),
            ),
            const SizedBox(height: 6),
            
            // Row 1
            _buildKeyRow(isNumberMode ? numberRow : row1),
            const SizedBox(height: 4),
            
            // Row 2
            _buildKeyRow(row2),
            const SizedBox(height: 4),
            
            // Row 3 with shift and backspace
            _buildThirdRow(),
            const SizedBox(height: 4),
            
            // Bottom row with 123, space, search
            _buildBottomRow(),
            const SizedBox(height: 4),
          ],
        ),
      ),
    );
  }

  Widget _buildKeyRow(List<String> keys) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: keys.map((key) => _buildKey(key)).toList(),
    );
  }

  Widget _buildThirdRow() {
    return Row(
      children: [
        // Shift key
        _buildSpecialKey(
          'shift',
          isPressed: isShiftPressed,
          child: Icon(
            Icons.keyboard_arrow_up,
            color: isShiftPressed ? Colors.white : Colors.black,
            size: 20,
          ),
        ),
        const SizedBox(width: 6),
        
        // Regular keys
        Expanded(
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: row3.map((key) => _buildKey(key)).toList(),
          ),
        ),
        
        const SizedBox(width: 6),
        
        // Backspace key
        _buildSpecialKey(
          'backspace',
          child: const Icon(
            Icons.backspace_outlined,
            color: Colors.black,
            size: 20,
          ),
        ),
      ],
    );
  }

  Widget _buildBottomRow() {
    return Row(
      children: [
        // 123 key
        _buildSpecialKey(
          '123',
          width: 50,
          child: Text(
            isNumberMode ? 'ABC' : '123',
            style: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w500,
              color: Colors.black,
            ),
          ),
        ),
        const SizedBox(width: 6),
        
        // Emoji key
        _buildSpecialKey(
          'emoji',
          width: 35,
          child: const Text(
            '😊',
            style: TextStyle(fontSize: 16),
          ),
        ),
        const SizedBox(width: 6),
        
        // Space bar
        Expanded(
          child: _buildSpecialKey(
            'space',
            height: 40,
            child: const Text(
              'space',
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.w400,
                color: Colors.black,
              ),
            ),
          ),
        ),
        
        const SizedBox(width: 6),
        
        // Search key
        _buildSpecialKey(
          'search',
          width: 70,
          child: const Text(
            'search',
            style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w500,
              color: Colors.black,
            ),
          ),
        ),
        const SizedBox(width: 6),
        
        // Microphone key
        _buildSpecialKey(
          'mic',
          width: 35,
          child: const Icon(
            Icons.mic_outlined,
            color: Colors.black,
            size: 20,
          ),
        ),
      ],
    );
  }

  Widget _buildKey(String key) {
    return GestureDetector(
      onTap: () => _onKeyTap(key),
      child: Container(
        width: 28,
        height: 32,
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(6),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.1),
              offset: const Offset(0, 1),
              blurRadius: 1,
            ),
          ],
        ),
        child: Center(
          child: Text(
            isShiftPressed ? key.toUpperCase() : key,
            style: const TextStyle(
              fontSize: 22,
              fontWeight: FontWeight.w400,
              color: Colors.black,
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildSpecialKey(
    String key, {
    Widget? child,
    double? width,
    double? height,
    bool isPressed = false,
  }) {
    return GestureDetector(
      onTap: () => _onKeyTap(key),
      child: Container(
        width: width ?? 36,
        height: height ?? 32,
        decoration: BoxDecoration(
          color: isPressed ? Colors.black : const Color(0xFFAEB3BB),
          borderRadius: BorderRadius.circular(6),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.1),
              offset: const Offset(0, 1),
              blurRadius: 1,
            ),
          ],
        ),
        child: Center(child: child ?? Text(key)),
      ),
    );
  }
}