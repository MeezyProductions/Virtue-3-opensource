package org.virtue.network.protocol.ondemand;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.virtue.network.protocol.message.OnDemandEncryptionMessage;
import org.virtue.network.protocol.message.OnDemandRequestMessage;

/**
 * @author Kyle Friz
 * @since Aug 8, 2014
 * 
 * @author Belthazar 
 * Revision Update: 833
 */
public class OnDemandDecoder extends ByteToMessageDecoder {

	/* (non-Javadoc)
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
		if (buf.readableBytes() < 6)
			return;
		
		int opcode = buf.readUnsignedByte();
        if (opcode == 0 || opcode == 1) {
            int type = buf.readUnsignedByte();
            int file = buf.readInt();
            out.add(new OnDemandRequestMessage(opcode == 1, type, file));
        } else if (opcode == 4) {
            int key = buf.readUnsignedByte();
            buf.readerIndex(buf.readerIndex() + 2);
            out.add(new OnDemandEncryptionMessage(key));
        } else {
            buf.readerIndex(buf.readerIndex() + 5);
        }
	}
}
