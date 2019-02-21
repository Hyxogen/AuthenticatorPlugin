package com.hyxogen.authenticator.user;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.hyxogen.authenticator.util.CryptoUtils;

public class UserFactory {
	
	public static String[] createUser(UUID uuid) {
		final byte[] key = CryptoUtils.generateKey(256);
		final byte[][] backupCodes = CryptoUtils.generateKeys(48, 6);
		String[] result = new String[6];
		
		for(int i = 0; i < 6; i++) {
			result[i] = Base64.encodeBase64String(backupCodes[i]);
			backupCodes[i] = DigestUtils.sha1(backupCodes[i]);
		}
		
		new User(uuid, key, backupCodes);
		return result;
	}
}