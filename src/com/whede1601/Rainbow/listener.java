package com.whede1601.Rainbow;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class listener implements Listener {

	private Object plugin;

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
}
