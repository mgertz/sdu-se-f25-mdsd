package main.metamodel;

import main.exception.NoSuchStateException;

import java.util.*;

public class Machine {
	private State initialState;

	private final List<State> states = new ArrayList<>();
	private Map<String, Integer> integers = new HashMap<>();

	public Machine() {
	}

	public List<State> getStates() {
		return states;
	}

	public State getInitialState() {
		return initialState;
	}

	public State getState(String name) {
		try {
			return states.stream().filter(state -> state.getName().equals(name)).findFirst().orElseThrow();
		} catch (NoSuchElementException e) {
			throw new NoSuchStateException("State: " + name + " not found!");
		}
	}

	public int numberOfIntegers() {
		return this.integers.size();
	}

	public boolean hasInteger(String string) {
		return this.integers.containsKey(string);
	}

	public int getInteger(String string) {
		return this.integers.get(string);
	}

	public void setIntegers(Map<String, Integer> integers) {
		this.integers = integers;
	}

	public void setInteger(String variable, int value) {
		this.integers.put(variable, value);
	}

	public void addState(State state) {
		this.states.add(state);
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

}

