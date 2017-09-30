package com.adsale.HEATEC.database.model;

public class LocalMessage {
	public String[] localNotification_time;
	public String[] localNotification_module;
	public String[] localNotification_text_tc;
	public String[] localNotification_text_sc;
	public String[] localNotification_text_en;
	
	public LocalMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocalMessage(String[] localNotification_time,
			String[] localNotification_module,
			String[] localNotification_text_tc,
			String[] localNotification_text_sc,
			String[] localNotification_text_en) {
		super();
		this.localNotification_time = localNotification_time;
		this.localNotification_module = localNotification_module;
		this.localNotification_text_tc = localNotification_text_tc;
		this.localNotification_text_sc = localNotification_text_sc;
		this.localNotification_text_en = localNotification_text_en;
	}
	public String[] getLocalNotification_time() {
		return localNotification_time;
	}
	public void setLocalNotification_time(String[] localNotification_time) {
		this.localNotification_time = localNotification_time;
	}
	public String[] getLocalNotification_module() {
		return localNotification_module;
	}
	public void setLocalNotification_module(String[] localNotification_module) {
		this.localNotification_module = localNotification_module;
	}
	public String[] getLocalNotification_text_tc() {
		return localNotification_text_tc;
	}
	public void setLocalNotification_text_tc(String[] localNotification_text_tc) {
		this.localNotification_text_tc = localNotification_text_tc;
	}
	public String[] getLocalNotification_text_sc() {
		return localNotification_text_sc;
	}
	public void setLocalNotification_text_sc(String[] localNotification_text_sc) {
		this.localNotification_text_sc = localNotification_text_sc;
	}
	public String[] getLocalNotification_text_en() {
		return localNotification_text_en;
	}
	public void setLocalNotification_text_en(String[] localNotification_text_en) {
		this.localNotification_text_en = localNotification_text_en;
	}
	
	
}
