package io.github.threepg.commands;

import io.github.threepg.imperialcraft.ImperialCraft;
import io.github.threepg.utilities.UserUtil;

import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Balance extends AbstractCommand {
	
	private ImperialCraft plugin = ImperialCraft.plugin;
	private Economy econ;
	private Logger LOG = plugin.getLogger();

	public final String LOG_TAG = "[IC]";
	public final ChatColor err = ChatColor.RED;
	public final ChatColor suc = ChatColor.GREEN;
	public final ChatColor base = ChatColor.DARK_AQUA;
	public final ChatColor mes = ChatColor.YELLOW;

	private String senderName;
	private String targetName;
	private String targetUUID;
	private OfflinePlayer targetPlayer;
	private String command;
	private double amount;
	private String permPrefix = "ic.";
	

	public Balance() {
		super("Retrieve player account balance", "/ic balance", "bal", "b");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		RegisteredServiceProvider<Economy> rsp = plugin.getServer()
				.getServicesManager().getRegistration(Economy.class);
		econ = rsp.getProvider();
		
		getBalance(sender, args);
	}
	
	
	
private void getBalance(CommandSender sender, String[] args) {
		
		if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase(sender.getName()))) {
			if (!(UserUtil.isAuthorized(sender, permPrefix + "balance"))) {
				sender.sendMessage(err + "Permission Denied.");
				return;
			}
			if (sender instanceof Player) {
				OfflinePlayer player = (Player) sender;
				if (econ.hasAccount(player)) {
					((Player) player).sendMessage(String.format(base
							+ "Balance:" + mes + " %s", econ.format(econ.getBalance(player))));
					return;
				} else {
					((Player) player).sendMessage(err + "You do not have an account yet.");
					return;
				}
			} else {
				LOG.info("/ic balance <player>");
				return;
			}
		} else if (!(UserUtil.isAuthorized(sender, permPrefix + "balance.others"))) {
			sender.sendMessage(err + "Permission Denied.");
			return;
		} else if (args.length == 1 && (!(args[0].equalsIgnoreCase(sender.getName())))) {
			if (targetPlayer != null) {
				if (econ.hasAccount(targetPlayer)) {
					sender.sendMessage(String.format(
							base + targetPlayer.getName() + " has %s",
							econ.format(econ.getBalance(targetPlayer))));
					return;
				} else {
					sender.sendMessage(err + targetPlayer.getName()
							+ " does not have an account.");
					return;
				}
			} else {
				sender.sendMessage(err + targetName + " is not a valid player.");
				return;
			}
		} else {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage(base + "Checks an account balance:");
				player.sendMessage(base + "/ic balance [player]");
				return;
			} else {
				sender.sendMessage("/ic balance <player>");
				return;
			}
		}
	}
}
































