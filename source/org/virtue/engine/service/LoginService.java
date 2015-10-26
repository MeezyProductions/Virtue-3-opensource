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
package org.virtue.engine.service;

import java.util.ArrayDeque;
import java.util.Queue;

import org.virtue.network.session.Session;
import org.virtue.network.session.impl.LoginSession;

/**
 * 
 * @author Kyle Friz
 * @since Sep 4, 2014
 */
public class LoginService implements Runnable {

	/**
	 * The {@link ArrayDeque} of {@link Session}
	 */
	private final Queue<LoginSession> pendingSessions = new ArrayDeque<>();

	/**
	 * Adds a {@link Session} to the pendingSessions array
	 * 
	 * @param session
	 */
	public void addPendingSession(LoginSession session) {
		synchronized (pendingSessions) {
			pendingSessions.add(session);
			pendingSessions.notifyAll();
		}
	}

	@Override
	public void run() {
		for (;;) {
			LoginSession session;
			synchronized (pendingSessions) {
				while ((session = pendingSessions.poll()) == null) {
					try {
						pendingSessions.wait();
					} catch (InterruptedException e) {
						/* ignore */
					}
				}
			}
			session.processLoginQueue();
		}
	}

}