package com.hyxogen.authenticator.user;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author Daan Meijer
 *
 */
public class UserHandler {

	protected static List<User> users = new ArrayList<>();

	public UserHandler(JavaPlugin plugin) {

	}

	public static User getUser(final UUID uuid) {
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			if (user.uuid.equals(uuid))
				return user;
		}
		return loadUser(uuid);
	}

	public static boolean exists(UUID uuid) {
		return getUser(uuid) != null;
	}

	/**
	 * TODO vervangen met een systeem die beter werkt met mysql/files
	 */
	protected static User loadUser(final UUID uuid) {
		try {
			FileInputStream file = new FileInputStream("\\users\\" + uuid.toString() + ".dat");
			ObjectInputStream stream = new ObjectInputStream(file);

			User user = (User) stream.readObject();

			stream.close();
			file.close();

			return user;
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}