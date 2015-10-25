package org.virtue.network.event.handler.impl;

import org.virtue.model.entity.player.Player;
import org.virtue.network.event.context.impl.KeepAliveEventContext;
import org.virtue.network.event.handler.GameEventHandler;
import org.virtue.network.event.impl.KeepAliveEvent;

/**
 * @author Tom
 *
 */
public class KeepAliveEventHandler implements GameEventHandler<KeepAliveEventContext> {

	@Override
	public void handle(Player player, KeepAliveEventContext context) {
		player.sendEvent(KeepAliveEvent.class, context);
	}
}
