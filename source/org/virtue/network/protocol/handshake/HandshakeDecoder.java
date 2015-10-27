package org.virtue.network.protocol.handshake;

import java.util.List;

import org.virtue.Constants;
import org.virtue.network.protocol.login.LoginDecoder;
import org.virtue.network.protocol.login.LoginEncoder;
import org.virtue.network.protocol.message.HandshakeMessage;
import org.virtue.network.protocol.ondemand.OnDemandDecoder;
import org.virtue.network.protocol.ondemand.OnDemandEncoder;
import org.virtue.network.protocol.ondemand.OnDemandXorEncoder;
import org.virtue.utility.BufferUtility;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author Kyle Friz
 * @since Aug 8, 2014
 */
public class HandshakeDecoder extends ByteToMessageDecoder {

	/* (non-Javadoc)
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
		if (buf.isReadable()) {
			HandshakeMessage type = new HandshakeMessage(buf.readUnsignedByte());
			if (type.getType() == null)
				return;
			
			switch (type.getType()) {
			case HANDSHAKE_LOGIN:
				ensureResponse(ctx);
				System.out.println("logggggggggggggggggggggginnnnnnnn");
				ctx.pipeline().replace("decoder", "decoder", new LoginDecoder());
				ctx.pipeline().addAfter("decoder", "encoder", new LoginEncoder());
				break;
			case HANDSHAKE_ONDEMAND:
				ensureResponse(ctx, buf);
				ctx.pipeline().replace("decoder", "decoder", new OnDemandDecoder());
				ctx.pipeline().addAfter("decoder", "xor-encoder", new OnDemandXorEncoder());
				ctx.pipeline().addAfter("xor-encoder", "encoder", new OnDemandEncoder());
				break;
			default:
				break;
			}
			if (buf.isReadable()) {
				// out.add(new Object[] { type,
				// buf.readBytes(buf.readableBytes()) });
			} else {
				out.add(type);
			}
		}
	}
	
	private void ensureResponse(ChannelHandlerContext ctx) {
		ByteBuf buffer = Unpooled.buffer(1);
		buffer.writeByte(0);
		ctx.channel().writeAndFlush(buffer);
	}
	
	private void ensureResponse(ChannelHandlerContext ctx, ByteBuf buf) {
		if (buf.readableBytes() < 6)
			return;
		
		ByteBuf buffer = Unpooled.buffer();
		
		int length = buf.readUnsignedByte();
        if (buf.readableBytes() >= length) {
        	int major = buf.readInt();
        	int minor = buf.readInt();
        	String token = BufferUtility.readString(buf);
			buf.readUnsignedByte();// 850+ Language ID
           if (major != Constants.FRAME_MAJOR && minor != Constants.FRAME_MINOR) {
                buffer.writeByte(6);
            } else {
            	if (!token.equals(Constants.ONDEMAND_TOKEN)) {
            		buffer.writeByte(6);
            	} else {
            			buffer.writeByte(0);
            			for (int i = 0; i < Constants.ONDEMAND_DELTA.length; i++) {
            				buffer.writeInt(Constants.ONDEMAND_DELTA[i]);
            			}
            	}
            }
        }
		ctx.channel().writeAndFlush(buffer);
	}
	
}