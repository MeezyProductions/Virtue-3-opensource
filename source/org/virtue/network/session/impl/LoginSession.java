package org.virtue.network.session.impl;

import java.io.IOException;

import org.virtue.network.session.Session;

import io.netty.channel.Channel;

public class LoginSession extends Session {

	public LoginSession(Channel channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}

	public void processLoginQueue() {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(Object message) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

}
