package org.virtue.script;

import java.util.HashMap;
import java.util.Vector;

import org.virtue.model.entity.player.Player;


public class Environment {

	private VirtueScript script;
	private Vector<State> taskpool = new Vector<State>();
	private HashMap<String, Byte> variables = new HashMap<String, Byte>();
	private HashMap<String, Integer> bigVariables = new HashMap<String, Integer>();
	private HashMap<String, RuntimeTrigger> runtimeTriggers = new HashMap<String, RuntimeTrigger>();
	private Player player;
	private MultiScriptEnvironment multiScriptEnvironment = null;
	private String name = "SingleTasked";

	public Environment(VirtueScript script) {
		this.script = script;
		this.name += script.getName();
	}

	public VirtueScript getScript() {
		return script;
	}

	public byte getVar(String string) {
		return variables.get(string);
	}

	public int getBigVar(String string) {
		return bigVariables.get(string);
	}

	public void setVar(String name, byte val) {
		variables.put(name, val);
	}

	public void setBigVar(String name, int val) {
		bigVariables.put(name, val);
	}

	public void endThread(State state) {
		taskpool.remove(state);
	}

	public int getThreadCount() {
		return taskpool.size();
	}

	public State getThreadState(int threadId) {
		return taskpool.get(threadId);
	}

	public void spawnThread(State state) {
		taskpool.add(state);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public RuntimeTrigger getRuntimeTrigger(Object arg0) {
		return runtimeTriggers.get(arg0);
	}

	public RuntimeTrigger setRuntimeTrigger(String arg0, RuntimeTrigger arg1) {
		if (multiScriptEnvironment != null)
			multiScriptEnvironment.addRuntimeTrigger(arg0, name);
		return runtimeTriggers.put(arg0, arg1);
	}

	public void handleRuntimeTrigger(String rtt_name, Interperter interpreter) {
		RuntimeTrigger rtt = runtimeTriggers.get(rtt_name);
		if (rtt == null)
			return;
		interpreter.handleRuntimeTrigger(rtt, this);
	}

	public void setMultiScriptEnvironment(MultiScriptEnvironment multiScriptEnvironment) {
		if (this.multiScriptEnvironment != null) {
			for (String rtt : runtimeTriggers.keySet())
				if (this.multiScriptEnvironment.hasRuntimeTrigger(rtt))
					this.multiScriptEnvironment.removeRuntimeTrigger(rtt);
			for (Trigger trig : script.getTriggers().keySet())
				if (this.multiScriptEnvironment.hasTrigger(trig))
					this.multiScriptEnvironment.removeTrigger(trig);
		}
		if (multiScriptEnvironment != null) {
			for (String rtt : runtimeTriggers.keySet())
				if (!multiScriptEnvironment.hasRuntimeTrigger(rtt))
					multiScriptEnvironment.addRuntimeTrigger(rtt, name);
			for (Trigger trig : script.getTriggers().keySet())
				if (!multiScriptEnvironment.hasTrigger(trig))
					multiScriptEnvironment.addTrigger(trig, name);
		}
		this.multiScriptEnvironment = multiScriptEnvironment;
	}

	public String getName() {
		return name;
	}

}
