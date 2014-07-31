package io.github.threepg.imperialcraft;

import io.github.threepg.listeners.EconomyServerListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

public class Economy_API_Vault extends AbstractEconomy {
	
	private Logger LOG = null;

	public final String name = "Imperial_Economy";
	private Plugin plugin = null;
	private Plugin vault = null;
	public ImperialCraft instance = null;
	
	public static DecimalFormat currencyFormat = new DecimalFormat("#0", DecimalFormatSymbols.getInstance(Locale.US));
	
	public Economy_API_Vault() {
		this.plugin = ImperialCraft.plugin;
		this.vault = plugin.getServer().getPluginManager().getPlugin("Vault");
		this.LOG = plugin.getLogger();
		
		plugin.getServer().getPluginManager()
			.registerEvents(new EconomyServerListener(this), plugin);
		
		Bukkit.getServicesManager()
			.register(Economy.class, this, vault, ServicePriority.Normal);
		
		// Load Plugin in case it wasn't loaded before
		if (instance == null) {
			Plugin tpg = plugin.getServer().getPluginManager().getPlugin("Tpg");
			if (tpg != null && tpg.isEnabled()) {
				instance = (ImperialCraft)plugin;
				LOG.info(String.format("[%s][Economy] %s hooked.", vault
						.getDescription().getName(), name));
			}
		}
	}
	
	// TODO: Still need to add the functions below. Create main Econ class first.
	
	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public int fractionalDigits() {
		return 0;
	}

	@Override
	public String format(double amount) {
		return null;
	}

	@Override
	public String currencyNamePlural() {
		return null;
	}

	@Override
	public String currencyNameSingular() {
		return null;
	}

	@Override
	public boolean hasAccount(String playerName) {
		return false;
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		return false;
	}

	@Override
	public double getBalance(String playerName) {
		return 0;
	}

	@Override
	public double getBalance(String playerName, String world) {
		return 0;
	}

	@Override
	public boolean has(String playerName, double amount) {
		return false;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount) {
		return false;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName,
			double amount) {
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName,
			double amount) {
		return null;
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
		return null;
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		return null;
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		return null;
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		return null;
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		return null;
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		return null;
	}

	@Override
	public List<String> getBanks() {
		return null;
	}

	@Override
	public boolean createPlayerAccount(String playerName) {
		return false;
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		return false;
	}

}
