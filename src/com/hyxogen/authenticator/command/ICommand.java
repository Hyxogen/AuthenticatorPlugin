package com.hyxogen.authenticator.command;

import org.bukkit.command.CommandSender;

/**
 * 
 * @author Daan Meijer
 *
 */
public interface ICommand {

	void onCommand(CommandSender sender, String[] args);

	public String getCommand();

	public String getUsage();

	public String getDescription();

	/**
	 * If the command requires a permission. <code>null</code> for no permission requirement
	 * 
	 * @return list of permissions for which a CommandSender needs only one
	 */
	public String[] getPermissions();

	public String[] getAliases();

	public int getMinArguments();

	public int getMaxArguments();

	/**
	 * TODO Misschien de basis hoofdcommand (nested) de helper functie laten zijn)
	 * 
	 * @return If the command has sub {@link ICommand}s, if this is true, the body
	 *         of the current {@link ICommand} will not be executed. The
	 *         {@link CommandManager} will instead search for a child
	 *         {@link ICommand} with this as the parent.
	 */
	public boolean isNested();
}