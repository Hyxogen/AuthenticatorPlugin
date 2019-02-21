package com.hyxogen.authenticator;

public class Session {

	public final long begin;
	public final byte[] address;
	
	private long end = -1;
	
	public Session(byte[] address) {
		this.begin = System.currentTimeMillis();
		this.address = address;
	}
	
	public Session(long beginTime, byte[] address) {
		this.begin = beginTime;
		this.address = address;
	}
	
	public long end() {
		assert !hasEnded();
		end = System.currentTimeMillis();
		return end - begin;
	}
	
	public long getEndTime() {
		return end;
	}
	
	public boolean hasEnded() {
		return end > 0;
	}
}