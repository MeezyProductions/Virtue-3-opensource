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
package org.virtue.network.session.impl;

import java.util.ArrayDeque;
import java.util.Deque;

import org.virtue.Virtue;
import org.virtue.engine.service.LoginService;
import org.virtue.engine.service.OnDemandService;
import org.virtue.model.Lobby;
import org.virtue.model.World;
import org.virtue.model.entity.player.Player;
import org.virtue.model.entity.player.PlayerInformation;
import org.virtue.network.NetworkHandler;
import org.virtue.network.protocol.event.GameEventDecoder;
import org.virtue.network.protocol.login.LoginDecoder;
import org.virtue.network.protocol.login.LoginEncoder;
import org.virtue.network.protocol.message.ResponseTypeMessage;
import org.virtue.network.protocol.message.login.LoginRequestMessage;
import org.virtue.network.protocol.message.login.LoginResponseMessage;
import org.virtue.network.session.Session;
import org.virtue.parser.ParserDataType;

import io.netty.channel.Channel;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Aug 8, 2014
 */
public class LoginSession extends Session {

	/**
	 * The {@link LoginSession} constructor
	 */
	public LoginSession(Channel channel) {
		super(channel);
		this.service = Virtue.getInstance().getEngine().getLoginService();
	}

	/**
	 * The {@link OnDemandService} used for the ArrayDeque of pending requests
	 */
	private final LoginService service;

	/**
	 * The {@link ArrayDwque} of pending requests
	 */
	private final Deque<LoginRequestMessage> loginQueue = new ArrayDeque<>();

	/**
	 * If the encoder is not sending any requests and no request are being
	 * queued
	 */
	private boolean idle = true;

	/**
	 * The new player
	 */
	private Player player;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.virtue.network.session.Session#messageReceived(java.lang.Object)
	 */
	@Override
	public void messageReceived(Object message) {
		if (message instanceof LoginRequestMessage) {
			LoginRequestMessage request = (LoginRequestMessage) message;

			synchronized (loginQueue) {
				loginQueue.addFirst(request);

				if (idle) {
					service.addPendingSession(this);
					idle = false;
				}
			}
		}
	}

	/**
	 * Processes the login queue
	 */
	public void processLoginQueue() {
		LoginRequestMessage request;

		synchronized (loginQueue) {
			request = loginQueue.pop();
			if (loginQueue.isEmpty()) {
				idle = true;
			} else {
				service.addPendingSession(this);
				idle = false;
			}
		}
		if (request != null) {
			int response = checkPlayer(request);
			if (player == null) {
				// player = new Player(request.getChannel(),
				// request.getUsername(), request.getPassword(),
				// PrivilegeLevel.forOpcode(2), CombatMode.EOC,
				// request.getEncodingCipher(), request.getDecodingCipher());
				channel.pipeline().remove(LoginDecoder.class);
				channel.writeAndFlush(new LoginResponseMessage(null, response, null));
				// channel.close();
				return;
			}

			switch (request.getLoginType()) {
			case LOGIN_LOBBY:
				break;
			case LOGIN_WORLD:

				break;
			}
			// player.initialize();

			channel.writeAndFlush(new LoginResponseMessage(player, response, request.getLoginType()));

			channel.pipeline().remove(LoginDecoder.class);
			channel.pipeline().remove(LoginEncoder.class);
			channel.pipeline().addFirst("decoder", new GameEventDecoder(player.getDecodingCipher()));
			channel.attr(NetworkHandler.attachment).set(new GameSession(channel, player));

			player.getDispatcher().dispatchLogin(request.getLoginType());
			System.out.println(
					request.getUsername() + ", " + request.getPassword() + ", " + request.getLoginType().toString());
		}
	}

	public int checkPlayer(LoginRequestMessage request) {
		PlayerInformation info;
			if (request.getUsername().length() <= 12) {
				info = Virtue.getInstance().getAccountIndex().lookupByUsername(request.getUsername());
			} else {
				info = null;
			}
		}
		if (info == null) {
			return ResponseTypeMessage.STATUS_INVALID_PASSWORD.getCode();
		}
		request.setUsername(info.getUsername());
		Object response = Virtue.getInstance().getParserRepository().getJsonParser().loadObjectDefinition(request,
				ParserDataType.CHARACTER);
		if (response instanceof Player) {
			player = (Player) response;
			player.setNames(info.getDisplayName(), info.getPrevName());
			/*
			 * if (player.isAuthenticated()) { return
			 * ResponseTypeMessage.AUTHENTICATED.getCode(): } else { return
			 * ResponseTypeMessage.STATUS_OK.getCode(); }
			 */
			return ResponseTypeMessage.STATUS_OK.getCode();
		}
		return (Integer) response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.virtue.network.session.Session#disconnect()
	 */
	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}
}
