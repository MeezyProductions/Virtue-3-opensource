/**
 * Copyright (c) 2014 Launcher Studios
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
package org.virtue;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtue.model.service.OnDemandService;
import org.virtue.network.Network;
import org.virtue.network.event.GameEventRepository;
import org.virtue.openrs.Cache;
import org.virtue.openrs.ChecksumTable;
import org.virtue.openrs.Container;
import org.virtue.openrs.FileStore;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Aug 8, 2014
 * @author Belthazar 
 * Revision Update: 833
 */
public class Launcher {

	/**
	 * The {@link Logger} Instance
	 */
	private static Logger logger = LoggerFactory.getLogger(Launcher.class);

	/**
	 * The {@link Launcher} Instance
	 */
	private static Launcher instance;
	
	/**
	 * The {@link Cache} Instance
	 */
	private static Cache cache;
	
	/**
	 * The {@link Container} Instance
	 */
	private static Container container;
	
	/**
	 * The {@link ByteBuffer} Instance containing the {@link ChecksumTable}
	 */
	private static ByteBuffer checksum;
	
	/**
	 * The {@link OnDemandService} Instance
	 */
	private static OnDemandService service;
	
	/**
	 * The {@link Executor} for the OnDemandService
	 */
	private static Executor executor;
	
	/**
	 * The {@link Network} Instance
	 */
	private static Network network;
	
	/**
	 * The {@link PacketRepository} Instance
	 */
	private static GameEventRepository repo;
	
	/**
	 * Main entry point of Launcher
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		instance = getInstance();
		try {
			instance.loadCache();
			instance.loadNetwork();
			instance.loadPackets();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			logger.info("Virtue 3 Loaded in " + TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis() - start))
					+ " seconds.");
			logger.info("Virtue 3 is currently running on " + System.getProperty("os.name") + " on a(n) "
					+ System.getProperty("os.arch") + " architecture.");
		}
	}
	
	/**
	 * Loads the cache and stores it
	 * @throws IOException 
	 */
	private void loadCache() throws IOException {
		cache = new Cache(FileStore.open(Constants.CACHE_REPOSITORY));
		container = new Container(Container.COMPRESSION_NONE, cache.createChecksumTable().encode(true, Constants.ONDEMAND_MODULUS, Constants.ONDEMAND_EXPONENT));
		checksum = container.encode();
		service = new OnDemandService();
		executor = Executors.newCachedThreadPool();
		executor.execute(service);
	}
	
	/**
	 * Loads the network and binds it to a port
	 */
	private void loadNetwork() {
		network = Network.getInstance();
		network.bindNetwork();
	}
	
	/**
	 * Loads the packets and stores them in {@link Maps}
	 */
	private void loadPackets() {
		repo = new GameEventRepository();
		repo.load();
	}
	
	/**
	 * Return the cache for the game build
	 */
	public Cache getCache() {
		return cache;
	}
	
	/**
	 * Return the container of the cache
	 */
	public Container getContainer() {
		return container;
	}
	
	/**
	 * Return the {@link ChecksumTable} data in the form of a {@link ByteBuffer}
	 */
	public ByteBuffer getChecksumTable() {
		return checksum;
	}
	
	/**
	 * Returns the OnDemand service use to service requests
	 */
	public OnDemandService getService() {
		return service;
	}
	
	/**
	 * Return the repo of encoders/decoders
	 */
	public GameEventRepository getRepository() {
		return repo;
	}
	
	/**
	 * Returns The {@link Launcher} Instance
	 */
	public static Launcher getInstance() {
		if (instance == null) {
			try {
				instance = new Launcher();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
}
