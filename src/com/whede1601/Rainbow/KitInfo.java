package com.whede1601.Rainbow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class KitInfo implements Serializable {
	  public String name = "NoName";
	  public ConcurrentHashMap<String, Material> rawData = new ConcurrentHashMap();
	  public int delaySeconds = 60;
	  public boolean once = false;
	  
	  public String toString()
	  {
	    return String.format(ChatColor.AQUA + "%s" + ChatColor.WHITE + "  delay=%d" + ChatColor.GREEN + "  %d items, once=%s", new Object[] { this.name, Integer.valueOf(this.delaySeconds), Integer.valueOf(this.rawData.size()), this.once });
	  }
}
