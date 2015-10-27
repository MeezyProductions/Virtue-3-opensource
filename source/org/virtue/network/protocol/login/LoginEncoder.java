package org.virtue.network.protocol.login;

import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.protocol.message.login.LoginResponseMessage;
import org.virtue.network.protocol.message.login.LoginTypeMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author Kyle Friz
 * Created on Oct 26, 2015
 */
public class LoginEncoder extends MessageToByteEncoder<LoginResponseMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, LoginResponseMessage response, ByteBuf out) throws Exception {
		if (response.getReturnCode() != 2) {
			ctx.channel().writeAndFlush(Unpooled.buffer(1).writeByte(response.getReturnCode()));
			return;
		}
		
		OutboundBuffer packet = new OutboundBuffer();
		packet.putVarByte(response.getReturnCode());
		
		if (response.getLoginType().equals(LoginTypeMessage.LOGIN_LOBBY)) {
			packet.putByte(0);
			
			packet.putByte(response.getPlayer().getRights());
			packet.putByte(0);
			packet.putByte(0);// 0 or 1

			packet.putTri(8388608);
			packet.putByte(0);//gender
			packet.putByte(0);// 0 or 1
			packet.putByte(0);// 0 or 1

			packet.putLong(1386299915556L);//sub end date
			packet.put5ByteInteger(125050283515445249L);
			packet.putByte(0x1 | 0x2);

			packet.putInt(0);
			packet.putInt(400000);
			packet.putShort(3843);
			packet.putShort(77);//unread emails
			packet.putShort(0);

			packet.putInt(0);//ip hash
			packet.putByte(3);//email status
			packet.putShort(53791);
			packet.putShort(4427);

			packet.putByte(0);// 0 or 1
			packet.putJagString(response.getPlayer().getUsername());
			packet.putByte(10);

			packet.putInt(37396180);
			packet.putShort(1);
            packet.putJagString("127.0.0.1");
		} else if (response.getLoginType().equals(LoginTypeMessage.LOGIN_WORLD)) {
			packet.putByte(0);
			packet.putByte(response.getPlayer().getRights());
			packet.putByte(0);
			packet.putByte(0);
			packet.putByte(0);
			packet.putByte(0);
			packet.putByte(0);
			packet.putShort(response.getPlayer().getIndex());
			packet.putByte(1);//Is this a member account?
			packet.putTri(0);
			packet.putByte(1);
			packet.putString(response.getPlayer().getUsername());
			packet.put6ByteInteger(1412041454282L);
		}
		packet.finishVarByte();
		ctx.channel().writeAndFlush(Unpooled.copiedBuffer(packet.buffer(), 0, packet.offset()));
	}

}
