package org.virtue.network.event.impl;

import java.util.List;

import org.virtue.model.World;
import org.virtue.model.WorldEntry;
import org.virtue.model.WorldList;
import org.virtue.model.entity.player.Player;
import org.virtue.network.event.GameEvent;
import org.virtue.network.event.buffer.InboundBuffer;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.impl.WorldListEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;

/**
 * @author Tom
 *
 */
public class WorldListEvent implements GameEvent<WorldListEventContext>, GameEventEncoder<WorldListEventContext> {

	@Override
	public WorldListEventContext createContext(InboundBuffer buffer) {
		int updateType = buffer.getInt();
		return new WorldListEventContext(updateType);
	}

	@Override
	public OutboundBuffer encode(Player player, WorldListEventContext context) {
		OutboundBuffer buffer = new OutboundBuffer();
		buffer.putVarShort(82, player);
		
		List<WorldEntry> worlds = WorldList.entries();
		buffer.putByte(1);
		buffer.putByte(2);
		buffer.putByte(context.isFullUpdate() ? 1 : 0);
		
		int size = worlds.size();
		if (context.isFullUpdate()) {
			buffer.putSmart(size);
			for (WorldEntry world : worlds) {
				buffer.putSmart(world.getCountry());
				buffer.putJagString(world.getRegion());
			}
			buffer.putSmart(0);
			buffer.putSmart(size + 1);
			buffer.putSmart(size);
			for (WorldEntry world : worlds) {
				buffer.putSmart(world.getId());
				buffer.putByte(0);
				buffer.putInt(world.isMembers() ? world.isLootshare() ? 0x1 | 0x8 : 0x1 : 0x8);
				buffer.putSmart(0);
				buffer.putJagString(world.getRegion());
				buffer.putJagString(world.getIp());
			}
			buffer.putInt(-1723296702);
		}
		
		for (WorldEntry world : worlds) {
			buffer.putSmart(world.getId());
			buffer.putShort(World.getInstance().getSize());
		}
		
		buffer.finishVarShort();
		return buffer;
	}

}
