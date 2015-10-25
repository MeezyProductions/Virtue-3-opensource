package org.virtue.network.protocol.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Aug 8, 2014
 */
public class HandshakeMessage {

	/**
	 * The {@link Logger} instance
	 */
	private static Logger logger = LoggerFactory.getLogger(HandshakeMessage.class);

	/**
	 * Enum of possible handshake types
	 */
	public static enum HandshakeTypes { HANDSHAKE_LOGIN, HANDSHAKE_ONDEMAND, HANDHSAKE_CREATION, HANDSHAKE_SOCIAL }

	/**
	 * The current handshake type
	 */
	private HandshakeTypes type;

	/**
	 * Creates a new HadshakeMessage with the opcode read from the buffer
	 * @param opcode
	 */
	public HandshakeMessage(int opcode) {
		this.type = forOpcode(opcode);
	}

	/**
	 * Returns the HandshakeType for the opcode supplied
	 */
	public HandshakeTypes forOpcode(int opcode) {
		switch (opcode) {
		case 15:
			return HandshakeTypes.HANDSHAKE_ONDEMAND;
		case 14:
			return HandshakeTypes.HANDSHAKE_LOGIN;
		//case 28:
			//return HandshakeTypes.HANDHSAKE_CREATION;
		//case 29:
			//return HandshakeTypes.HANDSHAKE_SOCIAL;
		default:
			logger.error("Unknown Handshake Opcode: " + opcode);
		}
		return null;
	}

	/**
	 * Returns the current handshake type
	 */
	public HandshakeTypes getType() {
		return type;
	}
	
}
