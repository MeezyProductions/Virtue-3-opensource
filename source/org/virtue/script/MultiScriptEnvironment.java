package org.virtue.script;

import java.util.HashMap;

import org.virtue.model.entity.player.Player;

abstract class MultiScriptEnvironment {

	private HashMap<String, Environment> envs = new HashMap<String, Environment>();
	private HashMap<String, String> rtts = new HashMap<String, String>();
	private HashMap<Trigger, String> trigs = new HashMap<Trigger, String>();

	public void addEnvironment(Environment e) {
		e.setMultiScriptEnvironment(this);
		envs.put(e.getName(), e);
	}

	public void removeEnvironment(Environment e) {
		e.setMultiScriptEnvironment(null);
		envs.remove(e.getName());
	}

	public Environment getEnvironment(String name) {
		if (name == null)
			return null;
		return envs.get(name);
	}

	public Environment getEnvironmentForTrigger(String rtt) {
		if (rtt == null)
			return null;
		return getEnvironment(rtts.get(rtt));
	}

	public Environment getEnvironmentForTrigger(Trigger trig) {
		if (trig == null)
			return null;
		return getEnvironment(trigs.get(trig));
	}

	public void addRuntimeTrigger(String arg0, String name) {
		rtts.put(arg0, name);
	}

	public boolean hasRuntimeTrigger(String rtt) {
		return rtts.containsKey(rtt);
	}

	public void removeRuntimeTrigger(String arg0) {
		rtts.remove(arg0);
	}

	public void handleRuntimeTrigger(String rtt_name, Interperter interperter) {
		if (!rtts.containsKey(rtt_name))
			return;
		getEnvironmentForTrigger(rtt_name).handleRuntimeTrigger(rtt_name, interperter);
	}

	public boolean handleTrigger(Trigger trig, Interperter interperter, Player p, Object subject, Object object) {
		if (!trigs.containsKey(trig) && interperter.getScriptForTrigger(trig) == null)
			return false;
		Environment environment = getEnvironmentForTrigger(trig);
		boolean launch = false;
		VirtueScript script;
		if (environment == null) {
			script = interperter.getScriptForTrigger(trig);
			environment = new Environment(script);
			environment.setPlayer(p);
			addEnvironment(environment);
			launch = true;
		}
		interperter.handleTrigger(trig, environment, subject, object);
		if (launch)
			launchEnvironment(environment, interperter);
		return true;
	}

	public abstract void launchEnvironment(Environment environment, Interperter interperter);

	public void addTrigger(Trigger arg0, String name) {
		trigs.put(arg0, name);
	}

	public void removeTrigger(Trigger arg0) {
		trigs.remove(arg0);
	}

	public boolean hasTrigger(Trigger rtt) {
		return trigs.containsKey(rtt);
	}

}
