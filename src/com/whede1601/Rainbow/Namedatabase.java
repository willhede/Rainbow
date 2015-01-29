package com.whede1601.Rainbow;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class Namedatabase implements Serializable {
	public ConcurrentHashMap<String, String> pdata = new ConcurrentHashMap();
}
