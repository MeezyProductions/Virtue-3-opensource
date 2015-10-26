package org.virtue.network.protocol.message;

import org.virtue.network.protocol.login.LoginDecoder.LoginTypes;

/**
 * @author Chryonic <http://rune-server.org/members/chryonic> Made on Oct 26,
 *         2015 for Virtue 3 opensource
 */
public class LoginRequest {


	private LoginTypes type;
	protected String username;
	protected String password;

	public LoginRequest(String username, String password, LoginTypes type) {
		this.username = username;
		this.password = password;
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public LoginTypes getType() {
		return type;
	}
}
