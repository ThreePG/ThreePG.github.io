package io.github.threepg.imperialcraft;

import io.github.threepg.commands.CommandHandler;

import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class ImperialCraft extends JavaPlugin {
	
	private final Logger LOG = this.getLogger();
	public static ImperialCraft plugin = null;
	
	private Economy econ = null;
	private transient ImpEconomy icEcon = null;
	
	public final String LOG_TAG = "[Imperial]";
	public final ChatColor err = ChatColor.RED;
	public final ChatColor suc = ChatColor.GREEN;
	public final ChatColor base = ChatColor.DARK_AQUA;
	public final ChatColor mes = ChatColor.YELLOW;
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdf = this.getDescription();
		ImperialCraft.plugin = this;
		this.saveDefaultConfig();
		this.reloadConfig();
		
		
		// Register Listeners
		getServer().getPluginManager().registerEvents(new ImperialPlayerListener(), this);
		
		// Command Instance(s)
		CommandHandler ch = new CommandHandler();
		ch.init();
		this.getCommand("ic").setExecutor(ch);
		
		// Logs
		LOG.info(LOG_TAG + " Version: " + pdf.getVersion());
		
		// Setup Economy
		if (!setupEconomy()) {
			LOG.severe(String.format(
					"[%s] - Disabled due to no Vault dependency found!",
					getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll();
		plugin = null;
	}
	
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		this.icEcon = new ImpEconomy();
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	public ImpEconomy getIcEcon() {
		return icEcon;
	}
}






















