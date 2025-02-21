package main;

import main.enums.ConditionType;
import main.enums.OperationType;
import main.enums.TransitionModifiers;
import main.dataClasses.Condition;
import main.dataClasses.Operation;
import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;

import java.util.*;

public class StateMachine {

	private State currentState;
	private String currentEventName;
	private Machine machine;
	private final Map<State, List<Map<TransitionModifiers, List<String>>>> transitionsToBeCreated = new HashMap<>();
	private final Map<TransitionModifiers, List<String>> currentUncreatedTransition = new HashMap<>();
	private final Map<String, Integer> integers = new HashMap<>();

	public Machine build() {
		Machine returnMachine = getMachine();
		this.machine.setIntegers(this.integers);
		if (currentState == null) {
			return returnMachine;
		}
		returnMachine.addState(currentState);

		if (!currentUncreatedTransition.isEmpty()) {
			flushActivelyEditingTransition();
		}

		this.transitionsToBeCreated.forEach((state, transitions) -> {

			transitions.forEach(methodCalls -> {
				String eventName = methodCalls.get(TransitionModifiers.WHEN).get(0);
				State target = machine.getState(methodCalls.get(TransitionModifiers.TO).get(0));

				Transition transition = new Transition(eventName, target);

				if (methodCalls.containsKey(TransitionModifiers.SET)) {
					List<String> args = methodCalls.get(TransitionModifiers.SET);
					transition.setOperation(new Operation(OperationType.SET, args.get(0), Integer.parseInt(args.get(1))));
				} else if (methodCalls.containsKey(TransitionModifiers.INCREMENT)) {
					List<String> args = methodCalls.get(TransitionModifiers.INCREMENT);
					transition.setOperation(new Operation(OperationType.INCREMENT, args.get(0), 0));
				} else if (methodCalls.containsKey(TransitionModifiers.DECREMENT)) {
					List<String> args = methodCalls.get(TransitionModifiers.DECREMENT);
					transition.setOperation(new Operation(OperationType.DECREMENT, args.get(0), 0));
				}

				if (methodCalls.containsKey(TransitionModifiers.IF_EQUALS)) {
					List<String> args = methodCalls.get(TransitionModifiers.IF_EQUALS);
					transition.setCondition(new Condition(ConditionType.EQUAL, args.get(0), Integer.parseInt(args.get(1))));
				} else if (methodCalls.containsKey(TransitionModifiers.IF_LESS_THAN)) {
					List<String> args = methodCalls.get(TransitionModifiers.IF_LESS_THAN);
					transition.setCondition(new Condition(ConditionType.LESS_THAN, args.get(0), Integer.parseInt(args.get(1))));
				} else if (methodCalls.containsKey(TransitionModifiers.IF_GREATER_THAN)) {
					List<String> args = methodCalls.get(TransitionModifiers.IF_GREATER_THAN);
					transition.setCondition(new Condition(ConditionType.GREATER_THAN, args.get(0), Integer.parseInt(args.get(1))));
				}
				state.addTransition(transition);
			});
		});

		return returnMachine;
	}

	public StateMachine state(String name) {
		if (currentState != null) {
			getMachine().addState(currentState);
		}
		if (!currentUncreatedTransition.isEmpty()) {
			flushActivelyEditingTransition();
		}
		currentState = new State(name);
		return this;
	}

	public StateMachine initial() {
		getMachine().setInitialState(currentState);
		return this;
	}

	public StateMachine when(String event) {
		if (!currentUncreatedTransition.isEmpty()) {
			flushActivelyEditingTransition();
		}
		currentUncreatedTransition.put(TransitionModifiers.WHEN, Collections.singletonList(event));
		return this;
	}

	public StateMachine to(String stateName) {
		currentUncreatedTransition.put(TransitionModifiers.TO, Collections.singletonList(stateName));
		return this;
	}

	public StateMachine integer(String string) {
		this.integers.put(string, new Integer(0));
		return this;
	}

	public StateMachine set(String variableName, int i) {
		currentUncreatedTransition.put(TransitionModifiers.SET, Arrays.asList(variableName, "" + i));
		return this;
	}

	public StateMachine increment(String variableName) {
		currentUncreatedTransition.put(TransitionModifiers.INCREMENT, Collections.singletonList(variableName));
		return this;
	}

	public StateMachine decrement(String variableName) {
		currentUncreatedTransition.put(TransitionModifiers.DECREMENT, Collections.singletonList(variableName));
		return this;
	}

	public StateMachine ifEquals(String variableName, int i) {
		currentUncreatedTransition.put(TransitionModifiers.IF_EQUALS, Arrays.asList(variableName, Integer.toString(i)));
		return this;
	}

	public StateMachine ifGreaterThan(String variableName, int i) {
		currentUncreatedTransition.put(TransitionModifiers.IF_GREATER_THAN, Arrays.asList(variableName, Integer.toString(i)));
		return this;
	}

	public StateMachine ifLessThan(String variableName, int i) {
		currentUncreatedTransition.put(TransitionModifiers.IF_LESS_THAN, Arrays.asList(variableName, Integer.toString(i)));
		return this;
	}

	private Machine getMachine() {
		if (machine == null) {
			machine = new Machine();
		}
		return machine;
	}

	private void flushActivelyEditingTransition() {
		transitionsToBeCreated.computeIfAbsent(currentState, k -> new ArrayList<>());
		transitionsToBeCreated.get(currentState).add(Map.copyOf(currentUncreatedTransition));
		currentUncreatedTransition.clear();
	}
}
