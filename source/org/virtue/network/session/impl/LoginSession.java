package org.virtue.network.session.impl;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import org.virtue.Launcher;
import org.virtue.engine.service.LoginService;
import org.virtue.model.Lobby;
import org.virtue.model.World;
import org.virtue.model.entity.player.Player;
import org.virtue.network.protocol.message.LoginRequest;
import org.virtue.network.protocol.message.LoginResponse;
import org.virtue.network.protocol.message.ResponseType;
import org.virtue.network.session.Session;

import io.netty.channel.Channel;

public class LoginSession extends Session {

	/**
	 * The {@link OnDemandService} used for the ArrayDeque of pending requests
	 */
	private final LoginService service;

	/**
	 * The {@link ArrayDwque} of pending requests
	 */
	private final Deque<LoginRequest> loginQueue = new ArrayDeque<>();

	/**
	 * If the encoder is not sending any requests and no request are being
	 * queued
	 */
	private boolean idle = true;

	/**
	 * The new player
	 */
	private Player player;
	
	public LoginSession(Channel channel) {
		super(channel);
		this.service = Launcher.getInstance().getEngine().getLoginService();
	}

	@Override
	public void messageReceived(Object message) throws IOException {
		if (message instanceof LoginRequest) {
			LoginRequest request = (LoginRequest) message;

			synchronized (loginQueue) {
				loginQueue.addFirst(request);

				if (idle) {
					service.addPendingSession(this);
					idle = false;
				}
			}
		}

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	public void processLoginQueue() {
		LoginRequest request;

		synchronized (loginQueue) {
			request = loginQueue.pop();
			if (loginQueue.isEmpty()) {
				idle = true;
			} else {
				service.addPendingSession(this);
				idle = false;
			}
		}
		
		player = new Player(channel, request.getUsername(), request.getPassword(), null, null);
		
		switch (request.getType()) {
		case LOGIN_LOBBY:
			Lobby.getInstance().addPlayer(player);
			break;
		case LOGIN_WORLD:
			Lobby.getInstance().removePlayer(player);
			World.getInstance().addPlayer(player);
			break;
		}
		
		channel.writeAndFlush(new LoginResponse(request.getType(), ResponseType.STATUS_OK, player));

	}

}
