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
package org.virtue.model.entity.player;

import org.virtue.Launcher;
import org.virtue.model.entity.Entity;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.GameEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;
import org.virtue.network.protocol.message.LoginTypeMessage;
import org.virtue.utility.ISAACCipher;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Aug 9, 2014
 */
public class Player extends Entity {
	
	/**
	 * The {@link Channel} Instance
	 */
	private Channel channel;
	
	/**
	 * The players username
	 */
	private String username;
	
	/**
	 * The players password
	 */
	private String password;
	
	/**
	 * The players privilege level
	 */
	private int rights;
	
	/**
	 * The players encoding ISAACCipher seeds
	 */
	private ISAACCipher encoding;
	
	/**
	 * The players decoding ISAACCipher seeds
	 */
	private ISAACCipher decoding;
	
	/**
	 * The var reository for the play
	 */
	private VarRepository vars;

	/**
	 * The {@link Player} Constructor
	 */
	public Player(Channel channel, String username, String password, ISAACCipher encoding, ISAACCipher decoding) {
		this.channel = channel;
		this.username = username;
		this.password = password;
		this.encoding = encoding;
		this.decoding = decoding;
		this.rights = 2;
	}
	
	public void initialise(LoginTypeMessage type) {
		this.vars = new VarRepository(this);
		switch (type) {
		case LOGIN_LOBBY:
			vars.onLobbyLogin();
			break;
		case LOGIN_WORLD:
			break;
		}
	}
	
	/**
	 * Sends a GameEventEncoder over the network
	 * @param clazz
	 * @param context
	 */
	public <T extends GameEventEncoder<?>> ChannelFuture sendEvent(Class<T> clazz, GameEventContext context) {
		if (channel.isActive()) {
			OutboundBuffer packet = Launcher.getInstance().getRepository().encode(this, clazz, context);
			ByteBuf buffer = Unpooled.copiedBuffer(packet.buffer(), 0, packet.offset());
			synchronized (channel) {
				return channel.writeAndFlush(buffer);
			}
		}
		return null;
	}
	
	/**
	 * Sends a OutBuffer over the network
	 * @param buffer
	 */
	public ChannelFuture sendBuffer(OutboundBuffer packet) {
		if (channel.isActive()) {
			ByteBuf buffer = Unpooled.copiedBuffer(packet.buffer(), 0, packet.offset());
			synchronized (channel) {
				return channel.writeAndFlush(buffer);
			}
		}
		return null;
	}
	
	/**
	 * Send a ByteBuf over the network
	 * @param buffer
	 */
	public ChannelFuture sendByteBuf(ByteBuf buffer) {
		if (channel.isActive()) {
			synchronized (channel) {
				return channel.writeAndFlush(Unpooled.copiedBuffer(buffer));
			}
		}
		return null;
	}

	/**
	 * Returns the players channel
	 */
	public Channel getChannel() {
		return channel;
	}
	
	/**
	 * Returns the players username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Returns the players password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Returns the players privilege level
	 */
	public int getRights() {
		return rights;
	}
	
	/**
	 * Returns the cipher used for encoding the opcode id
	 */
	public ISAACCipher getEncodingCipher() {
		return encoding;
	}
	
	/**
	 * Returns the cipher used for decoding the opcode id
	 */
	public ISAACCipher getDecodingCipher() {
		return decoding;
	}

	/**
	 * Returns the Vars for the player
	 */
	public VarRepository getVars() {
		return vars;
	}
	
}
