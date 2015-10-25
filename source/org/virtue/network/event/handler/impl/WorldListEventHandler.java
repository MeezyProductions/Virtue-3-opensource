package org.virtue.network.event.handler.impl;

import org.virtue.model.entity.player.Player;
import org.virtue.network.event.context.impl.WorldListEventContext;
import org.virtue.network.event.handler.GameEventHandler;
import org.virtue.network.event.impl.WorldListEvent;

/**
 * @author Tom
 *
 */
public class WorldListEventHandler implements GameEventHandler<WorldListEventContext> {

	@Override
	public void handle(Player player, WorldListEventContext context) {
		player.sendEvent(WorldListEvent.class, context);
	}
}
