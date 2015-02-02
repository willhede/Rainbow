package com.whede1601.Rainbow;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Color implements CommandExecutor {

	private Rainbow plugin;
	public Color(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where youdd need it.
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player))
	    {
	      System.out.println("Not from console!");
	      return false;
	    }
	    Player player = (Player) sender;
	    HandleColors(sender);
	    return true;
	}

	private void HandleColors(CommandSender cs)
	  {
	    cs.sendMessage("-----------------------------------------------------");
	    cs.sendMessage(
	      ChatColor.DARK_BLUE + RainbowUtil.TextLabel("&1 = Dark Blue", 18) + 
	      ChatColor.GRAY + RainbowUtil.TextLabel("&7 = Gray", 18) + 
	      ChatColor.LIGHT_PURPLE + "&d = Light Purple");
	    cs.sendMessage(
	      ChatColor.DARK_GREEN + RainbowUtil.TextLabel("&2 = Dark Green", 18) + 
	      ChatColor.DARK_GRAY + RainbowUtil.TextLabel("&8 = Dark Gray", 18) + 
	      ChatColor.YELLOW + "&e = Yellow");
	    cs.sendMessage(
	      ChatColor.DARK_AQUA + RainbowUtil.TextLabel("&3 = Dark Aqua", 18) + 
	      ChatColor.BLUE + RainbowUtil.TextLabel("&9 = Blue", 18) + 
	      ChatColor.WHITE + "&f = White");
	    cs.sendMessage(
	      ChatColor.DARK_RED + RainbowUtil.TextLabel("&4 = Dark Red", 18) + 
	      ChatColor.GREEN + RainbowUtil.TextLabel("&a = Green", 18) + 
	      ChatColor.WHITE + ChatColor.BOLD + "&l = Bold");
	    cs.sendMessage(
	      ChatColor.DARK_PURPLE + RainbowUtil.TextLabel("&5 = Dark Purple", 18) + 
	      ChatColor.AQUA + RainbowUtil.TextLabel("&b = Aqua", 18) + 
	      ChatColor.GRAY + ChatColor.ITALIC + "&o = Italic");
	    cs.sendMessage(
	      ChatColor.GOLD + RainbowUtil.TextLabel("&6 = Gold", 18) + 
	      ChatColor.RED + RainbowUtil.TextLabel("&c = Red", 18) + 
	      ChatColor.GRAY + ChatColor.UNDERLINE + "&n = Underline");
	    cs.sendMessage("-----------------------------------------------------");
	  }

}
