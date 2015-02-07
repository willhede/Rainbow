package com.whede1601.Rainbow;

import java.io.Serializable;

import org.bukkit.ChatColor;

class EggReward2 implements Serializable
{
  String msg = "";
  String cmd = "";
  double prob = 0.0D;

  public String toString()
  {
    return String.format(ChatColor.GOLD + "[%06.3f%s, " + ChatColor.YELLOW + "msg='%s', " + ChatColor.RED + "cmd='%s']", new Object[] { Double.valueOf(this.prob), "%", this.msg, this.cmd });
  }
}