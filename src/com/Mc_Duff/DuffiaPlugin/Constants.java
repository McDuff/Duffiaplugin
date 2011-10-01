package com.Mc_Duff.DuffiaPlugin;

import org.bukkit.util.config.Configuration;

public class Constants {
	public static String MOTD,MOTD1,MOTD2;
	public static void Load(Configuration config){
		config.load();
		
		MOTD = config.getString("motd", "Welcome +name");
		MOTD1 = config.getString("motd1", "Please respect the server admins and server rules!");
		MOTD2 = config.getString("motd2", "Visit us at duffia.gaming.multiplay.co.uk");
	}
}
	
