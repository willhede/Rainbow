package com.whede1601.Rainbow;

import java.io.PrintStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ragequit
  implements CommandExecutor
{
  private Rainbow plugin;

  public Ragequit(Rainbow plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player))
    {
      System.out.println("Not from console!");
      return false;
    }
    Player player = (Player)sender;
    String name = ChatColor.YELLOW + player.getDisplayName();
    if (player.isOp()) name = ChatColor.RED + player.getDisplayName();
    Bukkit.broadcastMessage(name + ChatColor.DARK_RED + ChatColor.BOLD + " RAGE QUIT " + ChatColor.RESET + RainbowUtil.RainbowString("The Server!"));
    player.kickPlayer("Rage Quit!");
    return true;
  }
}