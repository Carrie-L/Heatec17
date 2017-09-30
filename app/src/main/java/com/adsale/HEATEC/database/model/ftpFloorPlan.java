package com.adsale.HEATEC.database.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.util.Log;

public class ftpFloorPlan {
	private String cmsID;
	private ArrayList<String> files;
	private String version;
	
	public ftpFloorPlan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ftpFloorPlan(String cmsID, ArrayList<String> files, String version) {
		super();
		this.cmsID = cmsID;
		this.files = files;
		this.version = version;
	}
	public String getCmsID() {
		return cmsID;
	}
	public void setCmsID(String cmsID) {
		this.cmsID = cmsID;
	}
	public ArrayList<String> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<String> files) {
		this.files = files;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}