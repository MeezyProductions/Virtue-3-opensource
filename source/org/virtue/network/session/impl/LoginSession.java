package org.virtue.network.session.impl;

import java.util.ArrayDeque;
import java.util.Deque;

import org.virtue.Virtue;
import org.virtue.engine.service.LoginService;
import org.virtue.model.Lobby;
import org.virtue.model.entity.player.Player;
import org.virtue.network.protocol.event.GameEventDecoder;
import org.virtue.network.protocol.login.LoginDecoder;
import org.virtue.network.protocol.login.LoginEncoder;
import org.virtue.network.protocol.message.LoginRequest;
import org.virtue.network.session.Session;

import io.netty.channel.Channel;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.virtue.network.session.Session#messageReceived(java.lang.Object)
	 */
	@Override
	public void messageReceived(Object message) {
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

	/**
	 * Processes the login queue
	 */
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
			switch (request.getType()) {
			case LOGIN_LOBBY:
				Lobby.getInstance().addPlayer(player);
				break;
			case LOGIN_WORLD:
				break;
			}

			channel.pipeline().remove(LoginDecoder.class);
			channel.pipeline().remove(LoginEncoder.class);
			channel.pipeline().addFirst("decoder", new GameEventDecoder(player.getDecodingCipher()));
			System.out.println(
					request.getUsername() + ", " + request.getPassword() + ", " + request.getType().toString());
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
