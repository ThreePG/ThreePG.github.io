package io.github.threepg.commands;

import org.bukkit.command.CommandSender;

public abstract class AbstractCommand {
	
	private String[] aliases;
	private String message;
	private String usage;

	public AbstractCommand(String message, String usage, String... aliases) {
		this.message = message;
		this.usage = usage;
		this.aliases = aliases;
	}
	
	public abstract void onCommand(CommandSender sender, String[] args);

	public final String getMessage() {
		return message;
	}

	public final String getUsage() {
		return usage;
	}

	public final String[] getAliases() {
		return aliases;
	}
}
