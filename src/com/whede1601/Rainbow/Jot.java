package com.whede1601.Rainbow;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Jot implements CommandExecutor {

	private Rainbow plugin;

	public Jot(Rainbow plugin) {
		this.plugin = plugin; // Store the plugin in situations where youdd need it.
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if ((args.length >= 1) && (args[0].equalsIgnoreCase("top")))
	    {
	      int page = 1;
	      try
	      {
	        page = Integer.parseInt(args[1]);
	      }
	      catch (Exception localException)
	      {
	      }
	      if (page < 1) page = 1;
	      NameUtil.ShowTopPage(sender, page);
	      return true;
	    }
	    if ((args.length == 2) && (args[0].equalsIgnoreCase("util"))) {
	    	if (args[1].equalsIgnoreCase("Cron"))
	    	{
	  	      _CronData data = (_CronData)cron.mapData.get("jotsave");
		      if (data == null) data = new _CronData();
		      data.jobName = "jotsave";
		      data.cmdToRun = "jot util save";
		      data.msDelay = (long) 120000;
		      data.msLastRun = System.currentTimeMillis();
		      cron.mapData.put("jotsave", data);
		      cron.SaveCron();
	    	}
	    	if (args[1].equalsIgnoreCase("save"))
	    	{
	    		NameUtil.IntrumSave();
	    	}
	    	
	    }
	    
	    if (args.length == 1)
	    {
	      NameUtil.ShowPlayerLoginTime(sender, args[0]);
	      return true;
	    }		
	    return false;
	}

}
