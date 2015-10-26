/**
 * Copyright (c) 2015 Virtue 3
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
package org.virtue.network.session.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtue.Launcher;
import org.virtue.model.entity.player.Player;
import org.virtue.network.event.GameEventDefinition;
import org.virtue.network.event.context.GameEventContext;
import org.virtue.network.event.handler.GameEventHandler;
import org.virtue.network.protocol.message.GameEventMessage;
import org.virtue.network.session.Session;

import io.netty.channel.Channel;

/**
 * @author James
 * Created on Oct 25, 2015
 */
public class GameSession extends Session {

	/**
	 * The {@link Logger} instance
	 */
	private static Logger logger = LoggerFactory.getLogger(GameSession.class);

	/**
	 * 
	 */
	private static final boolean[] DOCUMENTED_OPCODES = new boolean[121];

	static {

	};

	/**
	 * The player that needs encoding/decoding
	 */
	private Player player;

	/**
	 * The {@link GameSession} constructor
	 */
	public GameSession(Channel channel, Player player) {
		super(channel);
		this.player = player;
	}

	@Override
	public void messageReceived(Object message) throws IOException {
		if (message instanceof GameEventMessage) {
			GameEventMessage req = (GameEventMessage) message;
			GameEventDefinition definition = Launcher.getInstance().getRepository().lookupReadEvent(req.getOpcode());
			if (definition == null) {
				if (!DOCUMENTED_OPCODES[req.getOpcode()]) {
					logger.warn("Unhandled Opcode: " + req.getOpcode() + ", " + req.getPayload().length());
				}
				return;
			}

			GameEventContext context = definition.getEvent().createContext(req.getPayload());
			if (context == null)
				return;

			@SuppressWarnings("unchecked")
			GameEventHandler<GameEventContext> handler = (GameEventHandler<GameEventContext>) definition.getHandler();
			if (handler == null)
				return;

			handler.handle(player, context);
		}

	}

	@Override
	public void disconnect() {

	}
}
