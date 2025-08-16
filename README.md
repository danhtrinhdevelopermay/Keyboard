# iOS Keyboard Android

An Android keyboard application that replicates the iOS keyboard interface and experience.

## Features

- **Identical iOS Design**: Pixel-perfect recreation of iOS keyboard layout and styling
- **Flutter Implementation**: Cross-platform development with native Android integration
- **Automatic Builds**: GitHub Actions workflow for continuous APK building
- **Input Method Service**: Full Android keyboard service integration

## Screenshots

The keyboard replicates the iOS interface with:
- Rounded key design with proper shadows
- iOS-style color scheme (light gray background, white keys)
- Proper key spacing and typography
- Shift, number, and symbol modes
- Special keys (space, backspace, search, microphone)

## Installation

### From Releases
1. Download the latest APK from the [Releases](https://github.com/your-username/ios-keyboard-android/releases) page
2. Enable "Install from unknown sources" in your Android settings
3. Install the APK file

### Manual Installation
1. Enable "Developer options" and "USB debugging" on your Android device
2. Connect your device to your computer
3. Run: `flutter install`

## Setup Instructions

After installing the app:

1. Go to **Settings** > **System** > **Languages & input**
2. Tap on **Virtual keyboard**
3. Tap on **Manage keyboards**
4. Enable **iOS Keyboard**
5. In any text field, tap the keyboard icon
6. Select **iOS Keyboard** from the list

## Development

### Prerequisites
- Flutter SDK (3.16.0 or later)
- Android Studio or VS Code
- Android SDK with API level 21+

### Getting Started
```bash
# Clone the repository
git clone https://github.com/your-username/ios-keyboard-android.git
cd ios-keyboard-android

# Install dependencies
flutter pub get

# Run the app
flutter run
```

### Building
```bash
# Build APK
flutter build apk --release

# Build App Bundle (for Play Store)
flutter build appbundle --release
```

## GitHub Actions

The project includes a GitHub Actions workflow that:
- Automatically builds APK and App Bundle on push/PR
- Runs code analysis and tests
- Creates releases with downloadable artifacts
- Uploads build artifacts for easy access

## Project Structure

```
lib/
├── main.dart              # Main application entry point
├── ios_keyboard.dart      # iOS keyboard widget implementation
android/
├── app/src/main/kotlin/   # Android keyboard service
├── app/src/main/res/      # Android resources
.github/workflows/
├── build.yml              # GitHub Actions build workflow
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Design inspired by iOS keyboard interface
- Built with Flutter framework
- Android Input Method Service integration