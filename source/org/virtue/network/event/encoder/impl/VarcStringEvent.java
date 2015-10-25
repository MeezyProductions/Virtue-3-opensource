package org.virtue.network.event.encoder.impl;

import org.virtue.model.entity.player.Player;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.impl.VarcStringEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;

/**
 * @author Tom
 *
 */
public class VarcStringEvent implements GameEventEncoder<VarcStringEventContext> {

	@Override
	public OutboundBuffer encode(Player player, VarcStringEventContext context) {
		OutboundBuffer buffer = new OutboundBuffer();
			if (context.getValue().length() >= Byte.MAX_VALUE) {
				buffer.putVarShort(34, player);
				buffer.putIntB(context.getKey());
				buffer.putString(context.getValue());
				buffer.finishVarShort();
			} else {
				buffer.putVarByte(7, player);
				buffer.putLEShortA(context.getKey());
				buffer.putString(context.getValue());
				buffer.finishVarByte();
			}
		return buffer;
	}
}
