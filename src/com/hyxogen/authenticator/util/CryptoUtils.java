package com.hyxogen.authenticator.util;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

import org.apache.commons.codec.digest.HmacUtils;

/**
 * 
 * @author Daan Meijer
 *
 */
public class CryptoUtils {

	public static byte[] generateKey(int size) {
		KeyGenerator generator = null;
		try {
			generator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		generator.init(size);
		return generator.generateKey().getEncoded();
	}

	public static byte[][] generateKeys(int size, int amount) {
		byte[][] out = new byte[amount][];
		for (int i = 0; i < amount; i++)
			out[i] = generateKey(size);
		return out;
	}

	public static String generateHOTP(byte[] key, long c, int decimals) {
		assert (decimals > 0 && decimals <= 10);
		byte[] byte_stream = HmacUtils.hmacSha1(key, ByteBuffer.allocate(Long.BYTES).putLong(c).array());

		int offset = byte_stream[19] & 0xf;
		int bin_code = (byte_stream[offset] & 0x7f) << 24 | (byte_stream[offset + 1] & 0xff) << 16
				| (byte_stream[offset + 2] & 0xff) << 8 | (byte_stream[offset + 3] & 0xff);

		int code = Math.floorMod(bin_code, (int) Math.pow(10, decimals));
		if (new String("" + code).length() == 5) {
			return "0" + code;
		} else {
			return "" + code;
		}
	}

	public static String generateTOTP(byte[] key, long time, int decimals) {
		assert (decimals > 0 && decimals <= 10);
		return generateHOTP(key, time / 30L, decimals);
	}

	/**
	 * 
	 * @param key      the secret key to generate the code with
	 * @param time     unix time in seconds
	 * @param margin   margin of error allowed, applied on both sides
	 * @param decimals amount of decimals to check, range {0-10}
	 * @param code     the code to check
	 * @return if the code supplied was equal to the one-time password on time
	 *         within the margin of error
	 */
	public static boolean checkTOTP(byte[] key, long time, int margin, int decimals, String code) {
		assert (code.length() == decimals);
		if (margin != 0) {
			for (int i = 0; i < margin; i++) {
				if (generateTOTP(key, time, decimals) == code)
					return true;
				else if (generateTOTP(key, time + i, decimals) == code)
					return true;
				else if (generateTOTP(key, time - i, decimals) == code)
					return true;
				else
					continue;
			}
		} else {
			return generateTOTP(key, time, decimals) == code;
		}
		return false;
	}
	
	public static boolean containsKey(byte[] key, byte[][] list) {
		for(int i = 0; i < list.length; i++) {
			if(list[i].equals(key))
				return true;
		}
		return false;
	}
}