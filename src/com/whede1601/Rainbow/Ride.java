package com.whede1601.Rainbow;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Ride implements CommandExecutor {
	@SuppressWarnings("unused")
	private final Rainbow plugin;
	  public static ConcurrentHashMap<String, Boolean> dictAllow = new ConcurrentHashMap();
 
	public Ride(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
	}

	public boolean CanRide(String pName)
	  {
	    Boolean res = (Boolean)dictAllow.get(pName);
	    if (res == null) return false;
	    return res.booleanValue();
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
		    p.sendMessage(ChatColor.RED + "Usage: /ride " + ChatColor.YELLOW + "Name" + ChatColor.WHITE + " - Ride Something!");
		    p.sendMessage(ChatColor.RED + "Usage: /ride " + ChatColor.AQUA + "toggle" + ChatColor.WHITE + " - Enable riders");
		      return false;
	    }
	    if (args.length >= 2)
	    {
	      p.sendMessage(ChatColor.RED + "Usage: /ride " + ChatColor.YELLOW + "Name" + ChatColor.WHITE + " - Ride Something!");
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
	    Entity tgtEnt = GetClosestEntity(p, 5.0D, tgtName);
	    if (tgtEnt == null)
	    {
	      p.sendMessage(ChatColor.RED + "No nearby target found named: " + ChatColor.YELLOW + tgtName);
	      return false;
	    }
	    tgtName = tgtEnt.getName();

	    if ((tgtEnt instanceof Player))
	    {
	      if (CanRide(tgtName) == false)
	      {
	        p.sendMessage(ChatColor.RED + "That target has not enabled riding.");
	        return false;
	      }
	      tgtEnt.setPassenger(p);
	    }
	    return false;
	}
		return false;
}

	private Entity GetClosestEntity(Player p, double checkDist, String tgtName) {
		    double bestDist = 100000.0D;
		    String tgtNameLower = tgtName.toLowerCase();
		    Entity bestEnt = null;
			List<Entity> allEnt = p.getNearbyEntities(bestDist, bestDist, bestDist);
			for (Entity ent : allEnt)
			{
				double dx = ent.getLocation().getBlockX() - p.getLocation().getBlockX();
				double dy = ent.getLocation().getBlockY() - p.getLocation().getBlockY();
				double dz = ent.getLocation().getBlockZ() - p.getLocation().getBlockZ();
		        double dist2 = Math.sqrt(dx * dx + dy * dy + dz * dz);
		        if (dist2 > checkDist)
		              continue;
		        boolean matches = false;
		        String entClassName = ent.getClass().getSimpleName();
		        
		        if (tgtName.equals("*")) matches = true;
		        if ((!matches) && (entClassName.toLowerCase().contains(tgtNameLower))) matches = true;
		        if ((!matches) && (ChatColor.stripColor(ent.getName()).toLowerCase().contains(tgtNameLower))) matches = true;
		        if ((!matches) || 
		          (dist2 >= bestDist))
		          continue;
		        bestDist = dist2;
		        bestEnt = ent;

			}
			return bestEnt;
		}
}