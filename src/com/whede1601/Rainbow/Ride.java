package com.whede1601.Rainbow;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
public class Ride implements CommandExecutor {
	@SuppressWarnings("unused")
	private final Rainbow plugin;
	  public static ConcurrentHashMap<String, Boolean> dictAllow = new ConcurrentHashMap();
 
	public Ride(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
	}

	public static boolean CanRide(String pName)
	  {
	    Boolean res = (Boolean)dictAllow.get(pName);
	    if (res == null) return false;
	    return res.booleanValue();
	  }
	private static Player getVehicle(Player player) {
			if (player.getVehicle() instanceof Player)
			{
				return (Player) player.getVehicle();
		    }
		    return null;
		  }

	private static Player getRootVehicle(Player vehicle) {
		    while (getVehicle(vehicle) != null) {
		      vehicle = getVehicle(vehicle);
		    }
		    return vehicle;
		  }

	  private static Player getLastPassenger(Player vehicle) {
		    while ((vehicle.getPassenger() != null) && ((vehicle.getPassenger() instanceof Player))) {
		      vehicle = (Player)vehicle.getPassenger();
		    }
		    return vehicle;
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
	    if (args.length == 0)
	    {
		    p.sendMessage(ChatColor.RED + "Usage: /ride " + ChatColor.AQUA + "toggle" + ChatColor.WHITE + " - Enable riders");
		      return false;
	    }
	    if (args.length >= 2)
	    {
	      p.sendMessage(ChatColor.RED + "Usage: /ride " + ChatColor.AQUA + "toggle" + ChatColor.WHITE + " - Enable riders");
	      return false;
	    }
	    if (args.length == 1)
	    {
		    String tgtName = args[0];
	    if (tgtName.equalsIgnoreCase("toggle"))
	    {
	      Boolean res = (Boolean)dictAllow.get(p.getName());
	      if (res == null) res = Boolean.valueOf(false);
	      res = Boolean.valueOf(!res.booleanValue());
	      dictAllow.put(p.getName(), res);
	      if (res.booleanValue())
	      {
	        p.sendMessage(ChatColor.AQUA + "You are now " + ChatColor.GREEN + " ALLOWING " + ChatColor.AQUA + " riders.");
	        p.sendMessage(ChatColor.GOLD + "- Use [/throw] to eject a rider");
	        p.sendMessage(ChatColor.GOLD + "- Use [/ride toggle] to disable.");
	      }
	      else
	      {
	        p.sendMessage(ChatColor.AQUA + "You are now " + ChatColor.RED + " NOT ALLOWING " + ChatColor.AQUA + " riders.");
	      }
	      return false;
	    }
	    return false;
	}
		return false;
	}
	public static void run(Player player, PlayerInteractEntityEvent e)
	{
		System.out.println(player);
		String p = player.getName();
		if (!CanRide(e.getRightClicked().getName()))
		{
//			player.sendMessage(ChatColor.RED + "You can not ride that player.");
			return;
		}
			Player vehicle = getVehicle(player);
			System.out.println(vehicle);
			if (vehicle != null)
			{
				vehicle = (Player)e.getRightClicked();
				System.out.println(vehicle);
				getLastPassenger(player).setPassenger(vehicle);
			}
			else
			{
				player.setPassenger(e.getRightClicked());
			}
	}
}