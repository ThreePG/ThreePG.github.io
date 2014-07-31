package io.github.threepg.imperialcraft;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

public class ImpEconomy extends AbstractEconomy {
	
	private Logger LOG = null;

	public final String name = "Imperial_Economy";
	private Plugin plugin = null;
	private Plugin vault = null;
	public ImperialCraft instance = null;
	
	public static DecimalFormat currencyFormat = new DecimalFormat("#0", DecimalFormatSymbols.getInstance(Locale.US));
	public static HashMap<String, Double> accounts = new HashMap<>(); // {PlayerUUID, Balance}
	
	
	public ImpEconomy() {
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
	
	// Personal method
	public static void setBalance(String player, double amount) {
		accounts.put(player, amount);
	}
	
	public boolean removeAccount(OfflinePlayer playerName) {
		try {
			accounts.remove(playerName.getUniqueId().toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean isEnabled() {
		if (instance == null) {
			return false;
		} else {
			return instance.isEnabled();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public int fractionalDigits() {
		return 0;
	}
	
	// Personal Method
	public double cleanFormat(double amount) {
		currencyFormat.setRoundingMode(RoundingMode.HALF_UP);
		currencyFormat.format(amount);
		return amount;
	}

	@Override
	public String format(double amount) {
		currencyFormat.setRoundingMode(RoundingMode.HALF_UP);
		String str = plugin.getConfig().getString("currency_symbol") + currencyFormat.format(amount);
		return str;
	}

	@Override
	public String currencyNamePlural() {
		return plugin.getConfig().getString("currency_name_plural");
	}

	@Override
	public String currencyNameSingular() {
		return plugin.getConfig().getString("currency_name_Singular");
	}
	
	@Override
	public boolean hasAccount(OfflinePlayer playerName) {
		return accounts.containsKey(playerName.getUniqueId().toString());
	}
	
	// TODO: Forward deprecated methods once User Utility is setup
	@Override
	public boolean hasAccount(String playerName) {
		return false;
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		return false;
	}
	
	@Override
	public double getBalance(OfflinePlayer playerName) {
		return accounts.get(playerName.getUniqueId().toString());
	}
	
	// TODO: Forward deprecated methods once User Utility is setup
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
	public EconomyResponse withdrawPlayer(OfflinePlayer playerName, double amount) {
		double fAmount = cleanFormat(amount);
		double cAmount = getBalance(playerName.getUniqueId().toString());
		double balance;
		EconomyResponse.ResponseType type;
		String errorMessage = null;

		try {
			setBalance(playerName.getUniqueId().toString(), cAmount - fAmount);
			balance = getBalance(playerName.getUniqueId().toString());
			type = EconomyResponse.ResponseType.SUCCESS;
		} catch (Exception e) {
			balance = 0;
			fAmount = 0;
			type = EconomyResponse.ResponseType.FAILURE;
			errorMessage = "Could not withdraw money at this time.";
		}
		return new EconomyResponse(fAmount, balance, type, errorMessage);
	}
	
	// TODO: Forward deprecated methods once User Utility is setup
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
	public EconomyResponse depositPlayer(OfflinePlayer playerName, double amount) {
		double fAmount = cleanFormat(amount);
		double cAmount = getBalance(playerName.getUniqueId().toString());
		double balance;
		EconomyResponse.ResponseType type;
		String errorMessage = null;

		try {
			setBalance(playerName.getUniqueId().toString(), cAmount + fAmount);
			balance = getBalance(playerName.getUniqueId().toString());
			type = EconomyResponse.ResponseType.SUCCESS;
		} catch (Exception e) {
			balance = 0;
			fAmount = 0;
			type = EconomyResponse.ResponseType.FAILURE;
			errorMessage = "Could not deposit money at this time.";
		}
		return new EconomyResponse(fAmount, balance, type, errorMessage);
	}
	
	// TODO: Forward deprecated methods once User Utility is setup
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
	public boolean createPlayerAccount(OfflinePlayer playerName) {
		try{
			accounts.put(playerName.getUniqueId().toString(), 0D);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// TODO: Forward deprecated methods once User Utility is setup
	@Override
	public boolean createPlayerAccount(String playerName) {
		return false;
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		return false;
	}

}
