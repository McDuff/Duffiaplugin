package com.Mc_Duff.DuffiaPlugin;

import org.bukkit.util.config.Configuration;

public class Constants {
	public static String MOTD;
	public static void Load(Configuration config){
		config.load();
		
		MOTD = config.getString("motd", "Welcome +name");
	}
}
	
