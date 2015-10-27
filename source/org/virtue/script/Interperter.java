package org.virtue.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import org.virtue.script.State.ThreadStatus;

public class Interperter {

	private HashMap<Trigger, VirtueScript> scriptTrigs = new HashMap<Trigger, VirtueScript>();

	/**
	 * @param trig
	 * @return
	 */
	public VirtueScript getScriptForTrigger(Trigger trig) {
		return scriptTrigs.get(trig);
	}

	/**
	 * @param rtt
	 * @param environment
	 */
	public void handleRuntimeTrigger(RuntimeTrigger rtt, Environment environment) {
		State state = rtt.getState();
		VirtueScript script = environment.getScript();
		boolean spawnThread = false;
		if (state == null) {
			state = new State();
			spawnThread = true;
		}
		switch (rtt.getType()) {
		case LABEL:
			Trigger labeltrigger = new Trigger("label", rtt.getParameter(), null);
			if (script.triggerExists(labeltrigger))
				state.setCurrentLine(script.getLineNumber(labeltrigger));
			break;
		}
		if (state.getStatus() == ThreadStatus.WAIT)
			state.setStatus(ThreadStatus.RUNNING);
		if (spawnThread)
			environment.spawnThread(state);
	}

	public void handleTrigger(Trigger t, Environment environment, Object subject, Object object) {
		VirtueScript script = environment.getScript();
		if (script.triggerExists(t)) {
			for (Trigger tt : script.getTriggers().keySet())
				if (tt.equals(t))
					t = tt;
			State state = new State();
			state.setCurrentLine(script.getLineNumber(t));
			switch (t.getSubjectTarget()) {
			case Trigger.OBJ:
				break;
			case Trigger.ITEM:
				break;
			case Trigger.LOC:
				break;
			case Trigger.NPC:
				break;
			case Trigger.PLR:
				break;
			}
			state.setObject(object);
			state.setObjectType(t.getObjectTarget());
			environment.spawnThread(state);
		}
	}


	public static VirtueScript loadScript(String filename) {
		try {
			File file = new File(filename);
			BufferedReader b = new BufferedReader(new FileReader(file));
			Vector<String> lines = new Vector<String>();
			while (true) {
				String s = b.readLine();
				if (s == null)
					break;
				lines.add(s);
			}
			return new VirtueScript(lines.toArray(new String[0]), file.getName());
		} catch (IOException e) {
			return null;
		}
	}

}
