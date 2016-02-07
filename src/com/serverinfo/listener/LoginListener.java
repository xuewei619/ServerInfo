package com.serverinfo.listener;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.serverinfo.plugins.LoginPlugin;

public class LoginListener implements Listener {
	
	private Map<String,Object> loginPlayer = LoginPlugin.loginPlayer;	

	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		Entity entity = event.getEntity();

		if (entity instanceof TNTPrimed) {
			entity.getWorld().createExplosion(entity.getLocation(), 0);
		}
	}

	@EventHandler
	public void onLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		System.out.println(player.getWalkSpeed());
		player.setWalkSpeed(0.0f);
		player.sendRawMessage(ChatColor.RED + "µ«¬º«Î ‰»Î/login √‹¬Î");
		player.sendRawMessage(ChatColor.RED + "◊¢≤·«Î ‰»Î/reg √‹¬Î");
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {		

		Object isLogin = loginPlayer.get(event.getPlayer().getName());
		if(isLogin == null){			
			event.getPlayer().teleport(event.getFrom());
		}			
	}

	@EventHandler
	public void onEntityTarget(EntityTargetLivingEntityEvent event) {
		Player player = (Player) event.getTarget();	
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		System.out.println(loginPlayer);
		Player player = event.getPlayer();		
		if(loginPlayer!= null  && !loginPlayer.isEmpty()){
			loginPlayer.remove(player.getName());
		}		
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		Entity entity = event.getEntity();		
		//double damage = event.getDamage();
		if(event.getDamager() instanceof Arrow && entity instanceof LivingEntity){
			LivingEntity lvEntity = (LivingEntity)entity;
			lvEntity.setFireTicks(1000);
		}
	}	
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
    {
        Entity player = event.getEntity();
 
        if(player instanceof Player)
        {
            if(event.getCause().equals(DamageCause.FALL))
            {
                event.setCancelled(true);
            }
           // else event.setCancelled(false);
        }
    }
	
}
