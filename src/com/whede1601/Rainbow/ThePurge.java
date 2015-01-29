package com.whede1601.Rainbow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

public class ThePurge {

	public static void Scoreboard() 
	{
		System.out.println("Scoreboard");
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("Deaths", "deathCount");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score = objective.getScore(ChatColor.GREEN + "Kills:");
		score.setScore(1);
	}
}
