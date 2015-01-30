package com.whede1601.Rainbow;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.server.v1_8_R1.ICommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class cron implements CommandExecutor{

	private static final CommandSender MinecraftServer = null;
	private Rainbow plugin;
	public static ConcurrentHashMap<String, _CronData> mapData = new ConcurrentHashMap();

	
	public cron(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where youdd need it.
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		long ms;
	    if (!(sender instanceof Player))
	    {
	      System.out.println("Not from console!");
	      return false;
	    }
		Player p = (Player)sender;
		if (!(p.hasPermission("rainbow.cron")))
		{
			p.sendMessage(ChatColor.RED + "Not enough permission.");
			return false;
		}
	    if ((args.length >= 1) && (args[0].equalsIgnoreCase("add")))
	    {
	      if (args.length < 4)
	      {
	        p.sendMessage(ChatColor.RED + "Usage: /cron add " + ChatColor.YELLOW + "MyLoop " + ChatColor.WHITE + "60s " + ChatColor.GOLD + "/give @a cake");
	        return false;
	      }
	      String jobName = args[1].trim();
	      String strTime = args[2].trim();
	      String strCmd = RainbowUtil.ConcatArgs(args, 3).trim();

	      ms = RainbowUtil.GetMSFromString(strTime);

	      if ((ms <= 0L) || (strCmd.length() <= 0))
	      {
	        p.sendMessage(ChatColor.RED + "Usage: /cron add " + ChatColor.YELLOW + "MyLoop " + ChatColor.WHITE + "60s " + ChatColor.GOLD + "/give @a cake");
	        return false;
	      }

	      String key = jobName.toLowerCase();
	      _CronData data = (_CronData)mapData.get(key);
	      if (data == null) data = new _CronData();
	      data.jobName = jobName;

	      data.cmdToRun = strCmd;
	      data.msDelay = ms;
	      data.msLastRun = System.currentTimeMillis();
	      mapData.put(key, data);

	      SaveCron();
	      p.sendMessage(ChatColor.GREEN + "Saved " + ChatColor.AQUA + jobName);
	      return false;
	    }
	    if ((args.length >= 1) && (args[0].equalsIgnoreCase("delete")))
	    {
	      if (args.length <= 1)
	      {
	        p.sendMessage(ChatColor.RED + "Usage: /cron " + ChatColor.AQUA + "delete " + ChatColor.YELLOW + "MyLoop");
	        return false;
	      }
	      String jobName = args[1].trim();
	      String key = jobName.toLowerCase();
	      _CronData data = (_CronData)mapData.get(key);

	      if (data == null)
	      {
	        p.sendMessage(ChatColor.RED + "No job named: " + ChatColor.YELLOW + jobName);
	        return false;
	      }
	      mapData.remove(key);
	      p.sendMessage(ChatColor.GREEN + "Deleted job: " + ChatColor.YELLOW + jobName);
	      SaveCron();
	      return false;
	    }
	    if ((args.length >= 1) && (args[0].equalsIgnoreCase("list")))
	    {
	      if (mapData.size() <= 0)
	      {
	        p.sendMessage(ChatColor.AQUA + "There are no jobs setup.");
	        return false;
	      }

	      p.sendMessage(ChatColor.LIGHT_PURPLE + RainbowUtil.TextLabel("Job Name", 10) + ChatColor.LIGHT_PURPLE + " " + 
	        RainbowUtil.TextLabel("Interval", 9) + ChatColor.LIGHT_PURPLE + " " + 
	        RainbowUtil.TextLabel("Time Left", 9) + ChatColor.LIGHT_PURPLE + " Command");
	      long msNow = System.currentTimeMillis();
	      for (String key : mapData.keySet())
	      {
	        _CronData data = (_CronData)mapData.get(key);
	        String strTime = RainbowUtil.TimeDeltaString_JustMinutesSecs(data.msDelay);
	        long msLeft = data.msDelay - (msNow - data.msLastRun);
	        if (msLeft < 0L) msLeft = 0L;
	        String strLeft = RainbowUtil.TimeDeltaString_JustMinutesSecs(msLeft);

	        p.sendMessage( 
	          ChatColor.YELLOW + RainbowUtil.TextLabel(data.jobName, 10) + " " + 
	          ChatColor.WHITE + RainbowUtil.TextLabel(strTime, 9) + " " + 
	          ChatColor.GRAY + RainbowUtil.TextLabel(strLeft, 9) + " " + 
	          ChatColor.GOLD + data.cmdToRun);
	      }
	      return false;
	    }
	    if ((args.length >= 1) && (args[0].equalsIgnoreCase("setdelay")))
	    {
	      if (args.length < 3)
	      {
	        p.sendMessage(ChatColor.RED + "Usage: /cron " + ChatColor.AQUA + "setdelay " + ChatColor.YELLOW + "MyLoop " + ChatColor.WHITE + "5m");
	        return false;
	      }
	      String jobName = args[1].trim();
	      String strTime = args[2].trim();

	      long mss = RainbowUtil.GetMSFromString(strTime);

	      if (mss <= 0L)
	      {
	        p.sendMessage(ChatColor.RED + "Usage: /cron " + ChatColor.AQUA + "setdelay " + ChatColor.YELLOW + "MyLoop " + ChatColor.WHITE + "5m");
	        return false;
	      }

	      String key = jobName.toLowerCase();
	      _CronData data = (_CronData)mapData.get(key);
	      if (data == null)
	      {
	        p.sendMessage(ChatColor.RED + "No job named: " + ChatColor.YELLOW + jobName);
	        return false;
	      }
	      data.msDelay = mss;

	      mapData.put(key, data);

	      SaveCron();
	      p.sendMessage(ChatColor.GREEN + "Updated job " + ChatColor.AQUA + jobName + ChatColor.GREEN + " new interval: " + ChatColor.WHITE + RainbowUtil.TimeDeltaString_JustMinutesSecs(mss));

	      return false;
	    }
	    if ((args.length >= 1) && (args[0].equalsIgnoreCase("setcmd")))
	    {
	      if (args.length < 3)
	      {
	        p.sendMessage(ChatColor.RED + "Usage: /cron " + ChatColor.AQUA + "setcmd " + ChatColor.YELLOW + "MyLoop " + ChatColor.GOLD + "/give @a cookie");
	        return false;
	      }
	      String jobName = args[1].trim();
	      String strCmd = RainbowUtil.ConcatArgs(args, 2).trim();

	      if (strCmd.length() <= 0)
	      {
	        p.sendMessage(ChatColor.RED + "Usage: /cron " + ChatColor.AQUA + "setcmd " + ChatColor.YELLOW + "MyLoop " + ChatColor.GOLD + "/give @a cookie");
	        return false;
	      }

	      String key = jobName.toLowerCase();
	      _CronData data = (_CronData)mapData.get(key);
	      if (data == null)
	      {
	        p.sendMessage(ChatColor.RED + "No job named: " + ChatColor.YELLOW + jobName);
	        return false;
	      }
	      data.cmdToRun = strCmd;

	      mapData.put(key, data);

	      SaveCron();
	      p.sendMessage(ChatColor.GREEN + "Updated job " + ChatColor.AQUA + jobName + ChatColor.GREEN + " new command: " + ChatColor.GOLD + strCmd);

	      return false;
	    }
		return false;
	}

	  public static void LoadCron()
	  {
	    try
	    {
	      File file = new File("plugins" + File.separatorChar + "Rainbow", "Cron.dat");
	      FileInputStream f = new FileInputStream(file);	      
	      ObjectInputStream s = new ObjectInputStream(new BufferedInputStream(f));
	      mapData = (ConcurrentHashMap)s.readObject();
	      for (Map.Entry entry : mapData.entrySet())
	      {
	    	  ((_CronData)entry.getValue()).msLastRun = System.currentTimeMillis();
	      }
	      s.close();
	    }
	    catch (Throwable exc)
	    {
	        mapData = new ConcurrentHashMap();
	        exc.printStackTrace();
	    }
	  }

	  public static void SaveCron()
	  {
	    try {
	      File file = new File("plugins" + File.separatorChar + "Rainbow", "Cron.dat");
	      FileOutputStream f = new FileOutputStream(file);
	      ObjectOutputStream s = new ObjectOutputStream(new BufferedOutputStream(f));
	      s.writeObject(mapData);
	      s.close();
	    }
	    catch (Throwable exc)
	    {
	      System.out.println("**********************************************");
	      System.out.println("SaveEmotes: " + exc.toString());
	      System.out.println("**********************************************");
	    }
	  }

	  public static void Run500ms() {
		    long msNow = System.currentTimeMillis();
		    for (Map.Entry entry : mapData.entrySet())
		    {
		      _CronData data = (_CronData)entry.getValue();
		      long delta = msNow - data.msLastRun;
		      ConsoleCommandSender sender = Bukkit.getConsoleSender();
		      if (delta <= data.msDelay)
		        continue;
		      try
		      {

		        System.out.println("[CRON] Job " + data.jobName + ": " + data.cmdToRun);
		        Bukkit.getServer().dispatchCommand(sender, data.cmdToRun);
		      }
		      catch (Exception exc)
		      {
		        exc.printStackTrace();
		      }

		      data.msLastRun = msNow;
		    }
		  }
}
