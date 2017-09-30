package com.adsale.HEATEC.database.model;

import sanvio.libs.util.DateUtils;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class clsScheduleInfo implements Parcelable {

	private int mScheduleID;
	private String mTitle;
	private String mNote;
	private String mLocation;
	private String mCompanyID;
	private String mStartTime;
	private int mLength;
	private int mAllday;

	public clsScheduleInfo(String mTitle, String mNote, String mLocation, String mCompanyID, String mStartTime, int mLength, int mAllday) {
		super();
		this.mTitle = mTitle;
		this.mNote = mNote;
		this.mLocation = mLocation;
		this.mCompanyID = mCompanyID;
		this.mStartTime = mStartTime;
		this.mLength = mLength;
		this.mAllday = mAllday;
	}

	public static final Parcelable.Creator<clsScheduleInfo> CREATOR = new Parcelable.Creator<clsScheduleInfo>() {
		public clsScheduleInfo createFromParcel(Parcel in) {
			return new clsScheduleInfo(in);
		}

		public clsScheduleInfo[] newArray(int size) {
			return new clsScheduleInfo[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mScheduleID);
		dest.writeString(mTitle);
		dest.writeString(mNote);
		dest.writeString(mLocation);
		dest.writeString(mCompanyID);
		dest.writeString(mStartTime);
		dest.writeInt(mLength);
		dest.writeInt(mAllday);
	}

	public clsScheduleInfo(Parcel in) {
		mScheduleID = in.readInt();
		mTitle = in.readString();
		mNote = in.readString();
		mLocation = in.readString();
		mCompanyID = in.readString();
		mStartTime = in.readString();
		mLength = in.readInt();
		mAllday = in.readInt();
	}

	public clsScheduleInfo(Cursor cursor) {
		mScheduleID = cursor.getInt(cursor.getColumnIndex("ScheduleID"));
		mTitle = cursor.getString(cursor.getColumnIndex("Title"));
		mNote = cursor.getString(cursor.getColumnIndex("Note"));
		mLocation = cursor.getString(cursor.getColumnIndex("Location"));
		mCompanyID = cursor.getString(cursor.getColumnIndex("CompanyID"));
		mStartTime = cursor.getString(cursor.getColumnIndex("StartTime"));
		mLength = cursor.getInt(cursor.getColumnIndex("Length"));
		mAllday = cursor.getInt(cursor.getColumnIndex("Allday"));
	}

	public clsScheduleInfo() {
		mScheduleID = -1;
		mTitle = "";
		mNote = "";
		mLocation = "";
		mCompanyID = "";
		mStartTime = "";
		mLength = 0;
		mAllday = 0;
	}

	/**
	 * @return the mScheduleID
	 */
	public int getScheduleID() {
		return mScheduleID;
	}

	/**
	 * @param mScheduleID
	 *            the mScheduleID to set
	 */
	public void setScheduleID(int mScheduleID) {
		this.mScheduleID = mScheduleID;
	}

	/**
	 * @return the mTitle
	 */
	public String getTitle() {
		return mTitle;
	}

	/**
	 * @param mTitle
	 *            the mTitle to set
	 */
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	/**
	 * @return the mNote
	 */
	public String getNote() {
		return mNote;
	}

	/**
	 * @param mNote
	 *            the mNote to set
	 */
	public void setNote(String mNote) {
		this.mNote = mNote;
	}

	/**
	 * @return the mLocation
	 */
	public String getLocation() {
		return mLocation;
	}

	/**
	 * @param mLocation
	 *            the mLocation to set
	 */
	public void setLocation(String mLocation) {
		this.mLocation = mLocation;
	}

	/**
	 * @return the mCompanyID
	 */
	public String getCompanyID() {
		return mCompanyID;
	}

	/**
	 * @param mCompanyID
	 *            the mCompanyID to set
	 */
	public void setCompanyID(String mCompanyID) {
		this.mCompanyID = mCompanyID;
	}

	/**
	 * @return the mStartTime
	 */
	public String getStartTime() {
		return mStartTime;
	}

	/**
	 * @param mStartTime
	 *            the mStartTime to set
	 */
	public void setStartTime(String mStartTime) {
		this.mStartTime = mStartTime;
	}

	/**
	 * @return the mLength
	 */
	public int getLength() {
		return mLength;
	}

	/**
	 * @param mLength
	 *            the mLength to set
	 */
	public void setLength(int mLength) {
		this.mLength = mLength;
	}

	/**
	 * @return the mAllday
	 */
	public int getAllday() {
		return mAllday;
	}

	/**
	 * @param mAllday
	 *            the mAllday to set
	 */
	public void setAllday(int mAllday) {
		this.mAllday = mAllday;
	}

	public String getStartTime(int curLanguage) {
		return DateUtils.FormatDateByString("yyyy-MM-dd HH:mm", "yyyy-MM-dd hh:mm a", mStartTime);
	}

}