package SOLID;

public class DependencyInversion {
//    Classes should depend on interfaces rather than concrete classes.

    interface Keyboard {}
    interface Mouse {}

    class WiredKeyboard implements Keyboard {}

    class BluetoothKeyboard implements Keyboard {}
    class WiredMouse implements Mouse {}
    class BluetoothMouse implements Mouse {}

//    Don't do
    class MacBook {
        private final WiredKeyboard keyBoard;
        private final WiredMouse mouse;

        public MacBook() {
            this.keyBoard = new WiredKeyboard();
            this.mouse = new WiredMouse();
        }
    }

//    Do
    class MacBook {
        private final Keyboard keyboard;
        private final Mouse mouse;

        public MacBook(WiredKeyboard wiredKeyboard, WiredMouse wiredMouse) {
            this.keyboard = wiredKeyboard;
            this.mouse = wiredMouse;
        }
    }
}

