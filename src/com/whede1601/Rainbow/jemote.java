package com.whede1601.Rainbow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class jemote implements CommandExecutor {
	private final Rainbow plugin;
	private final ConcurrentHashMap<String, _EmoteEntry> emotes = EmoteUtil.emotes.emotes;

	  public jemote(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
	}
 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0)
			sender.sendMessage("Not enough arguments");
//		if (!sender.hasPermission("rainbow.jemote") || (!(sender.hasPermission("rainbow.jemote.*"))))
//		{	
//			sender.sendMessage(ChatColor.RED + "Not enough permission");
//			return false;
//		}
	    if ((args.length >= 1) && (args[0].equalsIgnoreCase("list")))
	    {
	      String strEmotes = EmoteUtil.GetCommaList(sender);
	      sender.sendMessage(strEmotes);
	      return true;
	    }
	    if ((args.length >= 2) && (args[0].equalsIgnoreCase("info")))
	      {
	        String emote = args[1].toLowerCase();
	        EmoteUtil.ShowEmoteDetails(sender, emote);
	        return true;
	    }
	    if (sender.hasPermission("rainbow.jemote.edit"))
	    {
	          if ((args.length == 2) && (args[0].equalsIgnoreCase("delete")))
	          {
	            String emote = args[1].toLowerCase();
	            if (!emotes.containsKey(emote))
	            {
	              sender.sendMessage(ChatColor.RED + "Emote does not exist: " + ChatColor.YELLOW + emote);
	              return true;
	            }
	            emotes.remove(emote);
	            sender.sendMessage(ChatColor.GREEN + "Removed Emote: " + ChatColor.YELLOW + emote);
	            EmoteUtil.SaveEmotes();
	            return true;
	          }

	      if ((args.length >= 4) && (args[0].equalsIgnoreCase("add")))
	      {
	        String emote = args[1].toLowerCase();
	        if (emote.equalsIgnoreCase("jemote"))
	        {
	          sender.sendMessage(ChatColor.RED + "No! You can't make an emote named 'jemote'.");
	          return true;
	        }
	        String tgt = args[2].toLowerCase();
	        if ((!tgt.equalsIgnoreCase("default")) && 
	  	          (!tgt.equalsIgnoreCase("self")) && 
	  	          (!tgt.equalsIgnoreCase("other")))
	  	        {
	  	          sender.sendMessage(ChatColor.RED + "Unknown target type: " + ChatColor.YELLOW + tgt);
	  	          return false;
	  	        }
	        _EmoteEntry entry = (_EmoteEntry)emotes.get(emote);
	        long msNow = System.currentTimeMillis();
	        if (entry == null)
	        {
	          entry = new _EmoteEntry(emote);
	          entry.msCreated = msNow;
	          entry.createdBy = sender.getName();
	        }
	        entry.msUpdated = msNow;
	        entry.updatedBy = sender.getName();

	        if ((!tgt.equalsIgnoreCase("default")) && 
	          (!tgt.equalsIgnoreCase("self")) && 
	          (!tgt.equalsIgnoreCase("other")))
	        {
	          sender.sendMessage(ChatColor.RED + "Unknown target type: " + ChatColor.YELLOW + tgt);
	          return false;
	        }
	        String msg = RainbowUtil.FullTranslate(RainbowUtil.ConcatArgs(args, 3));

	        entry.msg.put(tgt, msg);
	        emotes.put(emote, entry);
	        EmoteUtil.SaveEmotes();
	        System.out.println(emote);
	        EmoteUtil.ShowEmoteDetails(sender, emote);
	        sender.sendMessage(ChatColor.GREEN + "Set " + ChatColor.YELLOW + emote + ChatColor.AQUA + " " + tgt + ChatColor.GREEN + ": " + msg);
	        return true;
	      }
	      if ((args.length >= 2) && (args[0].equalsIgnoreCase("add")))
	      {
	        String emote = args[1].toLowerCase();

	        if (emote.equalsIgnoreCase("jemote"))
	        {
	          sender.sendMessage(ChatColor.RED + "No! You can't make an emote named 'jemote'.");
	          return true;
	        }
	        
	        if (!(emote.isEmpty()))
	        {
	        _EmoteEntry entry = (_EmoteEntry)emotes.get(emote);
	        long msNow = System.currentTimeMillis();
	        if (entry == null)
	        {
	          entry = new _EmoteEntry(emote);
	          entry.msCreated = msNow;
	          entry.createdBy = sender.getName();
	        }
	        entry.msUpdated = msNow;
	        entry.updatedBy = sender.getName();
	        emotes.put(emote, entry);
	        EmoteUtil.SaveEmotes();
//	        EmoteUtil.ShowEmoteDetails(sender, emote);
	        return true;
	        }
	        	return false;
	      }
	    }
	    	sender.sendMessage(ChatColor.RED + "Not enough permissions");
	    EmoteUtil.ShowUsage(sender);
		return false;
}
}