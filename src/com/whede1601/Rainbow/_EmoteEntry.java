package com.whede1601.Rainbow;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;


public class _EmoteEntry
  implements Serializable
{
  public ConcurrentHashMap<String, String> msg;
  public long msCreated;
  public long msUpdated;
  public String createdBy;
  public String updatedBy;

  public _EmoteEntry(String emoteName)
  {
    this.msg = new ConcurrentHashMap();
    this.msg.put("default", String.format("%ss.", new Object[] { emoteName }));
    this.msg.put("self", String.format("%ss in a mirror.", new Object[] { emoteName }));
    this.msg.put("other", String.format("%ss at ", new Object[] { emoteName }) + "%s.");
  }
}