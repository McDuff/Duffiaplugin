package com.Mc_Duff.DuffiaPlugin;
//import all the stuff needed
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

import com.Mc_Duff.mini.Arguments;
import com.Mc_Duff.mini.Mini;
//extends java plugin so it can do basic java stuff...
public class DuffiaPlugin extends JavaPlugin{
	private PluginDescriptionFile info;
	private PluginManager pm;
	private File directory, config;
	public Mini database, database1;
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
		//define a player object
		Player caster = (Player) player;
		//check command name and ignore case (i.e capitals etc..)
		if (label.equalsIgnoreCase("spawn")){
			//create location object and define coords
			Location spawnloc = new Location(caster.getWorld(),45 ,65,-30,180,0);
			caster.teleport(spawnloc);
			caster.sendMessage(caster.getDisplayName()+ ", you have been returned to Spawn");
			return true; /*its a boolean so needs to return true to do the command*/
		} else if (label.equalsIgnoreCase("pvp")){	
			//gets data for individual from flatfile
			Arguments pvp = database1.getArguments(caster.getDisplayName());
			//pvp!=null checks if there is existing player data
			if(pvp != null){
				String pvpscore = "[PVP]Kills:" + pvp.getValue("K")+ " ,Deaths:" + pvp.getValue("D")+ " ,PVP coins:" + pvp.getValue("PVPCOIN");
				caster.sendMessage(pvpscore);
				return true;
			}
			else {
				String pvpscore = "Duffia was unable to determine your PVP record!";
				caster.sendMessage(pvpscore);
				return true;
			}
			//args.length to see if they put any additional arguments. i.e /buy <something>
		} else if(label.equalsIgnoreCase("buy") && args.length>0){		
			Arguments pvp = database1.getArguments(caster.getDisplayName());
			int pvpcoins = 0;
			
			if (pvp != null){
				//type conversion to int whilst getting data
				pvpcoins = Integer.parseInt(pvp.getValue("PVPCOIN"));	
			}
			
			if(args[0].equalsIgnoreCase("help")){
				caster.sendMessage("/buy help: Lists available buy options" );
				caster.sendMessage("/buy heal: Heals player for 5 PVPcoins" );
				return true;
			}
			else if (args[0].equalsIgnoreCase("heal")){
				if (pvp != null && pvpcoins>=5) {
					Arguments bentry = new Arguments(caster.getDisplayName());
					//sets command casters health to max (20)
					caster.setHealth(20);
					pvpcoins = pvpcoins-5;
					//get current stats
					int Kills = Integer.parseInt(pvp.getValue("K"));
					int Deaths = Integer.parseInt(pvp.getValue("D"));
					caster.sendMessage("You have fully healed for 5 PVP coins!");
					//ready data to re-add to flatfile overwrites whole line
					bentry.setValue("K",String.valueOf(Kills));
					bentry.setValue("D",String.valueOf(Deaths));
					bentry.setValue("PVPCOIN",String.valueOf(pvpcoins));
					//send updates to database
					database1.addIndex(bentry.getKey(), bentry);
					//must update database to make changes stick before using again
					database1.update();
					return true;
				} 
				else {
					caster.sendMessage(caster.getDisplayName() + " ,You do not have enough PVP coins!");
					return true;
				}
			}
			else {
				caster.sendMessage(caster.getDisplayName() + " ,Try using /buy help");
				return true;
			}
		}
		/*return false where none of the options are found so nothing is performed*/
		return false; 
	}
	
}
