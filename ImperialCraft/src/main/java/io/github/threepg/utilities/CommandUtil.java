package io.github.threepg.utilities;

import java.util.HashMap;
import java.util.Map;

public class CommandUtil {
	
	
	/**
	 * Checks a hashmap for the Command and/or it's possible aliases.
	 * 
	 * @param hashmap
	 * @return String - command key || null
	 */
	public static String isCommand(String command, HashMap<String, String> hashmap) { 
		String com = command.toLowerCase();
		
		if (com.equals("none")) {
			return null;
		}
		
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(com)) {
				return entry.getKey();
			} else {
				String[] alias = entry.getValue().split(" ");
				for (String s : alias) {
					if (s.equalsIgnoreCase(com)) {
						return entry.getKey();
					}
				}
			}
		}
		return null;
	}
	
}
