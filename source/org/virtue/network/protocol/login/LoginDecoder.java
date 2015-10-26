package org.virtue.network.protocol.login;

import java.math.BigInteger;
import java.net.ProtocolException;
import java.util.List;

import org.virtue.Constants;
import org.virtue.network.protocol.message.LoginRequest;
import org.virtue.utility.Base37Utility;
import org.virtue.utility.BufferUtility;
import org.virtue.utility.ISAACCipher;
import org.virtue.utility.XTEACipher;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author Chryonic <http://rune-server.org/members/chryonic> Made on Oct 26,
 *         2015 for Virtue 3 opensource
 */
public class LoginDecoder extends ByteToMessageDecoder {

	public enum LoginTypes {
		LOBBY, WORLD;
	}

	private LoginTypes type;
	private int size;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
		if (!buf.isReadable()) {
			throw new IllegalStateException("Not enough readable bytes from buffer!");
		}
		
		int loginType = buf.readByte() & 0xFF;
		if (loginType != 16 && loginType != 19 && loginType != 18
				&& loginType != 60) {
			throw new ProtocolException("Invalid login type: " + loginType);
		}
		
		type = loginType == 19 ? LoginTypes.LOBBY
				: LoginTypes.WORLD;
		size = buf.readShort() & 0xFFFF;
		
		if (buf.readableBytes() < size) {
			throw new IllegalStateException(
					"Not enough readable bytes from buffer!");
		}

		int version = buf.readInt();
		int subVersion = buf.readInt();
		
		if (version != Constants.FRAME_MAJOR
				&& subVersion != Constants.FRAME_MINOR) {
			throw new IllegalStateException(
					"Invalid client version/sub-version!");
		}

		 switch (type) {
		 case LOBBY:
			  decodeLobbyPayload(ctx, buf, out);
		 case WORLD:
			 buf.readByte();
			 decodeGamePayload(ctx, buf, out);
		 }
	}
	private void decodeLobbyPayload(ChannelHandlerContext ctx, ByteBuf buf,
			List<Object> out) throws ProtocolException {
		if (buf.readableBytes() < 2)
			throw new IllegalStateException(
					"Not enough readable bytes from buffer.");

		int secureBufferSize = buf.readShort() & 0xFFFF;
		if (buf.readableBytes() < secureBufferSize)
			throw new IllegalStateException(
					"Not enough readable bytes from buffer.");

		byte[] secureBytes = new byte[secureBufferSize];
		buf.readBytes(secureBytes);
		ByteBuf secureBuffer = Unpooled.wrappedBuffer(new BigInteger(
				secureBytes).modPow(Constants.LOGIN_EXPONENT,
				Constants.LOGIN_MODULUS).toByteArray());

		int blockOpcode = secureBuffer.readByte() & 0xFF;
		if (blockOpcode != 10)
			throw new ProtocolException("Invalid block opcode: " + blockOpcode);

		int[] xteaKey = new int[4];
		for (int i = 0; i < xteaKey.length; i++)
			xteaKey[i] = secureBuffer.readInt();

		long loginHash = secureBuffer.readLong();
		
		 if (loginHash != 0)
		 throw new ProtocolException("Invalid login hash: " + loginHash);

		secureBuffer.readByte();
		secureBuffer.readerIndex(secureBuffer.readerIndex() - 4);

		String password = BufferUtility.readString(secureBuffer);

		long[] loginSeeds = new long[2];
		for (int i = 0; i < loginSeeds.length; i++)
			loginSeeds[i] = secureBuffer.readLong();

		byte[] xteaBlock = new byte[buf.readableBytes()];
		buf.readBytes(xteaBlock);

		XTEACipher xtea = new XTEACipher(xteaKey);
		xtea.decrypt(xteaBlock, 0, xteaBlock.length);

		ByteBuf xteaBuffer = Unpooled.wrappedBuffer(xteaBlock);

		boolean decodeAsString = xteaBuffer.readByte() == 1;
		String username = decodeAsString ? BufferUtility.readString(xteaBuffer)
				: Base37Utility.decodeBase37(xteaBuffer.readLong());

		xteaBuffer.readUnsignedByte();// Game ID
		xteaBuffer.readUnsignedByte();// Language ID
		xteaBuffer.readUnsignedByte();// Frame type (Fixed, Resizable, or Full
										// Screen)

		xteaBuffer.readUnsignedShort();// Canvas Width
		xteaBuffer.readUnsignedShort();// Canvas Height
		xteaBuffer.readUnsignedByte();

		int[] randomData = new int[24];
		for (int index = 0; index < randomData.length; index++) {
			randomData[index] = xteaBuffer.readUnsignedByte();
		}

		String token = BufferUtility.readString(xteaBuffer);// Site Settings
		if (!token.equals(Constants.LOGIN_TOKEN))
			throw new IllegalStateException("Invalid server token. " + token);

		int length = xteaBuffer.readUnsignedByte();

		byte[] machineData = new byte[length];
		for (int data = 0; data < machineData.length; data++) {
			machineData[data] = (byte) xteaBuffer.readUnsignedByte();
		}

		xteaBuffer.readInt();
		BufferUtility.readString(xteaBuffer);
		xteaBuffer.readInt();
		xteaBuffer.readInt();
		BufferUtility.readString(xteaBuffer);

		xteaBuffer.readUnsignedByte();

		int[] cacheCRC = new int[36];
		for (int index = 0; index < cacheCRC.length; index++) {
			cacheCRC[index] = xteaBuffer.readInt();
		}

		int[] serverKeys = new int[xteaKey.length];
		for (int i = 0; i < serverKeys.length; i++) {
			serverKeys[i] = xteaKey[i] + 50;
		}
		
		out.add(new LoginRequest(username, password, type));
	}

	private void decodeGamePayload(ChannelHandlerContext ctx, ByteBuf buf,
			List<Object> out) throws ProtocolException {
		if (buf.readableBytes() < 2)
			throw new IllegalStateException(
					"Not enough readable bytes from buffer.");

		int secureBufferSize = buf.readShort() & 0xFFFF;
		if (buf.readableBytes() < secureBufferSize)
			throw new IllegalStateException(
					"Not enough readable bytes from buffer.");

		byte[] secureBytes = new byte[secureBufferSize];
		buf.readBytes(secureBytes);
		ByteBuf secureBuffer = Unpooled.wrappedBuffer(new BigInteger(
				secureBytes).modPow(Constants.LOGIN_EXPONENT,
				Constants.LOGIN_MODULUS).toByteArray());

		int blockOpcode = secureBuffer.readByte() & 0xFF;
		if (blockOpcode != 10)
			throw new ProtocolException("Invalid block opcode: " + blockOpcode);

		int[] xteaKey = new int[4];
		for (int i = 0; i < xteaKey.length; i++)
			xteaKey[i] = secureBuffer.readInt();

		secureBuffer.readLong();
		// if (loginHash != 0)
		// throw new ProtocolException("Invalid login hash: " + loginHash);

		secureBuffer.readByte();
		secureBuffer.readerIndex(secureBuffer.readerIndex() - 4);

		String password = BufferUtility.readString(secureBuffer);

		long[] loginSeeds = new long[2];
		for (int i = 0; i < loginSeeds.length; i++)
			loginSeeds[i] = secureBuffer.readLong();

		byte[] xteaBlock = new byte[buf.readableBytes()];
		buf.readBytes(xteaBlock);

		XTEACipher xtea = new XTEACipher(xteaKey);
		xtea.decrypt(xteaBlock, 0, xteaBlock.length);

		ByteBuf xteaBuffer = Unpooled.wrappedBuffer(xteaBlock);
		boolean decodeAsString = xteaBuffer.readByte() == 1;
		String username = decodeAsString ? BufferUtility.readString(xteaBuffer)
				: Base37Utility.decodeBase37(xteaBuffer.readLong());

		xteaBuffer.readUnsignedByte();
		xteaBuffer.readUnsignedShort();// Canvas Width
		xteaBuffer.readUnsignedShort();// Canvas Height

		xteaBuffer.readUnsignedByte();

		int[] randomData = new int[24];
		for (int index = 0; index < randomData.length; index++) {
			randomData[index] = xteaBuffer.readUnsignedByte();
		}

		String token = BufferUtility.readString(xteaBuffer);// Site Settings
		if (!token.equals(Constants.LOGIN_TOKEN))
			throw new IllegalStateException("Invalid server token.");

		xteaBuffer.readInt();

		int length = xteaBuffer.readUnsignedByte();

		byte[] machineData = new byte[length];
		for (int data = 0; data < machineData.length; data++) {
			machineData[data] = (byte) xteaBuffer.readUnsignedByte();
		}

		xteaBuffer.readInt();
		xteaBuffer.readInt();// User flow #2
		xteaBuffer.readInt();// User flow #1

		BufferUtility.readString(xteaBuffer);

		boolean extra = xteaBuffer.readUnsignedByte() == 1;
		if (extra) {
			BufferUtility.readString(xteaBuffer);// Create Additional Info
		}

		xteaBuffer.readUnsignedByte();// Javascript enabled
		xteaBuffer.readUnsignedByte();// Has Chrome browser
		xteaBuffer.readUnsignedByte();
		xteaBuffer.readUnsignedByte();

		xteaBuffer.readInt();
		@SuppressWarnings("unused")
		String videoCard = BufferUtility.readString(xteaBuffer);
		xteaBuffer.readUnsignedByte();

		xteaBuffer.readUnsignedShort();// Lobby node ID
		xteaBuffer.readUnsignedShort();// Media stream node ID

		int[] cacheCRC = new int[36];
		for (int index = 0; index < cacheCRC.length; index++) {
			cacheCRC[index] = xteaBuffer.readInt();
		}

		int[] serverKeys = new int[xteaKey.length];
		for (int i = 0; i < serverKeys.length; i++) {
			serverKeys[i] = xteaKey[i] + 50;
		}

		out.add(new LoginRequestMessage(ctx.channel(), UsernameUtility
				.formatForProtocol(username), password, username.contains("@"),
				new ISAACCipher(serverKeys), new ISAACCipher(xteaKey), type));
	}

	
}
