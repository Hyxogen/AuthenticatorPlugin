package com.hyxogen.authenticator.challenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.hyxogen.authenticator.user.User;

public class ChallengeHandler {

	public final Plugin plugin;

	private Map<UUID, List<IChallenge>> challenges = new HashMap<>();
	private Map<IChallenge, BukkitRunnable> runnables = new HashMap<>();

	public ChallengeHandler(Plugin plugin) {
		this.plugin = plugin;
	}

	public void challengeUser(User user, IChallenge challenge) {
		TimeoutRunnable runnable = new TimeoutRunnable(user, challenge);
		UUID uuid = user.uuid;

		if (challenges.containsKey(uuid)) {
			challenges.get(uuid).add(challenge);
			runnables.put(challenge, runnable);
		} else {
			challenges.put(uuid, Arrays.asList(challenge));
			runnables.put(challenge, runnable);
		}
	}
	
	public boolean hasChallenge(User user) {
		if(!challenges.containsKey(user.uuid))
			return false;
		else
			return !challenges.get(user.uuid).isEmpty();
	}
	
	/**
	 * This will call {@link IChallenge#success(User)}
	 * 
	 * @param user      the user the {@link IChallenge} belongs to
	 * @param challenge the {@link IChallenge} to finish
	 */
	@Deprecated
	private void finishChallenge(User user, IChallenge challenge) {
		UUID uuid = user.uuid;
		challenge.success(user);

		challenges.get(uuid).remove(challenge);
		runnables.get(challenge).cancel();
		runnables.remove(challenge);
	}
	
	private void failChallenge(User user, IChallenge challenge) {
		UUID uuid = user.uuid;
		challenge.fail(user);
		
		challenges.get(uuid).remove(challenge);
		runnables.get(challenge).cancel();
		runnables.remove(challenge);
	}

	public void failAll(User user) {
		List<IChallenge> challenges = this.challenges.get(user.uuid);
		for (int i = 0; i < challenges.size(); i++)
			failChallenge(user, challenges.get(i));
	}
	
	public void finishAll(User user) {
		List<IChallenge> challenges = this.challenges.get(user.uuid);
		for (int i = 0; i < challenges.size(); i++)
			finishChallenge(user, challenges.get(i));
	}

	private class TimeoutRunnable extends BukkitRunnable {

		public final User user;
		public final IChallenge challenge;

		private TimeoutRunnable(User user, IChallenge challenge) {
			this.user = user;
			this.challenge = challenge;
		}

		@Override
		public void run() {
			failChallenge(user, challenge);
		}
	}
}