package org.virtue.script;

import java.util.HashMap;

/**
 * 
 * @author James Created on Oct 26, 2015
 */
public class VirtueScript {

	private HashMap<Trigger, Integer> triggers = new HashMap<Trigger, Integer>();
	private String[] lines;
	private String name;

	public VirtueScript(String[] lines, String name) {
		this.lines = lines;
		this.name = name;
	}

	public boolean triggerExists(Trigger arg0) {
		return triggers.containsKey(arg0);
	}

	public Integer getLineNumber(Trigger arg0) {
		return triggers.get(arg0);
	}

	public Integer addTrigger(Trigger arg0, Integer arg1) {
		return triggers.put(arg0, arg1);
	}

	public String getLine(int no) {
		return lines[no];
	}

	public int getLineCount() {
		return lines.length;
	}

	public String getName() {
		return name;
	}

	public HashMap<Trigger, Integer> getTriggers() {
		return triggers;
	}

}
