package com.serverinfo.listener;

import java.util.Map;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.serverinfo.plugins.LoginPlugin;

public class ActionListener implements Listener {
	private Map<String,Object> arrowPlayer = LoginPlugin.arrowPlayer;
	
	@EventHandler
	public void OnRightClick(PlayerInteractEvent event){
		Action action = event.getAction();	
		Player player = event.getPlayer();		
		String playerName = player.getName();
		if(action == Action.LEFT_CLICK_AIR){
			if(arrowPlayer.get(playerName) != null){
				Arrow arrow = player.shootArrow();
				arrow.setCritical(true);
				arrow.setFallDistance(100.0f);
			}
		}		
	}
}
