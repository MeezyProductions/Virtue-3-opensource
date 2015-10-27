package org.virtue.model.entity.player;

/**
 * 
 * @author James Created on Oct 26, 2015
 */
public class Animation {

	/**
	 * Different animation constants.
	 */
	public final static Animation RESET = create(-1);

	/**
	 * Creates an animation with no delay.
	 * 
	 * @param id
	 *            The id.
	 * @return The new animation object.
	 */
	public static Animation create(int id) {
		return create(id, 0);
	}

	/**
	 * Creates an animation.
	 * 
	 * @param id
	 *            The id.
	 * @param delay
	 *            The delay.
	 * @return The new animation object.
	 */
	public static Animation create(int id, int delay) {
		return new Animation(id, delay);
	}

	/**
	 * The id.
	 */
	private int id;

	/**
	 * The delay.
	 */
	private int delay;

	/**
	 * Creates an animation.
	 * 
	 * @param id
	 *            The id.
	 * @param delay
	 *            The delay.
	 */
	private Animation(int id, int delay) {
		this.id = id;
		this.delay = delay;
	}

	/**
	 * Gets the id.
	 * 
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the delay.
	 * 
	 * @return The delay.
	 */
	public int getDelay() {
		return delay;
	}

}
