/**
 * Copyright (c) 2014 Virtue Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.virtue.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtue.model.entity.EntityList;
import org.virtue.model.entity.player.Player;

/**
 * @author Kyle Friz
 * @since Aug 8, 2014
 */
public class World {

	/**
	* The {@link Logger} instance
	*/
	private static Logger logger = LoggerFactory.getLogger(World.class);
	
	/**
	 * The {@link EntityList} instance
	 */
	private static EntityList<Player> players = new EntityList<>(2048);

	/**
	* The {@link World} instance
	*/
	public static World instance;
	
	/**
	 * Adds a player into the {@link EntityList} instance
	 * @param player - the player to add
	 */
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	/**
	 * Returns the size of the {@link EntityList} instance
	 * @return
	 */
	public int getSize() {
		return players.size();
	}

	/**
	* Returns the {@link World} instance
	*/
	public static World getInstance() {
		if (instance == null) {
			try {
				instance = new World();
			} catch (Exception e) {
				logger.error("Error creating new World instance", e);
			}
		}
		return instance;
	}}
