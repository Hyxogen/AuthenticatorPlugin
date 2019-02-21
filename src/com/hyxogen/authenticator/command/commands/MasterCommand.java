package com.hyxogen.authenticator.command.commands;

import org.bukkit.command.CommandSender;

import com.hyxogen.authenticator.command.ICommand;

public class MasterCommand implements ICommand {

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
	}

	@Override
	public String getCommand() {
		return "2fa";
	}

	@Override
	public String getUsage() {
		return "2fa help";
	}

	@Override
	public String getDescription() {
		return "The main command for AuthenticatorPlugin";
	}

	@Override
	public String[] getPermissions() {
		return null;
	}

	@Override
	public String[] getAliases() {
		return new String[] { "authenticator", "auth" };
	}

	@Override
	public int getMinArguments() {
		return 0;
	}

	@Override
	public int getMaxArguments() {
		//TODO check if this is right. It shouldn't matter because this command is nested
		return -1;
	}

	@Override
	public boolean isNested() {
		return true;
	}

}
