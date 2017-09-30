package com.adsale.HEATEC.database.model;

import java.util.Comparator;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class clsExhibitor implements Parcelable {

	private String mCompanyID;
	private String mCompanyNameTW;
	private String mDescriptionTW;
	private String mAddressTW;
	private String mSortTW;
	private String mCompanyNameCN;
	private String mDescriptionCN;
	private String mAddressCN;
	private String mSortCN;
	private String mCompanyNameEN;
	private String mDescriptionEN;
	private String mAddressEN;
	private String mSortEN;
	private String mExhibitorNO;
	private String mCountryID;
	private String mLogo;
	private String mTel;
	private String mTel1;
	private String mFax;
	private String mEmail;
	private String mWebsite;
	private double mLongitude;
	private double mLatitude;
	private int mLocation_X;
	private int mLocation_Y;
	private int mLocation_W;
	private int mLocation_H;
	private int mSEQ;
	private String mCreateDateTime;
	private String mUpdateDateTime;
	private String mRecordTimeStamp;
	private String mContactTW;
	private String mTitleTW;
	private String mContactCN;
	private String mTitleCN;
	private String mContactEN;
	private String mTitleEN;
	private int mIsFavourite;
	private String mNote;
	private String mFloor;

	public static final Parcelable.Creator<clsExhibitor> CREATOR = new Parcelable.Creator<clsExhibitor>() {
		public clsExhibitor createFromParcel(Parcel in) {
			return new clsExhibitor(in);
		}

		public clsExhibitor[] newArray(int size) {
			return new clsExhibitor[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}
	
	public clsExhibitor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mCompanyID);
		dest.writeString(mCompanyNameTW);
		dest.writeString(mDescriptionTW);
		dest.writeString(mAddressTW);
		dest.writeString(mSortTW);
		dest.writeString(mCompanyNameCN);
		dest.writeString(mDescriptionCN);
		dest.writeString(mAddressCN);
		dest.writeString(mSortCN);
		dest.writeString(mCompanyNameEN);
		dest.writeString(mDescriptionEN);
		dest.writeString(mAddressEN);
		dest.writeString(mSortEN);
		dest.writeString(mExhibitorNO);
		dest.writeString(mCountryID);
		dest.writeString(mLogo);
		dest.writeString(mTel);
		dest.writeString(mTel1);
		dest.writeString(mFax);
		dest.writeString(mEmail);
		dest.writeString(mWebsite);
		dest.writeDouble(mLongitude);
		dest.writeDouble(mLatitude);
		dest.writeInt(mLocation_X);
		dest.writeInt(mLocation_Y);
		dest.writeInt(mLocation_W);
		dest.writeInt(mLocation_H);
		dest.writeInt(mSEQ);
		dest.writeString(mCreateDateTime);
		dest.writeString(mUpdateDateTime);
		dest.writeString(mRecordTimeStamp);
		dest.writeString(mContactTW);
		dest.writeString(mTitleTW);
		dest.writeString(mContactCN);
		dest.writeString(mTitleCN);
		dest.writeString(mContactEN);
		dest.writeString(mTitleEN);
		dest.writeInt(mIsFavourite);
		dest.writeString(mNote);
		dest.writeString(mFloor);
	}

	public clsExhibitor(Parcel in) {
		mCompanyID = in.readString();
		mCompanyNameTW = in.readString();
		mDescriptionTW = in.readString();
		mAddressTW = in.readString();
		mSortTW = in.readString();
		mCompanyNameCN = in.readString();
		mDescriptionCN = in.readString();
		mAddressCN = in.readString();
		mSortCN = in.readString();
		mCompanyNameEN = in.readString();
		mDescriptionEN = in.readString();
		mAddressEN = in.readString();
		mSortEN = in.readString();
		mExhibitorNO = in.readString();
		mCountryID = in.readString();
		mLogo = in.readString();
		mTel = in.readString();
		mTel1 = in.readString();
		mFax = in.readString();
		mEmail = in.readString();
		mWebsite = in.readString();
		mLongitude = in.readDouble();
		mLatitude = in.readDouble();
		mLocation_X = in.readInt();
		mLocation_Y = in.readInt();
		mLocation_W = in.readInt();
		mLocation_H = in.readInt();
		mSEQ = in.readInt();
		mCreateDateTime = in.readString();
		mUpdateDateTime = in.readString();
		mRecordTimeStamp = in.readString();
		mContactTW = in.readString();
		mTitleTW = in.readString();
		mContactCN = in.readString();
		mTitleCN = in.readString();
		mContactEN = in.readString();
		mTitleEN = in.readString();
		mIsFavourite = in.readInt();
		mNote = in.readString();
		mFloor = in.readString();
	}

	public clsExhibitor(Cursor cursor) {
		mCompanyID = cursor.getString(cursor.getColumnIndex("CompanyID"));
		mCompanyNameTW = cursor.getString(cursor.getColumnIndex("CompanyNameTW"));
		mDescriptionTW = cursor.getString(cursor.getColumnIndex("DescriptionTW"));
		mAddressTW = cursor.getString(cursor.getColumnIndex("AddressTW"));
		mSortTW = cursor.getString(cursor.getColumnIndex("SortTW"));
		mCompanyNameCN = cursor.getString(cursor.getColumnIndex("CompanyNameCN"));
		mDescriptionCN = cursor.getString(cursor.getColumnIndex("DescriptionCN"));
		mAddressCN = cursor.getString(cursor.getColumnIndex("AddressCN"));
		mSortCN = cursor.getString(cursor.getColumnIndex("SortCN"));
		mCompanyNameEN = cursor.getString(cursor.getColumnIndex("CompanyNameEN"));
		mDescriptionEN = cursor.getString(cursor.getColumnIndex("DescriptionEN"));
		mAddressEN = cursor.getString(cursor.getColumnIndex("AddressEN"));
		mSortEN = cursor.getString(cursor.getColumnIndex("SortEN"));
		mExhibitorNO = cursor.getString(cursor.getColumnIndex("ExhibitorNO"));
		mCountryID = cursor.getString(cursor.getColumnIndex("CountryID"));
		mLogo = cursor.getString(cursor.getColumnIndex("Logo"));
		mTel = cursor.getString(cursor.getColumnIndex("Tel"));
		mTel1 = cursor.getString(cursor.getColumnIndex("Tel1"));
		mFax = cursor.getString(cursor.getColumnIndex("Fax"));
		mEmail = cursor.getString(cursor.getColumnIndex("Email"));
		mWebsite = cursor.getString(cursor.getColumnIndex("Website"));
		mLongitude = cursor.getDouble(cursor.getColumnIndex("Longitude"));
		mLatitude = cursor.getDouble(cursor.getColumnIndex("Latitude"));
		mLocation_X = cursor.getInt(cursor.getColumnIndex("Location_X"));
		mLocation_Y = cursor.getInt(cursor.getColumnIndex("Location_Y"));
		mLocation_W = cursor.getInt(cursor.getColumnIndex("Location_W"));
		mLocation_H = cursor.getInt(cursor.getColumnIndex("Location_H"));
		mSEQ = cursor.getInt(cursor.getColumnIndex("SEQ"));
		mCreateDateTime = cursor.getString(cursor.getColumnIndex("CreateDateTime"));
		mUpdateDateTime = cursor.getString(cursor.getColumnIndex("UpdateDateTime"));
		mRecordTimeStamp = cursor.getString(cursor.getColumnIndex("RecordTimeStamp"));
		mContactTW = cursor.getString(cursor.getColumnIndex("ContactTW"));
		mTitleTW = cursor.getString(cursor.getColumnIndex("TitleTW"));
		mContactCN = cursor.getString(cursor.getColumnIndex("ContactCN"));
		mTitleCN = cursor.getString(cursor.getColumnIndex("TitleCN"));
		mContactEN = cursor.getString(cursor.getColumnIndex("ContactEN"));
		mTitleEN = cursor.getString(cursor.getColumnIndex("TitleEN"));
		mIsFavourite = cursor.getInt(cursor.getColumnIndex("IsFavourite"));
		mNote = cursor.getString(cursor.getColumnIndex("Note"));
		mFloor = cursor.getString(cursor.getColumnIndex("Floor"));
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
	 * @return the mCompanyNameTW
	 */
	public String getCompanyNameTW() {
		return mCompanyNameTW;
	}

	/**
	 * @param mCompanyNameTW
	 *            the mCompanyNameTW to set
	 */
	public void setCompanyNameTW(String mCompanyNameTW) {
		this.mCompanyNameTW = mCompanyNameTW;
	}

	/**
	 * @return the mDescriptionTW
	 */
	public String getDescriptionTW() {
		return mDescriptionTW;
	}

	/**
	 * @param mDescriptionTW
	 *            the mDescriptionTW to set
	 */
	public void setDescriptionTW(String mDescriptionTW) {
		this.mDescriptionTW = mDescriptionTW;
	}

	/**
	 * @return the mAddressTW
	 */
	public String getAddressTW() {
		return mAddressTW;
	}

	/**
	 * @param mAddressTW
	 *            the mAddressTW to set
	 */
	public void setAddressTW(String mAddressTW) {
		this.mAddressTW = mAddressTW;
	}

	/**
	 * @return the mSortTW
	 */
	public String getSortTW() {
		return mSortTW;
	}

	/**
	 * @param mSortTW
	 *            the mSortTW to set
	 */
	public void setSortTW(String mSortTW) {
		this.mSortTW = mSortTW;
	}

	/**
	 * @return the mCompanyNameCN
	 */
	public String getCompanyNameCN() {
		return mCompanyNameCN;
	}

	/**
	 * @param mCompanyNameCN
	 *            the mCompanyNameCN to set
	 */
	public void setCompanyNameCN(String mCompanyNameCN) {
		this.mCompanyNameCN = mCompanyNameCN;
	}

	/**
	 * @return the mDescriptionCN
	 */
	public String getDescriptionCN() {
		return mDescriptionCN;
	}

	/**
	 * @param mDescriptionCN
	 *            the mDescriptionCN to set
	 */
	public void setDescriptionCN(String mDescriptionCN) {
		this.mDescriptionCN = mDescriptionCN;
	}

	/**
	 * @return the mAddressCN
	 */
	public String getAddressCN() {
		return mAddressCN;
	}

	/**
	 * @param mAddressCN
	 *            the mAddressCN to set
	 */
	public void setAddressCN(String mAddressCN) {
		this.mAddressCN = mAddressCN;
	}

	/**
	 * @return the mSortCN
	 */
	public String getSortCN() {
		return mSortCN;
	}

	/**
	 * @param mSortCN
	 *            the mSortCN to set
	 */
	public void setSortCN(String mSortCN) {
		this.mSortCN = mSortCN;
	}

	/**
	 * @return the mCompanyNameEN
	 */
	public String getCompanyNameEN() {
		return mCompanyNameEN;
	}

	/**
	 * @param mCompanyNameEN
	 *            the mCompanyNameEN to set
	 */
	public void setCompanyNameEN(String mCompanyNameEN) {
		this.mCompanyNameEN = mCompanyNameEN;
	}

	/**
	 * @return the mDescriptionEN
	 */
	public String getDescriptionEN() {
		return mDescriptionEN;
	}

	/**
	 * @param mDescriptionEN
	 *            the mDescriptionEN to set
	 */
	public void setDescriptionEN(String mDescriptionEN) {
		this.mDescriptionEN = mDescriptionEN;
	}

	/**
	 * @return the mAddressEN
	 */
	public String getAddressEN() {
		return mAddressEN;
	}

	/**
	 * @param mAddressEN
	 *            the mAddressEN to set
	 */
	public void setAddressEN(String mAddressEN) {
		this.mAddressEN = mAddressEN;
	}

	/**
	 * @return the mSortEN
	 */
	public String getSortEN() {
		return mSortEN;
	}

	/**
	 * @param mSortEN
	 *            the mSortEN to set
	 */
	public void setSortEN(String mSortEN) {
		this.mSortEN = mSortEN;
	}

	/**
	 * @return the mExhibitorNO
	 */
	public String getExhibitorNO() {
		return mExhibitorNO;
	}

	/**
	 * @param mExhibitorNO
	 *            the mExhibitorNO to set
	 */
	public void setExhibitorNO(String mExhibitorNO) {
		this.mExhibitorNO = mExhibitorNO;
	}

	/**
	 * @return the mCountryID
	 */
	public String getCountryID() {
		return mCountryID;
	}

	/**
	 * @param mCountryID
	 *            the mCountryID to set
	 */
	public void setCountryID(String mCountryID) {
		this.mCountryID = mCountryID;
	}

	/**
	 * @return the mLogo
	 */
	public String getLogo() {
		return mLogo;
	}

	/**
	 * @param mLogo
	 *            the mLogo to set
	 */
	public void setLogo(String mLogo) {
		this.mLogo = mLogo;
	}

	/**
	 * @return the mTel
	 */
	public String getTel() {
		return mTel;
	}

	/**
	 * @param mTel
	 *            the mTel to set
	 */
	public void setTel(String mTel) {
		this.mTel = mTel;
	}

	/**
	 * @return the mTel1
	 */
	public String getTel1() {
		return mTel1;
	}

	/**
	 * @param mTel1
	 *            the mTel1 to set
	 */
	public void setTel1(String mTel1) {
		this.mTel1 = mTel1;
	}

	/**
	 * @return the mFax
	 */
	public String getFax() {
		return mFax;
	}

	/**
	 * @param mFax
	 *            the mFax to set
	 */
	public void setFax(String mFax) {
		this.mFax = mFax;
	}

	/**
	 * @return the mEmail
	 */
	public String getEmail() {
		return mEmail;
	}

	/**
	 * @param mEmail
	 *            the mEmail to set
	 */
	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	/**
	 * @return the mWebsite
	 */
	public String getWebsite() {
		return mWebsite;
	}

	/**
	 * @param mWebsite
	 *            the mWebsite to set
	 */
	public void setWebsite(String mWebsite) {
		this.mWebsite = mWebsite;
	}

	/**
	 * @return the mLongitude
	 */
	public double getLongitude() {
		return mLongitude;
	}

	/**
	 * @param mLongitude
	 *            the mLongitude to set
	 */
	public void setLongitude(double mLongitude) {
		this.mLongitude = mLongitude;
	}

	/**
	 * @return the mLatitude
	 */
	public double getLatitude() {
		return mLatitude;
	}

	/**
	 * @param mLatitude
	 *            the mLatitude to set
	 */
	public void setLatitude(double mLatitude) {
		this.mLatitude = mLatitude;
	}

	/**
	 * @return the mLocation_X
	 */
	public int getLocation_X() {
		return mLocation_X;
	}

	/**
	 * @param mLocation_X
	 *            the mLocation_X to set
	 */
	public void setLocation_X(int mLocation_X) {
		this.mLocation_X = mLocation_X;
	}

	/**
	 * @return the mLocation_Y
	 */
	public int getLocation_Y() {
		return mLocation_Y;
	}

	/**
	 * @param mLocation_Y
	 *            the mLocation_Y to set
	 */
	public void setLocation_Y(int mLocation_Y) {
		this.mLocation_Y = mLocation_Y;
	}

	/**
	 * @return the mLocation_W
	 */
	public int getLocation_W() {
		return mLocation_W;
	}

	/**
	 * @param mLocation_W
	 *            the mLocation_W to set
	 */
	public void setLocation_W(int mLocation_W) {
		this.mLocation_W = mLocation_W;
	}

	/**
	 * @return the mLocation_H
	 */
	public int getLocation_H() {
		return mLocation_H;
	}

	/**
	 * @param mLocation_H
	 *            the mLocation_H to set
	 */
	public void setLocation_H(int mLocation_H) {
		this.mLocation_H = mLocation_H;
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

	/**
	 * @return the mContactTW
	 */
	public String getContactTW() {
		return mContactTW;
	}

	/**
	 * @param mContactTW
	 *            the mContactTW to set
	 */
	public void setContactTW(String mContactTW) {
		this.mContactTW = mContactTW;
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
	 * @return the mContactCN
	 */
	public String getContactCN() {
		return mContactCN;
	}

	/**
	 * @param mContactCN
	 *            the mContactCN to set
	 */
	public void setContactCN(String mContactCN) {
		this.mContactCN = mContactCN;
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
	 * @return the mContactEN
	 */
	public String getContactEN() {
		return mContactEN;
	}

	/**
	 * @param mContactEN
	 *            the mContactEN to set
	 */
	public void setContactEN(String mContactEN) {
		this.mContactEN = mContactEN;
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
	 * @return the mIsFavourite
	 */
	public int getIsFavourite() {
		return mIsFavourite;
	}

	/**
	 * @param mIsFavourite
	 *            the mIsFavourite to set
	 */
	public void setIsFavourite(int mIsFavourite) {
		this.mIsFavourite = mIsFavourite;
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
	 * @return the mFloor
	 */
	public String getFloor() {
		return mFloor;
	}

	/**
	 * @param mFloor
	 *            the mFloor to set
	 */
	public void setFloor(String mFloor) {
		this.mFloor = mFloor;
	}

	public String getCompanyName(int curLanguage) {
		String strCompanyName = "";
		switch (curLanguage) {
		case 1:
			strCompanyName = mCompanyNameEN;
			break;
		case 2:
			strCompanyName = mCompanyNameCN;
			break;

		default:
			strCompanyName = mCompanyNameTW;
			break;
		}
		return strCompanyName;
	}

	public String getAddress(int curLanguage) {
		String strAddress = "";
		switch (curLanguage) {
		case 1:
			strAddress = mAddressEN;
			break;
		case 2:
			strAddress = mAddressCN;
			break;

		default:
			strAddress = mAddressTW;
			break;
		}
		return strAddress;
	}

	public String getSort(int curLanguage) {
		String strSort = "";
		switch (curLanguage) {
		case 1:
			strSort = mSortEN;
			break;
		case 2:
			strSort = mSortCN + "";
			break;

		default:
			strSort = mSortTW + "";
			break;
		}
		return strSort;
	}
	

		private int mLanguage;

		public void setCurrentLang(int language) {
			mLanguage = language;
		}

		private String orderColumn(clsExhibitor exhibitor) {// logo: en-tc-sc
			if (mLanguage == 0) {
				return exhibitor.getLogo().split("-")[1];
			} else if (mLanguage == 1) {
				return exhibitor.getLogo().split("-")[0];
			} else {
				return exhibitor.getLogo().split("-")[2];
			}
		}

	@Override
	public String toString() {
		return "clsExhibitor [mCompanyID=" + mCompanyID + ", mCompanyNameTW=" + mCompanyNameTW + ", mCompanyNameCN=" + mCompanyNameCN + ", mCompanyNameEN="
				+ mCompanyNameEN + ", mIsFavourite=" + mIsFavourite + ", mNote=" + mNote + "]";
	}

}