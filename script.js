class IOSKeyboard {
    constructor() {
        this.isShiftPressed = false;
        this.isNumberMode = false;
        this.textArea = document.getElementById('demoText');
        this.initializeKeyboard();
    }

    initializeKeyboard() {
        const keys = document.querySelectorAll('.key');
        keys.forEach(key => {
            key.addEventListener('click', (e) => {
                this.handleKeyPress(e.target.dataset.key, e.target);
            });
        });
    }

    handleKeyPress(keyValue, keyElement) {
        // Add visual feedback
        keyElement.style.transform = 'scale(0.95)';
        setTimeout(() => {
            keyElement.style.transform = '';
        }, 100);

        switch(keyValue) {
            case 'shift':
                this.toggleShift();
                break;
            case '123':
                this.toggleNumberMode();
                break;
            case 'backspace':
                this.backspace();
                break;
            case 'space':
                this.insertText(' ');
                break;
            case 'search':
                this.insertText('\n');
                break;
            case 'emoji':
                this.insertText('😊');
                break;
            case 'mic':
                this.insertText('🎤');
                break;
            default:
                if (keyValue && keyValue.length === 1) {
                    const char = this.isShiftPressed ? keyValue.toUpperCase() : keyValue;
                    this.insertText(char);
                    if (this.isShiftPressed) {
                        this.toggleShift(); // Auto-disable shift after typing
                    }
                }
                break;
        }
    }

    toggleShift() {
        this.isShiftPressed = !this.isShiftPressed;
        const shiftKey = document.querySelector('.shift-key');
        const letterKeys = document.querySelectorAll('.key[data-key]');
        
        if (this.isShiftPressed) {
            shiftKey.classList.add('active');
            // Update letter keys to uppercase
            letterKeys.forEach(key => {
                const keyValue = key.dataset.key;
                if (keyValue && keyValue.length === 1 && /[a-z]/.test(keyValue)) {
                    key.textContent = keyValue.toUpperCase();
                }
            });
        } else {
            shiftKey.classList.remove('active');
            // Update letter keys to lowercase
            letterKeys.forEach(key => {
                const keyValue = key.dataset.key;
                if (keyValue && keyValue.length === 1 && /[a-z]/i.test(keyValue)) {
                    key.textContent = keyValue.toLowerCase();
                }
            });
        }
    }

    toggleNumberMode() {
        this.isNumberMode = !this.isNumberMode;
        const numberKey = document.querySelector('[data-key="123"]');
        
        if (this.isNumberMode) {
            numberKey.textContent = 'ABC';
            this.showNumberRow();
        } else {
            numberKey.textContent = '123';
            this.showLetterRow();
        }
    }

    showNumberRow() {
        const firstRow = document.querySelector('.key-row:first-of-type');
        const numbers = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'];
        const keys = firstRow.querySelectorAll('.key');
        
        keys.forEach((key, index) => {
            if (index < numbers.length) {
                key.textContent = numbers[index];
                key.dataset.key = numbers[index];
            }
        });
    }

    showLetterRow() {
        const firstRow = document.querySelector('.key-row:first-of-type');
        const letters = ['q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'];
        const keys = firstRow.querySelectorAll('.key');
        
        keys.forEach((key, index) => {
            if (index < letters.length) {
                const letter = this.isShiftPressed ? letters[index].toUpperCase() : letters[index];
                key.textContent = letter;
                key.dataset.key = letters[index];
            }
        });
    }

    insertText(text) {
        const start = this.textArea.selectionStart;
        const end = this.textArea.selectionEnd;
        const currentValue = this.textArea.value;
        
        const newValue = currentValue.substring(0, start) + text + currentValue.substring(end);
        this.textArea.value = newValue;
        
        // Move cursor to after inserted text
        const newCursorPos = start + text.length;
        this.textArea.setSelectionRange(newCursorPos, newCursorPos);
        this.textArea.focus();
    }

    backspace() {
        const start = this.textArea.selectionStart;
        const end = this.textArea.selectionEnd;
        const currentValue = this.textArea.value;
        
        if (start !== end) {
            // Delete selection
            const newValue = currentValue.substring(0, start) + currentValue.substring(end);
            this.textArea.value = newValue;
            this.textArea.setSelectionRange(start, start);
        } else if (start > 0) {
            // Delete one character before cursor
            const newValue = currentValue.substring(0, start - 1) + currentValue.substring(start);
            this.textArea.value = newValue;
            this.textArea.setSelectionRange(start - 1, start - 1);
        }
        
        this.textArea.focus();
    }
}

// Initialize keyboard when page loads
document.addEventListener('DOMContentLoaded', () => {
    new IOSKeyboard();
});

// Add some demo text
document.addEventListener('DOMContentLoaded', () => {
    const demoText = document.getElementById('demoText');
    demoText.value = 'Try typing with the iOS-style keyboard below! ✨\n\nThis is a complete Android keyboard app project with:\n• Flutter implementation\n• GitHub Actions for APK building\n• Input Method Service integration\n• Pixel-perfect iOS design';
});