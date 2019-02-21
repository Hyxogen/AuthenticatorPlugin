package com.hyxogen.authenticator.user.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.hyxogen.authenticator.util.Permissions;

/**
 * 
 * @author Daan Meijer
 *
 */
public class PlayerChatHandler implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if(player.hasPermission(Permissions.PERM_AUTH_REQUIRED)) {
			
		}
	}
}