package com.adsale.HEATEC.database.model;

public class M1 {
	
	public String version;
	public String time;
	public String imgUrl;
	public String tablet;
	public String phone;
	public String imgNameCN;
	public String imgNameEN;
	public String imgNameTW;
	public String function;
	
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
	
	public String getImgName(int language){
		String imgName="";
		if(language==0){
			imgName=imgNameTW;
		}else if(language==1){
			imgName=imgNameEN;
		}else{
			imgName=imgNameCN;
		}
		return imgName;
	}
	

}
