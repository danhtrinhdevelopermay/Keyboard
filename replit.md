# iOS Keyboard Android

## Overview

iOS Keyboard Android is a fully functional Android Input Method Service (IME) that provides a working iOS-style keyboard for Android devices. The application replaces the default Android keyboard with a pixel-perfect replica of Apple's keyboard interface, complete with native Android text input functionality. Users can install this as their system keyboard and use it across all Android applications. The project includes automated GitHub Actions workflows for continuous APK building and distribution.

## Recent Changes (August 17, 2025)

✓ **Created functional Android keyboard service**: Implemented complete KeyboardService with text input capabilities
✓ **Added multiple keyboard layouts**: QWERTY letters, numbers/symbols with switching functionality  
✓ **Built native Android UI**: Native XML layouts with iOS-style design instead of Flutter components
✓ **Fixed launcher icons**: Created proper app icons for all Android density levels
✓ **Added setup interface**: MainActivity helps users enable and select the keyboard in Android settings

## User Preferences

Preferred communication style: Simple, everyday language.

## System Architecture

### Frontend Architecture
- **Native Android Components**: Pure Android XML layouts and Kotlin/Java for optimal performance and system integration
- **Input Method Service**: Core Android IME implementation allowing full keyboard functionality across all apps
- **Web Demo Interface**: HTML/CSS/JavaScript implementation providing an interactive keyboard preview with iOS-styled visual elements
- **Multi-Layout Design**: QWERTY letters and numbers/symbols keyboards with seamless switching

### Mobile Application Structure
- **KeyboardService**: Fully functional Android IME with text input, deletion, and mode switching capabilities
- **MainActivity**: Setup interface guiding users through keyboard activation and selection process
- **State Management**: Kotlin-based state handling for shift modes, caps lock, and keyboard layout switching
- **Visual Design System**: iOS-themed key backgrounds, colors, and layout matching Apple's keyboard aesthetics

### Build and Deployment Pipeline
- **GitHub Actions Integration**: Automated CI/CD workflow for building and releasing APK files
- **Flutter Build System**: Automated compilation and packaging for Android distribution
- **Release Management**: Structured release process with downloadable APK artifacts

### UI/UX Design Patterns
- **iOS Design Replication**: Pixel-perfect recreation of Apple's keyboard aesthetics including rounded keys, shadows, and color schemes
- **Responsive Layout**: Adaptive keyboard sizing and spacing across different Android screen sizes
- **Multi-Mode Support**: Switch between alphabetic, numeric, and symbol input modes with proper state persistence

## External Dependencies

### Development Frameworks
- **Android SDK**: Native Android development tools and APIs for IME service integration
- **Kotlin Programming Language**: Primary language for Android keyboard service implementation
- **AndroidX Libraries**: Modern Android support libraries for UI components and compatibility

### Build and CI/CD Tools
- **GitHub Actions**: Automated build pipeline for continuous integration and APK generation
- **Android Studio/VS Code**: Recommended development environments with Flutter plugin support

### Web Technologies (Demo Interface)
- **HTML/CSS/JavaScript**: Web-based demonstration interface for keyboard preview
- **CSS Grid/Flexbox**: Layout systems for responsive keyboard key arrangement
- **DOM Manipulation**: JavaScript for interactive keyboard functionality in web demo

### Android Platform Integration
- **Input Method Service API**: Android system service for keyboard functionality
- **Android Settings Integration**: System-level keyboard management and selection interface
- **Permission System**: Android app permissions for keyboard access and functionality