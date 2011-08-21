package me.Mc_Duff.DuffiaPlugin.duffiaevents;

import me.Mc_Duff.DuffiaPlugin.DuffiaPlugin;
import me.Mc_Duff.mini.Arguments;
import org.bukkit.entity.Player;

public class pvptracker {
	DuffiaPlugin plugin;
	
	public pvptracker() {
	}
	
	public void pvprecorder(Player akiller,Player avictim) {
		
		Player killer = akiller;
		Player victim = avictim;
		
		if(killer != null && victim != null){
			Arguments lastkillerEntry = plugin.database.getArguments(killer.getDisplayName());	
			if(lastkillerEntry != null){
				String kkills = lastkillerEntry.getValue("K");
				String kdeaths = lastkillerEntry.getValue("D");
				int kk = Integer.parseInt(kkills)+1;
				int kd = Integer.parseInt(kdeaths);
				Arguments kentry = new Arguments(killer.getDisplayName());
				kentry.setValue("K",String.valueOf(kk));
				kentry.setValue("D",String.valueOf(kd));
				plugin.database1.addIndex(kentry.getKey(), kentry);
			}
			else {
				Arguments kentry = new Arguments(killer.getDisplayName());
				kentry.setValue("K",String.valueOf(1));
				kentry.setValue("D",String.valueOf(0));
				plugin.database1.addIndex(kentry.getKey(), kentry);
			}
			Arguments lastvictimEntry = plugin.database.getArguments(victim.getDisplayName());	
				if(lastvictimEntry != null){
					String vkills = lastvictimEntry.getValue("K");
					String vdeaths = lastvictimEntry.getValue("D");
					int vk = Integer.parseInt(vkills);
					int vd = Integer.parseInt(vdeaths)+1;
					Arguments ventry = new Arguments(victim.getDisplayName());
					ventry.setValue("K",String.valueOf(vk));
					ventry.setValue("D",String.valueOf(vd));
					plugin.database1.addIndex(ventry.getKey(), ventry);
				}
				else {
					Arguments ventry = new Arguments(victim.getDisplayName());
					ventry.setValue("K",String.valueOf(0));
					ventry.setValue("D",String.valueOf(1));
					plugin.database1.addIndex(ventry.getKey(), ventry);
				}
				plugin.database1.update();		
				
			
		}
	}
}
		
