package com.whede1601.Rainbow;

import java.util.ArrayList;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Purge implements CommandExecutor {

	private Rainbow plugin;
	public Purge(Rainbow plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player))
	    {
	      System.out.println("Not from console!");
	      return false;
	    }
		Player p = (Player)sender;
		if (args.length ==0 )
		{
			p.sendMessage("error: Not enough args");
			return false;
		}
		if (args[0].equalsIgnoreCase("start") && (p != null))
		{
			PurgeUtil.startPurge();
		}
		if (args[0].equalsIgnoreCase("stop") && (p != null))
		{
			PurgeUtil.stopPurge();
		}
	    if (args[0].equalsIgnoreCase("join") && (p != null))
	    {
	    	PurgeUtil.addUser(p);
	    	return true;
	    }
	    if (args[0].equalsIgnoreCase("quit") && (p != null))
	    {
	    	PurgeUtil.removeUser(p);
	    	return true;
	    }
		return false;
	}
	}