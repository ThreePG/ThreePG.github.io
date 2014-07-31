package io.github.threepg.utilities;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class UserUtil {
	
	/*
	 * Checks if Sender is authorized to use a given command.
	 * Console is always given authorization
	 */
	public static boolean isAuthorized(CommandSender sender, String perm) {
		
		if (sender instanceof ConsoleCommandSender) {
			return true;
		} else if (sender instanceof Player) {
			Player player = (Player) sender;
			return player.hasPermission(perm);
		}
		return false;
	}
}
