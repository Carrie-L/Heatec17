package com.adsale.HEATEC.database.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class clsCountry implements Parcelable, ICategory {

	private String mCountryID;
	private String mCountryNameTW;
	private String mCountryNameCN;
	private String mCountryNameEN;
	private String mSortTW;
	private String mSortCN;
	private String mSortEN;
	
	private String mCountryValue;

	public static final Parcelable.Creator<clsCountry> CREATOR = new Parcelable.Creator<clsCountry>() {
		public clsCountry createFromParcel(Parcel in) {
			return new clsCountry(in);
		}

		public clsCountry[] newArray(int size) {
			return new clsCountry[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mCountryID);
		dest.writeString(mCountryNameTW);
		dest.writeString(mCountryNameCN);
		dest.writeString(mCountryNameEN);
		dest.writeString(mSortTW);
		dest.writeString(mSortCN);
		dest.writeString(mSortEN);
		
		dest.writeString(mCountryValue);
	}

	public clsCountry(Parcel in) {
		mCountryID = in.readString();
		mCountryNameTW = in.readString();
		mCountryNameCN = in.readString();
		mCountryNameEN = in.readString();
		mSortTW = in.readString();
		mSortCN = in.readString();
		mSortEN = in.readString();
		
		mCountryValue = in.readString();
		
		
	}
	
	public clsCountry() {
		mCountryID = "";
		mCountryNameTW = "";
		mCountryNameCN = "";
		mCountryNameEN = "";
		mSortTW = "";
		mSortCN = "";
		mSortEN = "";
		
		mCountryValue = "";
	}

	public clsCountry(Cursor cursor) {
		mCountryID = cursor.getString(cursor.getColumnIndex("CountryID"));
		mCountryNameTW = cursor.getString(cursor.getColumnIndex("CountryNameTW"));
		mCountryNameCN = cursor.getString(cursor.getColumnIndex("CountryNameCN"));
		mCountryNameEN = cursor.getString(cursor.getColumnIndex("CountryNameEN"));
		mSortTW = cursor.getString(cursor.getColumnIndex("SortTW"));
		mSortCN = cursor.getString(cursor.getColumnIndex("SortCN"));
		mSortEN = cursor.getString(cursor.getColumnIndex("SortEN"));
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
	 * @return the mCountryNameTW
	 */
	public String getCountryNameTW() {
		return mCountryNameTW;
	}

	/**
	 * @param mCountryNameTW
	 *            the mCountryNameTW to set
	 */
	public void setCountryNameTW(String mCountryNameTW) {
		this.mCountryNameTW = mCountryNameTW;
	}

	/**
	 * @return the mCountryNameCN
	 */
	public String getCountryNameCN() {
		return mCountryNameCN;
	}

	/**
	 * @param mCountryNameCN
	 *            the mCountryNameCN to set
	 */
	public void setCountryNameCN(String mCountryNameCN) {
		this.mCountryNameCN = mCountryNameCN;
	}

	/**
	 * @return the mCountryNameEN
	 */
	public String getCountryNameEN() {
		return mCountryNameEN;
	}

	/**
	 * @param mCountryNameEN
	 *            the mCountryNameEN to set
	 */
	public void setCountryNameEN(String mCountryNameEN) {
		this.mCountryNameEN = mCountryNameEN;
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

	@Override
	public String getCategoryID() {
		
		return mCountryID;
	}

	@Override
	public String getCategoryName(int curLanguage) {
		
		String strCountryName = "";
		switch (curLanguage) {
		case 1:
			strCountryName = mCountryNameEN;
			break;
		case 2:
			strCountryName = mCountryNameCN;
			break;

		default:
			strCountryName = mCountryNameTW;
			break;
		}
		return strCountryName;
	}

	public String getCountryValue() {
		return mCountryValue;
	}

	public void setCountryValue(String countryValue) {
		this.mCountryValue = countryValue;
	}

	

	
	
}