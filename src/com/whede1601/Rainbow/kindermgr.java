package com.whede1601.Rainbow;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class kindermgr {

	public static ConcurrentHashMap<String, Boolean> forceWin = new ConcurrentHashMap();
	public static ConcurrentHashMap<String, EggReward2> rewards = new ConcurrentHashMap();

	  public static void LoadRewards()
	  {
	    try
	    {
		  long msStart = System.currentTimeMillis();
	      File file = new File("plugins" + File.separatorChar + "Rainbow", "EggRewards2.dat");
	      FileInputStream f = new FileInputStream(file);	      
	      ObjectInputStream s = new ObjectInputStream(new BufferedInputStream(f));
	      rewards = (ConcurrentHashMap)s.readObject();
	      s.close();

	    }
	    catch (Throwable exc)
	    {
	      rewards = new ConcurrentHashMap();
	      exc.printStackTrace();
	    }
	  }

	  public static void SaveRewards()
	  {
	    try {
	      File file = new File("plugins" + File.separatorChar + "Rainbow", "EggRewards2.dat");
	      FileOutputStream f = new FileOutputStream(file);
	      ObjectOutputStream s = new ObjectOutputStream(new BufferedOutputStream(f));
	      s.writeObject(rewards);
	      s.close();
	    }
	    catch (Throwable exc)
	    {
	      System.out.println("**********************************************");
	      System.out.println("SaveRewards: " + exc.toString());
	      System.out.println("**********************************************");
	    }
	  }

	  public static double ChanceOfWinningSomething()
	  {
	    int maxTrials = 100000;
	    int nWins = 0;
	    for (int i = 0; i < maxTrials; i++)
	    {
	      for (String key : rewards.keySet())
	      {
	        EggReward2 rew = (EggReward2)rewards.get(key);
	        if ((rew.cmd == null) || (rew.cmd.length() <= 1))
	          continue;
	        double rnd = 100.0D * Math.random();
	        if (rnd >= rew.prob)
	          continue;
	        nWins++;
	        break;
	      }
	    }

	    return 100.0D * nWins / maxTrials;
	  }
	  public static boolean GivePrize(Player p)
	  {
	    String pName = p.getName();
	    int nPrizes = 0;

	    for (String key : rewards.keySet())
	    {
	      EggReward2 rew = (EggReward2)rewards.get(key);
	      if ((rew.cmd == null) || (rew.cmd.length() <= 1))
	        continue;
	      double rnd = 100.0D * Math.random();

	      String forceKey = pName + "." + key;
	      if (forceWin.containsKey(forceKey))
	      {
	        System.out.println(ChatColor.LIGHT_PURPLE + "--- Forcing Win " + ChatColor.GOLD + key + ChatColor.WHITE + " for " + ChatColor.YELLOW + pName);
	        rnd = 0.0D;
	        forceWin.remove(forceKey);
	      }
	      if (rnd >= rew.prob)
	        continue;
	      if ((rew.msg != null) && (rew.msg.length() > 1))
	      {
	        if (rew.prob < 5.0D)
	        {
	          String msg = RainbowUtil.RainbowString("Kinder Egg! ") + ChatColor.WHITE + p.getName() + ChatColor.AQUA + " " + rew.msg;
	          Bukkit.getServer().broadcastMessage(msg);
	        }
	        else
	        {
	          String msg = RainbowUtil.RainbowString("Kinder Egg! ") + ChatColor.WHITE + "You " + ChatColor.AQUA + rew.msg;
	          p.sendMessage(msg);
	        }

	      }

	      String actCmd = String.format(rew.cmd, new Object[] { p.getName() });
	      System.out.println("CMD: " + actCmd);
	      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actCmd);
	      p.sendMessage(RainbowUtil.RainbowString("Kinder Egg") + ChatColor.AQUA + "Your egg had something in it!");
	      nPrizes++;
	    }

	    if (nPrizes <= 0)
	    {
	      p.sendMessage(ChatColor.RED + "Oh No! " + ChatColor.AQUA + "The egg was empty!");
	      return false;
	    }
	    return true;
	  }
}
