package com.serverinfo.listener;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.serverinfo.util.Utils;



public class DropListner implements Listener {
	
	private final double extre = 0.02;
	
	private final double top = 0.10;
	
	private final double meduim = 0.20;
	
	private final double normal = 0.30;
	
	//private final double daimondRate = 0.20;
	
	//private final double daimondRate = 0.20;
	
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		Entity entity = event.getEntity();
		if(entity instanceof Zombie ){
			
			if(Utils.Random(extre)){
				event.getDrops().add(new ItemStack(Material.DIAMOND_HELMET));
			}
			if(Utils.Random(extre)){
				event.getDrops().add(new ItemStack(Material.DIAMOND_SWORD));
			}
			if(Utils.Random(extre)){
				event.getDrops().add(new ItemStack(Material.DIAMOND_CHESTPLATE));
			}
			if(Utils.Random(extre)){
				event.getDrops().add(new ItemStack(Material.DIAMOND_LEGGINGS));
			}
			if(Utils.Random(extre)){
				event.getDrops().add(new ItemStack(Material.DIAMOND_BOOTS));
			}
			if(Utils.Random(top)){
				event.getDrops().add(new ItemStack(Material.DIAMOND));
			}
			if(Utils.Random(meduim)){
				event.getDrops().add(new ItemStack(Material.GOLD_INGOT));
			}
			if(Utils.Random(normal)){
				event.getDrops().add(new ItemStack(Material.IRON_INGOT));
			}			
		}
		
		if(entity instanceof Spider){
			
			if(Utils.Random(extre)){
				event.getDrops().add(new ItemStack(Material.DIAMOND_CHESTPLATE));
			}
			if(Utils.Random(top)){
				event.getDrops().add(new ItemStack(Material.DIAMOND));
			}
			if(Utils.Random(meduim)){
				event.getDrops().add(new ItemStack(Material.GOLD_INGOT));
			}
			if(Utils.Random(normal)){
				event.getDrops().add(new ItemStack(Material.IRON_INGOT));
			}	
		}
		
		if(entity instanceof Skeleton){
			if(Utils.Random(extre)){
				event.getDrops().add(new ItemStack(Material.DIAMOND_LEGGINGS));
			}
			if(Utils.Random(top)){
				event.getDrops().add(new ItemStack(Material.DIAMOND));
			}
			if(Utils.Random(meduim)){
				event.getDrops().add(new ItemStack(Material.GOLD_INGOT));
			}
			if(Utils.Random(normal)){
				event.getDrops().add(new ItemStack(Material.IRON_INGOT));
			}	
		}
	}

}
