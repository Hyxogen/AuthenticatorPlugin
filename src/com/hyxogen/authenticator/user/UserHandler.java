package com.hyxogen.authenticator.user;

import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

public class UserHandler {

	public final JavaPlugin plugin;

	public UserHandler(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * 
	 * @param uuid the {@link UUID} of the {@link User} to retrieve
	 * @param deep if true, the algorithm should attempt to retrieve the user from either
	 *             the MySQL database or the local file saves if unable to find an
	 *             already loaded user.
	 * @return the {@link User} with the corresponding {@link UUID}
	 * 
	 * @see User
	 */
	public User getUser(UUID uuid, boolean deep) {
		for(int i = 0; i < User.users.size(); i++) {
			User user = User.users.get(i);
			
			if(user.uuid == uuid)
				return user;
		}
		if(!deep)
			return null;
		
		//TODO attempt deep search for the user
		return null;
	}
}