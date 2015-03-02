package com.whede1601.Rainbow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.libs.jline.internal.Log.Level;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

public class PurgeUtil {
	static Boolean running = false;
	public static ArrayList<Player> purgeusers = new ArrayList();
	public static ConcurrentHashMap<Player, String> playgroup = new ConcurrentHashMap();
	public static ConcurrentHashMap<String, Integer> lifeStats = new ConcurrentHashMap();
	static ScoreboardManager manager = Bukkit.getScoreboardManager();
	static Scoreboard killboard = manager.getNewScoreboard();
	static Scoreboard deathboard = manager.getNewScoreboard();
	static Boolean op = false;
	static Objective killobjective = killboard.registerNewObjective("Kills", "dummy");
	static Objective deathobjective = deathboard.registerNewObjective("Deaths", "dummy");
	private Rainbow plugin;
	private static GroupManager groupmanager;
	private static Essentials essentials;

	public static void loadLifeStats()
	{
		try 
		{
			File file = new File("plugins" + File.separatorChar + "Rainbow", "PurgeStats.csv");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			try
			{
				while (true)
				{
					final String line = reader.readLine();
					if (line == null)
					{
						break;
					}
					else
					{
						String values[] = line.split(",");
						if (values.length == 2)
						{
							String name = values[0];
							int kills = Integer.parseInt(values[1]);
							lifeStats.put(name, kills);
						}			
					}
				}
			}
			finally
			{
				reader.close();
			}
		} catch (Throwable exc)
		{
			lifeStats = new ConcurrentHashMap();
		}
	}
	
	public static void SaveLifeStats()
	{
		try 
		{
			File file = new File("plugins" + File.separatorChar + "Rainbow", "PurgeStats.csv");
			try
			{
				BufferedWriter bWrite = new BufferedWriter(new FileWriter(file));
				for (Entry<String, Integer> entries : lifeStats.entrySet())
				{
					bWrite.write(entries.getKey() + "," + entries.getValue().toString());
					bWrite.newLine();
				}
			} catch (Throwable exc)
			{
				
			}
		} catch (Throwable exc)
		{
		      System.out.println("**********************************************");
		      System.out.println("Name: " + exc.toString());
		      System.out.println("**********************************************");
		}
	}
	public static void startPurge()
	{
		if (!running)
		{
		killobjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		killobjective.setDisplayName(" "+ChatColor.BOLD + ChatColor.GREEN +"Kills: ");
		loadLifeStats();
		running = true;
		return;
		}
		return;
	}
	
	public static void stopPurge()
	{
		if (running)
		{
			SaveLifeStats();
			killboard.clearSlot(DisplaySlot.SIDEBAR);
			for (Player p : purgeusers)
			{
				EnablePerms(p);
				p.setScoreboard(manager.getNewScoreboard());
			}
			running = false;
			return;
		}
		return;
	}
	public static void addUser(Player p) {
		if (!running)
		{
			p.sendMessage(ChatColor.RED + "Purge is not currently running.");
			return;
		}
		purgeusers.add(p);
		Score core = killobjective.getScore(p);
		core.setScore(0);
		DisablePerms(p);
		p.setScoreboard(killboard);
// TODO insert message alerting play joining
//		DisablePerms(p);
	}
	
	public static void removeUser(Player p)
	{
		if (!running)
		{
			p.sendMessage(ChatColor.RED + "Purge is not currently running.");
			return;
		}
		purgeusers.remove(p);
		p.setScoreboard(manager.getNewScoreboard());
		EnablePerms(p);
	}
	
	private static void DisablePerms(Player p) {
// TODO Hook into essentials and check group and save it.		
		String group = getGroup(p);
		if (!group.equalsIgnoreCase("Default"))
		{
			playgroup.put(p, group);
			setGroup(p, "Default");
		}
		removeCheats(p);
	}

	public static void EnablePerms(Player p)
	{
		if (!playgroup.containsKey(p)) return;
		String group = playgroup.get(p);
		setGroup(p, group);
	}
	
	public static void updateScoreboardOnDeath(Player deadPlayer, Player killerPlayer)
	{	
		Score core = killobjective.getScore(killerPlayer);
		Integer count = core.getScore();
		count++;
		core.setScore(count);
		killerPlayer.setScoreboard(killboard);
	}
	
	public static void safeRestart()
	{
		for (Player p : purgeusers)
		{
			EnablePerms(p);
		}
	}
	

	private static void removeCheats(Player p) {
		Plugin ess = Bukkit.getServer().getPluginManager().getPlugin("Essentials");	
		essentials = (Essentials) ess;
		User user = essentials.getUser(p);
		user.setGodModeEnabled(false);
		user.getBase().setFlying(false);
		user.getBase().setGameMode(GameMode.SURVIVAL);
	}
	
	public static String getGroup(Player player)
	{
		PluginManager pluginManager = Bukkit.getServer().getPluginManager();
		Plugin gmplugin = pluginManager.getPlugin("GroupManager");
		groupmanager = (GroupManager)gmplugin;
		final AnjoPermissionsHandler handler = groupmanager.getWorldsHolder().getWorldPermissions(player);
		
		if (handler == null)
		{
			return null;
		}
		return handler.getGroup(player.getName());
	}
	
	public static boolean setGroup(Player player, String string)
	{
		PluginManager pluginManager = Bukkit.getServer().getPluginManager();
		Plugin gmplugin = pluginManager.getPlugin("GroupManager");
		groupmanager = (GroupManager)gmplugin;
		final OverloadedWorldHolder handler = groupmanager.getWorldsHolder().getWorldData(player);
		if (handler == null)
		{
			return false;
		}
		handler.getUser(player.getName()).setGroup(handler.getGroup(string));
		return true;
	}

	public static void msgPlayers()
	{
		for (Player p : purgeusers)
		{
			
		}
	}
}
