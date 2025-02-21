package main.metamodel;

import main.exception.NoSuchTransitionException;

import java.util.ArrayList;
import java.util.List;

public class State {
    private final String name;
    private final List<Transition> transitions = new ArrayList<>();

    public State(String name) {
        this.name = name;
    }

    public Object getName() {
        return this.name;
    }

    public List<Transition> getTransitions() {
        return this.transitions;
    }

    public List<Transition> getTransitionsByEvent(String string) {
        ArrayList<Transition> foundTransitions = new ArrayList<>();
        for (Transition transition : transitions) {
            if (transition.getEvent().equals(string)) {
                foundTransitions.add(transition);
            }
        }
        if (foundTransitions.isEmpty()) {
            throw new NoSuchTransitionException("Transition: " + name + " not found!");
        }
        return foundTransitions;
    }

    public Transition getTransitionByEvent(String string) {
        for (Transition transition : transitions) {
            if (transition.getEvent().equals(string)) {
                return transition;
            }
        }
        throw new NoSuchTransitionException("Transition: " + name + " not found!");
    }

    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }

}
