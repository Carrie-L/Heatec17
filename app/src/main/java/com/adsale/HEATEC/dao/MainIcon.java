package com.adsale.HEATEC.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Entity mapped to table "MAIN_ICON".
 */
public class MainIcon implements Parcelable {

    private String IconID;
    private Boolean IsDelete;
    /** Not-null value. */
    private String TitleTW;
    /** Not-null value. */
    private String TitleCN;
    /** Not-null value. */
    private String TitleEN;
    private String Icon;
    private Integer CType;
    private String CFile;
    private String ZipDateTime;
    private Integer IsHidden;
    private Integer SEQ;
    /** Not-null value. */
    private String CreateDateTime;
    /** Not-null value. */
    private String UpdateDateTime;
    /** Not-null value. */
    private String RecordTimeStamp;
    private Integer IsDown;
    private String BaiDu_TJ;
    private String Google_TJ;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public MainIcon() {
    }

    public MainIcon(String IconID) {
        this.IconID = IconID;
    }

    public MainIcon(String IconID, Boolean IsDelete, String TitleTW, String TitleCN, String TitleEN, String Icon, Integer CType, String CFile, String ZipDateTime, Integer IsHidden, Integer SEQ, String CreateDateTime, String UpdateDateTime, String RecordTimeStamp, Integer IsDown, String BaiDu_TJ, String Google_TJ) {
        this.IconID = IconID;
        this.IsDelete = IsDelete;
        this.TitleTW = TitleTW;
        this.TitleCN = TitleCN;
        this.TitleEN = TitleEN;
        this.Icon = Icon;
        this.CType = CType;
        this.CFile = CFile;
        this.ZipDateTime = ZipDateTime;
        this.IsHidden = IsHidden;
        this.SEQ = SEQ;
        this.CreateDateTime = CreateDateTime;
        this.UpdateDateTime = UpdateDateTime;
        this.RecordTimeStamp = RecordTimeStamp;
        this.IsDown = IsDown;
        this.BaiDu_TJ = BaiDu_TJ;
        this.Google_TJ = Google_TJ;
    }

    public String getIconID() {
        return IconID;
    }

    public void setIconID(String IconID) {
        this.IconID = IconID;
    }

    public Boolean getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    /** Not-null value. */
    public String getTitleTW() {
        return TitleTW;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitleTW(String TitleTW) {
        this.TitleTW = TitleTW;
    }

    /** Not-null value. */
    public String getTitleCN() {
        return TitleCN;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitleCN(String TitleCN) {
        this.TitleCN = TitleCN;
    }

    /** Not-null value. */
    public String getTitleEN() {
        return TitleEN;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitleEN(String TitleEN) {
        this.TitleEN = TitleEN;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }

    public Integer getCType() {
        return CType;
    }

    public void setCType(Integer CType) {
        this.CType = CType;
    }

    public String getCFile() {
        return CFile;
    }

    public void setCFile(String CFile) {
        this.CFile = CFile;
    }

    public String getZipDateTime() {
        return ZipDateTime;
    }

    public void setZipDateTime(String ZipDateTime) {
        this.ZipDateTime = ZipDateTime;
    }

    public Integer getIsHidden() {
        return IsHidden;
    }

    public void setIsHidden(Integer IsHidden) {
        this.IsHidden = IsHidden;
    }

    public Integer getSEQ() {
        return SEQ;
    }

    public void setSEQ(Integer SEQ) {
        this.SEQ = SEQ;
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

    public Integer getIsDown() {
        return IsDown;
    }

    public void setIsDown(Integer IsDown) {
        this.IsDown = IsDown;
    }

    public String getBaiDu_TJ() {
        return BaiDu_TJ;
    }

    public void setBaiDu_TJ(String BaiDu_TJ) {
        this.BaiDu_TJ = BaiDu_TJ;
    }

    public String getGoogle_TJ() {
        return Google_TJ;
    }

    public void setGoogle_TJ(String Google_TJ) {
        this.Google_TJ = Google_TJ;
    }

    // KEEP METHODS - put your custom methods here
    public String getTitle(int language) {
        return language == 0 ? TitleTW : language == 1 ? TitleEN : TitleCN;
    }

    public void setTitle(int language,String title){
        if(language==0){
            TitleTW=title;
        }else if(language==1){
            TitleEN=title;
        }else{
            TitleCN=title;
        }
    }
    // KEEP METHODS END

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.IconID);
        dest.writeValue(this.IsDelete);
        dest.writeString(this.TitleTW);
        dest.writeString(this.TitleCN);
        dest.writeString(this.TitleEN);
        dest.writeString(this.Icon);
        dest.writeValue(this.CType);
        dest.writeString(this.CFile);
        dest.writeString(this.ZipDateTime);
        dest.writeValue(this.IsHidden);
        dest.writeValue(this.SEQ);
        dest.writeString(this.CreateDateTime);
        dest.writeString(this.UpdateDateTime);
        dest.writeString(this.RecordTimeStamp);
        dest.writeValue(this.IsDown);
        dest.writeString(this.BaiDu_TJ);
        dest.writeString(this.Google_TJ);
    }

    protected MainIcon(Parcel in) {
        this.IconID = in.readString();
        this.IsDelete = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.TitleTW = in.readString();
        this.TitleCN = in.readString();
        this.TitleEN = in.readString();
        this.Icon = in.readString();
        this.CType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.CFile = in.readString();
        this.ZipDateTime = in.readString();
        this.IsHidden = (Integer) in.readValue(Integer.class.getClassLoader());
        this.SEQ = (Integer) in.readValue(Integer.class.getClassLoader());
        this.CreateDateTime = in.readString();
        this.UpdateDateTime = in.readString();
        this.RecordTimeStamp = in.readString();
        this.IsDown = (Integer) in.readValue(Integer.class.getClassLoader());
        this.BaiDu_TJ = in.readString();
        this.Google_TJ = in.readString();
    }

    public static final Parcelable.Creator<MainIcon> CREATOR = new Parcelable.Creator<MainIcon>() {
        public MainIcon createFromParcel(Parcel source) {
            return new MainIcon(source);
        }

        public MainIcon[] newArray(int size) {
            return new MainIcon[size];
        }
    };
}
