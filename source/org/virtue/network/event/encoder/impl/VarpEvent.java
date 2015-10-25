package org.virtue.network.event.encoder.impl;

import org.virtue.model.entity.player.Player;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.impl.VarpEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;

/**
 * @author Tom
 *
 */
public class VarpEvent implements GameEventEncoder<VarpEventContext> {

	@Override
	public OutboundBuffer encode(Player player, VarpEventContext context) {
		OutboundBuffer buffer = new OutboundBuffer();
		if (context.isBit()) {
			if (context.getValue() <= Byte.MIN_VALUE || context.getValue() >= Byte.MAX_VALUE) {
				buffer.putPacket(137, player);
			} else {
				buffer.putPacket(143, player);
			}
		} else {
			if (context.getValue() <= Byte.MIN_VALUE || context.getValue() >= Byte.MAX_VALUE) {
				buffer.putPacket(9, player);
				buffer.putLEShort(context.getKey());
				buffer.putIntB(context.getValue());
			} else {
				buffer.putPacket(16, player);
				buffer.putLEShortA(context.getKey());
				buffer.putC(context.getValue());
			}
		}
		return buffer;
	}
}
