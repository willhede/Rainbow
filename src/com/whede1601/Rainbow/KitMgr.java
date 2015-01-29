package com.whede1601.Rainbow;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;

public class KitMgr
{
	  public static KitInfo hashKits = new KitInfo();
	  public static ConcurrentHashMap<String, Long> hashKitLastUsed = new ConcurrentHashMap();

	  public static void LoadKits()
	  {
	    try
	    {
	      File file = new File("plugins" + File.separatorChar + "Rainbow", "Kit.dat");
	      FileInputStream f = new FileInputStream(file);
	      ObjectInputStream s = new ObjectInputStream(new BufferedInputStream(f));
	      hashKits = (KitInfo)s.readObject();
	      hashKitLastUsed = (ConcurrentHashMap)s.readObject();
	      s.close();
	    }
	    catch (Throwable exc)
	    {
	      hashKits = new KitInfo();
	      hashKitLastUsed = new ConcurrentHashMap();
	      System.out.println("Starting New Kit Database...");
	      exc.printStackTrace();
	    }
	  }

	  public static void SaveKits()
	  {
	    try
	    {

	      File file = new File("plugins" + File.separatorChar + "Rainbow", "Kit.dat");
	      
	      FileOutputStream f = new FileOutputStream(file);
	      ObjectOutputStream s = new ObjectOutputStream(new BufferedOutputStream(f));
	      s.writeObject(hashKits);
	      s.writeObject(hashKitLastUsed);
	      s.close();
	    }
	    catch (Throwable exc)
	    {
	      System.out.println("**********************************************");
	      System.out.println("SaveKits: " + exc.toString());
	      System.out.println("**********************************************");
	    }
	  }
}