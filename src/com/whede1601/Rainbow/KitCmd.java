package com.whede1601.Rainbow;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCmd implements CommandExecutor {

	private Rainbow plugin;

	public KitCmd(Rainbow plugin) {
		this.plugin = plugin;	
		}

	public boolean IsKitAdmin(Player plr)
	  {
	    if (plr == null) return true;
	    return plr.hasPermission("kits.kitadmin");
	  }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* TODO Perms
		Player player = (Player) sender;
	    if ((args.length == 2) && (args[0].equalsIgnoreCase("info")))
	      {
	        KitMgr.ShowKitDetails(player, args[1]);
	      }

	      if ((args.length == 2) && (args[0].equalsIgnoreCase("delete")))
	      {
	        KitMgr.HandleDeleteKit(player, args[1]);
	      }
	      if ((args.length == 2) && ((args[0].equalsIgnoreCase("setitems")) || (args[0].equalsIgnoreCase("create"))))
	      {
	        if (sender == null)
	        {
	        	sender.sendMessage(ChatColor.RED + "Not from console!");
	        }
	        KitMgr.SetKitItems(player, args[1]);
	      }
	      if ((args.length == 3) && (args[0].equalsIgnoreCase("rename")))
	      {
	        KitMgr.RenameKit(player, args[1], args[2]);
	      }
	      if ((args.length >= 2) && ((args[0].equalsIgnoreCase("setonce")) || (args[0].equalsIgnoreCase("once"))))
	      {
	        boolean flag = true;
	        if (args.length >= 3)
	        {
	          if (args[2].equalsIgnoreCase("true")) { flag = true;
	          } else if (args[2].equalsIgnoreCase("false")) { flag = false;
	          } else
	          {
	        	  sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.LIGHT_PURPLE + "/kit SetOnce " + ChatColor.AQUA + "KitName" + ChatColor.WHITE + " True|False");
	          }
	        }

	        KitMgr.SetKitOnce(player, args[1], flag);
	      }
	      if ((args.length == 3) && ((args[0].equalsIgnoreCase("setdelay")) || (args[0].equalsIgnoreCase("delay"))))
	      {
	        String parm = args[2];
	        int multiplier = 1;
	        if (parm.endsWith("m"))
	        {
	          multiplier = 60;
	          parm = parm.substring(0, parm.length() - 1);
	        }
	        if (parm.endsWith("h"))
	        {
	          multiplier = 3600;
	          parm = parm.substring(0, parm.length() - 1);
	        }

	        int secs = KitMgr.GetIntegerArgument(" " + parm, Integer.valueOf(0)).intValue();
	        secs *= multiplier;
	        KitMgr.SetKitDelaySeconds(sender, args[1], secs);
	      }
	    if (args.length >= 1)
	    {
	      String kitName = args[0];
	      KitInfo kit = KitMgr.GetKitByName(kitName);
	      if (kit != null)
	      {
	        Player tgtPlayer = (Player) sender;
	        if (sender.hasPermission("") && (args.length == 2))
	        {
	          tgtPlayer = Bukkit.getServer().getPlayer(args[1]);
	          if (tgtPlayer == null)
	          {
	            player.sendMessage(ChatColor.RED + "No online player named: " + ChatColor.YELLOW + args[1]);
	          }
	        }
	        if (tgtPlayer == null)
	        {
	          player.sendMessage(ChatColor.RED + "CONSOLE can't receive kits!");
	        }
	        if (tgtPlayer != sender)
	        {
	          KitMgr.GiveKit(tgtPlayer, kitName, true);
	        }

	        if (KitMgr.CanSeeKit(tgtPlayer, kitName))
	        {
	          long secsLeft = KitMgr.kitDelayLeftSeconds(tgtPlayer, kitName);
	          if (secsLeft <= 0L)
	          {
	            KitMgr.GiveKit(tgtPlayer, kitName);
	          }
	          sender.sendMessage(ChatColor.RED + "Too Soon! " + ChatColor.AQUA + "Wait Left: " + ChatColor.WHITE + KitMgr.TimeDeltaString_JustMinutesSecs(secsLeft * 1000L));
	        }
	      }
	    }
//	    ShowPlayerUsage(sender);
*/		return false;
	}
}
