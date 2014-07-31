package io.github.threepg.imperialcraft;

import io.github.threepg.utilities.ConfigAccessor;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;


public class ImperialPlayerListener implements Listener {
	
	private ImperialCraft plugin = ImperialCraft.plugin;
	private ConfigAccessor config = null;
	private Economy econ;
	private ImpEconomy icEcon = null;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		econ = rsp.getProvider();
		Player player = e.getPlayer();
		this.config = new ConfigAccessor(plugin, player.getUniqueId().toString());
		
		if (!(config.configFile.exists())) {
			return;
		}
		config.getConfig().set("last-known-name", player.getName());
		config.saveConfig();
		econ.createPlayerAccount(player);
		double money;
		if ((money = config.getConfig().getDouble("money")) > 0) {
			econ.depositPlayer(player, money);
			return;
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		econ = rsp.getProvider();
		Player player = e.getPlayer();
		this.config = new ConfigAccessor(plugin, player.getUniqueId().toString());
		
		if (icEcon == null) {
			icEcon = plugin.getIcEcon();
		}
		
		if (econ.hasAccount(player)) {
			config.getConfig().set("money", econ.getBalance(player));
			config.saveConfig();
			icEcon.removeAccount(player);
			
		}
	}
	
}
