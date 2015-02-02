package com.whede1601.Rainbow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

	public class Diw implements CommandExecutor {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static ConcurrentHashMap<String, Boolean> deathallow = new ConcurrentHashMap();


		@SuppressWarnings("unused")
		private static final String is = null;
		@SuppressWarnings("unused")
		private final Rainbow plugin;
		 
		public Diw(Rainbow plugin) {
			this.plugin = plugin; // Store the plugin in situations where youdd need it.
		}
	
		
	@SuppressWarnings({ "unused", "unchecked" })
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player))
	    {
	      System.out.println("Not from console!");
	      return false;
	    }
		Player p = (Player)sender;
		if (args.length ==0 )
		{
			p.sendMessage("error: Not enough");
			return false;
		}
	    if (args[0].equalsIgnoreCase("deathtoggle") && (p != null) && p.hasPermission("rainbow.keepinv"))
	    {   
	      Boolean res = (Boolean)deathallow.get(p.getName());
	      if (res == null) res = Boolean.valueOf(true);
	      res = Boolean.valueOf(!res.booleanValue());
	      deathallow.put(p.getName(), res);
	      if (res.booleanValue())
	      {
	        p.sendMessage(ChatColor.AQUA + "You are now " + ChatColor.GREEN + " ALLOWING " + ChatColor.AQUA + " Keep inv on death.");
	      }
	      else
	      {
	        p.sendMessage(ChatColor.AQUA + "You are now " + ChatColor.RED + " NOT ALLOWING " + ChatColor.AQUA + " Keep inv on death.");
	      }
	      return false;
	    }
		if (args[0].equalsIgnoreCase("ThePurge") && (p != null) && p.hasPermission("rainbow.diw"))
		{
			System.out.println("the purge enabled");
			p.sendMessage("The purge enabled");
			ThePurge.Scoreboard();
		}
	    if ((args[0].equalsIgnoreCase("addlore")) && (p != null) && p.hasPermission("rainbow.diw"))
	    {
		      ItemStack is = p.getItemInHand();
		      if ((is == null))
		      {
		        p.sendMessage(ChatColor.RED + "Nothing in hand");
		        return false;
		      }
	      if (args.length == 1)
	      {
	        p.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GOLD + "/diw AddLore " + ChatColor.LIGHT_PURPLE + "Your Lore Text");
	        return false;
	      }
	      ItemMeta ism = is.getItemMeta();
	      String newLore = RainbowUtil.ConcatArgs(args, 1);
	      java.util.List<String> lore = ism.getLore();
	      if (lore == null) lore = new ArrayList();
	      lore.add(newLore);
	      ism.setLore(lore);
	      is.setItemMeta(ism);
	      p.sendMessage(ChatColor.GREEN + "Lore Added: " + ChatColor.LIGHT_PURPLE + newLore);
	      return true;
	    }
	    if ((args[0].equalsIgnoreCase("clearname")) && (p != null) && p.hasPermission("rainbow.diw"))
	    {
	      ItemStack is = p.getItemInHand();
	      if ((is == null))
	      {
	        p.sendMessage(ChatColor.RED + "Nothing in hand");
	        return false;
	      }
	      ItemMeta ism = is.getItemMeta();
	      ism.setDisplayName(null);
	      is.setItemMeta(ism);
	      p.sendMessage(ChatColor.GREEN + "Item name reset.");
	      return true;
	    }
	    if ((args[0].equalsIgnoreCase("clearlore")) && (p != null) && p.hasPermission("rainbow.diw"))
	    {
	      ItemStack is = p.getItemInHand();
	      if ((is == null))
	      {
	        p.sendMessage(ChatColor.RED + "Nothing in hand");
	        return false;
	      }
	      ItemMeta ism = is.getItemMeta();
	      if ((is == null))
	      ism.setLore(null);
	      is.setItemMeta(ism);
	      p.sendMessage(ChatColor.GREEN + "Lore Cleared!");
	      return true;
	    }
	    if ((args[0].equalsIgnoreCase("addname")) && (p != null) && p.hasPermission("rainbow.diw"))
	    {
	      if (args.length == 1)
	      {
	        p.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GOLD + "/diw AddName " + ChatColor.LIGHT_PURPLE + "Your Name Text");
	        return false;
	      }
	      ItemStack is = p.getItemInHand();
	      if ((is == null))
	      {
	        p.sendMessage(ChatColor.RED + "Nothing in hand");
	        return false;
	      }
	      ItemMeta ism = is.getItemMeta();

	      String newName = RainbowUtil.ConcatArgs(args, 1);
	      String colorName = RainbowUtil.TranslateColorString(newName, true);
	      ism.setDisplayName(colorName);
	      is.setItemMeta(ism);
	      p.sendMessage(ChatColor.GREEN + "Name Added: " + ChatColor.LIGHT_PURPLE + colorName);
	      return true;
	    }
	    if ((args[0].equalsIgnoreCase("xptrain")) && (p != null) && p.hasPermission("rainbow.diw"))
	    {
	    	if (args[1].isEmpty())
	    	{
		        p.sendMessage(ChatColor.RED + "No levels imputed");
		        return false;
	    	}
	    	String givelevels = args[1];
	    	int givenlevels = Integer.parseInt(givelevels);
	    	Player[] players = Bukkit.getOnlinePlayers();
	    	for (Player player : players)
	    	{
	    		int levels = player.getLevel();
	    		player.setLevel(levels + givenlevels);
	    	}
	    	return true;
	    }
	    sender.sendMessage(ChatColor.RED + "Not enough permissions.");
		return false;
	}
	public static boolean checkKeepInv(Player player) {
	    Boolean res = (Boolean)deathallow.get(player.getName());
	    if (res == null)
	    {
	    	if (player.hasPermission("rainbow.keepinv"))
	    	{
	    		return true;
	    	}
	    } else 
	    {
	    	if (res == true && player.hasPermission("rainbow.keepinv"))
	    	{
	    		return true;
	    	}
	    	return false;
	    }
	    return false;
		
	}
}
