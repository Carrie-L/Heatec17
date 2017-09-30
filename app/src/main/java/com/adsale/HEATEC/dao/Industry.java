package com.adsale.HEATEC.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "INDUSTRY".
 */
public class Industry {

    private String IndustryID;
    private Boolean IsDelete;
    private String IndustryNameTW;
    private String IndustryNameCN;
    private String IndustryNameEN;
    private String SortTW;
    private String SortCN;
    private String SortEN;
    private String CreateDateTime;
    private String UpdateDateTime;
    private String RecordTimeStamp;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Industry() {
    }

    public Industry(String IndustryID) {
        this.IndustryID = IndustryID;
    }

    public Industry(String IndustryID, Boolean IsDelete, String IndustryNameTW, String IndustryNameCN, String IndustryNameEN, String SortTW, String SortCN, String SortEN, String CreateDateTime, String UpdateDateTime, String RecordTimeStamp) {
        this.IndustryID = IndustryID;
        this.IsDelete = IsDelete;
        this.IndustryNameTW = IndustryNameTW;
        this.IndustryNameCN = IndustryNameCN;
        this.IndustryNameEN = IndustryNameEN;
        this.SortTW = SortTW;
        this.SortCN = SortCN;
        this.SortEN = SortEN;
        this.CreateDateTime = CreateDateTime;
        this.UpdateDateTime = UpdateDateTime;
        this.RecordTimeStamp = RecordTimeStamp;
    }

    public String getIndustryID() {
        return IndustryID;
    }

    public void setIndustryID(String IndustryID) {
        this.IndustryID = IndustryID;
    }

    public Boolean getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public String getIndustryNameTW() {
        return IndustryNameTW;
    }

    public void setIndustryNameTW(String IndustryNameTW) {
        this.IndustryNameTW = IndustryNameTW;
    }

    public String getIndustryNameCN() {
        return IndustryNameCN;
    }

    public void setIndustryNameCN(String IndustryNameCN) {
        this.IndustryNameCN = IndustryNameCN;
    }

    public String getIndustryNameEN() {
        return IndustryNameEN;
    }

    public void setIndustryNameEN(String IndustryNameEN) {
        this.IndustryNameEN = IndustryNameEN;
    }

    public String getSortTW() {
        return SortTW;
    }

    public void setSortTW(String SortTW) {
        this.SortTW = SortTW;
    }

    public String getSortCN() {
        return SortCN;
    }

    public void setSortCN(String SortCN) {
        this.SortCN = SortCN;
    }

    public String getSortEN() {
        return SortEN;
    }

    public void setSortEN(String SortEN) {
        this.SortEN = SortEN;
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

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
