package com.hyxogen.authenticator.user;

import java.util.UUID;

/**
 * 
 * @author Daan Meijer
 * 
 */
public class User {

	/**
	 * TODO Define these constants somewhere else
	 */
	public static final short NUM_BACKUP_CODES = 6;
	public static final int KEY_LENGTH = 256;

	/**
	 * Unhashed backup codes, make sure this doesn't persist long by calling
	 * {@link #finalize()} as soon as possible
	 */
	protected String[] tempCodes = null;
	
	public UserLogin lastLogin;
	public final UUID uuid;
	/**
	 * Hashed backup codes
	 */
	public final byte[][] backupCodes;
	public final byte[] key;

	/**
	 * @deprecated use {@link UserFactory#createUser(UUID)} instead
	 */
	@Deprecated
	public User(final UUID uuid) {
		this.uuid = null;
		this.backupCodes = null;
		this.key = null;

		UserFactory.createUser(uuid);
	}

	protected User(final UUID uuid, final byte[][] backupCodes, final byte[] key) {
		this.uuid = uuid;
		this.backupCodes = backupCodes;
		this.key = key;

		// The user class should not have this responsibility
		// TODO make this UserHandler's responsibility
		UserHandler.users.add(this);
	}

	/**
	 * @deprecated use {@link UserHandler#exists(UUID)} instead
	 */
	@Deprecated
	public static boolean exists(final UUID uuid) {
		return UserHandler.getUser(uuid) != null;
	}

	public void finalize() {
		tempCodes = null;
	}

	public String[] getTempCodes() {
		return tempCodes;
	}
}