package com.hyxogen.authenticator.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

import com.hyxogen.authenticator.Session;
import com.hyxogen.authenticator.util.CryptoUtils;

/**
 * 
 * @author Daan Meijer
 *
 */
public class User {

	public Session current = null;

	public final UUID uuid;

	private final byte[] key;
	private final byte[][] backupCodes;

	protected static final List<User> users = new ArrayList<>();

	/**
	 * @deprecated using this constructor, one will not be able to retrieve the
	 *             actual {@link #backupCodes}. Use
	 *             {@link UserFactory#createUser(UUID)} instead
	 */
	@Deprecated
	protected User(final UUID uuid) {
		this.uuid = uuid;
		this.key = CryptoUtils.generateKey(256); // TODO Define length in a constant somewhere else
		this.backupCodes = new byte[6][];

		for (int i = 0; i < 6; i++)
			backupCodes[i] = DigestUtils.sha1(CryptoUtils.generateKey(48)); // TODO define length in a constant

		users.add(this);
	}

	protected User(final UUID uuid, final byte[] key, final byte[][] backupCodes) {
		this.uuid = uuid;
		this.key = key;
		this.backupCodes = backupCodes;

		users.add(this);
	}

	/**
	 * @deprecated use {@link UserHandler#getUser(UUID, boolean)} instead
	 */
	public static User getUser(UUID uuid, boolean loaded) {
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);

			if (user.uuid == uuid)
				return user;
		}
		return null;
	}

	public byte[] getKey() {
		return key;
	}

	public byte[][] getBackupCodes() {
		return backupCodes;
	}
}