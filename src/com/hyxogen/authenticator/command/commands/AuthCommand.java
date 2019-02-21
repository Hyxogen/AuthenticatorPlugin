package com.hyxogen.authenticator.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hyxogen.authenticator.AuthenticationHandler;
import com.hyxogen.authenticator.challenge.ChallengeHandler;
import com.hyxogen.authenticator.command.ICommand;
import com.hyxogen.authenticator.user.User;
import com.hyxogen.authenticator.user.UserHandler;
import com.hyxogen.authenticator.util.CryptoUtils;
import com.hyxogen.authenticator.util.Permissions;

public class AuthCommand implements ICommand {

	public final AuthenticationHandler authHandler;

	public AuthCommand(AuthenticationHandler authHandler) {
		this.authHandler = authHandler;
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command is meant for players only.");
			return;
		}
		Player player = (Player) sender;
		
		if(!authHandler.needsAuthentication(player)) {
			player.sendMessage(ChatColor.RED + "You don't have any pending authentications!");
			return;
		}
		
		String code = args[0];
		if(code.length() != 6) {
			player.sendMessage(ChatColor.RED + "The code supplied is not of 6 digits!");
			return;
		}
		
		authHandler.handleAuth(player, code);
	}

	@Override
	public String getCommand() {
		return "auth";
	}

	@Override
	public String getUsage() {
		return "2fa auth <6-digit code>";
	}

	@Override
	public String getDescription() {
		return "Authenticate yourself with the generated one-time password by Google Authenticator or others.";
	}

	@Override
	public String[] getPermissions() {
		return new String[] { Permissions.PERM_AUTH_ALLOWED, Permissions.PERM_AUTH_REQUIRED };
	}

	@Override
	public String[] getAliases() {
		return new String[] {};
	}

	@Override
	public int getMinArguments() {
		return 1;
	}

	@Override
	public int getMaxArguments() {
		return 1;
	}

	@Override
	public boolean isNested() {
		return false;
	}

}
