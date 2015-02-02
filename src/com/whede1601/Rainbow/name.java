package com.whede1601.Rainbow;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class name implements CommandExecutor {
	private final Rainbow plugin;
 
	public name(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
	}
 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    Player player = (Player)sender;
        if (!player.hasPermission("rainbow.name")) {
            player.sendMessage(ChatColor.RED + "You don't have permission for that.");
            return true;
        }
        
        if (args.length <= 0)
            {
              player.sendMessage(ChatColor.RED + "Usage: " + ChatColor.AQUA + "/name " + ChatColor.YELLOW + "PartialName");
              return true;
            }
        if (args.length > 1)
        {
            player.sendMessage(ChatColor.RED + "Usage: " + ChatColor.AQUA + "/name " + ChatColor.YELLOW + "PartialName");
            return true;
        }
        if (args.length == 1)
        {
            String piece = args[0].toLowerCase();
            player.sendMessage("\n" + ChatColor.YELLOW + "Finding player names containing " + ChatColor.GOLD + piece);
            player.sendMessage("----------------------------------------------");
            int hits = 0;
            for (String name : NameUtil.DataNew.pdata.keySet())
            {
            	if ((name == null) || 
            			(name.toLowerCase().indexOf(piece) < 0))
            		continue;
            	if (hits < 10) player.sendMessage(ChatColor.GREEN + "Found Match: " + ChatColor.GOLD + name);
            	hits++;
            	if (hits != 10)
            		continue;
               player.sendMessage(ChatColor.YELLOW + "Stopping Output at " + hits + " matches.");
            }
            int nplayers = NameUtil.DataNew.pdata.keySet().size();
            String msg = String.format(ChatColor.AQUA + "Searched %d players. Found %d matches.", new Object[] { Integer.valueOf(nplayers), Integer.valueOf(hits) });
            player.sendMessage(msg);
            player.sendMessage("----------------------------------------------");
            }

        return false;
	}
}
