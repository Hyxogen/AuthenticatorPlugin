package com.hyxogen.authenticator.user.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.hyxogen.authenticator.AuthenticationHandler;
import com.hyxogen.authenticator.challenge.IChallenge;
import com.hyxogen.authenticator.user.User;
import com.hyxogen.authenticator.user.UserHandler;
import com.hyxogen.authenticator.util.Locale;
import com.hyxogen.authenticator.util.Permissions;

/**
 * 
 * @author Daan Meijer
 *
 */
public class PlayerJoinHandler implements Listener {
	
	public final AuthenticationHandler authHandler;
	
	public PlayerJoinHandler(AuthenticationHandler authHandler) {
		this.authHandler = authHandler;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if(player.hasPermission(Permissions.PERM_AUTH_REQUIRED)) {
			if(!authHandler.isReady(player)) {
				//TODO Go through setup
			} else {
				if(!authHandler.needsAuthentication(player))
					return;
				
				User user = UserHandler.getUser(player.getUniqueId());
				authHandler.challengeHandler.challengeUser(user, new IChallenge() {
					
					@Override
					public void success(User user) {
						player.sendMessage(Locale.INFO_USER_SUCCES_AUTH);
					}
					
					@Override
					public void fail(User user) {
						player.kickPlayer(Locale.INFO_USER_FAIL_AUTH);
					}
				});
			}
		}
	}
}