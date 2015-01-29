package com.whede1601.Rainbow;

import java.io.Serializable;

public class _CronData implements Serializable{
	public String jobName = null;
	public String cmdToRun = null;
	public long msDelay = 1L;
	public long msLastRun = 0L;
}
