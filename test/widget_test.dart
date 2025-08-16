import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:ios_keyboard_android/main.dart';

void main() {
  testWidgets('iOS Keyboard app smoke test', (WidgetTester tester) async {
    // Build our app and trigger a frame.
    await tester.pumpWidget(const IOSKeyboardApp());

    // Verify that the app title is displayed
    expect(find.text('iOS Keyboard Demo'), findsOneWidget);
    
    // Verify that the instruction text is present
    expect(find.text('Test the iOS-style keyboard:'), findsOneWidget);
    
    // Verify that the text field is present
    expect(find.byType(TextField), findsOneWidget);
  });
}