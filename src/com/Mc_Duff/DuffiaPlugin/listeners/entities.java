package com.Mc_Duff.DuffiaPlugin.listeners;

import java.util.Random;

import com.Mc_Duff.DuffiaPlugin.DuffiaPlugin;
import com.Mc_Duff.mini.Arguments;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;

public class entities extends  EntityListener{
	DuffiaPlugin plugin;
		
	public entities(DuffiaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void onEntityExplode(EntityExplodeEvent event){  		
    	event.setCancelled(true);
    }
	
	
	//note: entities can include players.
	//captures entitty damage event
	public void  onEntityDamage(EntityDamageEvent EntityDamaged)
	{
		if(EntityDamaged instanceof EntityDamageByEntityEvent){
			//sending typecastng an object into another to get the damager information
	    	EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)EntityDamaged;
	    	
	    	//if the entity hit is a checken randomly spawn something else
	    	if(e.getEntity() instanceof Chicken && e.getDamager() instanceof Player) {
	    		//cast entity to player type
	    		Player damager = (Player)e.getDamager();
	    		Random randomno = new Random();
	    		int randomInt = randomno.nextInt(100); 
	    		
	     		if(randomInt == 69){
	    			damager.sendMessage("You hit the Killer Chicken of Caerbannog");    		
	    			//spawns a ghast. But how to change its name or get its ID.
	    			damager.getWorld().spawnCreature(damager.getLocation(), CreatureType.GHAST);
	     		
	     		}
	     	
	    	}
	    	
	    	//Check to see if the damager and damaged are players by comparing to Player object
	    	if (e.getDamager() instanceof Player && e.getEntity() instanceof Player){
	    		//convert entitiy object into player object
				Player damager = (Player)e.getDamager();
				Player damaged = (Player)e.getEntity();
				//check that there are still Player information 				
				if(damager != null && damaged != null){
					//check health - damage is nto less that 0 as this event is prior to damage applied.
					double health = damaged.getHealth()- e.getDamage();
					if(health<=0){
						//this is to stop damage ticks from being counted multiple times
						damaged.setNoDamageTicks(4);
						String killmsg = "You killed " + damaged.getDisplayName();
						damager.sendMessage(killmsg);
						String diedmsg = "You were killed by " + damager.getDisplayName();
						damaged.sendMessage(diedmsg);
						Arguments lastkillerEntry = plugin.database1.getArguments(damager.getDisplayName());	
						if(lastkillerEntry != null){
							String kkills = lastkillerEntry.getValue("K");
							String kdeaths = lastkillerEntry.getValue("D");
							String kcoins = lastkillerEntry.getValue("PVPCOIN");
							int kk = Integer.parseInt(kkills)+1;
							int kd = Integer.parseInt(kdeaths);
							int kc = Integer.parseInt(kcoins)+1;
							Arguments kentry = new Arguments(damager.getDisplayName());
							kentry.setValue("K",String.valueOf(kk));
							kentry.setValue("D",String.valueOf(kd));
							kentry.setValue("PVPCOIN",String.valueOf(kc));
							plugin.database1.addIndex(kentry.getKey(), kentry);
						}
						else {
							Arguments kentry = new Arguments(damager.getDisplayName());
							kentry.setValue("K",String.valueOf(1));
							kentry.setValue("D",String.valueOf(0));
							kentry.setValue("PVPCOIN",String.valueOf(1));
							plugin.database1.addIndex(kentry.getKey(), kentry);
						}
						Arguments lastvictimEntry = plugin.database1.getArguments(damaged.getDisplayName());	
						if(lastvictimEntry != null){
							String vkills = lastvictimEntry.getValue("K");
							String vdeaths = lastvictimEntry.getValue("D");
							String kcoins = lastvictimEntry.getValue("PVPCOIN");
							int vk = Integer.parseInt(vkills);
							int vd = Integer.parseInt(vdeaths)+1;
							int vc = Integer.parseInt(kcoins);
							Arguments ventry = new Arguments(damaged.getDisplayName());
							ventry.setValue("K",String.valueOf(vk));
							ventry.setValue("D",String.valueOf(vd));
							ventry.setValue("PVPCOIN",String.valueOf(vc));
							plugin.database1.addIndex(ventry.getKey(), ventry);
						}
						else {
							Arguments ventry = new Arguments(damaged.getDisplayName());
							ventry.setValue("K",String.valueOf(0));
							ventry.setValue("D",String.valueOf(1));
							ventry.setValue("PVPCOIN",String.valueOf(0));
							plugin.database1.addIndex(ventry.getKey(), ventry);
						}
						plugin.database1.update();
					}
				}
	    	}
	    }
	}
		
}

