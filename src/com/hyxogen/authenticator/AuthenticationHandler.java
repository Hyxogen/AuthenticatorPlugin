package com.hyxogen.authenticator;

import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.entity.Player;

import com.hyxogen.authenticator.challenge.ChallengeHandler;
import com.hyxogen.authenticator.user.User;
import com.hyxogen.authenticator.user.UserHandler;
import com.hyxogen.authenticator.user.UserLogin;
import com.hyxogen.authenticator.util.CryptoUtils;
import com.hyxogen.authenticator.util.Permissions;

public class AuthenticationHandler {
	
	public static final int COOLDOWN = 30 * 60;
	
	public final ChallengeHandler challengeHandler;

	public AuthenticationHandler(ChallengeHandler challengeHandler) {
		this.challengeHandler = challengeHandler;
	}
	
	public boolean needsAuthentication(Player player) {
		if(!player.hasPermission(Permissions.PERM_AUTH_REQUIRED) && !player.hasPermission(Permissions.PERM_AUTH_ALLOWED))
			return false;
		if(!isReady(player))
			return true;
		
		long current = System.currentTimeMillis();
		User user = UserHandler.getUser(player.getUniqueId());
		
		UserLogin lastLogin = user.lastLogin;
		
		//TODO cache result zodat dit niet iedere movement gechecked moet worden.
		//We zouden ook slowness 100000 kunnen geven zodat ze niet kunnen bewegen
		//Dit werkt niet voor commands en chat berichten
		if(player.getAddress().getAddress().getAddress() != lastLogin.address)
			return true;
		if(((lastLogin.time - current) / 1000L) > COOLDOWN)
			return true;
		return false;
	}
	
	public boolean handleAuth(Player player, String code) {
//		if(!needsAuthentication(player))
//			return true;
		
		User user = UserHandler.getUser(player.getUniqueId());
		long current = System.currentTimeMillis();
		
		if(CryptoUtils.checkTOTP(user.key, current / 1000L, 1, 6, code)) {
			challengeHandler.finishAll(user);
			user.lastLogin = new UserLogin(player.getAddress().getAddress().getAddress(), System.currentTimeMillis());
			return true;
		} else if (CryptoUtils.containsKey(DigestUtils.sha1(code), user.backupCodes)) {
			challengeHandler.finishAll(user);
			user.lastLogin = new UserLogin(player.getAddress().getAddress().getAddress(), System.currentTimeMillis());
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isReady(Player player) {
		User user = UserHandler.getUser(player.getUniqueId());
		
		if(user.lastLogin == null)
			return false;
		else
			return true;
	}
}