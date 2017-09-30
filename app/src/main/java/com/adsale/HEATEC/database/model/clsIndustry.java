package com.adsale.HEATEC.database.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class clsIndustry implements Parcelable, ICategory {

	private String mIndustryID;
	private String mIndustryNameTW;
	private String mIndustryNameCN;
	private String mIndustryNameEN;
	private String mSortTW;
	private String mSortCN;
	private String mSortEN;

	public static final Parcelable.Creator<clsIndustry> CREATOR = new Parcelable.Creator<clsIndustry>() {
		public clsIndustry createFromParcel(Parcel in) {
			return new clsIndustry(in);
		}

		public clsIndustry[] newArray(int size) {
			return new clsIndustry[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mIndustryID);
		dest.writeString(mIndustryNameTW);
		dest.writeString(mIndustryNameCN);
		dest.writeString(mIndustryNameEN);
		dest.writeString(mSortTW);
		dest.writeString(mSortCN);
		dest.writeString(mSortEN);
	}

	public clsIndustry(Parcel in) {
		mIndustryID = in.readString();
		mIndustryNameTW = in.readString();
		mIndustryNameCN = in.readString();
		mIndustryNameEN = in.readString();
		mSortTW = in.readString();
		mSortCN = in.readString();
		mSortEN = in.readString();
	}

	public clsIndustry() {
		mIndustryID = "";
		mIndustryNameTW = "";
		mIndustryNameCN = "";
		mIndustryNameEN = "";
		mSortTW = "";
		mSortCN = "";
		mSortEN = "";
	}

	public clsIndustry(Cursor cursor) {
		mIndustryID = cursor.getString(cursor.getColumnIndex("IndustryID"));
		mIndustryNameTW = cursor.getString(cursor.getColumnIndex("IndustryNameTW"));
		mIndustryNameCN = cursor.getString(cursor.getColumnIndex("IndustryNameCN"));
		mIndustryNameEN = cursor.getString(cursor.getColumnIndex("IndustryNameEN"));
		mSortTW = cursor.getString(cursor.getColumnIndex("SortTW"));
		mSortCN = cursor.getString(cursor.getColumnIndex("SortCN"));
		mSortEN = cursor.getString(cursor.getColumnIndex("SortEN"));
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
	 * @return the mIndustryNameTW
	 */
	public String getIndustryNameTW() {
		return mIndustryNameTW;
	}

	/**
	 * @param mIndustryNameTW
	 *            the mIndustryNameTW to set
	 */
	public void setIndustryNameTW(String mIndustryNameTW) {
		this.mIndustryNameTW = mIndustryNameTW;
	}

	/**
	 * @return the mIndustryNameCN
	 */
	public String getIndustryNameCN() {
		return mIndustryNameCN;
	}

	/**
	 * @param mIndustryNameCN
	 *            the mIndustryNameCN to set
	 */
	public void setIndustryNameCN(String mIndustryNameCN) {
		this.mIndustryNameCN = mIndustryNameCN;
	}

	/**
	 * @return the mIndustryNameEN
	 */
	public String getIndustryNameEN() {
		return mIndustryNameEN;
	}

	/**
	 * @param mIndustryNameEN
	 *            the mIndustryNameEN to set
	 */
	public void setIndustryNameEN(String mIndustryNameEN) {
		this.mIndustryNameEN = mIndustryNameEN;
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

	@Override
	public String getCategoryID() {
		
		return getIndustryID();
	}

	@Override
	public String getCategoryName(int curLanguage) {
		
		String strIndustryName = "";
		switch (curLanguage) {
		case 1:
			strIndustryName = mIndustryNameEN;
			break;
		case 2:
			strIndustryName = mIndustryNameCN;
			break;

		default:
			strIndustryName = mIndustryNameTW;
			break;
		}
		return strIndustryName;
	}

	@Override
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

}