package com.adsale.HEATEC.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Entity mapped to table "NEWS".
 */
public class News implements Parcelable {

    private String NewsID;
    private Boolean IsDelete;
    private Integer LType;
    private String Logo;
    private String ShareLink;
    private String Title;
    private String Description;
    /** Not-null value. */
    private String CreateDateTime;
    /** Not-null value. */
    private String UpdateDateTime;
    /** Not-null value. */
    private String RecordTimeStamp;
    private String PublishDate;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public News() {
    }

    public News(String NewsID) {
        this.NewsID = NewsID;
    }

    public News(String NewsID, Boolean IsDelete, Integer LType, String Logo, String ShareLink, String Title, String Description, String CreateDateTime, String UpdateDateTime, String RecordTimeStamp, String PublishDate) {
        this.NewsID = NewsID;
        this.IsDelete = IsDelete;
        this.LType = LType;
        this.Logo = Logo;
        this.ShareLink = ShareLink;
        this.Title = Title;
        this.Description = Description;
        this.CreateDateTime = CreateDateTime;
        this.UpdateDateTime = UpdateDateTime;
        this.RecordTimeStamp = RecordTimeStamp;
        this.PublishDate = PublishDate;
    }

    public String getNewsID() {
        return NewsID;
    }

    public void setNewsID(String NewsID) {
        this.NewsID = NewsID;
    }

    public Boolean getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public Integer getLType() {
        return LType;
    }

    public void setLType(Integer LType) {
        this.LType = LType;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    public String getShareLink() {
        return ShareLink;
    }

    public void setShareLink(String ShareLink) {
        this.ShareLink = ShareLink;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    /** Not-null value. */
    public String getCreateDateTime() {
        return CreateDateTime;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCreateDateTime(String CreateDateTime) {
        this.CreateDateTime = CreateDateTime;
    }

    /** Not-null value. */
    public String getUpdateDateTime() {
        return UpdateDateTime;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUpdateDateTime(String UpdateDateTime) {
        this.UpdateDateTime = UpdateDateTime;
    }

    /** Not-null value. */
    public String getRecordTimeStamp() {
        return RecordTimeStamp;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRecordTimeStamp(String RecordTimeStamp) {
        this.RecordTimeStamp = RecordTimeStamp;
    }

    public String getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(String PublishDate) {
        this.PublishDate = PublishDate;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.NewsID);
        dest.writeValue(this.IsDelete);
        dest.writeValue(this.LType);
        dest.writeString(this.Logo);
        dest.writeString(this.ShareLink);
        dest.writeString(this.Title);
        dest.writeString(this.Description);
        dest.writeString(this.CreateDateTime);
        dest.writeString(this.UpdateDateTime);
        dest.writeString(this.RecordTimeStamp);
        dest.writeString(this.PublishDate);
    }

    protected News(Parcel in) {
        this.NewsID = in.readString();
        this.IsDelete = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.LType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.Logo = in.readString();
        this.ShareLink = in.readString();
        this.Title = in.readString();
        this.Description = in.readString();
        this.CreateDateTime = in.readString();
        this.UpdateDateTime = in.readString();
        this.RecordTimeStamp = in.readString();
        this.PublishDate = in.readString();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
