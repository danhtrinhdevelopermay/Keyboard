# iOS Keyboard Android

## Overview

iOS QuickType Keyboard for Android is a comprehensive Android Input Method Service (IME) featuring complete iOS-style QuickType functionality. The application provides auto-correction, smart word suggestions, text shortcuts, emoji recommendations, haptic feedback, and authentic iOS keyboard sounds. Built with native Android components for optimal performance, it includes both a functional keyboard service and an interactive web demonstration. The project features automated GitHub Actions workflows for APK building and distribution.

## Recent Changes (August 17, 2025)

✓ **Created functional Android keyboard service**: Implemented complete KeyboardService with text input capabilities
✓ **Added multiple keyboard layouts**: QWERTY letters, numbers/symbols with switching functionality  
✓ **Built native Android UI**: Native XML layouts with iOS-style design instead of Flutter components
✓ **Fixed launcher icons**: Created proper app icons for all Android density levels
✓ **Added setup interface**: MainActivity helps users enable and select the keyboard in Android settings
✓ **Fixed Android build issues**: Resolved Kotlin dependency conflicts for GitHub Actions APK builds
✓ **Added QuickType features**: Implemented auto-suggestions, auto-correction, text shortcuts, haptic feedback, and iOS sounds
✓ **Created web demo**: Built interactive QuickType demonstration showing all keyboard features
✓ **Fixed font resource errors**: Removed problematic sf_pro_text font references causing Android build failures
✓ **Created simple HTML page**: Added basic "hello world" page while preserving QuickType demo features
✓ **Built iOS Spotlight keyboard interface**: Created pixel-perfect Spotlight search keyboard with gaussian blur effects and Siri suggestions
✓ **Updated Android keyboard to Spotlight design**: Replaced standard keyboard with iOS Spotlight interface, search bar, and blur effects

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
- **QuickType Engine**: Advanced suggestion system with auto-correction, text expansion, and emoji recommendations
- **Haptic & Audio Systems**: iOS-style vibration feedback and keyboard sounds for authentic experience

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

## Build Issues and Solutions

### Kotlin Dependency Conflicts (Fixed August 17, 2025)

**Problem**: APK build failed in GitHub Actions with duplicate class errors:
- Kotlin stdlib 1.8.10 vs older stdlib-jdk7/jdk8 versions
- Error: "Duplicate class kotlin.collections.jdk8.CollectionsJDK8Kt found in modules"

**Root Cause**: Flutter dependencies pulled in newer Kotlin stdlib (1.8.10) while project used older version (1.7.10), causing class conflicts.

**Solution Applied**:
1. **Updated Kotlin version** in `android/build.gradle` from 1.7.10 to 1.8.10
2. **Updated Android Gradle Plugin** from 7.3.0 to 7.4.2 for compatibility
3. **Changed dependency** in `android/app/build.gradle` from `kotlin-stdlib-jdk7` to `kotlin-stdlib`
4. **Added dependency resolution strategy** forcing consistent Kotlin versions across all modules
5. **Enhanced gradle.properties** with R8 and desugaring configurations

**Files Modified**:
- `android/build.gradle`: Updated Kotlin version and added resolution strategy
- `android/app/build.gradle`: Changed stdlib dependency
- `android/gradle.properties`: Added build optimization settings

## QuickType Implementation (August 17, 2025)

### Features Added:
1. **Smart Word Suggestions**: Context-aware predictions for Vietnamese and English
2. **Auto-Correction Engine**: Automatic spelling fixes with iOS-style behavior
3. **Text Shortcuts**: Expandable abbreviations (omw → On my way!, ty → Thank you)
4. **Emoji Suggestions**: Context-based emoji recommendations
5. **Haptic Feedback**: iOS-style vibration patterns for different key types
6. **Keyboard Sounds**: Authentic iOS audio feedback system
7. **Key Pop-up Effects**: iOS-style character preview on key press

### Components Created:
- `SuggestionEngine.kt`: Word prediction and auto-correction logic
- `HapticFeedback.kt`: Vibration patterns matching iOS behavior
- `SoundManager.kt`: Audio feedback system for keyboard interactions
- `TextExpansionManager.kt`: Text shortcuts and abbreviation expansion
- `SwipeGestureDetector.kt`: Framework for future swipe-to-type feature
- `suggestion_bar.xml`: QuickType suggestion bar layout
- `keyboard_container.xml`: Combined keyboard with suggestion bar
- `quicktype_demo.html`: Interactive web demonstration of all features