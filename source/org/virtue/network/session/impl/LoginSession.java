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
