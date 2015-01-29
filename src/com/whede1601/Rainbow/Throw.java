package com.whede1601.Rainbow;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Throw implements CommandExecutor {
	@SuppressWarnings("unused")
	private final Rainbow plugin;
 
	public Throw(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
	}
 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("rainbow.ride")))
		{
			sender.sendMessage(ChatColor.RED + "Not enough permission.");
			return false;
		}
	    if (!(sender instanceof Player))
	    {
	      System.out.println("Not from console!");
	      return false;
	    }
	    Player p = (Player)sender;
	    if (p.getPassenger() == null)
	    {
	      p.sendMessage(ChatColor.RED + "You don't have a rider!");
	      return false;
	    }

	    p.sendMessage(ChatColor.GREEN + "You eject rider: " + ChatColor.WHITE + p.getPassenger().getName());
	    Entity rider = p.getPassenger();
	    rider.leaveVehicle();
	    return true;
	  }

}
