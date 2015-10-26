package org.virtue.network.protocol.message;

/**
 * @author Kyle Friz
 * @since Aug 8, 2014
 */
public class OnDemandEncryptionMessage {

	/**
	 * The key used in the XOR Encoder
	 */
    private final int key;

    /**
     * Creates a new encrypt message with the XOR key
     * @param key - key used in the XOR Encoder
     */
    public OnDemandEncryptionMessage(int key) {
        this.key = key;
    }

    /**
     * Returns the key used in the XOR Encoder
     */
    public int getKey() {
        return key;
    }
    
}
