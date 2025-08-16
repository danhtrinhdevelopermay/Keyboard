# iOS Keyboard Android

## Overview

iOS Keyboard Android is a Flutter-based mobile application that recreates the iOS keyboard interface for Android devices. The project provides a pixel-perfect replica of Apple's keyboard design, implemented as an Android Input Method Service (IME). The application includes a web-based demo interface for showcasing the keyboard's visual design and functionality, along with automated GitHub Actions workflows for continuous APK building and distribution.

## User Preferences

Preferred communication style: Simple, everyday language.

## System Architecture

### Frontend Architecture
- **Flutter Framework**: Cross-platform mobile development using Dart, enabling native Android integration while maintaining iOS-like UI components
- **Web Demo Interface**: HTML/CSS/JavaScript implementation providing an interactive keyboard preview with iOS-styled visual elements
- **Component-Based Design**: Modular keyboard layout with reusable key components, supporting multiple input modes (letters, numbers, symbols)

### Mobile Application Structure
- **Input Method Service (IME)**: Core Android service integration allowing the app to function as a system-wide keyboard replacement
- **State Management**: JavaScript-based state handling for shift modes, number/symbol toggling, and text input processing
- **Visual Feedback System**: Touch response animations and key press effects mimicking iOS keyboard behavior

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
- **Flutter SDK**: Primary development framework for cross-platform mobile app creation
- **Android SDK**: Native Android development tools and APIs for IME service integration
- **Dart Programming Language**: Core language for Flutter application logic

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