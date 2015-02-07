package com.whede1601.Rainbow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RainbowUtil {

	public static SimpleDateFormat shortDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	  public static String GetDateStringFromLong(long dt) {
		    return shortDateFormat.format(Long.valueOf(dt)).toString();
		  }
	  public static String FullTranslate(String parm)
	  {
	    parm = RainbowUtil.SpecialTranslate(parm);
	    parm = RainbowUtil.TranslateColorString(parm, true);
	    return parm;
	  }

	  public static String RainbowString(String str) {
		    return RainbowString(str, "");
		  }
	  
	  public static String RainbowString(String str, String ctl) {
		    if (ctl.equalsIgnoreCase("x")) return str;

		    StringBuilder sb = new StringBuilder();
		    int idx = 0;
		    boolean useBold = ctl.indexOf('b') >= 0;
		    boolean useItalics = ctl.indexOf('i') >= 0;
		    boolean useUnderline = ctl.indexOf('u') >= 0;

		    for (int i = 0; i < str.length(); i++)
		    {
		      if (idx % 6 == 0) sb.append(ChatColor.RED);
		      else if (idx % 6 == 1) sb.append(ChatColor.GOLD);
		      else if (idx % 6 == 2) sb.append(ChatColor.YELLOW);
		      else if (idx % 6 == 3) sb.append(ChatColor.GREEN);
		      else if (idx % 6 == 4) sb.append(ChatColor.AQUA);
		      else if (idx % 6 == 5) sb.append(ChatColor.LIGHT_PURPLE);

		      if (useBold) sb.append(ChatColor.BOLD);
		      if (useItalics) sb.append(ChatColor.ITALIC);
		      if (useUnderline) sb.append(ChatColor.UNDERLINE);

		      sb.append(str.charAt(i));

		      if (str.charAt(i) == ' ') continue; idx++;
		    }

		    return sb.toString();
		  }
	  public static String ConcatArgs(String[] args, int startIdx) {
		    if (args == null) return "";
		    if (args.length <= 0) return "";

		    StringBuilder sb = new StringBuilder();
		    for (int i = startIdx; i < args.length; i++) {
		      if (sb.length() > 0) {
		        sb.append(" ");
		      }
		      sb.append(args[i]);
		    }
		    return sb.toString();
		  }
	  public static boolean IsCharLetterOrDigit(char ch)
	  {
	    if (((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z') || (ch == '-') || (ch == '_'))) return true;

	    return (ch >= '0') && (ch <= '9');
	  }

	  public static String StringReplace(String src, String key, String val)
	  {
	    int idx = src.indexOf(key);
	    if (idx < 0) return src;
	    return src.substring(0, idx) + val + src.substring(idx + key.length());
	  }
	  public static String SpecialTranslate(String txt) {
		    String res = txt;
		    while (res.indexOf("{star1}") >= 0) res = StringReplace(res, "{star1}", "⚝");
		    while (res.indexOf("{star2}") >= 0) res = StringReplace(res, "{star2}", "★");
		    while (res.indexOf("{star3}") >= 0) res = StringReplace(res, "{star3}", "☆");
		    while (res.indexOf("{space}") >= 0) res = StringReplace(res, "{space}", " ");
		    while (res.indexOf("{_}") >= 0) res = StringReplace(res, "{_}", " ");

		    while (res.indexOf("{heart1}") >= 0) res = StringReplace(res, "{heart1}", "❤");
		    while (res.indexOf("{heart2}") >= 0) res = StringReplace(res, "{heart2}", "♡");
		    while (res.indexOf("{heart3}") >= 0) res = StringReplace(res, "{heart3}", "♥");
		    while (res.indexOf("{cross1}") >= 0) res = StringReplace(res, "{cross1}", "✞");
		    while (res.indexOf("{cross2}") >= 0) res = StringReplace(res, "{cross2}", "♱");
		    while (res.indexOf("{cross3}") >= 0) res = StringReplace(res, "{cross3}", "♰");
		    while (res.indexOf("{diamond1}") >= 0) res = StringReplace(res, "{diamond1}", "♦");
		    while (res.indexOf("{diamond2}") >= 0) res = StringReplace(res, "{diamond2}", "♢");
		    while (res.indexOf("{radio}") >= 0) res = StringReplace(res, "{radio}", "☢");
		    while (res.indexOf("{bio}") >= 0) res = StringReplace(res, "{bio}", "☣");
		    while (res.indexOf("{ankh}") >= 0) res = StringReplace(res, "{ankh}", "☥");
		    while (res.indexOf("{peace}") >= 0) res = StringReplace(res, "{peace}", "☮");
		    while (res.indexOf("{yinyang}") >= 0) res = StringReplace(res, "{yinyang}", "☯");
		    while (res.indexOf("{male}") >= 0) res = StringReplace(res, "{male}", "♂");
		    while (res.indexOf("{female}") >= 0) res = StringReplace(res, "{female}", "♀");
		    while (res.indexOf("{aquarius}") >= 0) res = StringReplace(res, "{aquarius}", "♒");
		    while (res.indexOf("{music1}") >= 0) res = StringReplace(res, "{music1}", "♩");
		    while (res.indexOf("{music2}") >= 0) res = StringReplace(res, "{music2}", "♪");
		    while (res.indexOf("{music3}") >= 0) res = StringReplace(res, "{music3}", "♫");
		    while (res.indexOf("{music4}") >= 0) res = StringReplace(res, "{music4}", "♬");
		    while (res.indexOf("{music5}") >= 0) res = StringReplace(res, "{music5}", "♭");
		    while (res.indexOf("{anchor}") >= 0) res = StringReplace(res, "{anchor}", "⚓");
		    while (res.indexOf("{atom}") >= 0) res = StringReplace(res, "{atom}", "⚛");
		    while (res.indexOf("{bolt}") >= 0) res = StringReplace(res, "{bolt}", "⚡");
		    while (res.indexOf("{plane}") >= 0) res = StringReplace(res, "{plane}", "✈");
		    while (res.indexOf("{flower1}") >= 0) res = StringReplace(res, "{flower1}", "❀");
		    while (res.indexOf("{flower2}") >= 0) res = StringReplace(res, "{flower2}", "❃");
		    while (res.indexOf("{flower3}") >= 0) res = StringReplace(res, "{flower3}", "✼");
		    while (res.indexOf("{newline}") >= 0) res = StringReplace(res, "{newline}", "\n");

		    while (res.indexOf("{fingers}") >= 0) res = StringReplace(res, "{fingers}", "✌");

		    while (res.indexOf("{coffee}") >= 0) res = StringReplace(res, "{coffee}", "☕");
		    while (res.indexOf("{shamrock}") >= 0) res = StringReplace(res, "{shamrock}", "☘");
		    while (res.indexOf("{doctor}") >= 0) res = StringReplace(res, "{doctor}", "☤");
		    while (res.indexOf("{swords}") >= 0) res = StringReplace(res, "{swords}", "⚔");
		    while (res.indexOf("{hermes}") >= 0) res = StringReplace(res, "{hermes}", "⚚");
		    while (res.indexOf("{heaven}") >= 0) res = StringReplace(res, "{heaven}", "☰");
		    while (res.indexOf("{earth}") >= 0) res = StringReplace(res, "{earth}", "☷");
		    while (res.indexOf("{handicap}") >= 0) res = StringReplace(res, "{handicap}", "♿");
		    while (res.indexOf("{ussr}") >= 0) res = StringReplace(res, "{ussr}", "☭");
		    while (res.indexOf("{storm}") >= 0) res = StringReplace(res, "{storm}", "☈");
		    while (res.indexOf("{sun}") >= 0) res = StringReplace(res, "{sun}", "☉");
		    while (res.indexOf("{sad}") >= 0) res = StringReplace(res, "{sad}", "☹");
		    while (res.indexOf("{phone}") >= 0) res = StringReplace(res, "{phone}", "☎");
		    while (res.indexOf("{whiteflag}") >= 0) res = StringReplace(res, "{whiteflag}", "⚐");
		    while (res.indexOf("{blackflag}") >= 0) res = StringReplace(res, "{blackflag}", "⚑");
		    while (res.indexOf("{farsi}") >= 0) res = StringReplace(res, "{farsi}", "☫");
		    while (res.indexOf("{khanda}") >= 0) res = StringReplace(res, "{khanda}", "☬");

		    return res;
		  }
	  public static String TranslateColorString(String parm, boolean IsOp) {
		    return TranslateColorString(parm, IsOp, false);
		  }

		  public static String TranslateColorString(String parm, boolean IsOp, boolean fAllowSpaces)
		  {
		    StringBuilder res = new StringBuilder();
		    boolean pending = false;
		    for (int i = 0; i < parm.length(); i++) {
		      char ch = parm.charAt(i);
		      if ((!pending) && (ch == '&')) { pending = true;
		      }
		      else if (pending) {
		        pending = false;
		        if (ch == '0') { res.append(ChatColor.BLACK);
		        } else if (ch == '1') { res.append(ChatColor.DARK_BLUE);
		        } else if (ch == '2') { res.append(ChatColor.DARK_GREEN);
		        } else if (ch == '3') { res.append(ChatColor.DARK_AQUA);
		        } else if (ch == '4') { res.append(ChatColor.DARK_RED);
		        } else if (ch == '5') { res.append(ChatColor.DARK_PURPLE);
		        } else if (ch == '6') { res.append(ChatColor.GOLD);
		        } else if (ch == '7') { res.append(ChatColor.GRAY);
		        } else if (ch == '8') { res.append(ChatColor.DARK_GRAY);
		        } else if (ch == '9') { res.append(ChatColor.BLUE);
		        } else if (ch == 'a') { res.append(ChatColor.GREEN);
		        } else if (ch == 'b') { res.append(ChatColor.AQUA);
		        } else if (ch == 'c') { res.append(ChatColor.RED);
		        } else if (ch == 'd') { res.append(ChatColor.LIGHT_PURPLE);
		        } else if (ch == 'e') { res.append(ChatColor.YELLOW);
		        } else if (ch == 'f') { res.append(ChatColor.WHITE);
		        } else if ((ch == 'l') && (IsOp)) { res.append(ChatColor.BOLD);
		        } else if ((ch == 'm') && (IsOp)){ res.append(ChatColor.STRIKETHROUGH);
		        } else if ((ch == 'k') && (IsOp)) { res.append(ChatColor.MAGIC);
		        } else if ((ch == 'n') && (IsOp)) { res.append(ChatColor.UNDERLINE);
		        } else if ((ch == 'o') && (IsOp)) { res.append(ChatColor.ITALIC);
		        } else if (ch == 'r') { res.append(ChatColor.RESET); } else {
		          if (ch != '&') continue; res.append("&");
		        }
		      }
		      else if (IsOp) {
		        res.append(ch);
		      }
		      else if (RainbowUtil.IsCharLetterOrDigit(ch)) { res.append(ch); } else {
		        if ((!fAllowSpaces) || (ch != ' ')) continue; res.append(ch);
		      }

		    }
		    return res.toString();
		  }

		  public static String TimeDeltaString_JustMinutesSecs(long ms) {
		    int secs = (int)(ms / 1000L % 60L);
		    int mins = (int)(ms / 1000L / 60L % 60L);
		    return String.format("%02dm %02ds", new Object[] { Integer.valueOf(mins), Integer.valueOf(secs) });
		  }

		  public static String TimeDeltaString(long ms) {
			    int secs = (int)(ms / 1000L % 60L);
			    int mins = (int)(ms / 1000L / 60L % 60L);
			    int hours = (int)(ms / 1000L / 60L / 60L % 24L);
			    int days = (int)(ms / 1000L / 60L / 60L / 24L);
			    return String.format("%02dd %02dh %02dm %02ds", new Object[] { Integer.valueOf(days), Integer.valueOf(hours), Integer.valueOf(mins), Integer.valueOf(secs) });
			  }
		  public static String TextLabel(String str, int padLen) {
		    return str + ChatColor.DARK_GRAY + TextAlignTrailerPerfect(str, padLen);
		  }

		  public static String TextAlignTrailerPerfect(String str, int padLen) {
		    StringBuffer tgt = new StringBuffer();

		    int pixelsTaken = 0;
		    for (int i = 0; i < str.length(); i++) {
		      char ch = str.charAt(i);

		      if (ch == 'f') pixelsTaken += 5;
		      else if (ch == 'i') pixelsTaken += 2;
		      else if (ch == ',') pixelsTaken += 2;
		      else if (ch == 'k') pixelsTaken += 5;
		      else if (ch == 'l') pixelsTaken += 3;
		      else if (ch == '\'') pixelsTaken += 3;
		      else if (ch == 't') pixelsTaken += 4;
		      else if (ch == 'I') pixelsTaken += 4;
		      else if (ch == '[') pixelsTaken += 4;
		      else if (ch == ']') pixelsTaken += 4;
		      else if (ch == ' ') pixelsTaken += 4;
		      else if (ch == '☮') pixelsTaken += 4;
		      else if (ch == '⚔') pixelsTaken += 7;
		      else
		      {
		        pixelsTaken += 6;
		      }
		    }

		    int spacesPixels = padLen * 6 - pixelsTaken;
		    int left = spacesPixels % 4;

		    for (int i = 0; i < left; i++)
		      tgt.append("⁚");
		    for (int i = 0; i < spacesPixels / 4; i++) {
		      tgt.append(" ");
		    }
		    return tgt.toString();
		  }

		  public static String GetTimeStringFromLong(long ms)
		  {
		    Date dt = new Date(ms);
		    int hr = dt.getHours();
		    int min = dt.getMinutes();

		    if (hr < 12) return String.format("%02d:%02dam EST", new Object[] { Integer.valueOf(hr == 0 ? 12 : hr), Integer.valueOf(min) });
		    hr -= 12;
		    return String.format("%02d:%02dpm EST", new Object[] { Integer.valueOf(hr == 0 ? 12 : hr), Integer.valueOf(min) });
		  }

		  static long GetMSFromString(String strTime)
		  {
		    try
		    {
		      long ms = 0L;
		      long multi = 1L;
		      if (strTime.endsWith("ms"))
		      {
		        strTime = strTime.substring(0, strTime.length() - 2);
		        multi = 1L;
		      }
		      else if (strTime.endsWith("s"))
		      {
		        strTime = strTime.substring(0, strTime.length() - 1);
		        multi = 1000L;
		      }
		      else if (strTime.endsWith("m"))
		      {
		        strTime = strTime.substring(0, strTime.length() - 1);
		        multi = 60000L;
		      }
		      else if (strTime.endsWith("h"))
		      {
		        strTime = strTime.substring(0, strTime.length() - 1);
		        multi = 3600000L;
		      }
		      else if (strTime.endsWith("d"))
		      {
		        strTime = strTime.substring(0, strTime.length() - 1);
		        multi = 86400000L;
		      }
		      else
		      {
		        multi = 1000L;
		      }
		      return multi * Long.parseLong(strTime);
		    }
		    catch (Exception localException)
		    {
		    }

		    return 0L;
		  }

}
