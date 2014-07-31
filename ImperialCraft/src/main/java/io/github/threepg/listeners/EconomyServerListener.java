package io.github.threepg.listeners;

import io.github.threepg.imperialcraft.Economy_API_Vault;
import io.github.threepg.imperialcraft.ImperialCraft;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

public class EconomyServerListener implements Listener {
	
	Economy_API_Vault economy = null;
	private Plugin vault = null;
	private Plugin ic = null;
	private Logger LOG = null;
	
	public EconomyServerListener(Economy_API_Vault economy) {
		this.economy = economy;
		this.ic = ImperialCraft.plugin;
		this.vault = ic.getServer().getPluginManager().getPlugin("Vault");
		this.LOG = ic.getLogger();
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		if (economy.instance == null) {
			Plugin ic = event.getPlugin();
			
			if (ic.getDescription().getName().equals("ImperialCraft")) {
				economy.instance = (ImperialCraft) ic;
				LOG.info(String.format("[%s][Economy] %s hooked.", 
						vault.getDescription().getName(), economy.name));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent event) {
		if(economy.instance != null) {
			if(event.getPlugin().getDescription().getName().equals("ImperialCraft")) {
				economy.instance = null;
				LOG.info(String.format("[%s][Economy] %s unhooked", 
						vault.getDescription().getName(), economy.name));
			}
		}
	}
	
}

























