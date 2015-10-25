package org.virtue.network.protocol.ondemand;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Aug 8, 2014
 */
public class OnDemandXorEncoder extends MessageToByteEncoder<ByteBuf> {

	/**
	 * The XOR key
	 */
    private int key = 0;
	
	/* (non-Javadoc)
	 * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
		while (in.isReadable()) {
			out.writeByte(in.readUnsignedByte() ^ key);
		}
	}

	/**
	 * Sets the key to be used in the XOR process
	 * @param key - the key
	 */
    public void setKey(int key) {
        this.key = key;
    }

}
