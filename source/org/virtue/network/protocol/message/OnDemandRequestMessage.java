package org.virtue.network.protocol.message;

/**
 * 
 * @author Kyle Friz
 * @since Aug 8, 2014
 */
public final class OnDemandRequestMessage {

	/**
	 * Is the request file a priority
	 */
    private final boolean priority;
    
    /**
     * The requested type (aka Index)
     */
    private final int type;
    
    /**
     * The requested file (aka Archive)
     */
    private final int file;

    /**
     * Creates a new request message to be read by the cache
     * @param priority - is the file a priority
     * @param type - the request type
     * @param file - the requested file
     */
    public OnDemandRequestMessage(boolean priority, int type, int file) {
        this.priority = priority;
        this.type = type;
        this.file = file;
    }

    /**
     * Returns if the requested file is a priority
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
     * The the requested file
     */
    public int getFile() {
        return file;
    }

}
