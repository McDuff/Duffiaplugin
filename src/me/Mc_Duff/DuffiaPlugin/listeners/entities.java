package me.Mc_Duff.DuffiaPlugin.listeners;

import me.Mc_Duff.DuffiaPlugin.DuffiaPlugin;
import me.Mc_Duff.DuffiaPlugin.duffiaevents.pvptracker;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

public class entities extends  EntityListener{
	DuffiaPlugin plugin;
	pvptracker tracker;
	
	public entities(DuffiaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void  onEntityDamage(EntityDamageEvent EntityDamaged)
	{
	    if (EntityDamaged.getCause() == (DamageCause.ENTITY_ATTACK)){
	    	EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)EntityDamaged;
	    	
	    	//Player damager1 = (Player)e.getDamager();
	    	//Entity damaged1 = (Entity)e.getEntity();
	    	//damager1.sendMessage("You hit " + damaged1.getEntityId());  
	    	
	    	//Check to see if the damager and damaged are players
	    	if (e.getDamager() instanceof Player && e.getEntity() instanceof Player){
				Player damager = (Player)e.getDamager();
				Player damaged = (Player)e.getEntity();
								
				tracker.pvprecorder(damager, damaged);
				
				if (damaged.isDead()) {
					damager.sendMessage("You killed " + damaged.getDisplayName());
					damaged.sendMessage(damager.getDisplayName() + " killed you!");
				}   	
		    	else {
		    		damager.sendMessage("You hit " + damaged.getDisplayName());
					damaged.sendMessage(damager.getDisplayName() + " hit you!");
		    	}
	    	}
	    }
	}
	

	public void onEntityDeath(EntityDeathEvent EntityDead){
		if (EntityDead instanceof Player){
			Player deadperson = (Player) EntityDead.getEntity();
			EntityDamageEvent killer = deadperson.getLastDamageCause();
			if (killer.getEntity() instanceof Player) {
				Player Murderer = (Player)killer.getEntity();
				Murderer.sendMessage("You killed " + deadperson.getDisplayName());
				deadperson.sendMessage(Murderer.getDisplayName() + " killed you!");
			}
		}
	}
}

