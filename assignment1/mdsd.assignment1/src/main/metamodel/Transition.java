package main.metamodel;

import main.enums.ConditionType;
import main.enums.OperationType;
import main.dataClasses.Condition;
import main.dataClasses.Operation;

public class Transition {
    private final String event;
    private final State target;
    private Condition condition;
    private Operation operation;

    public Transition(String event, State target) {
        this.event = event;
        this.target = target;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getEvent() {
        return this.event;
    }

    public State getTarget() {
        return target;
    }

    public boolean hasOperation() {
        return this.operation != null;
    }

    public boolean hasSetOperation() {
        return this.operation.getOperationType().equals(OperationType.SET);
    }

    public boolean hasIncrementOperation() {
        return this.operation.getOperationType().equals(OperationType.INCREMENT);
    }

    public boolean hasDecrementOperation() {
        return this.operation.getOperationType().equals(OperationType.DECREMENT);
    }

    public String getOperationVariableName() {
        return this.operation.getVar();
    }

    public int getOperationValue() {
        return this.operation.getValue();
    }

    public boolean isConditional() {
        return this.condition != null;
    }

    public String getConditionVariableName() {
        return this.condition.getVariableName();
    }

    public Integer getConditionComparedValue() {
        return this.condition.getCompareValue();
    }

    public boolean isConditionEqual() {
        return this.condition.getConditionType().equals(ConditionType.EQUAL);
    }

    public boolean isConditionGreaterThan() {
        return this.condition.getConditionType().equals(ConditionType.GREATER_THAN);
    }

    public boolean isConditionLessThan() {
        return this.condition.getConditionType().equals(ConditionType.LESS_THAN);
    }
}
