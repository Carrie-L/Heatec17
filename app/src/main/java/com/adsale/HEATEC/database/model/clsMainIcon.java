package com.adsale.HEATEC.database.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class clsMainIcon implements Parcelable {
	private String mIconID;
	private String mIcon;

	private String mTitleTW;
	private String mTitleCN;
	private String mTitleEN;

	private int mSEQ;
	private int mCType;
	private String mCFile;

	private String mZipDateTime;

	private String mCreateDateTime;
	private String mUpdateDateTime;
	private String mRecordTimeStamp;

	private boolean mIsHidden;
	private boolean mIsDown;
	
	private String mBaiDu_TJ;
	private String mGoogle_TJ;
	
	private String mDescription;


	public static final Parcelable.Creator<clsMainIcon> CREATOR = new Parcelable.Creator<clsMainIcon>() {
		public clsMainIcon createFromParcel(Parcel in) {
			return new clsMainIcon(in);
		}

		public clsMainIcon[] newArray(int size) {
			return new clsMainIcon[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mIconID);
		dest.writeString(mIcon);
		dest.writeString(mTitleTW);
		dest.writeString(mTitleCN);
		dest.writeString(mTitleEN);
		dest.writeInt(mSEQ);
		dest.writeInt(mCType);
		dest.writeString(mCFile);
		dest.writeString(mZipDateTime);
		dest.writeString(mCreateDateTime);
		dest.writeString(mUpdateDateTime);
		dest.writeString(mRecordTimeStamp);
		dest.writeInt(mIsHidden ? 1 : 0);
		dest.writeInt(mIsDown ? 1 : 0);
		dest.writeString(mBaiDu_TJ);
		dest.writeString(mGoogle_TJ);
	//	dest.writeString(mDescription);
	}

	public clsMainIcon(Parcel in) {
		mIconID = in.readString();
		mIcon = in.readString();
		mTitleTW = in.readString();
		mTitleCN = in.readString();
		mTitleEN = in.readString();
		mSEQ = in.readInt();
		mCType = in.readInt();
		mCFile = in.readString();
		mZipDateTime = in.readString();
		mCreateDateTime = in.readString();
		mUpdateDateTime = in.readString();
		mRecordTimeStamp = in.readString();
		mIsHidden = in.readInt() == 1;
		mIsDown = in.readInt() == 1;
		mBaiDu_TJ = in.readString();
		mGoogle_TJ = in.readString();
	//	mDescription=in.readString();
	}

	public clsMainIcon() {
		mIconID = "";
		mIcon = "";
		mTitleTW = "";
		mTitleCN = "";
		mTitleEN = "";
		mSEQ = 0;
		mCType = 0;
		mCFile = "";
		mZipDateTime = "";
		mCreateDateTime = "";
		mUpdateDateTime = "";
		mRecordTimeStamp = "";
		mIsHidden = false;
		mIsDown = false;
		mBaiDu_TJ = "";
		mGoogle_TJ = "";
	//	mDescription="";
	}

	public clsMainIcon(Cursor cursor) {
		mIconID = cursor.getString(cursor.getColumnIndex("IconID"));
		mIcon = cursor.getString(cursor.getColumnIndex("Icon"));
		mTitleTW = cursor.getString(cursor.getColumnIndex("TitleTW"));
		mTitleCN = cursor.getString(cursor.getColumnIndex("TitleCN"));
		mTitleEN = cursor.getString(cursor.getColumnIndex("TitleEN"));
		mSEQ = cursor.getInt(cursor.getColumnIndex("SEQ"));
		mCType = cursor.getInt(cursor.getColumnIndex("CType"));
		mCFile = cursor.getString(cursor.getColumnIndex("CFile"));
		mZipDateTime = cursor.getString(cursor.getColumnIndex("ZipDateTime"));
		mCreateDateTime = cursor.getString(cursor.getColumnIndex("CreateDateTime"));
		mUpdateDateTime = cursor.getString(cursor.getColumnIndex("UpdateDateTime"));
		mRecordTimeStamp = cursor.getString(cursor.getColumnIndex("RecordTimeStamp"));
		mIsHidden = cursor.getInt(cursor.getColumnIndex("IsHidden")) == 1;
		mIsDown = cursor.getInt(cursor.getColumnIndex("IsDown")) == 1;
		mBaiDu_TJ = cursor.getString(cursor.getColumnIndex("BaiDu_TJ"));
		mGoogle_TJ = cursor.getString(cursor.getColumnIndex("Google_TJ"));
	//	mDescription= cursor.getString(cursor.getColumnIndex("Description"));
	}

	/**
	 * @return the mIconID
	 */
	public String getIconID() {
		return mIconID;
	}

	/**
	 * @param mIconID
	 *            the mIconID to set
	 */
	public void setIconID(String mIconID) {
		this.mIconID = mIconID;
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
	
	public void setTitle(int curLanguage,String pTitle) {
		switch (curLanguage) {
		case 1:
			 mTitleEN=pTitle;
			break;
		case 2:
			 mTitleCN=pTitle;
			break;
		default:
			 mTitleTW=pTitle;
			break;
		}
	}
	/**
	 * @return the mIcon
	 */
	public String getIcon() {
		return mIcon;
	}

	/**
	 * @param mIcon
	 *            the mIcon to set
	 */
	public void setIcon(String mIcon) {
		this.mIcon = mIcon;
	}

	/**
	 * @return the mIsHidden
	 */
	public boolean getIsHidden() {
		return mIsHidden;
	}

	/**
	 * @param mIsHidden
	 *            the mIsHidden to set
	 */
	public void setIsHidden(boolean mIsHidden) {
		this.mIsHidden = mIsHidden;
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

	public boolean getIsDown() {
		return mIsDown;
	}

	public String getBaiDu_TJ() {
		return mBaiDu_TJ;
	}

	public void setBaiDu_TJ(String mBaiDu_TJ) {
		this.mBaiDu_TJ = mBaiDu_TJ;
	}

	public String getGoogle_TJ() {
		return mGoogle_TJ;
	}

	public void setGoogle_TJ(String mGoogle_TJ) {
		this.mGoogle_TJ = mGoogle_TJ;
	}

	/*public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}
	*/
	
}