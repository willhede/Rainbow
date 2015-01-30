package com.whede1601.Rainbow;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class NameUtil implements Comparable{
	  public static long ServerStartTime = 0L;
	  public static Namedatabase Data = new Namedatabase();
	  public static Namedatabase DataBackup = new Namedatabase();
	  public static Namedatabase1 DataNew = new Namedatabase1();
	  public static String DataFilename = "PlayerOnlineTime.dat";
// TODO check if backup file exists. If doesn't save backup file. 
// TODO Then after file creation. 
	public static void LoadData()
	  {
	    try
	    {
		  File file = new File("plugins" + File.separatorChar + "Rainbow", "PlayerOnlineTimeNew.dat");
	      FileInputStream f = new FileInputStream(file);
	      ObjectInputStream s = new ObjectInputStream(f);
	      DataNew = (Namedatabase1)s.readObject();		
	      s.close();
	      return;
	    }
	    catch (Throwable exc)
	    {
	      System.out.println("Starting new file: " + DataFilename);
	      DataNew = new Namedatabase1();
	      System.out.println(exc);
	    }
	  }
	  public static void SaveData()
	  {
	    try {
		  File file = new File("plugins" + File.separatorChar + "Rainbow", "PlayerOnlineTimeNew.dat");
	      long msStart = System.currentTimeMillis();

	      FileOutputStream f = new FileOutputStream(file);
	      ObjectOutputStream s = new ObjectOutputStream(new BufferedOutputStream(f));
	      s.writeObject(DataNew);
	      s.close();
	    }
	    catch (Throwable exc)
	    {
	      System.out.println("**********************************************");
	      System.out.println("SaveData: " + exc.toString());
	      System.out.println("**********************************************");
	    }
	  }

	  public static String GetPlayerExactName(String tgtName)
	  {
	    for (String name : DataNew.pdata.keySet())
	    {
	      if (name.equalsIgnoreCase(tgtName)) return name;
	    }
	    return null;
	  }

	  public static void Login(String player) {
		String key = player;
		NameEntry entry = DataNew.pdata.get(key);
	    if (entry == null)
	    {
	        System.out.println("-----------------------------------------");
	        System.out.println("FIRST LOGIN ON THIS SERVER: " + player);
	        System.out.println("-----------------------------------------");
	        entry = new NameEntry();
	    }
	    entry.msLastLogin = (System.currentTimeMillis() + 1L);
	    DataNew.pdata.put(key, entry);

	}
	  public static long GetTotalOnlineTime(NameEntry entry)
	  {
	    if ((entry.msLastLogin > entry.msLastLogout) && (entry.msLastLogin >= ServerStartTime))
	    {
	      long msNowOnline = System.currentTimeMillis() - entry.msLastLogin;
	      return entry.msTotal + msNowOnline;
	    }
	    return entry.msTotal;
	  }

	public static void Logout(String player)
	{
		String key = player;
		NameEntry entry = DataNew.pdata.get(key);
	    if (entry == null)
	    {
	      System.out.println(ChatColor.RED + "Unexpected Logout before Login: " + ChatColor.YELLOW + key);
	      return;
	    }
	    entry.msLastLogout = System.currentTimeMillis();
	    long msThisSession = entry.msLastLogout - entry.msLastLogin;
	    entry.msTotal += msThisSession;
	    DataNew.pdata.put(key, entry);

	}
	
	  public static void ShowPlayerLoginTime(CommandSender sender, String pname)
	  {
	    if (pname.equalsIgnoreCase("me")) pname = sender.getName();
	    String exactName = GetPlayerExactName(pname);

	    if (exactName == null)
	    {
	      sender.sendMessage(ChatColor.RED + "No data for: " + ChatColor.YELLOW + pname);
	      return;
	    }
	    sender.sendMessage(ChatColor.AQUA + "----------------------------------------------------");
	    int labelLen = 20;
	    sender.sendMessage(ChatColor.AQUA + RainbowUtil.TextLabel("Online Time For: ", labelLen) + ChatColor.YELLOW + exactName);
	    NameEntry entry = DataNew.pdata.get(exactName);
	    long msTotal = entry.msTotal;
	    sender.sendMessage(ChatColor.DARK_AQUA + RainbowUtil.TextLabel("Last Login: ", labelLen) + ChatColor.WHITE + RainbowUtil.GetDateStringFromLong(entry.msLastLogin) + " " + ChatColor.DARK_AQUA + RainbowUtil.GetTimeStringFromLong(entry.msLastLogin));
	    sender.sendMessage(ChatColor.DARK_AQUA + RainbowUtil.TextLabel("Last Logout: ", labelLen) + ChatColor.WHITE + RainbowUtil.GetDateStringFromLong(entry.msLastLogout) + " " + ChatColor.DARK_AQUA + RainbowUtil.GetTimeStringFromLong(entry.msLastLogout));
	    if (entry.msLastLogin > entry.msLastLogout)
	    {
	      long msNowOnline = System.currentTimeMillis() - entry.msLastLogin;
	      sender.sendMessage(ChatColor.GREEN + RainbowUtil.TextLabel("This Session: ", labelLen) + ChatColor.DARK_GREEN + RainbowUtil.TimeDeltaString(msNowOnline));

	      msTotal += msNowOnline;
	    }
	    else
	    {
	      sender.sendMessage(ChatColor.RED + "Not Online Currently");
	    }
	    sender.sendMessage(ChatColor.GREEN + RainbowUtil.TextLabel("Total Online Time: ", labelLen) + ChatColor.GOLD + RainbowUtil.TimeDeltaString(msTotal));
	  }

	public static void ShowTopPage(CommandSender sender, int page)
	  {
	    int recordsPerPage = 8;
	    int idxStart = 0 + recordsPerPage * (page - 1);
	    int idxEnd = idxStart + recordsPerPage;
	    ArrayList names = new ArrayList(DataNew.pdata.keySet());
	   System.out.println(names);
	    Collections.sort(names, new Comparator()
	    {
	      public int compare(String name1, String name2) {
	        long total1 = NameUtil.GetTotalOnlineTime((NameEntry)NameUtil.DataNew.pdata.get(name1));
	        long total2 = NameUtil.GetTotalOnlineTime((NameEntry)NameUtil.DataNew.pdata.get(name2));
	        if (total1 > total2) return -1;
	        if (total1 < total2) return 1;
	        return 0;
	      }

		@Override
		public int compare(Object name1, Object name2) {
			return 0;
		}
	    });

	    String strPage = "page " + page;
	    if (idxEnd >= names.size())
	    {
	      idxEnd = names.size();
	      idxStart = idxEnd - recordsPerPage;
	      if (idxStart < 0) idxStart = 0;
	      strPage = "end of list";
	    }

	    sender.sendMessage(ChatColor.AQUA + "----- Top Online Player Time List -----");
	    sender.sendMessage(ChatColor.GOLD + "Showing " + ChatColor.WHITE + strPage + ChatColor.GOLD + ", records " + ChatColor.WHITE + String.format("%d - %d", new Object[] { Integer.valueOf(idxStart + 1), Integer.valueOf(idxEnd) }));
	    for (int i = idxStart; i < idxEnd; i++)
	    {
	      String pName = (String)names.get(i);
	      NameEntry entry = DataNew.pdata.get(pName);
	      Player plr = Bukkit.getServer().getPlayer(pName);
	      String trailer = "";
	      if (plr != null) trailer = ChatColor.GREEN + " Online Now";
	      String line = ChatColor.YELLOW + RainbowUtil.TextLabel(new StringBuilder(String.valueOf(String.format("#%d ", new Object[] { Integer.valueOf(i + 1) }))).append(pName).toString(), 20) + " " + ChatColor.WHITE + RainbowUtil.TimeDeltaString(GetTotalOnlineTime(entry)) + trailer;
	      if (!(sender instanceof Player))
	      {
	        line = ChatColor.AQUA + String.format(new StringBuilder("#%-3d ").append(ChatColor.YELLOW).append("%-16s ").append(ChatColor.WHITE).append("%s").toString(), new Object[] { Integer.valueOf(i + 1), pName, RainbowUtil.TimeDeltaString(GetTotalOnlineTime(entry)) }) + trailer;
	      }
	      sender.sendMessage(line);
	    }
	  }

	public static void IntrumSave() 
	{
		Player[] players = Bukkit.getOnlinePlayers();
		for (Player p : players)
		{
			String plr = p.getName();
		    NameEntry entry = DataNew.pdata.get(plr);
		    long msNowOnline = System.currentTimeMillis() - entry.msLastLogin;
		    entry.msTotal += msNowOnline;
		}
		SaveData();	
	}
	@Deprecated
	public static void LoadDataOld()
	  {
	    try
	    {
		  File file = new File("plugins" + File.separatorChar + "Rainbow", "PlayerOnlineTime.dat");
	      FileInputStream f = new FileInputStream(file);
	      ObjectInputStream s = new ObjectInputStream(f);
	      Data = (Namedatabase)s.readObject();	
	      s.close();
	      file.delete();
	      return;
	    }
	    catch (Throwable exc)
	    {
	    }
	  }
	@Deprecated
	public static void ConvertHashMap() {
		LoadDataOld();
		for (String name : Data.pdata.keySet())
		{
			System.out.println(name);
			NameEntry entry = new NameEntry();
//			DataNew.pdata.put(name, entry);
			SaveData();
		}
	}
}
