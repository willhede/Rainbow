package com.whede1601.Rainbow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class kinder implements CommandExecutor {

	private Rainbow plugin;

	public kinder(Rainbow plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length <= 0)
	    {
	      SendUsage(sender);
	      return false;
	    }

	    if (args[0].equalsIgnoreCase("add") && sender.hasPermission("rainbow.kinder"))
	    {
	      if (args.length < 4)
	      {
	        sender.sendMessage(ChatColor.RED + "Usage: /kinder add [name] [chance 0-100] [command]");
	        return false;
	      }
	      String rewardName = args[1].toUpperCase();
	      double prob = 0.0D;
	      try
	      {
	        prob = Double.parseDouble(args[2]);
	      }
	      catch (Exception localException)
	      {
	      }
	      if (prob < 0.0D) prob = 0.0D;
	      if (prob > 100.0D) prob = 100.0D;

	      String tgtName = RainbowUtil.ConcatArgs(args, 3);

	      EggReward2 ew = (EggReward2)kindermgr.rewards.get(rewardName);
	      if (ew == null) ew = new EggReward2();
	      ew.cmd = tgtName;
	      ew.prob = prob;
	      kindermgr.rewards.put(rewardName, ew);
	      kindermgr.SaveRewards();
	      sender.sendMessage(ChatColor.GREEN + "Saved Reward: " + ChatColor.YELLOW + ew.toString());
	      return true;
	    }
	    if (args[0].equalsIgnoreCase("load") && sender.hasPermission("rainbow.kinder"))
	    {
	      kindermgr.LoadRewards();
	      sender.sendMessage(ChatColor.GREEN + "Reloaded rewards");
	      return true;
	    }

	    if (args[0].equalsIgnoreCase("forcewin") && sender.hasPermission("rainbow.kinder"))
	    {
	      if (args.length < 3)
	      {
	        if ((args.length == 2) && (args[1].equalsIgnoreCase("reset")))
	        {
	          kindermgr.forceWin = new ConcurrentHashMap();
	          sender.sendMessage(ChatColor.GREEN + "Force Wins reset!");
	          return true;
	        }
	        sender.sendMessage(ChatColor.RED + "Usage: /kinder forcewin [player] [reward]");
	        sender.sendMessage(ChatColor.RED + "Usage: /kinder forcewin reset");
	        return false;
	      }
	      String rewardName = args[2].toUpperCase();
	      EggReward2 ew = (EggReward2)kindermgr.rewards.get(rewardName);
	      if (ew == null)
	      {
	    	sender.sendMessage(ChatColor.RED + "No reward exists named: " + ChatColor.YELLOW + rewardName);
	        return false;
	      }

	      String pName = args[1];
	      Player pTgt = Bukkit.getPlayer(pName);
	      if (pTgt != null) pName = pTgt.getName();

	      kindermgr.forceWin.put(pName + "." + rewardName, Boolean.valueOf(true));
	      sender.sendMessage(ChatColor.GREEN + "Forcing Win: " + ChatColor.GOLD + rewardName + ChatColor.GREEN + " for " + ChatColor.YELLOW + pName);
	      return true;
	    }

	    if (args[0].equalsIgnoreCase("setmsg") && sender.hasPermission("rainbow.kinder"))
	    {
	      if (args.length < 3)
	      {
	    	sender.sendMessage(ChatColor.RED + "Usage: /kinder setmsg [name] [msg]");
	        return false;
	      }
	      String rewardName = args[1].toUpperCase();
	      String argMsg = RainbowUtil.ConcatArgs(args, 2);
	      String msg = RainbowUtil.SpecialTranslate(argMsg);
	      msg = RainbowUtil.TranslateColorString(msg, false);

	      EggReward2 ew = (EggReward2)kindermgr.rewards.get(rewardName);
	      if (ew == null) ew = new EggReward2();
	      ew.msg = msg;
	      kindermgr.rewards.put(rewardName, ew);
	      kindermgr.SaveRewards();
	      sender.sendMessage(ChatColor.GREEN + "Saved Reward: " + ChatColor.YELLOW + ew.toString());
	      return true;
	    }

	    if (args[0].equalsIgnoreCase("setcmd") && sender.hasPermission("rainbow.kinder"))
	    {
	      if (args.length < 3)
	      {
	    	sender.sendMessage(ChatColor.RED + "Usage: /kinder setcmd [name] [cmd]");
	        return false;
	      }
	      String rewardName = args[1].toUpperCase();
	      String argMsg = RainbowUtil.ConcatArgs(args, 2);

	      EggReward2 ew = (EggReward2)kindermgr.rewards.get(rewardName);
	      if (ew == null) ew = new EggReward2();
	      ew.cmd = argMsg;
	      kindermgr.rewards.put(rewardName, ew);
	      kindermgr.SaveRewards();
	      sender.sendMessage(ChatColor.GREEN + "Saved Reward: " + ChatColor.YELLOW + ew.toString());
	      return true;
	    }

	    if (args[0].equalsIgnoreCase("setchance") && sender.hasPermission("rainbow.kinder"))
	    {
	      if (args.length < 3)
	      {
	    	sender.sendMessage(ChatColor.RED + "Usage: /kinder setchance [name] [chance]");
	        return false;
	      }
	      String rewardName = args[1].toUpperCase();
	      double prob = 0.0D;
	      try
	      {
	        prob = Double.parseDouble(args[2]);
	      }
	      catch (Exception localException1)
	      {
	      }
	      if (prob < 0.0D) prob = 0.0D;
	      if (prob > 100.0D) prob = 100.0D;

	      EggReward2 ew = (EggReward2)kindermgr.rewards.get(rewardName);
	      if (ew == null) ew = new EggReward2();
	      ew.prob = prob;
	      kindermgr.rewards.put(rewardName, ew);
	      kindermgr.SaveRewards();
	      sender.sendMessage(ChatColor.GREEN + "Saved Reward: " + ChatColor.YELLOW + ew.toString());
	      return true;
	    }
	    EggReward2 ew;
	    if (args[0].equalsIgnoreCase("rename") && sender.hasPermission("rainbow.kinder"))
	    {
	      if (args.length < 3)
	      {
	    	sender.sendMessage(ChatColor.RED + "Usage: /kinder rename [name1] [name2]");
	        return false;
	      }
	      String name1 = args[1].toUpperCase();
	      String name2 = args[2].toUpperCase();

	      ew = (EggReward2)kindermgr.rewards.get(name1);
	      if (ew == null)
	      {
	    	sender.sendMessage(ChatColor.RED + "No reward found with name: " + ChatColor.YELLOW + name1);
	        return false;
	      }

	      EggReward2 ewTarget = (EggReward2)kindermgr.rewards.get(name2);
	      if (ewTarget != null)
	      {
	    	sender.sendMessage(ChatColor.RED + "Target name already exists: " + ChatColor.YELLOW + name2);
	        return false;
	      }

	      kindermgr.rewards.put(name2, ew);
	      kindermgr.rewards.remove(name1);
	      sender.sendMessage(ChatColor.GREEN + "Renamed " + ChatColor.YELLOW + name1 + ChatColor.WHITE + " to " + ChatColor.YELLOW + name2);
	      return true;
	    }

	    if ((args[0].equalsIgnoreCase("del")) || (args[0].equalsIgnoreCase("delete") && sender.hasPermission("rainbow.kinder"))
)
	    {
	      if (args.length < 2)
	      {
	    	sender.sendMessage(ChatColor.RED + "Usage: /kinder del [name]");
	        return false;
	      }
	      String rewardName = args[1].toUpperCase();

	      EggReward2 ew1 = (EggReward2)kindermgr.rewards.get(rewardName);
	      if (ew1 == null)
	      {
	    	sender.sendMessage(ChatColor.RED + "No reward found with name: " + ChatColor.YELLOW + rewardName);
	        return false;
	      }
	      kindermgr.rewards.remove(rewardName);
	      kindermgr.SaveRewards();
	      sender.sendMessage(ChatColor.GREEN + "Removed Reward: " + ChatColor.YELLOW + rewardName);
	      return true;
	    }

	    if (args[0].equalsIgnoreCase("list") && sender.hasPermission("rainbow.kinder"))
	    {
	      for (String key : kindermgr.rewards.keySet())
	      {
	        EggReward2 rew = (EggReward2)kindermgr.rewards.get(key);
	        sender.sendMessage(ChatColor.LIGHT_PURPLE + key + " " + ChatColor.WHITE + rew.toString());
	      }
	      if (kindermgr.rewards.size() <= 0)
	      {
	    	sender.sendMessage(ChatColor.RED + "No rewards set.");
	      }
	      else
	      {
	    	sender.sendMessage(ChatColor.DARK_GREEN + "Chance of winning something: " + ChatColor.WHITE + String.format("~%.2f%%", new Object[] { Double.valueOf(kindermgr.ChanceOfWinningSomething()) }));
	      }
	      return true;
	    }

	    if (args[0].equalsIgnoreCase("give") && sender.hasPermission("rainbow.kinder"))
	    {
	      if (args.length < 3)
	      {
	    	sender.sendMessage(ChatColor.RED + "Usage: /kinder give [playername] [amount]");
	        return false;
	      }
	      String tgtPlrName = args[1];
	      Player tgtPlr = Bukkit.getPlayer(tgtPlrName);
	      if (tgtPlr == null)
	      {
	    	sender.sendMessage(ChatColor.RED + "No player online named: " + ChatColor.YELLOW + tgtPlrName);
	        return false;
	      }
	      int amt = 1;
	      try
	      {
	        amt = Integer.parseInt(args[2]);
	      }
	      catch (Exception localException2)
	      {
	      }
	      if (amt < 1) amt = 1;
	      if (amt > 64) amt = 64;


	      ItemStack egg = new ItemStack(383, amt);
	      ItemMeta ism = egg.getItemMeta();
	      ism.setDisplayName(RainbowUtil.RainbowString("Kinder Egg"));
	      egg.setItemMeta(ism);
	      tgtPlr.getInventory().addItem(egg);
	      if (amt == 1) sender.sendMessage(ChatColor.GREEN + "Kinder Egg sent to " + tgtPlrName + "!"); else
	        sender.sendMessage(ChatColor.GREEN + "Kinder Egg x" + amt + " sent to " + tgtPlrName + "!");
	      if (amt == 1) tgtPlr.sendMessage(ChatColor.GREEN + "You receive a " + RainbowUtil.RainbowString("Kinder Egg!")); else
	        tgtPlr.sendMessage(ChatColor.GREEN + "You receive " + ChatColor.WHITE + amt + " " + RainbowUtil.RainbowString("Kinder Eggs!"));
	      return true;
	    }
	    sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.LIGHT_PURPLE + "/kinder " + ChatColor.YELLOW + "give [playername] [amount]");
	    SendUsage(sender);
		return false;
	  }
	  public static void SendUsage(CommandSender sender)
	  {
	    sender.sendMessage(RainbowUtil.RainbowString("--- Kinder Eggs ---"));
	    if (!sender.isOp()) return;
	    sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.LIGHT_PURPLE + "/kinder " + ChatColor.YELLOW + "add " + ChatColor.WHITE + "[name] [chance] [command]");
	    sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.LIGHT_PURPLE + "/kinder " + ChatColor.YELLOW + "setcmd " + ChatColor.WHITE + "[name] [cmd]");
	    sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.LIGHT_PURPLE + "/kinder " + ChatColor.YELLOW + "setmsg " + ChatColor.WHITE + "[name] [msg]");
	    sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.LIGHT_PURPLE + "/kinder " + ChatColor.YELLOW + "setchance " + ChatColor.WHITE + "[name] [chance]");
	    sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.LIGHT_PURPLE + "/kinder " + ChatColor.YELLOW + "del " + ChatColor.WHITE + "[name]");
	    sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.LIGHT_PURPLE + "/kinder " + ChatColor.YELLOW + "rename " + ChatColor.WHITE + "[name1] [name2]");
	    sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.LIGHT_PURPLE + "/kinder " + ChatColor.YELLOW + "give " + ChatColor.WHITE + "[playername] [amount]");
	    sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.LIGHT_PURPLE + "/kinder " + ChatColor.YELLOW + "list | load");
	  }

}
