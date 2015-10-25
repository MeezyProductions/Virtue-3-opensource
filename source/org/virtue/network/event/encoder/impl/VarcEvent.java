package org.virtue.network.event.encoder.impl;

import org.virtue.model.entity.player.Player;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.impl.VarcEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;

/**
 * @author Tom
 *
 */
public class VarcEvent implements GameEventEncoder<VarcEventContext> {

	@Override
	public OutboundBuffer encode(Player player, VarcEventContext context) {
		OutboundBuffer buffer = new OutboundBuffer();
		if (context.isBit()) {
			if (context.getValue() <= Byte.MIN_VALUE || context.getValue() >= Byte.MAX_VALUE) {
				buffer.putPacket(55, player);
			} else {
				buffer.putPacket(58, player);
			}
		} else {
			if (context.getValue() <= Byte.MIN_VALUE || context.getValue() >= Byte.MAX_VALUE) {
				buffer.putPacket(149, player);
				buffer.putLEInt(context.getValue());
				buffer.putLEShort(context.getKey());
			} else {
				buffer.putPacket(52, player);
				buffer.putS(context.getValue());
				buffer.putLEShort(context.getKey());
			}
		}
		return buffer;
	}
}
