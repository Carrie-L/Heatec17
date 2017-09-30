package com.adsale.HEATEC.database.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ftpExhibitorList implements Parcelable{
	private String version;
	private ftpExDisplay display;
	public ftpExhibitorList(String version, ftpExDisplay display) {
		super();
		this.version = version;
		this.display = display;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ftpExDisplay getDisplay() {
		return display;
	}



	public void setDisplay(ftpExDisplay display) {
		this.display = display;
	}



	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	
}
