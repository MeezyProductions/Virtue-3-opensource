package org.virtue.network.event.impl;

import org.virtue.model.entity.player.Player;
import org.virtue.network.event.GameEvent;
import org.virtue.network.event.buffer.InboundBuffer;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.impl.KeepAliveEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;

/**
 * @author Tom
 *
 */
public class KeepAliveEvent implements GameEvent<KeepAliveEventContext>, GameEventEncoder<KeepAliveEventContext> {

	@Override
	public KeepAliveEventContext createContext(InboundBuffer buffer) {
		return new KeepAliveEventContext();
	}

	@Override
	public OutboundBuffer encode(Player player, KeepAliveEventContext context) {
		OutboundBuffer buffer = new OutboundBuffer();
		buffer.putPacket(156, player);
		return buffer;
	}
}
