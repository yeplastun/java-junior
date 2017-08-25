package com.acme.edu.command;

import com.acme.edu.State;

public class IntLoggerCommand extends NumberLoggerCommand {
    public IntLoggerCommand(State state) {
        super(state);
    }

    private static boolean isAdditionSafe(int a, int b) {
        return a <= 0 || b <= 0 || a + b >= 0;
    }

    @Override
    public void collect(Object o) {
        int x = (int) o;
        if (isAdditionSafe(state.sum, x)) {
            state.sum += x;
        } else {
            print();
            state.sum = x;
        }
    }
}
