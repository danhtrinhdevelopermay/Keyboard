import 'package:flutter/material.dart';
import 'ios_keyboard.dart';

void main() {
  runApp(const IOSKeyboardApp());
}

class IOSKeyboardApp extends StatelessWidget {
  const IOSKeyboardApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'iOS Keyboard',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        useMaterial3: true,
      ),
      home: const KeyboardDemoScreen(),
    );
  }
}

class KeyboardDemoScreen extends StatefulWidget {
  const KeyboardDemoScreen({super.key});

  @override
  State<KeyboardDemoScreen> createState() => _KeyboardDemoScreenState();
}

class _KeyboardDemoScreenState extends State<KeyboardDemoScreen> {
  final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('iOS Keyboard Demo'),
        backgroundColor: Colors.blue.shade50,
      ),
      body: Column(
        children: [
          Expanded(
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text(
                    'Test the iOS-style keyboard:',
                    style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                  const SizedBox(height: 16),
                  TextField(
                    controller: _controller,
                    decoration: const InputDecoration(
                      hintText: 'Tap here to type...',
                      border: OutlineInputBorder(),
                    ),
                    maxLines: 5,
                  ),
                  const SizedBox(height: 20),
                  const Text(
                    'Instructions:',
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                  ),
                  const SizedBox(height: 8),
                  const Text(
                    '1. Go to Settings > System > Languages & input\n'
                    '2. Tap on "Virtual keyboard"\n'
                    '3. Tap on "Manage keyboards"\n'
                    '4. Enable "iOS Keyboard"\n'
                    '5. Tap the keyboard icon in any text field\n'
                    '6. Select "iOS Keyboard" from the list',
                    style: TextStyle(fontSize: 14),
                  ),
                ],
              ),
            ),
          ),
          const IOSKeyboard(),
        ],
      ),
    );
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }
}