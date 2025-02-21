package main.dataClasses;

import main.enums.ConditionType;

public class Condition {
    private final ConditionType conditionType;
    private final String variableName;
    private final int compareValue;

    public Condition(ConditionType conditionType, String variableName, int compareValue) {
        this.conditionType = conditionType;
        this.variableName = variableName;
        this.compareValue = compareValue;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public String getVariableName() {
        return variableName;
    }

    public int getCompareValue() {
        return compareValue;
    }
}
