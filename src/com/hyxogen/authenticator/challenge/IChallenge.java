package com.hyxogen.authenticator.challenge;

import com.hyxogen.authenticator.user.User;

public interface IChallenge {
	
	void success(User user);
	
	void fail(User user);
}