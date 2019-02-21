package com.hyxogen.authenticator;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.KeyGenerator;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.digest.HmacUtils;
import org.bukkit.plugin.java.JavaPlugin;

import com.hyxogen.authenticator.user.UserHandler;

/**
 * 
 * @author Daan Meijer
 * 
 */
public class AuthenticatorPlugin extends JavaPlugin {
	
	public void onEnable() {
		
	}

	public void onDisable() {

	}

	public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException, InvalidKeyException, DecoderException {
		Base32 codec = new Base32();
		
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(256);
		byte[] secret = generator.generateKey().getEncoded();
		System.out.println("SECRET " + codec.encodeAsString(secret));
		
		byte[] key = Arrays.copyOf(secret, secret.length);

		while (true) {
			long current = (System.currentTimeMillis() / 1000L) / 30L;
			
			byte[] byte_stream = HmacUtils.hmacSha1(key, ByteBuffer.allocate(Long.BYTES).putLong(current).array());
			
			int offset = byte_stream[19] & 0xf;
			int bin_code = 
					(byte_stream[offset] & 0x7f) << 24 
					| (byte_stream[offset + 1] & 0xff) << 16
					| (byte_stream[offset + 2] & 0xff) << 8 
					| (byte_stream[offset + 3] & 0xff);

			System.out.println(Math.floorMod(bin_code, (int) Math.pow(10, 6)));
			Thread.sleep(1000);
		}
	}
	
	public UserHandler getUserHandler() {
		return null; //TODO define this
	}
}