package com.adsale.HEATEC.database.model;

public class ftpRegEndMessage {
	private String en;
	private String sc;
	private String tc;
	
	public ftpRegEndMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ftpRegEndMessage(String en, String sc, String tc) {
		super();
		this.en = en;
		this.sc = sc;
		this.tc = tc;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public String getSc() {
		return sc;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
	public String getTc() {
		return tc;
	}
	public void setTc(String tc) {
		this.tc = tc;
	}
	
	public String getRegMessage(int language){
		if(language==0){
			return tc;
		}else if(language==1){
			return en;
		}else{
			return sc;
		}
		
	}
	
}
