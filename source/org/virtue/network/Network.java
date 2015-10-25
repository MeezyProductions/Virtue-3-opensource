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
package org.virtue.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Aug 8, 2014
 */
public class Network {

	/**
	 * The {@link Logger} Instance
	 */
	private static Logger logger = LoggerFactory.getLogger(Network.class);

	/**
	 * The {@link Network} Instance
	 */
	public static Network instance;
	
	/**
	 * The {@link ServerBootstrap} Instance
	 */
	private ServerBootstrap bootstrap;

	/**
	 * Returns The {@link Network} Instance
	 */
	public static Network getInstance() {
		if (instance == null) {
			try {
				instance = new Network();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/**
	 * Binds a pipeline to a port
	 */
	public void bindNetwork() {
		bootstrap = new ServerBootstrap();
		bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup());
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 100);
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childHandler(new NetworkPipeline());
		try {
			bootstrap.localAddress(43594).bind().sync();
		} catch (InterruptedException exception) {
			logger.error("Error in binding the network", exception);
		} finally {
			logger.info("Bound Launcher to the network");
		}
	}
}
