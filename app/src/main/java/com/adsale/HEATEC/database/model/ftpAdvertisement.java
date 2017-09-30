package com.adsale.HEATEC.database.model;

import java.util.ArrayList;

/**
 * 广告
 * 检测对应的广告位置是否需要更新
 * @author orz
 *
 */
public class ftpAdvertisement {
	private ArrayList<String> files;
	private String version;
	
	public ftpAdvertisement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ftpAdvertisement(ArrayList<String> files, String version) {
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
