package org.virtue.network.event.encoder.impl;

import org.virtue.model.entity.player.Player;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.impl.RootWidgetEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;

/**
 * @author Tom
 *
 */
public class RootWidgetEvent implements GameEventEncoder<RootWidgetEventContext> {

	@Override
	public OutboundBuffer encode(Player player, RootWidgetEventContext context) {
		OutboundBuffer buffer = new OutboundBuffer();
		buffer.putPacket(123, player);
		buffer.putIntB(0);
		buffer.putS(0);
		buffer.putLEShort(context.getWidgetType());
		buffer.putIntA(0);
		buffer.putLEInt(0);
		buffer.putIntV3(0);
		return buffer;
	}
}
