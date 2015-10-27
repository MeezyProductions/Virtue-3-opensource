package org.virtue.script;

public class RuntimeTrigger {

	private Object parameter;
	private RuntimeTriggerType type;
	private State state;

	/**
	 * @param type
	 * @param parameter
	 * @param state
	 */
	public RuntimeTrigger(RuntimeTriggerType type, Object parameter, State state) {
		this.type = type;
		this.parameter = parameter;
		this.state = state;
	}

	/**
	 * @return gets Parameter
	 */
	public Object getParameter() {
		return parameter;
	}

	/**
	 * @return get type
	 */
	public RuntimeTriggerType getType() {
		return type;
	}

	/**
	 * @return get state
	 */
	public State getState() {
		return state;
	}

	/**
	 * 
	 * @author James Created on Oct 26, 2015
	 */
	public enum RuntimeTriggerType {
		LABEL, RESUME,
	}

}
