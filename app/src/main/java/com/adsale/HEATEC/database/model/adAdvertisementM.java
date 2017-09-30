package com.adsale.HEATEC.database.model;

import java.util.Date;

/**
 * 对应的M1-M6的每一个广告详细信息。
 * @author orz
 *
 */
public class adAdvertisementM {
	
	public String cmsID;
	public String filepath;
	public String format;
	public String function;
	public String isChange;
	public String time;
	public String version;
	 
	public String[] action_companyID;
	public String[] action_eventID;
	public String[] action_newsID;
	public String[] action_seminariD;
	public String[] android_phone;
	public String[] android_tablet;
	public String[] iOS_pad;
	public String[] iOS_phone;
	
	public String getStart() {
		if(time != null && time.split("-").length > 0)
		{
			return time.split("-")[0];
		}
		return "";
	}
	 
	public String getEnd() {
		if(time != null && time.split("-").length > 0)
		{
			return time.split("-")[1];
		}
		return "";
	}
	
	
}
