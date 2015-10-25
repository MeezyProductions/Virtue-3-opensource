package org.virtue.network.event;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtue.model.entity.player.Player;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.GameEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;
import org.virtue.network.event.encoder.impl.RootWidgetEvent;
import org.virtue.network.event.encoder.impl.VarcEvent;
import org.virtue.network.event.encoder.impl.VarcStringEvent;
import org.virtue.network.event.encoder.impl.VarpEvent;
import org.virtue.network.event.encoder.impl.WidgetEvent;
import org.virtue.network.event.encoder.impl.WidgetSettingsEvent;
import org.virtue.network.event.handler.GameEventHandler;
import org.virtue.network.event.handler.impl.KeepAliveEventHandler;
import org.virtue.network.event.handler.impl.WorldListEventHandler;
import org.virtue.network.event.impl.KeepAliveEvent;
import org.virtue.network.event.impl.WorldListEvent;

/**
 * @author Tom
 *
 */
public class GameEventRepository {

	private Logger logger = LoggerFactory.getLogger(GameEventRepository.class);
	
	private Map<Integer, GameEventDefinition> readEvents = new HashMap<>();
	private Map<Class<?>, GameEventEncoder<? extends GameEventContext>> writeEvents = new HashMap<>();
	
	public void load() {
		registerReadEvent(94, new KeepAliveEvent(), new KeepAliveEventHandler());
		registerReadEvent(34, new WorldListEvent(), new WorldListEventHandler());
		logger.info("Registered " + readEvents.size() + " game read events.");
		
		registerWriteEvent(VarpEvent.class);
		registerWriteEvent(VarcEvent.class);
		registerWriteEvent(VarcStringEvent.class);
		registerWriteEvent(KeepAliveEvent.class);
		registerWriteEvent(WorldListEvent.class);
		registerWriteEvent(RootWidgetEvent.class);
		registerWriteEvent(WidgetEvent.class);
		registerWriteEvent(WidgetSettingsEvent.class);
		logger.info("Registered " + writeEvents.size() + " game write events.");
	}
	
	public GameEventDefinition lookupReadEvent(int opcode) {
		return readEvents.get(opcode);
	}
	
	public void registerWriteEvent(Class<? extends GameEventEncoder<? extends GameEventContext>> clazz) {
		try {
			writeEvents.put(clazz, clazz.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameEventContext> GameEventEncoder<T> lookupWriteEvent(Class<?> clazz) {
		return (GameEventEncoder<T>) writeEvents.get(clazz);
	}
	
	public OutboundBuffer encode(Player player, Class<?> clazz, GameEventContext context) {
		return lookupWriteEvent(clazz).encode(player, context);
	}

	public <T extends GameEventContext> void registerReadEvent(int[] opcodes, GameEvent<T> event, GameEventHandler<T> handler) {
		for (int opcode : opcodes) {
			registerReadEvent(opcode, new GameEventDefinition(event, handler));
		}
	}
	
	public <T extends GameEventContext> void registerReadEvent(int opcode, GameEvent<T> event, GameEventHandler<T> handler) {
		registerReadEvent(opcode, new GameEventDefinition(event, handler));
	}

	public void registerReadEvent(int opcode, GameEventDefinition definition) {
		readEvents.put(opcode, definition);
	}
}
