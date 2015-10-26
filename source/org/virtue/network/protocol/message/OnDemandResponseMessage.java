package org.virtue.network.protocol.message;

import io.netty.buffer.ByteBuf;

/**
 * 
 * @author Kyle Friz
 * @since Aug 8, 2014
 */
public final class OnDemandResponseMessage {

	/**
	 * is the request file a priority file
	 */
    private final boolean priority;
    
    /**
     * The type (aka Index) requested
     */
    private final int type;
    
    /**
     * The file (aka Archive) requested
     */
    private final int file;
    
    /**
     * The {@link ByteBuf} filled with data from the type and file read in the cache
     */
    private final ByteBuf container;

    /**
     * Creates a new response message to be sent
     * @param priority - file request a priority
     * @param type - type requested
     * @param file - file requested
     * @param container - data of the read file
     */
    public OnDemandResponseMessage(boolean priority, int type, int file, ByteBuf container) {
        this.priority = priority;
        this.type = type;
        this.file = file;
        this.container = container;
    }

    /**
     * Returns if the request file is a priority
     */
    public boolean isPriority() {
        return priority;
    }

    /**
     * Returns the requested type
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the request file
     */
    public int getFile() {
        return file;
    }

    /**
     * Returns the requested file's data read from the cache
     */
    public ByteBuf getContainer() {
        return container;
    }

}
