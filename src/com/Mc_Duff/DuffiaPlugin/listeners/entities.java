package com.Mc_Duff.DuffiaPlugin.listeners;

import com.Mc_Duff.DuffiaPlugin.DuffiaPlugin;
import com.Mc_Duff.mini.Arguments;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

public class entities extends  EntityListener{
	DuffiaPlugin plugin;
		
	public entities(DuffiaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void  onEntityDamage(EntityDamageEvent EntityDamaged)
	{
		if(EntityDamaged instanceof EntityDamageByEntityEvent){
	    	EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)EntityDamaged;
	    		    	
	    	//Check to see if the damager and damaged are players
	    	if (e.getDamager() instanceof Player && e.getEntity() instanceof Player){
				Player damager = (Player)e.getDamager();
				Player damaged = (Player)e.getEntity();
								
				if(damager != null && damaged != null){
					double health = damaged.getHealth()- e.getDamage();
					if(health<=0){
						damaged.setNoDamageTicks(4);
						String killmsg = "You killed " + damaged.getDisplayName();
						damager.sendMessage(killmsg);
						String diedmsg = "You were killed by " + damager.getDisplayName();
						damaged.sendMessage(diedmsg);
						Arguments lastkillerEntry = plugin.database1.getArguments(damager.getDisplayName());	
						if(lastkillerEntry != null){
							String kkills = lastkillerEntry.getValue("K");
							String kdeaths = lastkillerEntry.getValue("D");
							int kk = Integer.parseInt(kkills)+1;
							int kd = Integer.parseInt(kdeaths);
							Arguments kentry = new Arguments(damager.getDisplayName());
							kentry.setValue("K",String.valueOf(kk));
							kentry.setValue("D",String.valueOf(kd));
							plugin.database1.addIndex(kentry.getKey(), kentry);
						}
						else {
							Arguments kentry = new Arguments(damager.getDisplayName());
							kentry.setValue("K",String.valueOf(1));
							kentry.setValue("D",String.valueOf(0));
							plugin.database1.addIndex(kentry.getKey(), kentry);
						}
						Arguments lastvictimEntry = plugin.database1.getArguments(damaged.getDisplayName());	
						if(lastvictimEntry != null){
							String vkills = lastvictimEntry.getValue("K");
							String vdeaths = lastvictimEntry.getValue("D");
							int vk = Integer.parseInt(vkills);
							int vd = Integer.parseInt(vdeaths)+1;
							Arguments ventry = new Arguments(damaged.getDisplayName());
							ventry.setValue("K",String.valueOf(vk));
							ventry.setValue("D",String.valueOf(vd));
							plugin.database1.addIndex(ventry.getKey(), ventry);
						}
						else {
							Arguments ventry = new Arguments(damaged.getDisplayName());
							ventry.setValue("K",String.valueOf(0));
							ventry.setValue("D",String.valueOf(1));
							plugin.database1.addIndex(ventry.getKey(), ventry);
						}
						plugin.database1.update();
					}
				}
	    	}
	    }
	}
}

