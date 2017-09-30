package com.adsale.HEATEC.database.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class clsMapFloor implements Parcelable {

	private String mMapFloorID;
	private String mParentID;

	/**
	 * 0 分类 1 展馆 2 平面�?
	 */
	private int mType;

	private String mNameTW;
	private String mNameCN;
	private String mNameEN;

	private int mSEQ;

	private String mCreateDateTime;
	private String mUpdateDateTime;
	private String mRecordTimeStamp;

	public static final Parcelable.Creator<clsMapFloor> CREATOR = new Parcelable.Creator<clsMapFloor>() {
		public clsMapFloor createFromParcel(Parcel in) {
			return new clsMapFloor(in);
		}

		public clsMapFloor[] newArray(int size) {
			return new clsMapFloor[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mMapFloorID);
		dest.writeString(mParentID);
		dest.writeInt(mType);
		dest.writeString(mNameTW);
		dest.writeString(mNameCN);
		dest.writeString(mNameEN);
		dest.writeInt(mSEQ);
		dest.writeString(mCreateDateTime);
		dest.writeString(mUpdateDateTime);
		dest.writeString(mRecordTimeStamp);
	}

	public clsMapFloor(Parcel in) {
		mMapFloorID = in.readString();
		mParentID = in.readString();
		mType = in.readInt();
		mNameTW = in.readString();
		mNameCN = in.readString();
		mNameEN = in.readString();
		mSEQ = in.readInt();
		mCreateDateTime = in.readString();
		mUpdateDateTime = in.readString();
		mRecordTimeStamp = in.readString();
	}

	public clsMapFloor() {
		mMapFloorID = "";
		mParentID = "";
		mType = 0;
		mNameTW = "";
		mNameCN = "";
		mNameEN = "";
		mSEQ = 0;
		mCreateDateTime = "";
		mUpdateDateTime = "";
		mRecordTimeStamp = "";
	}

	public clsMapFloor(Cursor cursor) {
		mMapFloorID = cursor.getString(cursor.getColumnIndex("MapFloorID"));
		mParentID = cursor.getString(cursor.getColumnIndex("ParentID"));
		mType = cursor.getInt(cursor.getColumnIndex("Type"));
		mNameTW = cursor.getString(cursor.getColumnIndex("NameTW"));
		mNameCN = cursor.getString(cursor.getColumnIndex("NameCN"));
		mNameEN = cursor.getString(cursor.getColumnIndex("NameEN"));
		mSEQ = cursor.getInt(cursor.getColumnIndex("SEQ"));
		mCreateDateTime = cursor.getString(cursor.getColumnIndex("CreateDateTime"));
		mUpdateDateTime = cursor.getString(cursor.getColumnIndex("UpdateDateTime"));
		mRecordTimeStamp = cursor.getString(cursor.getColumnIndex("RecordTimeStamp"));
	}

	public String getMapFloorID() {
		return mMapFloorID;
	}

	public void setMapFloorID(String mMapFloorID) {
		this.mMapFloorID = mMapFloorID;
	}

	public String getParentID() {
		return mParentID;
	}

	public void setParentID(String mParentID) {
		this.mParentID = mParentID;
	}

	public int getType() {
		return mType;
	}

	public void setType(int mType) {
		this.mType = mType;
	}

	public String getNameTW() {
		return mNameTW;
	}

	public void setNameTW(String mNameTW) {
		this.mNameTW = mNameTW;
	}

	public String getNameCN() {
		return mNameCN;
	}

	public void setNameCN(String mNameCN) {
		this.mNameCN = mNameCN;
	}

	public String getNameEN() {
		return mNameEN;
	}

	public void setNameEN(String mNameEN) {
		this.mNameEN = mNameEN;
	}

	public int getSEQ() {
		return mSEQ;
	}

	public void setSEQ(int mSEQ) {
		this.mSEQ = mSEQ;
	}

	public String getCreateDateTime() {
		return mCreateDateTime;
	}

	public void setCreateDateTime(String mCreateDateTime) {
		this.mCreateDateTime = mCreateDateTime;
	}

	public String getUpdateDateTime() {
		return mUpdateDateTime;
	}

	public void setUpdateDateTime(String mUpdateDateTime) {
		this.mUpdateDateTime = mUpdateDateTime;
	}

	public String getRecordTimeStamp() {
		return mRecordTimeStamp;
	}

	public void setRecordTimeStamp(String mRecordTimeStamp) {
		this.mRecordTimeStamp = mRecordTimeStamp;
	}

	public String getName(int language) {
		String strName = "";
		switch (language) {
		case 1:
			strName = mNameEN;
			break;
		case 2:
			strName = mNameCN;
			break;
		default:
			strName = mNameTW;
			break;
		}
		return strName;
	}

}