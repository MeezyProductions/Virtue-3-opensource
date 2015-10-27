package org.virtue.model.entity.player;

/**
 * 
 * @author James Created on Oct 26, 2015
 */
public final class Locatiom {

	private int x;
	private int y;
	private int plane;

	public Locatiom(int x, int y, int plane) {
		this.x = x;
		this.y = y;
		this.plane = plane;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getPlane() {
		return plane;
	}

	public int getChunkX() {
		return (x >> 3);
	}

	public int getChunkY() {
		return (y >> 3);
	}

	public int getRegionX() {
		return (x >> 6);
	}

	public int getRegionY() {
		return (y >> 6);
	}

	public int getRegionHash() {
		return getRegionY() + (getRegionX() << 8) + (plane << 16);
	}

	public int getTileHash() {
		return y + (x << 14) + (plane << 28);
	}

	public boolean withinDistance(Locatiom position, int distance) {
		if (position.plane != plane)
			return false;
		int deltaX = position.x - x, deltaY = position.y - y;
		return deltaX <= distance && deltaX >= -distance && deltaY <= distance && deltaY >= -distance;
	}

	public Locatiom copy() {
		return new Locatiom(x, y, plane);
	}
}
