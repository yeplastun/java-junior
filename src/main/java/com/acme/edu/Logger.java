package com.acme.edu;

import com.acme.edu.command.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private final State state = new State();
    private final Map<Class, LoggerCommand> commands;

    public Logger() {
        Map<Class, LoggerCommand> tempMap = new HashMap<>();
        tempMap.put(Object.class, new ObjectLoggerCommand());
        tempMap.put(int[].class, new IntArrayLoggerCommand());
        tempMap.put(String.class, new StringLoggerCommand(state));
        tempMap.put(Character.class, new CharacterLoggerCommand());
        tempMap.put(Byte.class, new ByteLoggerCommand(state));
        tempMap.put(Integer.class, new IntLoggerCommand(state));
        tempMap.put(Boolean.class, new BooleanLoggerCommand());
        commands = Collections.unmodifiableMap(tempMap);
    }

    private LoggerCommand getCommand(Class clazz) {
        LoggerCommand command = commands.get(clazz);
        return command == null ? commands.get(Object.class) : command;
    }

    private void saveState(Object message, Class clazz) {
        state.setPreviousClass(clazz);
        state.setPreviousInstance(message);
    }

    private void print(State state) {
        System.out.println(getCommand(state.getPreviousClass()).format(state.getPreviousInstance()));
    }

    public void log(Object message) {
        if (message == null) {
            throw new IllegalArgumentException("loggable message mustn't be null");
        }

        Class clazz = message.getClass();
        if (state.getPreviousClass() == null) {
            getCommand(clazz).initialize(message);
            saveState(message, clazz);
            return;
        }

        final boolean needToCollect = clazz.equals(state.getPreviousClass()) && getCommand(clazz).requiresCollection();
        if (needToCollect) {
            getCommand(clazz).collect(message);

            Object objectToLog = state.getObjectToLog();
            if (objectToLog != null) {
                System.out.println(getCommand(objectToLog.getClass()).format(objectToLog));
                state.setObjectToPrint(null);
                saveState(message, clazz);
            }
            return;
        }

        print(state);
        getCommand(clazz).initialize(message);
        saveState(message, clazz);
    }

    public void flush() {
        if (state.getPreviousClass() == null) {
            return;
        }
        print(state);
        saveState(null, null);
    }
}