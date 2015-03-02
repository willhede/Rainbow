package com.whede1601.Rainbow;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CmdTabCompleter implements TabCompleter{

	private Rainbow plugin;

	public CmdTabCompleter(Rainbow plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (cmd.getName().equalsIgnoreCase("diw"))
		{
			ArrayList<String> staticdiw = new ArrayList<>();

			if (staticdiw.isEmpty())
			{
				staticdiw.add("deathtoggle");
				staticdiw.add("addlore");
				staticdiw.add("clearlore");
				staticdiw.add("addname");
				staticdiw.add("clearname");
				staticdiw.add("addtag");
				staticdiw.add("cleartag");
				staticdiw.add("xptrain");
				staticdiw.add("god");
				staticdiw.add("king");
				staticdiw.add("queen");
				staticdiw.add("prince");
			}
			return staticdiw;
		}
		if (cmd.getName().equalsIgnoreCase("jemote"))
		{
			ArrayList<String> staticjemote = new ArrayList<>();
			if (staticjemote.isEmpty())
			{
				staticjemote.add("list");
				staticjemote.add("info");
				staticjemote.add("delete");
				staticjemote.add("add");
				staticjemote.add("info");

			}
			return staticjemote;
		}
		if (cmd.getName().equalsIgnoreCase("kinder"))
		{
			ArrayList<String> statickinder = new ArrayList<>();
			if (statickinder.isEmpty())
			{
				statickinder.add("add");
				statickinder.add("setcmd");
				statickinder.add("setmsg");
				statickinder.add("setchance");
				statickinder.add("del");
				statickinder.add("rename");
				statickinder.add("give");
				statickinder.add("load | load");
			}
			return statickinder;
		}
		if (cmd.getName().equalsIgnoreCase("cron"))
		{
			ArrayList<String> staticcron = new ArrayList<>();
			if (staticcron.isEmpty())
			{
				staticcron.add("add");
				staticcron.add("delete");
				staticcron.add("list");
				staticcron.add("setdelay");
				staticcron.add("setcmd");
			}
			return staticcron;
		}
		return null;
	}

}
