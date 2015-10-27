package org.virtue.script;

import org.virtue.model.entity.player.Location;
import org.virtue.model.entity.player.Player;

/**
 * @author James Created on Oct 26, 2015
 */
public class State {

	/**
	 * 
	 * @author James Created on Oct 26, 2015
	 */
	public enum ThreadStatus {
		RUNNING, WAIT, DELAY, PAUSE
	}

	private boolean conditionflag;
	private int currentLine = 0;
	private Location coord;
	private Player otherPlayer;
	private ThreadStatus status = ThreadStatus.RUNNING;
	private int sleepTime = 0;
	private Object object;
	private int objectType;

	/**
	 * @param sets
	 *            conditionflag
	 */
	public void setConditionFlag(boolean conditionflag) {
		this.conditionflag = conditionflag;
	}

	/**
	 * @return gets condition flag
	 */
	public boolean getConditionFlag() {
		return conditionflag;
	}

	/**
	 * @param sets
	 *            lineNumber
	 */
	public void setCurrentLine(Integer lineNumber) {
		this.currentLine = lineNumber;
	}

	/**
	 * @returns get currentline
	 */
	public int getCurrentLine() {
		return currentLine;
	}

	/**
	 * @param sets
	 *            the coord
	 */
	public void setCoord(Location coord) {
		this.coord = coord;
	}

	/**
	 * @return get coord
	 */
	public Location getCoord() {
		return coord;
	}

	/**
	 * @param status
	 */
	public void setStatus(ThreadStatus status) {
		this.status = status;
	}

	/**
	 * @return get status
	 */
	public ThreadStatus getStatus() {
		return status;
	}

	/**
	 * @return get sleeptime
	 */
	public int getSleepTime() {
		return sleepTime;
	}

	/**
	 * @param sets
	 *            the sleep time
	 */
	public void setSleepTime(int i) {
		sleepTime = i * 60;
	}

	/**
	 * @param set
	 *            sleep
	 */
	public void _setSleepTime(int i) {
		sleepTime = i;
	}

	/**
	 * @param otherPlayer
	 */
	public void setOtherPlayer(Player otherPlayer) {
		this.otherPlayer = otherPlayer;
	}

	/**
	 * @return the other player
	 */
	public Player getOtherPlayer() {
		return otherPlayer;
	}

	/**
	 * @return get Object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param set
	 *            object
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @return gets object type
	 */
	public int getObjectType() {
		return objectType;
	}

	/**
	 * 
	 * @param sets
	 *            objectType
	 */
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

}
