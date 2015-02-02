package com.whede1601.Rainbow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

public class NameColor implements CommandExecutor {

	private Rainbow plugin;
	private Essentials essentials;

	public NameColor(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where youdd need it.
		Plugin ess = Bukkit.getServer().getPluginManager().getPlugin("Essentials");	
		essentials = (Essentials) ess;
		}
	public void SendUsage(Player p)
	  {
	    String pName = p.getName();
	    int mid = pName.length() / 2;
	    String left = pName.substring(0, mid);
	    String right = pName.substring(mid);
	    p.sendMessage(ChatColor.RED + "Try Example: " + ChatColor.DARK_AQUA + "/namecolor " + ChatColor.WHITE + "&b" + left + "&d" + right);
	    p.sendMessage(ChatColor.RED + "That would make your name: " + ChatColor.AQUA + left + ChatColor.LIGHT_PURPLE + right);
	  }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player))
	    {
	      System.out.println("Not from console!");
	      return false;
	    }
	    Player player = (Player) sender;
	    if (args.length <= 0)
	    {
	      SendUsage(player);
	      return false;
	    }
	    if (!player.hasPermission("rainbow.namecolor"))
	    {
	    	return false;
	    }
	    String pName = player.getName();
	    String newName = args[0];
		newName = RainbowUtil.TranslateColorString(newName, false, false);
	    if (!pName.equalsIgnoreCase(ChatColor.stripColor(newName)))
	    {
	      player.sendMessage(ChatColor.RED + "Name must match your own. You can just add color!");
	      SendUsage(player);
	      return false;
	    }
		player.sendMessage(newName);
		System.out.println(newName);
		User user = essentials.getUser(pName);
		user.setNickname(newName);
	    player.sendMessage(ChatColor.GREEN + "Your colored name is now: " + ChatColor.YELLOW + newName);
		return true;
	  	}
}
