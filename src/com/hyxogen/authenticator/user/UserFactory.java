package com.hyxogen.authenticator.user;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.hyxogen.authenticator.util.CryptoUtils;

/**
 * 
 * @author Daan Meijer
 *
 */
public class UserFactory {

	public static User createUser(UUID uuid) {
		assert !UserHandler.exists(uuid);
		byte[] key = CryptoUtils.generateKey(256);

		byte[][] codes = CryptoUtils.generateKeys(48, 6);
		String[] stringCodes = new String[User.NUM_BACKUP_CODES];

		for (int i = 0; i < User.NUM_BACKUP_CODES; i++) {
			stringCodes[i] = Base64.encodeBase64String(codes[i]);
			codes[i] = DigestUtils.sha1(codes[i]);
		}
		
		User user = new User(uuid, codes, key);
		user.tempCodes = stringCodes;
		return user;
	}
}