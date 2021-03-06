package com.adsale.HEATEC.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

import android.os.Parcel;
import android.os.Parcelable;

import com.adsale.HEATEC.util.SystemMethod;

/**
 * Entity mapped to table "EXHIBITOR".
 */
public class Exhibitor implements Parcelable {

    private String CompanyID;
    private Boolean IsDelete;
    private String CompanyNameTW;
    private String DescriptionTW;
    private String AddressTW;
    private String SortTW;
    private String CompanyNameCN;
    private String DescriptionCN;
    private String AddressCN;
    private String SortCN;
    private String CompanyNameEN;
    private String DescriptionEN;
    private String AddressEN;
    private String SortEN;
    private String ExhibitorNO;
    private String CountryID;
    private String Logo;
    private String Tel;
    private String Tel1;
    private String Fax;
    private String Email;
    private String Website;
    private String Longitude;
    private String Latitude;
    private String Location_X;
    private String Location_Y;
    private String Location_W;
    private String Location_H;
    private String SEQ;
    private String CreateDateTime;
    private String UpdateDateTime;
    private String RecordTimeStamp;
    private String ContactTW;
    private String TitleTW;
    private String ContactCN;
    private String TitleCN;
    private String ContactEN;
    private String TitleEN;
    private String IsFavourite;
    private String Note;
    private String Floor;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Exhibitor() {
    }

    public Exhibitor(String CompanyID) {
        this.CompanyID = CompanyID;
    }

    public Exhibitor(String CompanyID, Boolean IsDelete, String CompanyNameTW, String DescriptionTW, String AddressTW, String SortTW, String CompanyNameCN, String DescriptionCN, String AddressCN, String SortCN, String CompanyNameEN, String DescriptionEN, String AddressEN, String SortEN, String ExhibitorNO, String CountryID, String Logo, String Tel, String Tel1, String Fax, String Email, String Website, String Longitude, String Latitude, String Location_X, String Location_Y, String Location_W, String Location_H, String SEQ, String CreateDateTime, String UpdateDateTime, String RecordTimeStamp, String ContactTW, String TitleTW, String ContactCN, String TitleCN, String ContactEN, String TitleEN, String IsFavourite, String Note, String Floor) {
        this.CompanyID = CompanyID;
        this.IsDelete = IsDelete;
        this.CompanyNameTW = CompanyNameTW;
        this.DescriptionTW = DescriptionTW;
        this.AddressTW = AddressTW;
        this.SortTW = SortTW;
        this.CompanyNameCN = CompanyNameCN;
        this.DescriptionCN = DescriptionCN;
        this.AddressCN = AddressCN;
        this.SortCN = SortCN;
        this.CompanyNameEN = CompanyNameEN;
        this.DescriptionEN = DescriptionEN;
        this.AddressEN = AddressEN;
        this.SortEN = SortEN;
        this.ExhibitorNO = ExhibitorNO;
        this.CountryID = CountryID;
        this.Logo = Logo;
        this.Tel = Tel;
        this.Tel1 = Tel1;
        this.Fax = Fax;
        this.Email = Email;
        this.Website = Website;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.Location_X = Location_X;
        this.Location_Y = Location_Y;
        this.Location_W = Location_W;
        this.Location_H = Location_H;
        this.SEQ = SEQ;
        this.CreateDateTime = CreateDateTime;
        this.UpdateDateTime = UpdateDateTime;
        this.RecordTimeStamp = RecordTimeStamp;
        this.ContactTW = ContactTW;
        this.TitleTW = TitleTW;
        this.ContactCN = ContactCN;
        this.TitleCN = TitleCN;
        this.ContactEN = ContactEN;
        this.TitleEN = TitleEN;
        this.IsFavourite = IsFavourite;
        this.Note = Note;
        this.Floor = Floor;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String CompanyID) {
        this.CompanyID = CompanyID;
    }

    public Boolean getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public String getCompanyNameTW() {
        return CompanyNameTW;
    }

    public void setCompanyNameTW(String CompanyNameTW) {
        this.CompanyNameTW = CompanyNameTW;
    }

    public String getDescriptionTW() {
        return DescriptionTW;
    }

    public void setDescriptionTW(String DescriptionTW) {
        this.DescriptionTW = DescriptionTW;
    }

    public String getAddressTW() {
        return AddressTW;
    }

    public void setAddressTW(String AddressTW) {
        this.AddressTW = AddressTW;
    }

    public String getSortTW() {
        return SortTW;
    }

    public void setSortTW(String SortTW) {
        this.SortTW = SortTW;
    }

    public String getCompanyNameCN() {
        return CompanyNameCN;
    }

    public void setCompanyNameCN(String CompanyNameCN) {
        this.CompanyNameCN = CompanyNameCN;
    }

    public String getDescriptionCN() {
        return DescriptionCN;
    }

    public void setDescriptionCN(String DescriptionCN) {
        this.DescriptionCN = DescriptionCN;
    }

    public String getAddressCN() {
        return AddressCN;
    }

    public void setAddressCN(String AddressCN) {
        this.AddressCN = AddressCN;
    }

    public String getSortCN() {
        return SortCN;
    }

    public void setSortCN(String SortCN) {
        this.SortCN = SortCN;
    }

    public String getCompanyNameEN() {
        return CompanyNameEN;
    }

    public void setCompanyNameEN(String CompanyNameEN) {
        this.CompanyNameEN = CompanyNameEN;
    }

    public String getDescriptionEN() {
        return DescriptionEN;
    }

    public void setDescriptionEN(String DescriptionEN) {
        this.DescriptionEN = DescriptionEN;
    }

    public String getAddressEN() {
        return AddressEN;
    }

    public void setAddressEN(String AddressEN) {
        this.AddressEN = AddressEN;
    }

    public String getSortEN() {
        return SortEN;
    }

    public void setSortEN(String SortEN) {
        this.SortEN = SortEN;
    }

    public String getExhibitorNO() {
        return ExhibitorNO;
    }

    public void setExhibitorNO(String ExhibitorNO) {
        this.ExhibitorNO = ExhibitorNO;
    }

    public String getCountryID() {
        return CountryID;
    }

    public void setCountryID(String CountryID) {
        this.CountryID = CountryID;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public String getTel1() {
        return Tel1;
    }

    public void setTel1(String Tel1) {
        this.Tel1 = Tel1;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(String Location_X) {
        this.Location_X = Location_X;
    }

    public String getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(String Location_Y) {
        this.Location_Y = Location_Y;
    }

    public String getLocation_W() {
        return Location_W;
    }

    public void setLocation_W(String Location_W) {
        this.Location_W = Location_W;
    }

    public String getLocation_H() {
        return Location_H;
    }

    public void setLocation_H(String Location_H) {
        this.Location_H = Location_H;
    }

    public String getSEQ() {
        return SEQ;
    }

    public void setSEQ(String SEQ) {
        this.SEQ = SEQ;
    }

    public String getCreateDateTime() {
        return CreateDateTime;
    }

    public void setCreateDateTime(String CreateDateTime) {
        this.CreateDateTime = CreateDateTime;
    }

    public String getUpdateDateTime() {
        return UpdateDateTime;
    }

    public void setUpdateDateTime(String UpdateDateTime) {
        this.UpdateDateTime = UpdateDateTime;
    }

    public String getRecordTimeStamp() {
        return RecordTimeStamp;
    }

    public void setRecordTimeStamp(String RecordTimeStamp) {
        this.RecordTimeStamp = RecordTimeStamp;
    }

    public String getContactTW() {
        return ContactTW;
    }

    public void setContactTW(String ContactTW) {
        this.ContactTW = ContactTW;
    }

    public String getTitleTW() {
        return TitleTW;
    }

    public void setTitleTW(String TitleTW) {
        this.TitleTW = TitleTW;
    }

    public String getContactCN() {
        return ContactCN;
    }

    public void setContactCN(String ContactCN) {
        this.ContactCN = ContactCN;
    }

    public String getTitleCN() {
        return TitleCN;
    }

    public void setTitleCN(String TitleCN) {
        this.TitleCN = TitleCN;
    }

    public String getContactEN() {
        return ContactEN;
    }

    public void setContactEN(String ContactEN) {
        this.ContactEN = ContactEN;
    }

    public String getTitleEN() {
        return TitleEN;
    }

    public void setTitleEN(String TitleEN) {
        this.TitleEN = TitleEN;
    }

    public String getIsFavourite() {
        return IsFavourite;
    }

    public void setIsFavourite(String IsFavourite) {
        this.IsFavourite = IsFavourite;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String Floor) {
        this.Floor = Floor;
    }

    // KEEP METHODS - put your custom methods here
    public String getCompanyName(int language) {
        return SystemMethod.getName(language,CompanyNameTW, CompanyNameEN, CompanyNameCN);
    }

    public String getSort(int language) {
        return SystemMethod.getName(language,SortTW, SortEN, SortCN);
    }

    private String getDesc(int language) {
        if (language == 0) {
            return DescriptionTW;
        } else if (language == 1) {
            return DescriptionEN;
        } else {
            return DescriptionCN;
        }
    }
    // KEEP METHODS END

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CompanyID);
        dest.writeValue(this.IsDelete);
        dest.writeString(this.CompanyNameTW);
        dest.writeString(this.DescriptionTW);
        dest.writeString(this.AddressTW);
        dest.writeString(this.SortTW);
        dest.writeString(this.CompanyNameCN);
        dest.writeString(this.DescriptionCN);
        dest.writeString(this.AddressCN);
        dest.writeString(this.SortCN);
        dest.writeString(this.CompanyNameEN);
        dest.writeString(this.DescriptionEN);
        dest.writeString(this.AddressEN);
        dest.writeString(this.SortEN);
        dest.writeString(this.ExhibitorNO);
        dest.writeString(this.CountryID);
        dest.writeString(this.Logo);
        dest.writeString(this.Tel);
        dest.writeString(this.Tel1);
        dest.writeString(this.Fax);
        dest.writeString(this.Email);
        dest.writeString(this.Website);
        dest.writeString(this.Longitude);
        dest.writeString(this.Latitude);
        dest.writeString(this.Location_X);
        dest.writeString(this.Location_Y);
        dest.writeString(this.Location_W);
        dest.writeString(this.Location_H);
        dest.writeString(this.SEQ);
        dest.writeString(this.CreateDateTime);
        dest.writeString(this.UpdateDateTime);
        dest.writeString(this.RecordTimeStamp);
        dest.writeString(this.ContactTW);
        dest.writeString(this.TitleTW);
        dest.writeString(this.ContactCN);
        dest.writeString(this.TitleCN);
        dest.writeString(this.ContactEN);
        dest.writeString(this.TitleEN);
        dest.writeString(this.IsFavourite);
        dest.writeString(this.Note);
        dest.writeString(this.Floor);
    }

    protected Exhibitor(Parcel in) {
        this.CompanyID = in.readString();
        this.IsDelete = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.CompanyNameTW = in.readString();
        this.DescriptionTW = in.readString();
        this.AddressTW = in.readString();
        this.SortTW = in.readString();
        this.CompanyNameCN = in.readString();
        this.DescriptionCN = in.readString();
        this.AddressCN = in.readString();
        this.SortCN = in.readString();
        this.CompanyNameEN = in.readString();
        this.DescriptionEN = in.readString();
        this.AddressEN = in.readString();
        this.SortEN = in.readString();
        this.ExhibitorNO = in.readString();
        this.CountryID = in.readString();
        this.Logo = in.readString();
        this.Tel = in.readString();
        this.Tel1 = in.readString();
        this.Fax = in.readString();
        this.Email = in.readString();
        this.Website = in.readString();
        this.Longitude = in.readString();
        this.Latitude = in.readString();
        this.Location_X = in.readString();
        this.Location_Y = in.readString();
        this.Location_W = in.readString();
        this.Location_H = in.readString();
        this.SEQ = in.readString();
        this.CreateDateTime = in.readString();
        this.UpdateDateTime = in.readString();
        this.RecordTimeStamp = in.readString();
        this.ContactTW = in.readString();
        this.TitleTW = in.readString();
        this.ContactCN = in.readString();
        this.TitleCN = in.readString();
        this.ContactEN = in.readString();
        this.TitleEN = in.readString();
        this.IsFavourite = in.readString();
        this.Note = in.readString();
        this.Floor = in.readString();
    }

    public static final Parcelable.Creator<Exhibitor> CREATOR = new Parcelable.Creator<Exhibitor>() {
        public Exhibitor createFromParcel(Parcel source) {
            return new Exhibitor(source);
        }

        public Exhibitor[] newArray(int size) {
            return new Exhibitor[size];
        }
    };
}
