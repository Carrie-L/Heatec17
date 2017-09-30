package com.adsale.HEATEC.database.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.util.Log;

public class ftpNotification {
	private ArrayList<String> files;
	private String version;
	
	public ftpNotification() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ftpNotification(  ArrayList<String> files, String version) {
		super();
		this.files = files;
		this.version = version;
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