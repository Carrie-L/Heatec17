package com.adsale.HEATEC.database.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class clsWebContent implements Parcelable {
	private int mPageId;

	private String mTitleTW;
	private String mTitleCN;
	private String mTitleEN;

	private int mCType;
	private String mCFile;

	private String mContentEN;
	private String mContentSC;
	private String mContentTC;

	private String mZipDateTime;

	private String mCreateDateTime;
	private String mUpdateDateTime;
	private String mRecordTimeStamp;

	private boolean mIsDown;

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public clsWebContent createFromParcel(Parcel in) {
			return new clsWebContent(in);
		}

		public clsWebContent[] newArray(int size) {
			return new clsWebContent[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mPageId);
		dest.writeString(mTitleTW);
		dest.writeString(mTitleCN);
		dest.writeString(mTitleEN);
		dest.writeInt(mCType);
		dest.writeString(mCFile);
		dest.writeString(mContentEN);
		dest.writeString(mContentSC);
		dest.writeString(mContentTC);
		dest.writeString(mZipDateTime);
		dest.writeString(mCreateDateTime);
		dest.writeString(mUpdateDateTime);
		dest.writeString(mRecordTimeStamp);
		dest.writeInt(mIsDown ? 1 : 0);
	}

	public clsWebContent(Parcel in) {
		mPageId = in.readInt();
		mTitleTW = in.readString();
		mTitleCN = in.readString();
		mTitleEN = in.readString();
		mCType = in.readInt();
		mCFile = in.readString();
		mContentEN = in.readString();
		mContentSC = in.readString();
		mContentTC = in.readString();
		mZipDateTime = in.readString();
		mCreateDateTime = in.readString();
		mUpdateDateTime = in.readString();
		mRecordTimeStamp = in.readString();
		mIsDown = in.readInt() == 1;
	}

	public clsWebContent() {
		mPageId = 0;
		mTitleTW = "";
		mTitleCN = "";
		mTitleEN = "";
		mCType = 0;
		mCFile = "";
		mContentEN = "";
		mContentSC = "";
		mContentTC = "";
		mZipDateTime = "";
		mCreateDateTime = "";
		mUpdateDateTime = "";
		mRecordTimeStamp = "";
		mIsDown = false;
	}

	public clsWebContent(Cursor cursor) {
		mPageId = cursor.getInt(cursor.getColumnIndex("PageId"));
		mTitleTW = cursor.getString(cursor.getColumnIndex("TitleTW"));
		mTitleCN = cursor.getString(cursor.getColumnIndex("TitleCN"));
		mTitleEN = cursor.getString(cursor.getColumnIndex("TitleEN"));
		mCType = cursor.getInt(cursor.getColumnIndex("CType"));
		mCFile = cursor.getString(cursor.getColumnIndex("CFile"));
		mContentEN = cursor.getString(cursor.getColumnIndex("ContentEN"));
		mContentSC = cursor.getString(cursor.getColumnIndex("ContentSC"));
		mContentTC = cursor.getString(cursor.getColumnIndex("ContentTC"));
		mZipDateTime = cursor.getString(cursor.getColumnIndex("ZipDateTime"));
		mCreateDateTime = cursor.getString(cursor.getColumnIndex("CreateDateTime"));
		mUpdateDateTime = cursor.getString(cursor.getColumnIndex("UpdateDateTime"));
		mRecordTimeStamp = cursor.getString(cursor.getColumnIndex("RecordTimeStamp"));
		mIsDown = cursor.getInt(cursor.getColumnIndex("IsDown")) == 1;
	}

	/**
	 * @return the mPageId
	 */
	public int getPageId() {
		return mPageId;
	}

	/**
	 * @param mPageId
	 *            the mPageId to set
	 */
	public void setPageId(int mPageId) {
		this.mPageId = mPageId;
	}

	/**
	 * @return the mTitleTW
	 */
	public String getTitleTW() {
		return mTitleTW;
	}

	/**
	 * @param mTitleTW
	 *            the mTitleTW to set
	 */
	public void setTitleTW(String mTitleTW) {
		this.mTitleTW = mTitleTW;
	}

	/**
	 * @return the mCType
	 */
	public int getCType() {
		return mCType;
	}

	/**
	 * @param mCType
	 *            the mCType to set
	 */
	public void setCType(int mCType) {
		this.mCType = mCType;
	}

	/**
	 * @return the mCFile
	 */
	public String getCFile() {
		return mCFile;
	}

	/**
	 * @param mCFile
	 *            the mCFile to set
	 */
	public void setCFile(String mCFile) {
		this.mCFile = mCFile;
	}

	/**
	 * @return the mContentEN
	 */
	public String getContentEN() {
		return mContentEN;
	}

	/**
	 * @param mContentEN
	 *            the mContentEN to set
	 */
	public void setContentEN(String mContentEN) {
		this.mContentEN = mContentEN;
	}

	/**
	 * @return the mContentSC
	 */
	public String getContentSC() {
		return mContentSC;
	}

	/**
	 * @param mContentSC
	 *            the mContentSC to set
	 */
	public void setContentSC(String mContentSC) {
		this.mContentSC = mContentSC;
	}

	/**
	 * @return the mContentTC
	 */
	public String getContentTC() {
		return mContentTC;
	}

	/**
	 * @param mContentTC
	 *            the mContentTC to set
	 */
	public void setContentTC(String mContentTC) {
		this.mContentTC = mContentTC;
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

	public String getContent(int curLanguage) {
		String strContent = "";
		switch (curLanguage) {
		case 1:
			strContent = mContentEN;
			break;
		case 2:
			strContent = mContentSC;
			break;

		default:
			strContent = mContentTC;
			break;
		}
		return strContent;
	}

	/**
	 * @return the mTitleCN
	 */
	public String getTitleCN() {
		return mTitleCN;
	}

	/**
	 * @param mTitleCN
	 *            the mTitleCN to set
	 */
	public void setTitleCN(String mTitleCN) {
		this.mTitleCN = mTitleCN;
	}

	/**
	 * @return the mTitleEN
	 */
	public String getTitleEN() {
		return mTitleEN;
	}

	/**
	 * @param mTitleEN
	 *            the mTitleEN to set
	 */
	public void setTitleEN(String mTitleEN) {
		this.mTitleEN = mTitleEN;
	}

	/**
	 * @return the mZipDateTime
	 */
	public String getZipDateTime() {
		return mZipDateTime;
	}

	/**
	 * @param mZipDateTime
	 *            the mZipDateTime to set
	 */
	public void setZipDateTime(String mZipDateTime) {
		this.mZipDateTime = mZipDateTime;
	}

	public String getTitle(int curLanguage) {
		String strTitle = "";
		switch (curLanguage) {
		case 1:
			strTitle = mTitleEN;
			break;
		case 2:
			strTitle = mTitleCN;
			break;
		default:
			strTitle = mTitleTW;
			break;
		}
		return strTitle;
	}

	public boolean getIsDown() {
		return mIsDown;
	}

	public void setIsDown(boolean mIsDown) {
		this.mIsDown = mIsDown;
	}

}