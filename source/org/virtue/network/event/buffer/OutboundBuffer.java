package org.virtue.network.event.buffer;

import org.virtue.model.entity.player.Player;
import org.virtue.utility.JagStringUtility;

/**
 * @author Tom
 * @author Emperor
 * 
 */
public class OutboundBuffer extends Buffer {

	private static final int[] BIT_MASK = new int[32];

	static {
		for (int i = 0; i < 32; i++)
			BIT_MASK[i] = (1 << i) - 1;
	}

	private int bitPosition;
	private int opcodeStart;

	public OutboundBuffer() {
		this(DEFAULT_SIZE);
	}

	public OutboundBuffer(int capacity) {
		this.buffer = new byte[capacity];
	}

	public OutboundBuffer(byte[] buffer) {
		this.buffer = buffer;
		this.offset = buffer.length;
		this.length = buffer.length;
	}

	public void checkPosition(int position) {
		if (position >= buffer.length) {
			byte[] newData = new byte[position + DEFAULT_SIZE];
			System.arraycopy(buffer, 0, newData, 0, buffer.length);
			this.buffer = newData;
		}
	}

	public void putByte(int value) {
		putByte(value, offset++);
	}

	public void putInt(int value) {
		putByte(value >> 24);
		putByte(value >> 16);
		putByte(value >> 8);
		putByte(value);
	}

	public void put5ByteInteger(long value) {
		putByte((int) (value >> 32));
		putInt((int) (value & 0xffffffff));
	}

	private void putByte(int value, int position) {
		checkPosition(position);
		buffer[position] = (byte) value;
	}

	public void putBytes(byte[] data) {
		int offset = 0;
		int length = data.length;
		checkPosition(this.offset + length - offset);
		System.arraycopy(data, offset, buffer, this.offset, length);
		this.offset += (length - offset);
	}

	public void putBytes(byte[] b, int offset, int length) {
		checkPosition(this.offset + length - offset);
		System.arraycopy(b, offset, buffer, this.offset, length);
		this.offset(this.offset + (length - offset));
	}

	public void setBitAccess() {
		bitPosition = offset * 8;
	}

	public void setByteAccess() {
		offset = (bitPosition + 7) / 8;
	}

	public void putA(int val) {
		putByte((byte) (val + 128));

	}

	public void putC(int val) {
		putByte((byte) -val);

	}

	public void putS(int val) {
		putByte((byte) (128 - val));

	}

	public void putTri(int val) {
		putByte((byte) (val >> 16));
		putByte((byte) (val >> 8));
		putByte((byte) val);

	}

	public void putShort(int val) {
		putByte((byte) val >> 8);
		putByte((byte) val);

	}

	public void putLEShort(int val) {
		putByte((byte) val);
		putByte((byte) (val >> 8));

	}

	public void putShortA(int val) {
		putByte((byte) (val >> 8));
		putByte((byte) (val + 128));

	} 

	public void putLEShortA(int val) {
		putByte((byte) (val + 128));
		putByte((byte) (val >> 8));

	}

	public void putLEInt(int val) {
		putByte((byte) val);
		putByte((byte) (val >> 8));
		putByte((byte) (val >> 16));
		putByte((byte) (val >> 24));

	}
	
	public void putLEIntA(int val) {
		putByte((byte) val);
		putByte((byte) (val >> 8));
		putByte((byte) (val >> 24));
		putByte((byte) (val >> 16));

	}

	public void putIntA(int val) {
		putByte((byte) (val >> 8));
		putByte((byte) val);
		putByte((byte) (val >> 24));
		putByte((byte) (val >> 16));

	}

	public void putIntB(int val) {
		putByte((byte) (val >> 16));
		putByte((byte) (val >> 24));
		putByte((byte) val);
		putByte((byte) (val >> 8));

	}
	
	public void putIntV1(int val) {
		putByte((byte) (val >> 8));
		putByte((byte) (val >> 24));
		putByte((byte) (val >> 16));
		putByte((byte) val);
	}
	
	public void putIntV2(int val) {
		putByte((byte) val);
		putByte((byte) (val >> 24));
		putByte((byte) (val >> 16));
		putByte((byte) (val >> 8));
	}
	
	public void putIntV3(int val) {
		putByte((byte) (val >> 24));
		putByte((byte) (val >> 16));
		putByte((byte) (val >> 8));
		putByte((byte) val);
	}

	public void putLong(long val) {
		putInt((int) (val >> 32));
		putInt((int) val);

	}

	public void putSmart(int val) {
		if (val > Byte.MAX_VALUE) {
			putShort((short) (val + 32768));
		} else {
			putByte((byte) val);
		}

	}
    
    public void putSmartS(int val) {
		if (val >= 0 && val < 128) {
			putByte(val);
		} else if (val >= 0 && val < 32768) {
			putShort(32768 + val);
		} else {
		    throw new IllegalArgumentException();
		}
    }

	public void putIntSmart(int val) {
		if (val > Short.MAX_VALUE) {
			putInt(val + 32768);
		} else {
			putShort((short) val);
		}
	}

	public void putBigSmart(int val) {
		if (val > Short.MAX_VALUE) {
			putInt(val - Integer.MAX_VALUE - 1);
		} else {
			putShort((short) val >= 0 ? val : 32767);
		}
	}

	public void putString(String val) {
		checkPosition(offset() + val.length() + 1);
		System.arraycopy(val.getBytes(), 0, buffer(), offset(), val.length());
		offset = offset() + val.length();
		putByte(0);
	}

	public void putJagString(String val) {
		putByte((byte) 0);
		putByte(val.getBytes());
		putByte((byte) 0);
	}

	public void putJagString2(String val) {
		byte[] packed = new byte[256];
		int length = JagStringUtility.packGJString2(0, packed, val);
		putByte(0);
		putBytes(packed, 0, length);
		putByte(0);
	}

	public void putByte(byte[] val) {
		for (byte b : val) {
			putByte(b);
		}
	}
	
	public void putReverse(byte[] src, int offset, int length) {
		for (int i = length - 1; i >= offset; i--) {
			putByte(src[i]);
		}
	}

	public void putReverseA(byte[] data, int offset, int length) {
		for (int i = length - 1; i >= offset; i--) {
			putA(data[i]);
		}
	}

	public void putBytesA(byte[] data, int offset, int length) {
		for (int i = offset; i < offset + length; i++) {
			putA(data[i]);
		}
	}

	public void putBits(int numBits, int value) {
		int bytePos = bitPosition >> 3;
		int bitOffset = 8 - (bitPosition & 7);
		bitPosition += numBits;
		for (; numBits > bitOffset; bitOffset = 8) {
			checkPosition(bytePos);
			buffer[bytePos] &= ~BIT_MASK[bitOffset];
			buffer[bytePos++] |= value >> numBits - bitOffset & BIT_MASK[bitOffset];
			numBits -= bitOffset;
		}
		checkPosition(bytePos);
		if (numBits == bitOffset) {
			buffer[bytePos] &= ~BIT_MASK[bitOffset];
			buffer[bytePos] |= value & BIT_MASK[bitOffset];
		} else {
			buffer[bytePos] &= ~(BIT_MASK[numBits] << bitOffset - numBits);
			buffer[bytePos] |= (value & BIT_MASK[numBits]) << bitOffset - numBits;
		}
	}

	public void putVarByte(int packetId) {
		putPacket(packetId);
		putByte(0);
		opcodeStart = offset - 1;
	}

	public void putVarShort(int packetId) {
		putPacket(packetId);
		putShort(0);
		opcodeStart = offset - 2;
	}

	public void putPacket(int packetId) {
		if (packetId >= 128) {
			putByte((packetId >> 8) + 128);
			putByte(packetId);
		} else {
			putByte(packetId);
		}
	}
	
	public void putVarByte(int packetId, Player player) {
		putPacket(packetId, player);
		putByte(0);
		opcodeStart = offset - 1;
	}

	public void putVarShort(int packetId, Player player) {
		putPacket(packetId, player);
		putShort(0);
		opcodeStart = offset - 2;
	}

	public void putPacket(int packetId, Player player) {
		if (packetId >= 128) {
			putByte(((packetId >> 8) + 128) + player.getEncodingCipher().nextInt());
			putByte(packetId + player.getEncodingCipher().nextInt());
		} else {
			putByte(packetId + player.getEncodingCipher().nextInt());
		}
	}

	public void finishVarByte() {
		putByte(offset - (opcodeStart + 2) + 1, opcodeStart);
	}

	public void finishVarShort() {
		int size = offset - (opcodeStart + 2);
		putByte(size >> 8, opcodeStart++);
		putByte(size, opcodeStart);
	}
	
	public void putNISVar(int key, int val) {
		putLEShort(key);
		putInt(val);
	}
}
