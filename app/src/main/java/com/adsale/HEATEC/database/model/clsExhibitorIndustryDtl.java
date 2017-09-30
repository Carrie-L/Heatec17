package com.adsale.HEATEC.database.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class clsExhibitorIndustryDtl implements Parcelable {

	private String mCompanyID;
	private String mIndustryID;
	private String mCreateDateTime;
	private String mUpdateDateTime;
	private String mRecordTimeStamp;

	public clsExhibitorIndustryDtl() {
	}

	public static final Parcelable.Creator<clsExhibitorIndustryDtl> CREATOR = new Parcelable.Creator<clsExhibitorIndustryDtl>() {
		public clsExhibitorIndustryDtl createFromParcel(Parcel in) {
			return new clsExhibitorIndustryDtl(in);
		}

		public clsExhibitorIndustryDtl[] newArray(int size) {
			return new clsExhibitorIndustryDtl[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mCompanyID);
		dest.writeString(mIndustryID);
		dest.writeString(mCreateDateTime);
		dest.writeString(mUpdateDateTime);
		dest.writeString(mRecordTimeStamp);
	}

	public clsExhibitorIndustryDtl(Parcel in) {
		mCompanyID = in.readString();
		mIndustryID = in.readString();
		mCreateDateTime = in.readString();
		mUpdateDateTime = in.readString();
		mRecordTimeStamp = in.readString();
	}

	public clsExhibitorIndustryDtl(Cursor cursor) {
		mCompanyID = cursor.getString(cursor.getColumnIndex("CompanyID"));
		mIndustryID = cursor.getString(cursor.getColumnIndex("IndustryID"));
		mCreateDateTime = cursor.getString(cursor.getColumnIndex("CreateDateTime"));
		mUpdateDateTime = cursor.getString(cursor.getColumnIndex("UpdateDateTime"));
		mRecordTimeStamp = cursor.getString(cursor.getColumnIndex("RecordTimeStamp"));
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
	 * @return the mIndustryID
	 */
	public String getIndustryID() {
		return mIndustryID;
	}

	/**
	 * @param mIndustryID
	 *            the mIndustryID to set
	 */
	public void setIndustryID(String mIndustryID) {
		this.mIndustryID = mIndustryID;
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

	/**
	 * @return the mRecordTimeStamp
	 */
	public String getRecordTimeStamp() {
		return mRecordTimeStamp;
	}

	/**
	 * @param mRecordTimeStamp
	 *            the mRecordTimeStamp to set
	 */
	public void setRecordTimeStamp(String mRecordTimeStamp) {
		this.mRecordTimeStamp = mRecordTimeStamp;
	}

}