package com.Mc_Duff.DuffiaPlugin.listeners;

import com.Mc_Duff.DuffiaPlugin.Constants;
import com.Mc_Duff.DuffiaPlugin.DuffiaPlugin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Mc_Duff.mini.Arguments;

//players listener
public class players extends PlayerListener{
	//load in our plugin so it shares code from duffiaplugin.java
	DuffiaPlugin plugin;
	
	public players(DuffiaPlugin plugin) {
		this.plugin = plugin;
	}
	//catches PLAYER_JOIN event and does stuff
	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		//this checks players last location and KDR and posts it to players ingame
		if(player != null){
			player.sendMessage(Constants.MOTD.replace("+name", player.getDisplayName()));
			Arguments lastEntry = plugin.database.getArguments(player.getDisplayName());
			Arguments pvpEntry = plugin.database1.getArguments(player.getDisplayName());
			
			if(lastEntry != null){
				String last = "Last logout coords: " + lastEntry.getValue("x")+ "," +lastEntry.getValue("y")+ "," + lastEntry.getValue("z");
				player.sendMessage(last);
			}
			else {
				String last = "Duffia was unable to determine your last location!";
				player.sendMessage(last);	
			}
			
			if(pvpEntry != null){
				String pvp = "[PVP]Kills:" + pvpEntry.getValue("K")+ " ,Deaths:" + pvpEntry.getValue("D")+ " ,PVP coins:" + pvpEntry.getValue("PVPCOIN");
				player.sendMessage(pvp);
			}
			else {
				String pvp = "Duffia was unable to determine your PVP record!";
				player.sendMessage(pvp);	
			}
			
		}
	}
	//catches PLAYER_QUIT event
	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		//stores players login location to a flat file database
		Location current = player.getLocation();
		Arguments entry = new Arguments(player.getDisplayName());
		//converts an int type back into a string for the flatfile entry values
		entry.setValue("x",String.valueOf((int) current.getX()));
		entry.setValue("y",String.valueOf((int) current.getY()));
		entry.setValue("z",String.valueOf((int) current.getZ()));
		
		plugin.database.addIndex(entry.getKey(), entry);
		plugin.database.update();
	}	

}
