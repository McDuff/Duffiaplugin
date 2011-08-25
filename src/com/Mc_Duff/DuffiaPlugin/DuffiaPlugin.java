package com.Mc_Duff.DuffiaPlugin;

import java.io.File;
import java.io.IOException;

import com.Mc_Duff.DuffiaPlugin.listeners.block;
import com.Mc_Duff.DuffiaPlugin.listeners.entities;
import com.Mc_Duff.DuffiaPlugin.listeners.players;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.Mc_Duff.mini.Mini;

public class DuffiaPlugin extends JavaPlugin{
	private PluginDescriptionFile info;
	private PluginManager pm;
	private File directory, config;
	public Mini database, database1;
	//public final HashMap<Player, ArrayList<Block>> basicusers = new Hashmap;
	
	public void onDisable(){ 
		System.out.println("[" + info.getName() +  "] " + info.getVersion() + " disabled");	
	}
	
	public void onEnable(){ 
/*             configuration         */		
		//gets server description from plugin.yml
		info = getDescription();
		//enable the plugin manager
		pm = getServer().getPluginManager();
		//create a directory for files
		directory = getDataFolder();
		//define database files
		database = new Mini(directory.getPath(), "locations.mini");
		database1 = new Mini(directory.getPath(), "pvp.mini");
		//check if directory is present and create new if not
		if(!directory.exists())
			directory.mkdirs();
		//define a new config file
		config = new File(directory,"config.yml");
		//check if config is present, if not create a new one
		if(!config.exists())
			try {
				config.createNewFile();
			} catch (IOException ex) {
				System.out.println("[" + info.getName() + "] Error:"+ ex.getMessage());
			}
		
		//load config files
		Constants.Load(new Configuration(new File(directory, "config.yml")));
		//sends a message to the console
		System.out.println("[" + info.getName() +  "] " + info.getVersion() + " enabled");
		
/*             events         */	
		//player join event
		pm.registerEvent(Type.PLAYER_JOIN, new players(this), Priority.Low, this);
		//player quit event
		pm.registerEvent(Type.PLAYER_QUIT, new players(this), Priority.Low, this);
		//Block place event
		pm.registerEvent(Type.BLOCK_PLACE, new block(this), Priority.Low, this);		
		//Block break event
		pm.registerEvent(Type.BLOCK_BREAK, new block(this), Priority.Low, this);
		//entity damage
		pm.registerEvent(Type.ENTITY_DAMAGE, new entities(this), Priority.Low, this);
		
	}
	 
	public boolean onCommand(CommandSender player, Command cmd, String label,String[] args) {

		if (label.equalsIgnoreCase("spawn")){
			Player caster = (Player) player;
			Location startloc = new Location(caster.getWorld(),45 ,65,-30,180,0);
			caster.teleport(startloc);
			caster.sendMessage("you have used the /" + label + " command");
			return true;
		}
		return false;
	}
	
	
}
