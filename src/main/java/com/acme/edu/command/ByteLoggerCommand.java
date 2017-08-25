package com.acme.edu.command;

import com.acme.edu.State;

public class ByteLoggerCommand extends NumberLoggerCommand {
    public ByteLoggerCommand(State state) {
        super(state);
    }

    private static boolean isAdditionSafe(int a, byte b) {
        return a <= 0 || b <= 0 || a + b >= 0;
    }

    @Override
    public void collect(Object o) {
        byte x = (byte) o;
        if (isAdditionSafe(state.sum, x)) {
            state.sum += x;
        } else {
            print();
            state.sum = x;
        }
    }
}
