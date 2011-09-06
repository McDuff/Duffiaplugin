package com.Mc_Duff.DuffiaPlugin;
//import all the stuff needed
import java.io.File;
import java.io.IOException;

import com.Mc_Duff.DuffiaPlugin.listeners.block;
import com.Mc_Duff.DuffiaPlugin.listeners.playercommand;
import com.Mc_Duff.DuffiaPlugin.listeners.entities;
import com.Mc_Duff.DuffiaPlugin.listeners.players;

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
//extends java plugin so it can do basic java stuff...
public class DuffiaPlugin extends JavaPlugin{
	private PluginDescriptionFile info;
	private PluginManager pm;
	private File directory, config;
	public Mini database, database1, database2;
	//public final HashMap<Player, ArrayList<Block>> basicusers = new Hashmap;
	
	public void onDisable(){ 
		//message to console
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
		//define flatfile databases
		database = new Mini(directory.getPath(), "locations.mini");
		database1 = new Mini(directory.getPath(), "pvp.mini");
		database2 = new Mini(directory.getPath(), "economy.mini");
		//check if directory is present and create new if not
		if(!directory.exists())
			directory.mkdirs();
		//define a new config file
		config = new File(directory,"config.yml");
		//check if config is present, if not create a new one
		if(!config.exists())
			try {
				config.createNewFile();
				//watch for input/output exceptions
			} catch (IOException ex) {
				System.out.println("[" + info.getName() + "] Error:"+ ex.getMessage());
			}
		
		//load config files
		Constants.Load(new Configuration(new File(directory, "config.yml")));
		//sends a message to the console
		System.out.println("[" + info.getName() +  "] " + info.getVersion() + " enabled");
		
/*            events to trigger listeners         */	
		//player join event
		pm.registerEvent(Type.PLAYER_JOIN, new players(this), Priority.Low, this);
		//player quit event
		pm.registerEvent(Type.PLAYER_QUIT, new players(this), Priority.Low, this);
		//Block place event
		pm.registerEvent(Type.BLOCK_PLACE, new block(this), Priority.Low, this);		
		//Block break event
		pm.registerEvent(Type.BLOCK_BREAK, new block(this), Priority.Low, this);
		//Block break event
		pm.registerEvent(Type.BLOCK_DAMAGE, new block(this), Priority.Low, this);
		//entity damage
		pm.registerEvent(Type.ENTITY_DAMAGE, new entities(this), Priority.Low, this);
        //setting blocks alight event
		pm.registerEvent(Type.BLOCK_IGNITE, new block(this), Priority.Low, this);
        //block burning event
		pm.registerEvent(Type.BLOCK_BURN, new block(this), Priority.Low, this);
		//exploding entity events
		pm.registerEvent(Type.ENTITY_EXPLODE, new entities(this), Priority.Low, this);
	}
	/*me wants to move this outside of my main class.....*/
	//when player command is true do something
	//note: must list the command in the plugin.yml
	public boolean onCommand(CommandSender player, Command cmd, String label,String[] args) {
		
		//if a player casts the command
		if(player instanceof Player){
		//create a new object of playercommand of this (this is this plugin)
		playercommand pcmd = new playercommand(this);
		//run the object including the information from the command event
		pcmd.pcommand(player, cmd, label, args);
		//returns command true as it is a player
		return true;
		}
		//returns command false as it is not a player casting it
		return false;
		
	}	

}
