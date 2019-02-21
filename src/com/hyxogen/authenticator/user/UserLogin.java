package com.hyxogen.authenticator.user;

public class UserLogin {
	
	public final byte[] address;
	public final long time;
	
	public UserLogin(byte[] address, long time) {
		this.address = address;
		this.time = time;
	}
}