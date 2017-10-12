package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class HeatecGenerator {

    public static void main(String[] args) throws Exception {
        System.out.println("main");

        Schema schema=new Schema(1,"com.adsale.HEATEC.dao");
        addCountry(schema);
        addExhibitor(schema);
        addExhibitorIndustryDtl(schema);
        addFloor(schema);
        addIndustry(schema);
        addMainIcon(schema);
        addMapFloor(schema);
        addNews(schema);
        addNewsLink(schema);
        addScheduleInfo(schema);
        addUpdateDate(schema);
        addWebContent(schema);
        schema.enableKeepSectionsByDefault();
        new DaoGenerator().generateAll(schema,"../Heatec17/app/src/main/java");// E:\studio_workspace\Heatec17\app\src\main\java\
    }

    private static void addCountry(Schema schema){
        Entity entity=schema.addEntity("Country");
        entity.addStringProperty("CountryID");
        entity.addStringProperty("CountryNameTW");
        entity.addStringProperty("CountryNameCN");
        entity.addStringProperty("CountryNameEN");
        entity.addStringProperty("SortTW");
        entity.addStringProperty("SortCN");
        entity.addStringProperty("SortEN");
    }

    private static void addExhibitor(Schema schema){
        Entity entity=schema.addEntity("Exhibitor");
        entity.addStringProperty("CompanyID").primaryKey();
        entity.addBooleanProperty("IsDelete");
        entity.addStringProperty("CompanyNameTW");
        entity.addStringProperty("DescriptionTW");
        entity.addStringProperty("AddressTW");
        entity.addStringProperty("SortTW");
        entity.addStringProperty("CompanyNameCN");
        entity.addStringProperty("DescriptionCN");
        entity.addStringProperty("AddressCN");
        entity.addStringProperty("SortCN");
        entity.addStringProperty("CompanyNameEN");
        entity.addStringProperty("DescriptionEN");
        entity.addStringProperty("AddressEN");
        entity.addStringProperty("SortEN");
        entity.addStringProperty("ExhibitorNO");
        entity.addStringProperty("CountryID");
        entity.addStringProperty("Logo");
        entity.addStringProperty("Tel");
        entity.addStringProperty("Tel1");
        entity.addStringProperty("Fax");
        entity.addStringProperty("Email");
        entity.addStringProperty("Website");
        entity.addStringProperty("Longitude");
        entity.addStringProperty("Latitude");
        entity.addStringProperty("Location_X");
        entity.addStringProperty("Location_Y");
        entity.addStringProperty("Location_W");
        entity.addStringProperty("Location_H");
        entity.addStringProperty("SEQ");
        entity.addStringProperty("CreateDateTime");
        entity.addStringProperty("UpdateDateTime");
        entity.addStringProperty("RecordTimeStamp");
        entity.addStringProperty("ContactTW");
        entity.addStringProperty("TitleTW");
        entity.addStringProperty("ContactCN");
        entity.addStringProperty("TitleCN");
        entity.addStringProperty("ContactEN");
        entity.addStringProperty("TitleEN");
        entity.addStringProperty("IsFavourite");
        entity.addStringProperty("Note");
        entity.addStringProperty("Floor");
    }

    private static void addExhibitorIndustryDtl(Schema schema){
        Entity entity=schema.addEntity("ExhibitorIndustryDtl");
        entity.addStringProperty("CompanyID").primaryKey();
        entity.addBooleanProperty("IsDelete");
        entity.addStringProperty("IndustryID").notNull();
        entity.addStringProperty("CreateDateTime").notNull();
        entity.addStringProperty("UpdateDateTime").notNull();
        entity.addStringProperty("RecordTimeStamp").notNull();
    }

    private static void addFloor(Schema schema){
        Entity entity=schema.addEntity("Floor");
        entity.addStringProperty("FloorID").primaryKey();
        entity.addBooleanProperty("IsDelete");
        entity.addStringProperty("FloorNameTW");
        entity.addStringProperty("FloorNameCN");
        entity.addStringProperty("FloorNameEN");
        entity.addIntProperty("SEQ");//default 0
        entity.addStringProperty("CreateDateTime").notNull();
        entity.addStringProperty("UpdateDateTime").notNull();
        entity.addStringProperty("RecordTimeStamp").notNull();
    }

    private static void addIndustry(Schema schema){
        Entity entity=schema.addEntity("Industry");
        entity.addStringProperty("IndustryID").primaryKey();
        entity.addBooleanProperty("IsDelete");
        entity.addStringProperty("IndustryNameTW");
        entity.addStringProperty("IndustryNameCN");
        entity.addStringProperty("IndustryNameEN");
        entity.addStringProperty("SortTW");// default ''
        entity.addStringProperty("SortCN");// default ''
        entity.addStringProperty("SortEN");// default ''
        entity.addStringProperty("CreateDateTime");
        entity.addStringProperty("UpdateDateTime");
        entity.addStringProperty("RecordTimeStamp");
    }

    private static void addMainIcon(Schema schema){
        Entity entity=schema.addEntity("MainIcon");
        entity.addStringProperty("IconID").primaryKey();
        entity.addBooleanProperty("IsDelete");
        entity.addStringProperty("TitleTW").notNull();
        entity.addStringProperty("TitleCN").notNull();
        entity.addStringProperty("TitleEN").notNull();
        entity.addStringProperty("Icon");
        entity.addIntProperty("CType");// default 0
        entity.addStringProperty("CFile");
        entity.addStringProperty("ZipDateTime");
        entity.addIntProperty("IsHidden");// default 0
        entity.addIntProperty("SEQ");
        entity.addStringProperty("CreateDateTime").notNull();
        entity.addStringProperty("UpdateDateTime").notNull();
        entity.addStringProperty("RecordTimeStamp").notNull();
        entity.addIntProperty("IsDown");// default 0
        entity.addStringProperty("BaiDu_TJ");
        entity.addStringProperty("Google_TJ");
    }

    private static void addMapFloor(Schema schema){
        Entity entity=schema.addEntity("MapFloor");
        entity.addStringProperty("MapFloorID").primaryKey();
        entity.addBooleanProperty("IsDelete");
        entity.addStringProperty("ParentID");
        entity.addIntProperty("Type");// default 0
        entity.addStringProperty("NameTW");
        entity.addStringProperty("NameCN");
        entity.addStringProperty("NameEN");
        entity.addIntProperty("SEQ");// default 0
        entity.addStringProperty("CreateDateTime").notNull();
        entity.addStringProperty("UpdateDateTime").notNull();
        entity.addStringProperty("RecordTimeStamp").notNull();
    }

    private static void addNews(Schema schema){
        Entity entity=schema.addEntity("News");
        entity.addStringProperty("NewsID").primaryKey();
        entity.addBooleanProperty("IsDelete");
        entity.addIntProperty("LType");// default 1
        entity.addStringProperty("Logo");// default ''
        entity.addStringProperty("ShareLink");// default ''
        entity.addStringProperty("Title");
        entity.addStringProperty("Description");
        entity.addStringProperty("CreateDateTime").notNull();
        entity.addStringProperty("UpdateDateTime").notNull();
        entity.addStringProperty("RecordTimeStamp").notNull();
        entity.addStringProperty("PublishDate");
    }

    private static void addNewsLink(Schema schema){
        Entity entity=schema.addEntity("NewsLink");
        entity.addStringProperty("LinkID").primaryKey();
        entity.addBooleanProperty("IsDelete");
        entity.addStringProperty("NewsID");
        entity.addStringProperty("Photo");
        entity.addStringProperty("Title");
        entity.addStringProperty("Link");
        entity.addIntProperty("SEQ");
        entity.addStringProperty("CreateDateTime").notNull();
        entity.addStringProperty("UpdateDateTime").notNull();
        entity.addStringProperty("RecordTimeStamp").notNull();
    }

    private static void addWebContent(Schema schema){
        Entity entity=schema.addEntity("WebContent");
        entity.addStringProperty("PageId").primaryKey();
        entity.addBooleanProperty("IsDelete");
        entity.addStringProperty("TitleTW");// default ''
        entity.addStringProperty("TitleCN");// default ''
        entity.addStringProperty("TitleEN");// default ''
        entity.addIntProperty("CType");// default 0
        entity.addStringProperty("CFile");// default ''
        entity.addStringProperty("ZipDateTime");// default ''
        entity.addStringProperty("ContentEN");
        entity.addStringProperty("ContentSC");
        entity.addStringProperty("ContentTC");
        entity.addStringProperty("CreateDateTime");
        entity.addStringProperty("UpdateDateTime");
        entity.addStringProperty("RecordTimeStamp");
        entity.addIntProperty("IsDown");// default 0
    }

    private static void addScheduleInfo(Schema schema){
        Entity entity=schema.addEntity("ScheduleInfo");
        entity.addLongProperty("ScheduleID").primaryKey().autoincrement();
        entity.addStringProperty("CompanyID");
        entity.addStringProperty("Title");
        entity.addStringProperty("Location");
        entity.addIntProperty("Day_Index");//start from 0
        entity.addStringProperty("StartTime");
        entity.addIntProperty("Hour");
        entity.addIntProperty("Minute");
        entity.addStringProperty("Note");
    }

    private static void addUpdateDate(Schema schema){
        Entity entity=schema.addEntity("UpdateDate");
        entity.addStringProperty("ModuleID").primaryKey();
        entity.addStringProperty("LastUpdateDate");
    }







}
