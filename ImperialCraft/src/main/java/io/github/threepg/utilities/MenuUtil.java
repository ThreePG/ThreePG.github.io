package io.github.threepg.utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class MenuUtil {

	private String displayName;
	private int perPage;
	private ChatColor chatColor;
	public final ChatColor err = ChatColor.RED;
	public final ChatColor suc = ChatColor.GREEN;
	public final ChatColor base = ChatColor.DARK_AQUA;
	public final ChatColor mes = ChatColor.YELLOW;

	private ArrayList<String> entries = new ArrayList<String>();

	public MenuUtil(String displayName, int perPage, ChatColor chatColor)
			throws InvalidNumberException {
		this.displayName = displayName;
		this.perPage = perPage;
		this.chatColor = chatColor;

		if (perPage < 1) {
			throw new InvalidNumberException("Number must be greater than 0.");
		}
	}

	/**
	 * Add an entry.
	 *
	 * @param entry
	 *            The entry to add.
	 */
	public void addEntry(String entry) {
		entries.add(entry);
	}

	/**
	 * Returns the total number of entries.
	 *
	 * @return
	 */
	public int size() {
		return entries.size();
	}

	/**
	 * Display a page to a player. Checks player permissions level prior to showing command
	 *
	 * @param sender
	 *            The player/console to send it to.
	 * @param page
	 *            The page number.
	 * @throws InvalidNumberException
	 */
	public void showPage(CommandSender sender, int page) throws InvalidNumberException {
		if (page < 1) {
			throw new InvalidNumberException(err + "Page must be greater than 0");
		}
		
		int maxPages;
		if ((size() % perPage) == 0) {
			maxPages = size() / perPage;
		} else {
			maxPages = size() / perPage + 1;
		}
		
		if (page > maxPages) {
			throw new InvalidNumberException(err + "Page does not exist");
		}

		int start = perPage * (page - 1);

		sender.sendMessage(chatColor + "===[ " + ChatColor.RESET
				+ displayName + ChatColor.GRAY + " (" + page + " / " + maxPages
				+ ") " + chatColor + "]===");

		for (int x = 0; x < perPage; x++) {
			int entry = x + start;
			if (entry >= size())
				break;
			
			sender.sendMessage(entries.get(entry));
		}

	}

	public class InvalidNumberException extends Exception {

		private static final long serialVersionUID = 1L;
		private String msg;

		public InvalidNumberException(String msg) {
			this.msg = msg;
		}

		public String getMessage() {
			return this.msg;
		}

	}
}