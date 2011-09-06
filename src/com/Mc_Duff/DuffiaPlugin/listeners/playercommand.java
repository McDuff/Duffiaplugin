package com.Mc_Duff.DuffiaPlugin.listeners;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.Mc_Duff.DuffiaPlugin.DuffiaPlugin;
import com.Mc_Duff.DuffiaPlugin.economy.economy;
import com.Mc_Duff.mini.Arguments;

public class playercommand {
	
	//allow access to Duffiaplugin class
	DuffiaPlugin plugin;
	
	//constructor
	public playercommand(DuffiaPlugin plugin) {
		this.plugin = plugin;
	}
	
	//this object is called in main Duffiaplugin class when a command happens
	public void pcommand(CommandSender player, Command cmd, String label,String[] args){
		
		//define a player object
		Player caster = (Player) player;
		//check command name and ignore case (i.e capitals etc..)
		if (label.equalsIgnoreCase("spawn")){
			//create location object and define coords
			Location spawnloc = new Location(caster.getWorld(),45 ,65,-30,180,0);
			caster.teleport(spawnloc);
			caster.sendMessage(caster.getDisplayName()+ ", you have been returned to Spawn");
			 /*its a boolean so needs to return true to do the command*/
		} else if (label.equalsIgnoreCase("pvp")){	
			//gets data for individual from flatfile
			Arguments pvp = plugin.database1.getArguments(caster.getDisplayName());
			//pvp!=null checks if there is existing player data
			if(pvp != null){
				String pvpscore = "[PVP]Kills:" + pvp.getValue("K")+ " ,Deaths:" + pvp.getValue("D")+ " ,PVP coins:" + pvp.getValue("PVPCOIN");
				caster.sendMessage(pvpscore);
				
			}
			else {
				String pvpscore = "Duffia was unable to determine your PVP record!";
				caster.sendMessage(pvpscore);
				
			}
			//args.length to see if they put any additional arguments. i.e /buy <something>
		} else if(label.equalsIgnoreCase("buy") && args.length>0){		
			Arguments pvp = plugin.database1.getArguments(caster.getDisplayName());
			int pvpcoins = 0;
			
			if (pvp != null){
				//type conversion to int whilst getting data
				pvpcoins = Integer.parseInt(pvp.getValue("PVPCOIN"));	
			}
			
			if(args[0].equalsIgnoreCase("help")){
				caster.sendMessage("/buy help: Lists available buy options" );
				caster.sendMessage("/buy heal: Heals player for 5 PVPcoins" );
			
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
					plugin.database1.addIndex(bentry.getKey(), bentry);
					//must update database to make changes stick before using again
					plugin.database1.update();
				
				} 
				else {
					caster.sendMessage(caster.getDisplayName() + " ,You do not have enough PVP coins!");
			
				}
			}
			else {
				caster.sendMessage(caster.getDisplayName() + " ,Try using /buy help");	
			}
		}
		if(label.equalsIgnoreCase("money")){
			
			economy check = new economy(plugin);	
			
			if(args.length>0 && caster.isOp()){
				try {
					Player account = plugin.getServer().getPlayer(args[0]);
					check.checkaccount(caster, account);
				} catch (Exception e) {
					caster.sendMessage("Cannot find player!");
				}
						
			} else {
				check.checkaccount(caster, caster);
			}
		}
		if(label.equalsIgnoreCase("pay")){
			
			economy pay = new economy(plugin);	
			if(args.length>1){
				Player account = plugin.getServer().getPlayer(args[0]);

				try {
					int amount = Integer.parseInt(args[1]);
					if (account!=null){
						pay.payplayer(caster, account, amount);
					} else {				
						caster.sendMessage("Cannot find player!");
					}
				}
				catch (Exception e) {
				caster.sendMessage("Amount not valid");
				}
			
			} else {
				caster.sendMessage("please use the format: /pay [player] <amount>!");
			}
		}
		if(label.equalsIgnoreCase("addmoney")){
				
			economy addmoney = new economy(plugin);	
			if(args.length>1){
				Player account = plugin.getServer().getPlayer(args[0]);

				try {
					int amount = Integer.parseInt(args[1]);
					if (account!=null && caster.isOp()){
						addmoney.addmoney(caster, account, amount);
					} else {				
					caster.sendMessage("Cannot find player!");
					}
				}
				catch (Exception e) {
					caster.sendMessage("Amount not valid");
				}
					
			} else {
					caster.sendMessage("please use the format: /pay [player] <amount>!");
			}
		}
		if(label.equalsIgnoreCase("takemoney")){
			
			economy removemoney = new economy(plugin);	
			if(args.length>1){
				Player account = plugin.getServer().getPlayer(args[0]);

				try {
					int amount = Integer.parseInt(args[1]);
					if (account!=null && caster.isOp()){
						removemoney.takemoney(caster, account, amount);
					} else {				
					caster.sendMessage("Cannot find player!");
					}
				}
				catch (Exception e) {
					caster.sendMessage("Amount not valid");
				}
					
			} else {
					caster.sendMessage("please use the format: /pay [player] <amount>!");
			}
		}
		if(label.equalsIgnoreCase("crayon")){
			caster.sendMessage("Coming Soon!");
		
		}
		if(cmd.getName().equalsIgnoreCase("startpack")){
		
			ItemStack item1 = new ItemStack(274, 1);
			ItemStack item2 = new ItemStack(272, 1);
			ItemStack item3 = new ItemStack(50, 16);
			PlayerInventory inventory = caster.getInventory();
			inventory.addItem(new ItemStack[] { item1, item2, item3 });
			caster.sendMessage("You have been given a starter pack!");
		}	
	}
	
}
