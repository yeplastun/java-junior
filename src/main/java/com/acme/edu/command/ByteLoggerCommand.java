package com.acme.edu.command;

import com.acme.edu.State;

public class ByteLoggerCommand extends NumberLoggerCommand {
    public ByteLoggerCommand(State state) {
        super(state);
    }

    private static boolean isAdditionSafe(int a, byte b) {
        return a <= 0 || b <= 0 || (byte) (a + b) >= 0;
    }

    @Override
    public void collect(Object o) {
        byte x = (byte) o;
        if (isAdditionSafe((byte) state.getPreviousInstance(), x)) {
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
