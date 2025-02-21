package main.dataClasses;

import main.enums.OperationType;

public class Operation {
    private final OperationType operationType;
    private final String var;
    private final int value;

    public Operation(OperationType operationType, String var, int value) {
        this.operationType = operationType;
        this.var = var;
        this.value = value;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getVar() {
        return var;
    }

    public int getValue() {
        return value;
    }
}
