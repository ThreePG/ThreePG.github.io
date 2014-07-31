package io.github.threepg.commands;

import io.github.threepg.imperialcraft.ImperialCraft;
import io.github.threepg.imperialcraft.Menus;
import io.github.threepg.utilities.MathUtil;
import io.github.threepg.utilities.MenuUtil;
import io.github.threepg.utilities.MenuUtil.InvalidNumberException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandHandler implements CommandExecutor {
	
	private ImperialCraft plugin = ImperialCraft.plugin;
	public static List<AbstractCommand> cmds = new ArrayList<AbstractCommand>();
	public final String LOG_TAG = "[Imperial]";
	private Logger LOG = plugin.getLogger();
	public final ChatColor err = ChatColor.RED;
	public final ChatColor suc = ChatColor.GREEN;
	public final ChatColor base = ChatColor.DARK_AQUA;
	public final ChatColor mes = ChatColor.YELLOW;
	
	// All new commands MUST be initialized.
	public void init() {
		cmds.add(new SendMe());
		cmds.add(new Econ());
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		

		if (cmd.getName().equalsIgnoreCase("ic")) {
			if(args.length == 0) {
				MenuUtil mu = Menus.getCommands();
				try {
					mu.showPage(sender, 1);
					return true;
				} catch (InvalidNumberException e) {
					sender.sendMessage(err + e.getMessage());
				}
				return true;
			}

			if (args.length == 1 && MathUtil.isNumber(args[0])) {
				MenuUtil mu = Menus.getCommands();
				try {
					mu.showPage(sender, Integer.valueOf(args[0]));
					return true;
				} catch (InvalidNumberException e) {
					sender.sendMessage(err + e.getMessage());
				}
				return true;
			}
		}
		
		AbstractCommand command = getCommand(args[0]);
		
		if (command == null) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage(err + "Invalid Command");
				return true;
			} else {
				LOG.info(LOG_TAG + " Invalid Command");
			}
		}
		
		Vector<String> a = new Vector<String>(Arrays.asList(args));
		a.remove(0);
		args = a.toArray(new String[a.size()]);
		command.onCommand(sender, args);

		return false;
	}


	private AbstractCommand getCommand(String string) {
		
		for(AbstractCommand command : cmds) {
			if(command.getClass().getSimpleName().equalsIgnoreCase(string)) {
				return command;
			}

			for(String alias : command.getAliases()) {
				if(string.equalsIgnoreCase(alias))
					return command;
			}
		}
		return null;
	}

}