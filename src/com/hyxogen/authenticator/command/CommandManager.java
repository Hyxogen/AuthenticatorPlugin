package com.hyxogen.authenticator.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.hyxogen.authenticator.util.Locale;

/**
 * 
 * @author Daan Meijer
 *
 */
public class CommandManager implements CommandExecutor {

	private List<ICommand> commands = new ArrayList<>();
	private Map<ICommand, List<ICommand>> children = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ICommand c_object = getCommand(command.getName(), commands);
		if (c_object != null)
			handleCommand(sender, c_object, args);
		return true;
	}

	/**
	 * Register a {@link ICommand} to be recognised by the command handler
	 * 
	 * @param command the {@link ICommand} to register
	 * @param parent  the parent {@link ICommand} of the command param. This is used
	 *                for nested commands. Set to <code>null</code> for no parent.
	 */
	public void registerCommand(ICommand command, ICommand parent) {
		if (parent == null)
			commands.add(command);
		else {
			if (children.containsKey(parent))
				children.get(parent).add(command);
			else
				children.put(parent, Arrays.asList(command));
		}
	}

	protected void handleCommand(CommandSender sender, ICommand command, String[] args) {
		boolean success = false;
		if (command.getPermissions() != null) {
			for (int p = 0; p < command.getPermissions().length; p++) {
				if (sender.hasPermission(command.getPermissions()[p])) {
					success = true;
					break;
				}
			}
		}

		if (!success) {
			sender.sendMessage(Locale.WARN_INSUF_PERMISSIONS);
			return;
		}

		if (command.isNested()) {
			List<String> args_remaining = Arrays.asList(args);
			String subCommand = args_remaining.remove(0);
			ICommand child = getCommand(subCommand, children.get(command));
			handleCommand(sender, child, (String[]) args_remaining.toArray());
		} else {
			int min = command.getMinArguments();
			int max = command.getMaxArguments();

			if (args.length > min || args.length < max) {
				sender.sendMessage(ChatColor.DARK_RED + "Incorrect arguments, usage: " + command.getUsage());
				return;
			}

			command.onCommand(sender, args);
		}
	}

	/**
	 * @deprecated use {@link #getCommand(String, Collection)} instead
	 */
	@Deprecated
	public ICommand getCommand(String command, boolean children) {
		if (children)
			return getCommand(command, getAllCommands());
		else
			return getCommand(command, commands);
	}

	public List<ICommand> getAllCommands() {
		List<ICommand> result = new ArrayList<>();
		for (int i = 0; i < commands.size(); i++) {
			ICommand command = commands.get(i);
			result.add(command);

			if (children.containsKey(command))
				result.addAll(children.get(command));
		}
		return result;
	}

	public ICommand getChildCommand(ICommand parent, String command) {
		return getCommand(command, children.get(parent));
	}

	private ICommand getCommand(String command, Collection<ICommand> list) {
		Iterator<ICommand> iterator = list.iterator();
		ICommand c_object = null;
		while ((c_object = iterator.next()) != null) {
			if (c_object.getCommand().equalsIgnoreCase(command))
				return c_object;

			for (int i = 0; i < c_object.getAliases().length; i++) {
				if (c_object.getAliases()[i].equalsIgnoreCase(command))
					return c_object;
			}
		}
		return null;
	}
}