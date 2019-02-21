package com.hyxogen.authenticator.util;

import java.nio.ByteBuffer;

import org.apache.commons.codec.digest.HmacUtils;

import com.hyxogen.authenticator.user.User;

/**
 * 
 * @author Daan Meijer
 * @deprecated use {@link CryptoUtils} instead
 */
@Deprecated
public class PasswordGenerator {
	
	private final byte[] key;
	
	/**
	 * @deprecated use {@link CryptoUtils} instead
	 */
	@Deprecated
	public PasswordGenerator(final byte[] key) {
		this.key = key;
	}
	
	/**
	 * @deprecated use {@link CryptoUtils} instead
	 */
	@Deprecated
	public PasswordGenerator(final User user) {
		this.key = user.key;
	}
	
	/**
	 * @see {@link https://tools.ietf.org/html/rfc6238#section-4} for a detailed explanation of the algorithm
	 * @return a TOTP based password with supplied key
	 * @deprecated use {@link CryptoUtils#generateTOTP(byte[], long, int)} instead
	 */
	@Deprecated
	public String generatePassword() {
		long current = (System.currentTimeMillis() / 1000L) / 30L;
		
		byte[] byte_stream = HmacUtils.hmacSha1(key, ByteBuffer.allocate(Long.BYTES).putLong(current).array());
		
		int offset = byte_stream[19] & 0xf;
		int bin_code = 
				(byte_stream[offset] & 0x7f) << 24 
				| (byte_stream[offset + 1] & 0xff) << 16
				| (byte_stream[offset + 2] & 0xff) << 8 
				| (byte_stream[offset + 3] & 0xff);
		
		int code = Math.floorMod(bin_code, (int) Math.pow(10, 6));
		if(new String("" + code).length() == 5) {
			return "0" + code;
		} else {
			return "" + code;
		}
	}
}