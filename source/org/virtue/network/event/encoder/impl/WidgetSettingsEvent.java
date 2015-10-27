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
package org.virtue.network.event.encoder.impl;

import org.virtue.model.entity.player.Player;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.impl.WidgetSettingsEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;

/**
 * @author Kyle Friz
 * @since Sep 13, 2014
 */
public class WidgetSettingsEvent implements GameEventEncoder<WidgetSettingsEventContext> {

	/* (non-Javadoc)
	 * @see org.virtue.network.event.encoder.GameEventEncoder#encode(org.virtue.model.entity.player.Player, org.virtue.network.event.context.GameEventContext)
	 */
	@Override
	public OutboundBuffer encode(Player player, WidgetSettingsEventContext context) {
		OutboundBuffer buffer = new OutboundBuffer();
		buffer.putPacket(121, player);
		buffer.putIntA(context.getRoot() << 16 | context.getComponent());
		buffer.putLEShortA(context.getTo());
		buffer.putLEInt(context.getSettings());
		buffer.putShort(context.getFrom());
		return buffer;
	}

}
