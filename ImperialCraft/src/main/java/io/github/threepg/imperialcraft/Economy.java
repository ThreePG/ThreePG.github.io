package io.github.threepg.imperialcraft;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Economy {
	
	private ImperialCraft plugin = null;
	
	static DecimalFormat currencyFormat = Economy_API_Vault.currencyFormat;
	
	public HashMap<String, Double> accounts = new HashMap<>(); // {PlayerUUID, Balance}
	
	public Economy() {
		if (this.plugin == null) {
			this.plugin = ImperialCraft.plugin;
		}
	}
	
	
}
