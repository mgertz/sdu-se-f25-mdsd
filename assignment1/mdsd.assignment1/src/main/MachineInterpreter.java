package main;

import main.exception.NoSuchTransitionException;
import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;

import java.util.List;

public class MachineInterpreter {
    private State currentState;
    private Machine machine;

    public void run(Machine m) {
        machine = m;
        this.currentState = m.getInitialState();
    }

    public State getCurrentState() {
        return this.currentState;
    }

    public void processEvent(String string) {
        try {
            List<Transition> transitions = this.currentState.getTransitionsByEvent(string);
            processTransitions(transitions);
        } catch (NoSuchTransitionException ignored){}
    }

    private void processTransitions(List<Transition> transitions) {
        for (Transition transition : transitions) {
            if (transition.isConditional()) {
                if (processConditions(transition)) continue;
            }
            if (transition.hasOperation()) {
                processOperation(transition);
            }
            this.currentState = transition.getTarget();
            return;
        }
    }

    private boolean processConditions(Transition transition) {
        if (transition.isConditionEqual()) {
            String variable = transition.getConditionVariableName();
            int operationValue = transition.getConditionComparedValue();

            return getInteger(variable) != operationValue;
        } else if (transition.isConditionGreaterThan()) {
            String variable = transition.getConditionVariableName();
            int operationValue = transition.getConditionComparedValue();

            return !(getInteger(variable) > operationValue);
        } else { // Less than
            String variable = transition.getConditionVariableName();
            int operationValue = transition.getConditionComparedValue();

            return !(getInteger(variable) < operationValue);
        }
    }

    private void processOperation(Transition transition) {
        if (transition.hasSetOperation()) {
            String variable = transition.getOperationVariableName();
            int operationValue = transition.getOperationValue();
            setInteger(variable, operationValue);
        } else if (transition.hasDecrementOperation()) {
            String variable = transition.getOperationVariableName();
            int operationValue = getInteger(variable) - 1;
            setInteger(variable, operationValue);
        } else { // Increment
            String variable = transition.getOperationVariableName();
            int operationValue = getInteger(variable) + 1;
            setInteger(variable, operationValue);
        }
    }

    public int getInteger(String string) {
        return this.machine.getInteger(string);
    }

    public void setInteger(String string, int value) {
        this.machine.setInteger(string, value);
    }
}
