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
package org.virtue.network.protocol.login;

/**
 * @author James
 * Created on Oct 25, 2015
 */
public enum Response {

	LOGIN_CONTINUE(0), LOGIN_OK(2), INVALID_PASSWORD(3), DISABLED_ACCOUNT(4), ACCOUNT_LOGGED_IN(5), SERVER_UPDATED(
			6), WORLD_FULL(7), LOGIN_SERVER_OFFLINE(8), TO_MANY_CONNECTIONS(9), BAD_SESSION_ID(
					10), LOGIN_SERVER_REJECTED(11), MEMBERS_WORLD(12), LOGIN_INCOMPLETE(13), SERVER_UPDATE(
							14), LOGIN_EXCEEDED(16), IN_MEMBERS_AREA(17), INVALID_LOGIN_SERVER_REQUEST(
									20), JUST_LEFT_WORLD(21);

	private final int response;

	/**
	 * Creates a new {@link org.virtue.network.login.Response}.
	 * 
	 * @param response
	 *            The response.
	 */
	Response(int response) {
		this.response = response;
	}

	/**
	 * Gets the response id.
	 * 
	 * @return The {@code response}.
	 */
	public int getResponse() {
		return response;
	}
}
