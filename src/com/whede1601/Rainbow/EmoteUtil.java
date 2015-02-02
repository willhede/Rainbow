package com.whede1601.Rainbow;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.Files;

public class EmoteUtil {
	
      public static _emotes emotes = new _emotes();
	  public static SimpleDateFormat shortDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	  public static ConcurrentHashMap<String, Long> timeLastUsed = new ConcurrentHashMap();
//	  private static String OldFilename = "Emotes.dat.vSnapshot";
//	  private static  String Filename = "Emotes.dat";
//	  private static  String Directory = "plugin" + File.separator + "Rainbow" + File.separator + Filename;
	  
	  public static void LoadEmotes()
	  {
	    try
	    {
		  long msStart = System.currentTimeMillis();
	      File file = new File("plugins" + File.separatorChar + "Rainbow", "Emotes.dat");
	      FileInputStream f = new FileInputStream(file);	      
	      ObjectInputStream s = new ObjectInputStream(new BufferedInputStream(f));
	      emotes = (_emotes)s.readObject();
	      s.close();

	      long msEnd = System.currentTimeMillis();
	      String msg = String.format("%-20s: " + ChatColor.WHITE + "%5d emotes.   Took %3d ms", new Object[] { "Emote DB Loaded", Integer.valueOf(emotes.emotes.size()), Long.valueOf(msEnd - msStart) });
	      System.out.println(msg);
	      
	      if (emotes.emotes.containsKey("jemote"))
	      {
	        emotes.emotes.remove("jemote");
//	        _JoeUtils.ConsoleMsg(ChatColor.LIGHT_PURPLE + "--- Removed emote named 'jemote' for safety.");
	      }

	    }
	    catch (Throwable exc)
	    {
	      System.out.println("Starting New Emote Database...");
	      emotes = new _emotes();
	      exc.printStackTrace();
	    }
	  }

	  public static void SaveEmotes()
	  {
	    try {
	      File file = new File("plugins" + File.separatorChar + "Rainbow", "Emotes.dat");
	      FileOutputStream f = new FileOutputStream(file);
	      ObjectOutputStream s = new ObjectOutputStream(new BufferedOutputStream(f));
	      s.writeObject(emotes);
	      s.close();
	    }
	    catch (Throwable exc)
	    {
	      System.out.println("**********************************************");
	      System.out.println("SaveEmotes: " + exc.toString());
	      System.out.println("**********************************************");
	    }
	  }
	  
	private static boolean CanDoEmote(CommandSender cs, String emote) {
	      if (cs.hasPermission("rainbow.jemote." + emote))
	      {
	        if (!cs.hasPermission("rainbow.jemote.*"))
	        {
	          return false;
	        }
	      }
	      return true;
	}
	  public static void ShowEmoteDetails(CommandSender cs, String emote)
	  {
	    emote = emote.toLowerCase();
	    _EmoteEntry entry = (_EmoteEntry)emotes.emotes.get(emote);
	    if (entry == null)
	    {
	      cs.sendMessage(ChatColor.RED + "Emote is not defined: " + ChatColor.YELLOW + emote);
	      return;
	    }
	    cs.sendMessage(ChatColor.GREEN + "---------------------------------------");
	    cs.sendMessage(ChatColor.GOLD + "Emote: " + ChatColor.YELLOW + emote);
	    cs.sendMessage(ChatColor.WHITE + "Created " + ChatColor.AQUA + RainbowUtil.GetDateStringFromLong(entry.msCreated) + ChatColor.WHITE + " by " + ChatColor.YELLOW + entry.createdBy);
	    cs.sendMessage(ChatColor.WHITE + "Last Updated " + ChatColor.AQUA + RainbowUtil.GetDateStringFromLong(entry.msUpdated) + ChatColor.WHITE + " by " + ChatColor.YELLOW + entry.updatedBy);
	    for (String key : entry.msg.keySet())
	    {
	    	cs.sendMessage(ChatColor.AQUA + key + ": " + ChatColor.GREEN + (String)entry.msg.get(key));
	    }
	    cs.sendMessage(ChatColor.GREEN + "---------------------------------------");
	  }

	public static void HandleEmote(Player player, String args[]) {
		String emote = args[0].toLowerCase();
		long time = System.currentTimeMillis();
		if (!emotes.emotes.containsKey(emote)) {
			player.sendMessage("Emote does not exist");
			return;
		}
		CanDoEmote(player, emote);
	    _EmoteEntry entry = (_EmoteEntry)emotes.emotes.get(emote);
	    if ((timeCanUseEmote(player, time)) != true) return;
	    if (args.length > 1)
	    {	
	    	String tgtName = args[1];
	    	Player pTgt = Bukkit.getPlayer(tgtName);
	    	if (tgtName.equalsIgnoreCase(player.getName()))
	    	{
// TODO Sending message.    
	    	}
	        String trailer = String.format((String)entry.msg.get("other"), new Object[] { pTgt.getName() });
	        Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " " + trailer);
	        timeLastUsed.put(player.getName(), System.currentTimeMillis());
	        System.out.println(System.currentTimeMillis());
	        return;
	    }
	   Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " " + (String)entry.msg.get("default"));
	   timeLastUsed.put(player.getName(), System.currentTimeMillis());
	}

	public static boolean timeCanUseEmote(Player player, Long CurrentTime)
	{
		String p = player.getName();
		if (!(timeLastUsed.containsKey(p))) return true;
		long LastUsed = timeLastUsed.get(p);
		long NextUse = LastUsed + 5000;
		long TimeLeft = NextUse - CurrentTime;
        if (TimeLeft < 0L) TimeLeft = 0L;
		if (TimeLeft != 0)
		{
			String strLeft = RainbowUtil.TimeDeltaString_JustMinutesSecs(TimeLeft);
			player.sendMessage(ChatColor.RED + "Can use emotes again in " + RainbowUtil.TextLabel(strLeft, 0));
			return false;
		}
		return true;		
	}
	public static String GetCommaList(CommandSender sender) {
		String strEmotes = GetCommaList();
	    sender.sendMessage(ChatColor.GREEN + "All Emotes: " + ChatColor.YELLOW + strEmotes);
		return null;
	}

	private static String GetCommaList() {
		Set<String> keys = emotes.emotes.keySet();
	    StringBuffer buf = new StringBuffer();
	    for (String str : keys)
	    {
	      if (buf.length() > 0) buf.append(", ");
	      buf.append(str);
	    }
		return buf.toString();
	}	  
	  public static void ShowUsage(CommandSender sender)
	  {
	    sender.sendMessage(ChatColor.AQUA + "----- Emotion Options -----");
	    sender.sendMessage(ChatColor.GOLD + "/jemote list " + ChatColor.WHITE + " -- List all emotes");
	    sender.sendMessage(ChatColor.GOLD + "/jemote mine " + ChatColor.WHITE + " -- List my emotes");

	    if (sender.hasPermission("Rainbow.edit"))
	    {
	      sender.sendMessage(ChatColor.LIGHT_PURPLE + "- Admin Options: "+ ChatColor.WHITE + "Using 'smile' as example...");
	      sender.sendMessage(ChatColor.LIGHT_PURPLE + "/jemote info smile");
	      sender.sendMessage(ChatColor.LIGHT_PURPLE + "/jemote delete smile");
	      sender.sendMessage(ChatColor.LIGHT_PURPLE + "/jemote add smile " + ChatColor.AQUA + "self" + ChatColor.GREEN + " smiles in a mirror.");
	      sender.sendMessage(ChatColor.LIGHT_PURPLE + "/jemote add smile " + ChatColor.AQUA + "other" + ChatColor.GREEN + " smiles at %s happily!");
	    }
	  }
		@Deprecated
		public static void addDefaultEmotes()
		{
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add agree default agrees.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add agree other agrees with %s");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add answer default Answers all questions!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add answer other Answer's The 99th question of %s!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add attack default Is using kamehameha!!!!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add attack other Is using kamehameha!! on %s");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add bad default Says:'watch out we got a badass over here'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add bad other Says to '%s:watch out we got a badass over here'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add bb default Says:'what's up doc!'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add bb other Says to %s:'what's up doc!'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add bearhug default give's everyone a big bear hug!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add bearhug other give's %s a big bear hug!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add beg default begs.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add beg other begs %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add blush default blushes.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add blush other blushes at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add cheer default cheers.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add cheer other cheers for %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add clap default claps.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add clap other claps for %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add comfort default comforts everyone.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add comfort other comforts %s.i");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add down default Turn down for what?!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add down other Turns down for what with %s!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add explode default explodes!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add explode other explodes on %s!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add facepalm default Facepalms..!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add facepalm other facepalms at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add fangirl default fangirls!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add fangirl other fangirls at %s!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add fistbump default fistbumps everyone!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add fistbump other fistbumps %s!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add flirt default flirts");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add flirt other flirts with %s");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add fox default What does the FOX say!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add fox other says to %s What does the FOX say!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add joke default default: says:'What’s Beethoven’s favorite fruit?…Ba-na-na-naaa!'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add joke other says to %s:'What’s Beethoven’s favorite fruit?…Ba-na-na-naaa!'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add kegg default Says:'i got the kinder fever OHOOOHOO!'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add kegg other Says to %s:'i got the kinder fever OHOOOHOO!'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add kiss default kisses.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add kiss other kisses %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add laugh default laughs.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add laugh other laughs at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add lick default Licks lips.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add lick other Licks %s lips..");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add lift default 'do u even lift,bruh'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add lift other 'do u even lift,%s");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add like default likes that status!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add like other likes what %s just said!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add lol default Lol's on everyone with a duckface!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add lol other Lol's on %s with a duckface!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add mad default 'Are u mad, brah'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add mad other Are u mad, %s");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add milkshake default pulls out a milkshake. All the boys come to the yard!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add milkshake other shares a milkshake with %s. YUM!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add nod default nods.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add nod other nods at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add nutella default Is eating a big sandwich with Nutella!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add nutella other Is eating Nutella on a sandwich with %s !");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add nuzzle default nuzzles everyone.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add nuzzle other nuzzles up close to %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add omg default OMGs!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add omg other OMGs at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add panic default panics!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add panic other panics at %s!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add party default Is Celebrating with no reason!!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add party other partys at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add pizza default Is eating a Big Big PIZZA!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add pizza other is eating a Big Big PIZZA with %s");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add point default points.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add point other points at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add poke default agrees with %s");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add poke other agrees with %s");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add pout default pouts.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add pout other pouts at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add rockstar default says:'So what? I'm still a rock star!'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add rockstar other says to %s:'So what? I'm still a rock star!'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add sad default Is sitting verry sad in that lonely corner!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add sad other sads at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add scream default screams!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add scream other screams at %s!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add selfie default takes a selfie!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add selfie other takes a selfie with %s!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add sexy default Is bringing the Sexy back!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add sexy other Is bringing with %s the Sexy back!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add shake default shakes head.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add shake other shakes head at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add silly default sitting silly in a lonely corner!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add silly other sitting silly with %s in a lonely corner!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add sing default sings.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add sing other sings.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add smash default smashes his head to a Wall");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add smash other smashes his head with %s to a Wall");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add smirk default smirks.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add smirk other smirks at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add snack default steal all snacks!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add snack other steals %s snacks");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add stare default stares.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add stare other stares at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add swag default swaggers in.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add swag other swaggers toward %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add taco default Threw a taco!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add taco other Threw %s a taco!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add tap default taps foot patiently.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add tap other taps %s on the shoulder.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add thank default thanks everyone.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add thank other thanks %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add think default thinks.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add think other thinks at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add tickle default tickles everyone's feet!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add tickle other tickles %s's feet!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add tmyk default says: The More You Know ✩");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add tmyk other says to %s: The More You Know ✩");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add touch default says Cant Touch this!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add touch other says Cant Touch this to %s!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add tractor default thinks that tractor's SEXY!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add tractor other thinks %s's tractor is SEXY!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add troll default says: 'I'm from Planet Minecraft'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add troll other says to %s: 'I'm from Planet Minecraft'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add twerk default dances like Miley Cyrus!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add twerk other dances like Miley Cyrus with %s!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add unamused default looks unamused.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add unamused other gives %s an unamused face.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add violin default plays a sad violin.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add violin other plays a sad violin for %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add wave default waves.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add wave other waves to %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add welcome default welcomes everyone.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add welcome other welcomes %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add wiggle default saying #Wiggle Wiggle Wiggle");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add wiggle other wiggles at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add wink default winks an eye.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add wink other winks an eye at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add wiz default says: 'You're a wizard Harry'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add wiz other says to %s: 'You're a wizard Harry'");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add yawn default yawns.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add yawn other yawns at %s.");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add yolo default Yells 'YOLO' after playing a banjo!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jemote add yolo other yolos at %s.");
		}
}