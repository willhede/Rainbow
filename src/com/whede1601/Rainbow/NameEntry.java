package com.whede1601.Rainbow;

import java.io.Serializable;

public class NameEntry implements Serializable{
	  public long msTotal = 0L;
	  public long msLastLogin = System.currentTimeMillis();
	  public long msLastLogout = System.currentTimeMillis();
}
