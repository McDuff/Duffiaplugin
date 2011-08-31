package com.Mc_Duff.DuffiaPlugin.listeners;

import com.Mc_Duff.DuffiaPlugin.DuffiaPlugin;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class block extends BlockListener {
	DuffiaPlugin plugin;
	
	public block(DuffiaPlugin plugin) {
		this.plugin = plugin;
	}
	//captures Block_Place
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockPlaced();
		
		if(player != null && block != null){
			String placemessage1 = player.getName() + " placed " + block.getTypeId(); 
			String placemessage2 = "@ X:" + block.getX() + ",Y:" + block.getY()+ ",Z:" + block.getZ();
			//was trying to check if the player couldn't place the block i.e spawn or protection
			//this failed needs looking at!!!!!!!!
			if(event.isCancelled()!= true){
				player.sendMessage(placemessage1);
				player.sendMessage(placemessage2);
			}
		}
	}
	//captures Block_Break event
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
	//captures block on fire event
	public void onBlockIgnite(BlockIgniteEvent event) {
    	
        if (event.getCause() != IgniteCause.FLINT_AND_STEEL) {
        	
            event.setCancelled(true);
            
        }
    }
    
	//captures blocks burning event
    public void onBlockBurn(BlockBurnEvent event){
    		
    	event.setCancelled(true);
    	
    }
}
