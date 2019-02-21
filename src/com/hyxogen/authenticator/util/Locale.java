package com.hyxogen.authenticator.util;

import org.bukkit.ChatColor;

/**
 * 
 * @author Daan Meijer
 *
 */
public class Locale {

	public static final String INFO_USER_SUCCES_AUTH = ChatColor.GREEN + "You have succesfully authenticated yourself";
	
	public static final String INFO_USER_NEEDS_AUTH = ChatColor.RED + "You have to authenticate your identity before you can continue";
	
	public static final String INFO_USER_FAIL_AUTH = ChatColor.RED + "You have failed to authenticate yourself before timout was reached";
	
	public static final String WARN_USER_NOT_AUTH = ChatColor.RED + "You are not allowed to do that untill you've autheticated your identity";
	
	public static final String WARN_INSUF_PERMISSIONS = ChatColor.RED + "You have insufficient permissions to do this";
}