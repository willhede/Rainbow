package com.whede1601.Rainbow;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class Namedatabase1 implements Serializable{
	public ConcurrentHashMap<String, NameEntry> pdata = new ConcurrentHashMap();

}
