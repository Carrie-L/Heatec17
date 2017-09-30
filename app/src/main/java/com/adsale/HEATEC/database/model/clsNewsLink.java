package com.adsale.HEATEC.database.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class clsNewsLink implements Parcelable {

	private String mLinkID;
	private String mNewsID;
	private String mPhoto;
	private String mTitle;
	private String mLink;
	private int mSEQ;
	private String mCreateDateTime;
	private String mUpdateDateTime;
	private String mRecordTimeStamp;

	public static final Parcelable.Creator<clsNewsLink> CREATOR = new Parcelable.Creator<clsNewsLink>() {
		public clsNewsLink createFromParcel(Parcel in) {
			return new clsNewsLink(in);
		}

		public clsNewsLink[] newArray(int size) {
			return new clsNewsLink[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mLinkID);
		dest.writeString(mNewsID);
		dest.writeString(mPhoto);
		dest.writeString(mTitle);
		dest.writeString(mLink);
		dest.writeInt(mSEQ);
		dest.writeString(mCreateDateTime);
		dest.writeString(mUpdateDateTime);
		dest.writeString(mRecordTimeStamp);

	}

	public clsNewsLink(Parcel in) {
		mLinkID = in.readString();
		mNewsID = in.readString();
		mPhoto = in.readString();
		mTitle = in.readString();
		mLink = in.readString();
		mSEQ = in.readInt();
		mCreateDateTime = in.readString();
		mUpdateDateTime = in.readString();
		mRecordTimeStamp = in.readString();
	}

	public clsNewsLink() {
		mLinkID = "";
		mNewsID = "";
		mPhoto = "";
		mTitle = "";
		mLink = "";
		mSEQ = 0;
		mCreateDateTime = "";
		mUpdateDateTime = "";
		mRecordTimeStamp = "";
	}

	public clsNewsLink(Cursor cursor) {
		mLinkID = cursor.getString(cursor.getColumnIndex("LinkID"));
		mNewsID = cursor.getString(cursor.getColumnIndex("NewsID"));
		mPhoto = cursor.getString(cursor.getColumnIndex("Photo"));
		mTitle = cursor.getString(cursor.getColumnIndex("Title"));
		mLink = cursor.getString(cursor.getColumnIndex("Link"));
		mSEQ = cursor.getInt(cursor.getColumnIndex("SEQ"));
		mCreateDateTime = cursor.getString(cursor.getColumnIndex("CreateDateTime"));
		mUpdateDateTime = cursor.getString(cursor.getColumnIndex("UpdateDateTime"));
		mRecordTimeStamp = cursor.getString(cursor.getColumnIndex("RecordTimeStamp"));
	}

	/**
	 * @return the mLinkID
	 */
	public String getLinkID() {
		return mLinkID;
	}

	/**
	 * @param mLinkID
	 *            the mLinkID to set
	 */
	public void setLinkID(String mLinkID) {
		this.mLinkID = mLinkID;
	}

	/**
	 * @return the mNewsID
	 */
	public String getNewsID() {
		return mNewsID;
	}

	/**
	 * @param mNewsID
	 *            the mNewsID to set
	 */
	public void setNewsID(String mNewsID) {
		this.mNewsID = mNewsID;
	}

	/**
	 * @return the mPhoto
	 */
	public String getPhoto() {
		return mPhoto;
	}

	/**
	 * @param mPhoto
	 *            the mPhoto to set
	 */
	public void setPhoto(String mPhoto) {
		this.mPhoto = mPhoto;
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
	 * @return the mLink
	 */
	public String getLink() {
		return mLink;
	}

	/**
	 * @param mLink
	 *            the mLink to set
	 */
	public void setLink(String mLink) {
		this.mLink = mLink;
	}

	/**
	 * @return the mSEQ
	 */
	public int getSEQ() {
		return mSEQ;
	}

	/**
	 * @param mSEQ
	 *            the mSEQ to set
	 */
	public void setSEQ(int mSEQ) {
		this.mSEQ = mSEQ;
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