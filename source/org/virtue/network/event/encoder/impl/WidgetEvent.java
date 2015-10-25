package org.virtue.network.event.encoder.impl;

import org.virtue.model.entity.player.Player;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.impl.WidgetEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;

/**
 * @author Tom
 *
 */
public class WidgetEvent implements GameEventEncoder<WidgetEventContext> {

	@Override
	public OutboundBuffer encode(Player player, WidgetEventContext context) {
		OutboundBuffer buffer = new OutboundBuffer();
		buffer.putPacket(43, player);
		buffer.putIntB(context.getRoot() << 16 | context.getComponent());
		buffer.putIntB(0);
		buffer.putS(context.isClickable() ? 1 : 0);
		buffer.putIntB(0);
		buffer.putLEShort(context.getWidgetId());
		buffer.putLEInt(0);
		buffer.putIntB(0);
		return buffer;
	}
}
