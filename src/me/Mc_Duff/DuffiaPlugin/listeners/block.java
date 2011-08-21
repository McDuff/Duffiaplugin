package me.Mc_Duff.DuffiaPlugin.listeners;

import me.Mc_Duff.DuffiaPlugin.DuffiaPlugin;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class block extends BlockListener {
	DuffiaPlugin plugin;
	
	public block(DuffiaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockPlaced();
		
		if(player != null && block != null){
			String placemessage1 = player.getName() + " placed " + block.getTypeId(); 
			String placemessage2 = "@ X:" + block.getX() + ",Y:" + block.getY()+ ",Z:" + block.getZ();
			
			if(event.isCancelled()!= true){
				player.sendMessage(placemessage1);
				player.sendMessage(placemessage2);
			}
		}
	}
		
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
			
		if(player != null && block != null){
			String breakmessage1 = player.getName() + " broke " + block.getTypeId();
			String breakmessage2 = "@ X:" + block.getX() + ",Y:" + block.getY()+ ",Z:" + block.getZ();
					
			player.sendMessage(breakmessage1);
			player.sendMessage(breakmessage2);
		}
	}
}
