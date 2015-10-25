package org.virtue.network.event;

import org.virtue.network.event.buffer.InboundBuffer;
import org.virtue.network.event.context.GameEventContext;

/**
 * @author Tom
 *
 */
public interface GameEvent<E extends GameEventContext> {

	public E createContext(InboundBuffer buffer);
}
