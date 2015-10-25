package org.virtue.network.event;

import org.virtue.network.event.context.GameEventContext;
import org.virtue.network.event.handler.GameEventHandler;

/**
 * @author Tom
 *
 */
public class GameEventDefinition {

	private GameEvent<? extends GameEventContext> event;
	private GameEventHandler<? extends GameEventContext> handler;

	public GameEventDefinition(GameEvent<? extends GameEventContext> event, GameEventHandler<? extends GameEventContext> handler) {
		this.event = event;
		this.handler = handler;
	}

	public GameEvent<? extends GameEventContext> getEvent() {
		return event;
	}

	public GameEventHandler<? extends GameEventContext> getHandler() {
		return handler;
	}
}
