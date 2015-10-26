package org.virtue.network.protocol.ondemand;

import org.virtue.network.protocol.message.OnDemandResponseMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Aug 8, 2014
 */
public class OnDemandEncoder extends MessageToByteEncoder<OnDemandResponseMessage> {

	/* (non-Javadoc)
	 * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, OnDemandResponseMessage response, ByteBuf buf) throws Exception {
		ByteBuf container = response.getContainer();
        int type = response.getType();
        int file = response.getFile();
        int compression = container.readUnsignedByte();
        int size = container.readInt();

        if (!response.isPriority())
			file |= ~0x7FFFFFFF;
        
        ByteBuf buffer = Unpooled.buffer();
        
        buffer.writeByte(type);
        buffer.writeInt(file);
        buffer.writeByte(compression);
        buffer.writeInt(size);

		int bytes = container.readableBytes();
		if (bytes > 502) {
			bytes = 502;
		}
        buffer.writeBytes(container.readBytes(bytes));

        while ((bytes = container.readableBytes()) != 0) {
            bytes = container.readableBytes();
            if (bytes == 0)
                break;
            else if (bytes > 507)
                bytes = 507;

            buffer.writeByte(type);
            buffer.writeInt(file);
            buffer.writeBytes(container.readBytes(bytes));
        }
        ctx.channel().writeAndFlush(buffer);
	}

}
