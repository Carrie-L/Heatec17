package com.adsale.HEATEC.database.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.util.Log;

public class ftpName {
	private String en;
	private String sc;
	private String tc;
	
	
	public ftpName() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ftpName(String en, String sc, String tc) {
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

}