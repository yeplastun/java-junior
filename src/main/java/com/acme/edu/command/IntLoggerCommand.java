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
        if (isAdditionSafe((Integer) state.getPreviousInstance(), x)) {
            state.setPreviousInstance((Integer) state.getPreviousInstance() + x);
        } else {
            state.setObjectToPrint(state.getPreviousInstance());
            state.setPreviousInstance(x);
        }
    }

    @Override
    public void initialize(Object o) {
        state.setPreviousInstance(o);
    }
}
