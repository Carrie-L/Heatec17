package com.adsale.HEATEC.database.model;

import java.util.ArrayList;
import java.util.List;

import com.adsale.HEATEC.database.NewsLinkDBHelper;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class clsNews implements Parcelable {

 
	private String mNewsID;
	private int mLType;
	private String mLogo;
	private String mTitle;
	private String mShareLink;
	private String mDescription;
	private String mPublishDate;
	private String mCreateDateTime;
	private String mUpdateDateTime;
	private String mRecordTimeStamp;

	private List<clsNewsLink> mColNewsLinks;

	public static final Parcelable.Creator<clsNews> CREATOR = new Parcelable.Creator<clsNews>() {
		public clsNews createFromParcel(Parcel in) {
			return new clsNews(in);
		}

		public clsNews[] newArray(int size) {
			return new clsNews[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mNewsID);
		dest.writeInt(mLType);
		dest.writeString(mLogo);
		dest.writeString(mTitle);
		dest.writeString(mShareLink);
		dest.writeString(mDescription);
		dest.writeString(mPublishDate);
		dest.writeString(mCreateDateTime);
		dest.writeString(mUpdateDateTime);
		dest.writeString(mRecordTimeStamp);
		dest.writeList(mColNewsLinks);
	}

	public clsNews(Parcel in) {
		mNewsID = in.readString();
		mLType = in.readInt();
		mLogo = in.readString();
		mTitle = in.readString();
		mShareLink = in.readString();
		mDescription = in.readString();
		mPublishDate = in.readString();
		mCreateDateTime = in.readString();
		mUpdateDateTime = in.readString();
		mRecordTimeStamp = in.readString();
		mColNewsLinks = new ArrayList<clsNewsLink>();
		in.readTypedList(mColNewsLinks, clsNewsLink.CREATOR);
	}

	public clsNews() {
		mNewsID = "";
		mLType = 1;
		mLogo = "";
		mTitle = "";
		mShareLink = "";
		mDescription = "";
		mPublishDate = "";
		mCreateDateTime = "";
		mUpdateDateTime = "";
		mRecordTimeStamp = "";

		mColNewsLinks = new ArrayList<clsNewsLink>();
	}

	public clsNews(Cursor cursor) {
		mNewsID = cursor.getString(cursor.getColumnIndex("NewsID"));
		mLType = cursor.getInt(cursor.getColumnIndex("LType"));
		mLogo = cursor.getString(cursor.getColumnIndex("Logo"));
		mTitle = cursor.getString(cursor.getColumnIndex("Title"));
		mShareLink = cursor.getString(cursor.getColumnIndex("ShareLink"));
		mDescription = cursor.getString(cursor.getColumnIndex("Description"));
		mPublishDate = cursor.getString(cursor.getColumnIndex("PublishDate"));
		mCreateDateTime = cursor.getString(cursor.getColumnIndex("CreateDateTime"));
		mUpdateDateTime = cursor.getString(cursor.getColumnIndex("UpdateDateTime"));
		mRecordTimeStamp = cursor.getString(cursor.getColumnIndex("RecordTimeStamp"));

		mColNewsLinks = new ArrayList<clsNewsLink>();
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
	 * @return the mLType
	 */
	public int getLType() {
		return mLType;
	}

	/**
	 * @param mLType
	 *            the mLType to set
	 */
	public void setLType(int mLType) {
		this.mLType = mLType;
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
	 * @return the mDescription
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * @param mDescription
	 *            the mDescription to set
	 */
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
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
	 * @return the mColNewsLinks
	 */
	public List<clsNewsLink> getColNewsLinks() {
		return mColNewsLinks;
	}

	/**
	 * @return the mColNewsLinks
	 */
	public List<clsNewsLink> getColNewsLinks(Context c) {
		if (mColNewsLinks.isEmpty()) {
			mColNewsLinks = new NewsLinkDBHelper(c).getNewsLinkList(mNewsID);
		}
		return mColNewsLinks;
	}

	/**
	 * @param mColNewsLinks
	 *            the mColNewsLinks to set
	 */
	public void setColNewsLinks(List<clsNewsLink> mColNewsLinks) {
		this.mColNewsLinks = mColNewsLinks;
	}

	/**
	 * @return the mShareLink
	 */
	public String getShareLink() {
		return mShareLink;
	}

	/**
	 * @param mShareLink
	 *            the pShareLink to set
	 */
	public void setShareLink(String pShareLink) {
		this.mShareLink = pShareLink;
	}

	public String getPublishDate() {
		return mPublishDate;
	}

	public void setPublishDate(String mPublishDate) {
		this.mPublishDate = mPublishDate;
	}

}