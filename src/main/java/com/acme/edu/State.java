package com.acme.edu;

public class State {
    private int stringCounter = 0;
    private Object previousInstance = null;
    private Class previousClass = null;
    private Object objectToPrint = null;

    public int getStringCounter() {
        return stringCounter;
    }

    public Object getPreviousInstance() {
        return previousInstance;
    }

    public Class getPreviousClass() {
        return previousClass;
    }

    public Object getObjectToLog() {
        return objectToPrint;
    }

    public void setStringCounter(int stringCounter) {
        this.stringCounter = stringCounter;
    }

    public void setPreviousInstance(Object previousInstance) {
        this.previousInstance = previousInstance;
    }

    public void setPreviousClass(Class previousClass) {
        this.previousClass = previousClass;
    }

    public void setObjectToPrint(Object objectToPrint) {
        this.objectToPrint = objectToPrint;
    }
}