package com.whede1601.Rainbow;

import me.ryanhamshire.GriefPrevention.GriefPrevention;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.earth2me.essentials.Essentials;

public class e implements CommandExecutor {

	@SuppressWarnings("unused")
	private Rainbow plugin;
	private Essentials essentials;
	private GriefPrevention gp;

	public e(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
		Plugin ess = Bukkit.getServer().getPluginManager().getPlugin("Essentials");	
		essentials = (Essentials) ess;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (args.length <= 0) return false;
	    Player player = (Player)sender;
	    EmoteUtil.HandleEmote(player, args, essentials);
		return false;
	}

}
