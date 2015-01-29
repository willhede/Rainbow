package com.whede1601.Rainbow;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.text.html.parser.Entity;

import org.apache.commons.lang.text.StrBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

@SuppressWarnings("unused")
public class Rainbow extends JavaPlugin
	implements Listener{
	
	public static Rainbow plugin;
	public void onEnable()
	{
	    File data = getDataFolder();
	    Bukkit.getServer().getPluginManager().registerEvents(this, this);
	    this.saveDefaultConfig();
		EmoteUtil.LoadEmotes();
		NameUtil.LoadData();
		cron.LoadCron();
		Runner();
		KitMgr.LoadKits();
		NameUtil.ServerStartTime = System.currentTimeMillis();
	    this.getCommand("jemote").setExecutor(new jemote(plugin));
		this.getCommand("name").setExecutor(new name(plugin));
		this.getCommand("e").setExecutor(new e(plugin));
		this.getCommand("diw").setExecutor(new Diw(plugin));
		this.getCommand("ride").setExecutor(new Ride(plugin));
		this.getCommand("throw").setExecutor(new Throw(plugin));
		this.getCommand("cron").setExecutor(new cron(plugin));
		this.getCommand("Jot").setExecutor(new Jot(plugin));
		VersionCheck();
		this.getServer().getPluginManager().registerEvents(new listener(this), (this));
//		this.getCommand("kit").setExecutor(new KitCmd(plugin));
	}
	public void onDisable()
	{
	    File data = getDataFolder();
		NameUtil.SaveData();
		EmoteUtil.SaveEmotes();
		cron.SaveCron();
		KitMgr.SaveKits();
		NameUtil.CheckOnline();
	}

	public void VersionCheck() {
	boolean config = this.getConfig().getBoolean("Settings.Upgraded");
	if (config = true)
	{
	}
	if (config = false);
	{
		NameUtil.ConvertHashMap();
		this.getConfig().set("Settings.Upgraded", true);
		this.saveConfig();
	}
}
		//To access the plugin vfrom other classes
	public static Plugin getPlugin() {
	return plugin;
	}
	public void Runner()
	{
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	cron.Run500ms();
            }
        }, 0L, 10L);
    }
}
