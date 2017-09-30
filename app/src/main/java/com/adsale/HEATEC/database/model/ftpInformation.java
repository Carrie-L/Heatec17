package com.adsale.HEATEC.database.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ftpInformation implements Parcelable {

	public String version;
	public String date;
	public String days;
	public ftpName name;
	public String registration;
	public String regiterLink;
	public String functionpath;
	public ftpAdvertisement advertisement;
	public ftpExhibitorList exhibitorList;
	public ftpFloorPlan floorplan;
	public ftpFloorPlanImages floorplanimages;
	public ftpEvent event;
	public ftpSeminar seminar;
	public ftpNotification notification;
	public ftpRegEndMessage regEndMessage;
	public ftpContentList contentList;

	public ftpInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	




	public String getVersion() {
		return version;
	}






	public void setVersion(String version) {
		this.version = version;
	}






	public String getDate() {
		return date;
	}






	public void setDate(String date) {
		this.date = date;
	}






	public String getDays() {
		return days;
	}






	public void setDays(String days) {
		this.days = days;
	}






	public ftpName getName() {
		return name;
	}






	public void setName(ftpName name) {
		this.name = name;
	}






	public String getRegistration() {
		return registration;
	}






	public void setRegistration(String registration) {
		this.registration = registration;
	}






	public String getRegiterLink() {
		return regiterLink;
	}






	public void setRegiterLink(String regiterLink) {
		this.regiterLink = regiterLink;
	}






	public String getFunctionpath() {
		return functionpath;
	}






	public void setFunctionpath(String functionpath) {
		this.functionpath = functionpath;
	}






	public ftpAdvertisement getAdvertisement() {
		return advertisement;
	}






	public void setAdvertisement(ftpAdvertisement advertisement) {
		this.advertisement = advertisement;
	}






	public ftpExhibitorList getExhibitorList() {
		return exhibitorList;
	}






	public void setExhibitorList(ftpExhibitorList exhibitorList) {
		this.exhibitorList = exhibitorList;
	}






	public ftpFloorPlan getFloorplan() {
		return floorplan;
	}






	public void setFloorplan(ftpFloorPlan floorplan) {
		this.floorplan = floorplan;
	}






	public ftpFloorPlanImages getFloorplanimages() {
		return floorplanimages;
	}






	public void setFloorplanimages(ftpFloorPlanImages floorplanimages) {
		this.floorplanimages = floorplanimages;
	}






	public ftpEvent getEvent() {
		return event;
	}






	public void setEvent(ftpEvent event) {
		this.event = event;
	}






	public ftpSeminar getSeminar() {
		return seminar;
	}






	public void setSeminar(ftpSeminar seminar) {
		this.seminar = seminar;
	}






	public ftpNotification getNotification() {
		return notification;
	}






	public void setNotification(ftpNotification notification) {
		this.notification = notification;
	}






	public ftpRegEndMessage getRegEndMessage() {
		return regEndMessage;
	}






	public void setRegEndMessage(ftpRegEndMessage regEndMessage) {
		this.regEndMessage = regEndMessage;
	}






	public ftpInformation(String version, String date, String days,
			ftpName name, String registration, String regiterLink,
			String functionpath, ftpAdvertisement advertisement,
			ftpExhibitorList exhibitorList, ftpFloorPlan floorplan,
			ftpFloorPlanImages floorplanimages, ftpEvent event,
			ftpSeminar seminar, ftpNotification notification,
			ftpRegEndMessage regEndMessage) {
		super();
		this.version = version;
		this.date = date;
		this.days = days;
		this.name = name;
		this.registration = registration;
		this.regiterLink = regiterLink;
		this.functionpath = functionpath;
		this.advertisement = advertisement;
		this.exhibitorList = exhibitorList;
		this.floorplan = floorplan;
		this.floorplanimages = floorplanimages;
		this.event = event;
		this.seminar = seminar;
		this.notification = notification;
		this.regEndMessage = regEndMessage;
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