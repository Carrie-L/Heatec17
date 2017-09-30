package com.adsale.HEATEC.database.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class clsFloor implements Parcelable, ICategory {

	private String mFloorID;
	private String mFloorNameTW;
	private String mFloorNameCN;
	private String mFloorNameEN;
	private int mSEQ;
	private String mCreateDateTime;
	private String mUpdateDateTime;
	private String mRecordTimeStamp;

	public static final Parcelable.Creator<clsFloor> CREATOR = new Parcelable.Creator<clsFloor>() {
		public clsFloor createFromParcel(Parcel in) {
			return new clsFloor(in);
		}

		public clsFloor[] newArray(int size) {
			return new clsFloor[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mFloorID);
		dest.writeString(mFloorNameTW);
		dest.writeString(mFloorNameCN);
		dest.writeString(mFloorNameEN);
		dest.writeInt(mSEQ);
		dest.writeString(mCreateDateTime);
		dest.writeString(mUpdateDateTime);
		dest.writeString(mRecordTimeStamp);
	}

	public clsFloor(Parcel in) {
		mFloorID = in.readString();
		mFloorNameTW = in.readString();
		mFloorNameCN = in.readString();
		mFloorNameEN = in.readString();
		mSEQ = in.readInt();
		mCreateDateTime = in.readString();
		mUpdateDateTime = in.readString();
		mRecordTimeStamp = in.readString();
	}

	public clsFloor() {
		mFloorID = "";
		mFloorNameTW = "";
		mFloorNameCN = "";
		mFloorNameEN = "";
		mSEQ = 0;
		mCreateDateTime = "";
		mUpdateDateTime = "";
		mRecordTimeStamp = "";
	}

	public clsFloor(Cursor cursor) {
		mFloorID = cursor.getString(cursor.getColumnIndex("FloorID"));
		mFloorNameTW = cursor.getString(cursor.getColumnIndex("FloorNameTW"));
		mFloorNameCN = cursor.getString(cursor.getColumnIndex("FloorNameCN"));
		mFloorNameEN = cursor.getString(cursor.getColumnIndex("FloorNameEN"));
		mSEQ = cursor.getInt(cursor.getColumnIndex("SEQ"));
		mCreateDateTime = cursor.getString(cursor.getColumnIndex("CreateDateTime"));
		mUpdateDateTime = cursor.getString(cursor.getColumnIndex("UpdateDateTime"));
		mRecordTimeStamp = cursor.getString(cursor.getColumnIndex("RecordTimeStamp"));
	}

	/**
	 * @return the mFloorID
	 */
	public String getFloorID() {
		return mFloorID;
	}

	/**
	 * @param mFloorID
	 *            the mFloorID to set
	 */
	public void setFloorID(String mFloorID) {
		this.mFloorID = mFloorID;
	}

	/**
	 * @return the mFloorNameTW
	 */
	public String getFloorNameTW() {
		return mFloorNameTW;
	}

	/**
	 * @param mFloorNameTW
	 *            the mFloorNameTW to set
	 */
	public void setFloorNameTW(String mFloorNameTW) {
		this.mFloorNameTW = mFloorNameTW;
	}

	/**
	 * @return the mFloorNameCN
	 */
	public String getFloorNameCN() {
		return mFloorNameCN;
	}

	/**
	 * @param mFloorNameCN
	 *            the mFloorNameCN to set
	 */
	public void setFloorNameCN(String mFloorNameCN) {
		this.mFloorNameCN = mFloorNameCN;
	}

	/**
	 * @return the mFloorNameEN
	 */
	public String getFloorNameEN() {
		return mFloorNameEN;
	}

	/**
	 * @param mFloorNameEN
	 *            the mFloorNameEN to set
	 */
	public void setFloorNameEN(String mFloorNameEN) {
		this.mFloorNameEN = mFloorNameEN;
	}

	/**
	 * @return the mSEQ
	 */
	public int getSEQ() {
		return mSEQ;
	}

	/**
	 * @param mSEQ
	 *            the pSEQ to set
	 */
	public void setSEQ(int pSEQ) {
		this.mSEQ = pSEQ;
	}

	/**
	 * @return the mCreateDateTime
	 */
	public String getCreateDateTime() {
		return mCreateDateTime;
	}

	/**
	 * @param mCreateDateTime
	 *            the mCreateDateTime to set
	 */
	public void setCreateDateTime(String mCreateDateTime) {
		this.mCreateDateTime = mCreateDateTime;
	}

	/**
	 * @return the mRecordTimeStamp
	 */
	public String getRecordTimeStamp() {
		return mRecordTimeStamp;
	}

	/**
	 * @param pRecordTimeStamp
	 *            the mRecordTimeStamp to set
	 */
	public void setRecordTimeStamp(String pRecordTimeStamp) {
		this.mRecordTimeStamp = pRecordTimeStamp;
	}

	/**
	 * @return the mUpdateDateTime
	 */
	public String getUpdateDateTime() {
		return mUpdateDateTime;
	}

	/**
	 * @param mUpdateDateTime
	 *            the mUpdateDateTime to set
	 */
	public void setUpdateDateTime(String mUpdateDateTime) {
		this.mUpdateDateTime = mUpdateDateTime;
	}

	@Override
	public String getCategoryID() {
		
		return mFloorID;
	}

	@Override
	public String getCategoryName(int curLanguage) {
		
		String strFloorName = "";
		switch (curLanguage) {
		case 1:
			strFloorName = mFloorNameEN;
			break;
		case 2:
			strFloorName = mFloorNameCN;
			break;

		default:
			strFloorName = mFloorNameTW;
			break;
		}
		return strFloorName;
	}

	@Override
	public String getSort(int curLanguage) {
		
		return null;
	}

}