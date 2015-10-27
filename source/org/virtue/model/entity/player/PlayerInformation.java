package org.virtue.model.entity.player;

import org.virtue.utility.Base37Utility;

public class PlayerInformation {

	private String email;
	private long userhash;
	private String displayName;
	private String prevName;
	private boolean locked;

	public PlayerInformation(String email, long userhash, String displayName, String prevName, boolean locked) {
		this.email = email;
		this.userhash = userhash;
		this.displayName = displayName;
		this.prevName = prevName;
		this.locked = locked;
	}

	protected void setDisplayName(String name) {
		if (prevName == null) {
			this.prevName = this.displayName;
		}
		this.displayName = name;
	}

	protected void setLocked(boolean locked) {
		this.locked = locked;
	}

	public long getUserHash() {
		return userhash;
	}

	public String getUsername() {
		return Base37Utility.decodeBase37(userhash);
	}

	public String getEmail() {
		return email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getPrevName() {
		return prevName;
	}

	public boolean isLocked() {
		return locked;
	}

	public static String generateNamePlaceholder(long hash) {
		String hexString = Long.toHexString(hash);
		return new StringBuilder("[#").append(hexString.substring(0, Math.min(hexString.length(), 9))).append(']')
				.toString();
	}

}
