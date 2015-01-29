package com.whede1601.Rainbow;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.Files;

public class EmoteUtil {
	
      public static _emotes emotes = new _emotes();
	  public static SimpleDateFormat shortDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//	  private static String OldFilename = "Emotes.dat.vSnapshot";
//	  private static  String Filename = "Emotes.dat";
//	  private static  String Directory = "plugin" + File.separator + "Rainbow" + File.separator + Filename;
	  
	  public static void LoadEmotes()
	  {
	    try
	    {
		  long msStart = System.currentTimeMillis();
	      File file = new File("plugins" + File.separatorChar + "Rainbow", "Emotes.dat");
	      FileInputStream f = new FileInputStream(file);	      
	      ObjectInputStream s = new ObjectInputStream(new BufferedInputStream(f));
	      emotes = (_emotes)s.readObject();
	      s.close();

	      long msEnd = System.currentTimeMillis();
	      String msg = String.format("%-20s: " + ChatColor.WHITE + "%5d emotes.   Took %3d ms", new Object[] { "Emote DB Loaded", Integer.valueOf(emotes.emotes.size()), Long.valueOf(msEnd - msStart) });
	      System.out.println(msg);
	      
	      if (emotes.emotes.containsKey("jemote"))
	      {
	        emotes.emotes.remove("jemote");
//	        _JoeUtils.ConsoleMsg(ChatColor.LIGHT_PURPLE + "--- Removed emote named 'jemote' for safety.");
	      }

	    }
	    catch (Throwable exc)
	    {
	      System.out.println("Starting New Emote Database...");
	      emotes = new _emotes();
	      exc.printStackTrace();
	    }
	  }

	  public static void SaveEmotes()
	  {
	    try {
	      File file = new File("plugins" + File.separatorChar + "Rainbow", "Emotes.dat");
	      FileOutputStream f = new FileOutputStream(file);
	      ObjectOutputStream s = new ObjectOutputStream(new BufferedOutputStream(f));
	      System.out.println("Saving");
	      s.writeObject(emotes);
	      s.close();
	    }
	    catch (Throwable exc)
	    {
	      System.out.println("**********************************************");
	      System.out.println("SaveEmotes: " + exc.toString());
	      System.out.println("**********************************************");
	    }
	  }
	  
	private static boolean CanDoEmote(CommandSender cs, String emote) {
	      if (cs.hasPermission("rainbow.jemote." + emote))
	      {
	        if (!cs.hasPermission("rainbow.jemote.*"))
	        {
	          return false;
	        }
	      }
	      return true;
	}
	  public static void ShowEmoteDetails(CommandSender cs, String emote)
	  {
	    emote = emote.toLowerCase();
	    _EmoteEntry entry = (_EmoteEntry)emotes.emotes.get(emote);
	    if (entry == null)
	    {
	      cs.sendMessage(ChatColor.RED + "Emote is not defined: " + ChatColor.YELLOW + emote);
	      return;
	    }
	    cs.sendMessage(ChatColor.GREEN + "---------------------------------------");
	    cs.sendMessage(ChatColor.GOLD + "Emote: " + ChatColor.YELLOW + emote);
	    cs.sendMessage(ChatColor.WHITE + "Created " + ChatColor.AQUA + RainbowUtil.GetDateStringFromLong(entry.msCreated) + ChatColor.WHITE + " by " + ChatColor.YELLOW + entry.createdBy);
	    cs.sendMessage(ChatColor.WHITE + "Last Updated " + ChatColor.AQUA + RainbowUtil.GetDateStringFromLong(entry.msUpdated) + ChatColor.WHITE + " by " + ChatColor.YELLOW + entry.updatedBy);
	    for (String key : entry.msg.keySet())
	    {
	    	cs.sendMessage(ChatColor.AQUA + key + ": " + ChatColor.GREEN + (String)entry.msg.get(key));
	    }
	    cs.sendMessage(ChatColor.GREEN + "---------------------------------------");
	  }

	public static void HandleEmote(Player player, String args[]) {
		String emote = args[0].toLowerCase();
		if (!emotes.emotes.containsKey(emote)) {
			player.sendMessage("Emote does not exist");
			return;
		}
		CanDoEmote(player, emote);
	    _EmoteEntry entry = (_EmoteEntry)emotes.emotes.get(emote);
	    
	    if (args.length > 1)
	    {	
	    	String tgtName = args[1];
	    	Player pTgt = Bukkit.getPlayer(tgtName);
	    	if (tgtName.equalsIgnoreCase(player.getName()))
	    	{
// TODO Sending message.    
	    	}
	        String trailer = String.format((String)entry.msg.get("other"), new Object[] { pTgt.getName() });
	        Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " " + trailer);
	        return;
	    }
	   Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " " + (String)entry.msg.get("default"));
	}

	public static String GetCommaList(CommandSender sender) {
		String strEmotes = GetCommaList();
	    sender.sendMessage(ChatColor.GREEN + "All Emotes: " + ChatColor.YELLOW + strEmotes);
		return null;
	}

	private static String GetCommaList() {
		Set<String> keys = emotes.emotes.keySet();
	    StringBuffer buf = new StringBuffer();
	    for (String str : keys)
	    {
	      if (buf.length() > 0) buf.append(", ");
	      buf.append(str);
	    }
		return buf.toString();
	}	  
	  public static void ShowUsage(CommandSender sender)
	  {
	    sender.sendMessage(ChatColor.AQUA + "----- Emotion Options -----");
	    sender.sendMessage(ChatColor.GOLD + "/jemote list " + ChatColor.WHITE + " -- List all emotes");
	    sender.sendMessage(ChatColor.GOLD + "/jemote mine " + ChatColor.WHITE + " -- List my emotes");

	    if (sender.hasPermission("Rainbow.edit"))
	    {
	      sender.sendMessage(ChatColor.LIGHT_PURPLE + "- Admin Options: "+ ChatColor.WHITE + "Using 'smile' as example...");
	      sender.sendMessage(ChatColor.LIGHT_PURPLE + "/jemote info smile");
	      sender.sendMessage(ChatColor.LIGHT_PURPLE + "/jemote delete smile");
	      sender.sendMessage(ChatColor.LIGHT_PURPLE + "/jemote add smile " + ChatColor.AQUA + "self" + ChatColor.GREEN + " smiles in a mirror.");
	      sender.sendMessage(ChatColor.LIGHT_PURPLE + "/jemote add smile " + ChatColor.AQUA + "other" + ChatColor.GREEN + " smiles at %s happily!");
	    }
	  }
}