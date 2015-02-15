package com.whede1601.Rainbow;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class listener implements Listener {

	private Rainbow plugin;

	public listener(Rainbow rainbow) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
	}
	@EventHandler  
	public void onPlayerJoin(PlayerLoginEvent e)
	{
		String player = e.getPlayer().getName();
		NameUtil.Login(player);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
	    if (!(e.getEntity() instanceof Player))
	    {
	      System.out.println(e.getEntity());
	      return;
	    }
		Player player = e.getEntity();
		boolean check = Diw.checkKeepInv(player);
		if (check == true)
		{
			e.setKeepInventory(true);
			return;
		}
	}
	
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent e)
	{
		String player = e.getPlayer().getName();
		NameUtil.Logout(player);
	}

	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent e)
	{
		if (e.getRightClicked() instanceof Player)                                                                
		{
			System.out.println("test");
			Player player = e.getPlayer();
			System.out.println(player);
			Ride.run(player, e);
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
	    if ((p.getInventory().getItemInHand().getTypeId() == 383) && 
	    	      ((e.getAction() == Action.RIGHT_CLICK_AIR) || 
	    	      (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
	    	      (p.getItemInHand().getAmount() >= 1)) {
	    	int amt = p.getItemInHand().getAmount();
	    	if (amt <= 1)
	        {
	          p.setItemInHand(null);
	        }
	        else
	        {
	          p.getItemInHand().setAmount(amt - 1);
	        }
	    	kindermgr.GivePrize(p);
	    }
	    }
}
