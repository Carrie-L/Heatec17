package com.adsale.HEATEC.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "FLOOR".
 */
public class Floor {

    private String FloorID;
    private Boolean IsDelete;
    private String FloorNameTW;
    private String FloorNameCN;
    private String FloorNameEN;
    private Integer SEQ;
    /** Not-null value. */
    private String CreateDateTime;
    /** Not-null value. */
    private String UpdateDateTime;
    /** Not-null value. */
    private String RecordTimeStamp;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Floor() {
    }

    public Floor(String FloorID) {
        this.FloorID = FloorID;
    }

    public Floor(String FloorID, Boolean IsDelete, String FloorNameTW, String FloorNameCN, String FloorNameEN, Integer SEQ, String CreateDateTime, String UpdateDateTime, String RecordTimeStamp) {
        this.FloorID = FloorID;
        this.IsDelete = IsDelete;
        this.FloorNameTW = FloorNameTW;
        this.FloorNameCN = FloorNameCN;
        this.FloorNameEN = FloorNameEN;
        this.SEQ = SEQ;
        this.CreateDateTime = CreateDateTime;
        this.UpdateDateTime = UpdateDateTime;
        this.RecordTimeStamp = RecordTimeStamp;
    }

    public String getFloorID() {
        return FloorID;
    }

    public void setFloorID(String FloorID) {
        this.FloorID = FloorID;
    }

    public Boolean getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public String getFloorNameTW() {
        return FloorNameTW;
    }

    public void setFloorNameTW(String FloorNameTW) {
        this.FloorNameTW = FloorNameTW;
    }

    public String getFloorNameCN() {
        return FloorNameCN;
    }

    public void setFloorNameCN(String FloorNameCN) {
        this.FloorNameCN = FloorNameCN;
    }

    public String getFloorNameEN() {
        return FloorNameEN;
    }

    public void setFloorNameEN(String FloorNameEN) {
        this.FloorNameEN = FloorNameEN;
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

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}