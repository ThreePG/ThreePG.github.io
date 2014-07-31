package io.github.threepg.imperialcraft;

import java.util.HashMap;
import java.util.Map;

import io.github.threepg.commands.AbstractCommand;
import io.github.threepg.commands.CommandHandler;
import io.github.threepg.utilities.MenuUtil;
import io.github.threepg.utilities.MenuUtil.InvalidNumberException;
import io.github.threepg.utilities.UserUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Menus {
	
	public static MenuUtil getCommands() {
		try {
			MenuUtil menu = new MenuUtil("Imperial Craft", 7, ChatColor.DARK_AQUA);
			// int i = 1; // To include a numbered list
			for (AbstractCommand command : CommandHandler.cmds) {
				menu.addEntry(ChatColor.GOLD + 
						command.getUsage() + ": " + 
						ChatColor.RESET + 
						command.getMessage());
				// i++;
			}
			return menu;
		} catch (InvalidNumberException e) {
			e.getMessage();
		}
		return null;
	}
	
	/**
	 * Gathers a sub command menu
	 * 
	 * @param sender
	 * Pass sender in order to verify permissions
	 * @param permissionKey
	 * Enter permission main key: Ex. "ic.econ."
	 * @param title
	 * Title of the sub menu
	 * @param hashmap
	 * Hashmap of commands/messages to sort through
	 * @return
	 */
	
	public static MenuUtil getSubCommands(CommandSender sender, String permissionKey, String title,
			HashMap<String, String> hashmap) {
		try {
			MenuUtil menu = new MenuUtil("Imperial Craft " + title + " Sub-Commands", 7,
					ChatColor.DARK_AQUA);

			for (Map.Entry<String, String> entry : hashmap.entrySet()) {
				String[] command = entry.getKey().split(" ");
				
				if (UserUtil.isAuthorized(sender, permissionKey + command[1])) {
					menu.addEntry(ChatColor.GOLD + entry.getKey() + ": "
							+ ChatColor.RESET + entry.getValue());
				}
			}
			if (menu.size() < 1) {
				menu.addEntry("");
			}
			return menu;
		} catch (InvalidNumberException e) {
			sender.sendMessage(e.getMessage());
		}
		return null;
	}
}
