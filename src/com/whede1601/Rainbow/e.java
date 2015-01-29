package com.whede1601.Rainbow;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class e implements CommandExecutor {

	@SuppressWarnings("unused")
	private Rainbow plugin;
	public e(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (args.length <= 0) return false;
	    Player player = (Player)sender;
	    EmoteUtil.HandleEmote(player, args);
		return false;
	}

}
