package com.hyxogen.authenticator.util;

/**
 * 
 * @author Daan Meijer
 *
 */
public class Permissions {

	/**
	 * Users with this permission are required to opt-in to the 2-factor authentication system and are not allowed to join without.
	 */
	public static final String PERM_AUTH_REQUIRED = "auth.required";
	
	/*
	 * Users with this permission have the option to opt-in to the 2-factor authentication system.
	 */
	public static final String PERM_AUTH_ALLOWED = "auth.allowed";
	
	/**
	 * User with this permission are allowed to force another user to have 2-factor authentication or to remove it from one user.
	 * The user is unable to change other users with the {@link Permissions#PERM_AUTH_ADMIN} permission.
	 */
	public static final String PERM_AUTH_ADMIN = "auth.admin";
	
}