package com.adsale.HEATEC.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.adsale.HEATEC.dao.MainIcon;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MAIN_ICON".
*/
public class MainIconDao extends AbstractDao<MainIcon, String> {

    public static final String TABLENAME = "MAIN_ICON";

    /**
     * Properties of entity MainIcon.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property IconID = new Property(0, String.class, "IconID", true, "ICON_ID");
        public final static Property IsDelete = new Property(1, Boolean.class, "IsDelete", false, "IS_DELETE");
        public final static Property TitleTW = new Property(2, String.class, "TitleTW", false, "TITLE_TW");
        public final static Property TitleCN = new Property(3, String.class, "TitleCN", false, "TITLE_CN");
        public final static Property TitleEN = new Property(4, String.class, "TitleEN", false, "TITLE_EN");
        public final static Property Icon = new Property(5, String.class, "Icon", false, "ICON");
        public final static Property CType = new Property(6, Integer.class, "CType", false, "CTYPE");
        public final static Property CFile = new Property(7, String.class, "CFile", false, "CFILE");
        public final static Property ZipDateTime = new Property(8, String.class, "ZipDateTime", false, "ZIP_DATE_TIME");
        public final static Property IsHidden = new Property(9, Integer.class, "IsHidden", false, "IS_HIDDEN");
        public final static Property SEQ = new Property(10, Integer.class, "SEQ", false, "SEQ");
        public final static Property CreateDateTime = new Property(11, String.class, "CreateDateTime", false, "CREATE_DATE_TIME");
        public final static Property UpdateDateTime = new Property(12, String.class, "UpdateDateTime", false, "UPDATE_DATE_TIME");
        public final static Property RecordTimeStamp = new Property(13, String.class, "RecordTimeStamp", false, "RECORD_TIME_STAMP");
        public final static Property IsDown = new Property(14, Integer.class, "IsDown", false, "IS_DOWN");
        public final static Property BaiDu_TJ = new Property(15, String.class, "BaiDu_TJ", false, "BAI_DU__TJ");
        public final static Property Google_TJ = new Property(16, String.class, "Google_TJ", false, "GOOGLE__TJ");
    };


    public MainIconDao(DaoConfig config) {
        super(config);
    }
    
    public MainIconDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MAIN_ICON\" (" + //
                "\"ICON_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: IconID
                "\"IS_DELETE\" INTEGER," + // 1: IsDelete
                "\"TITLE_TW\" TEXT NOT NULL ," + // 2: TitleTW
                "\"TITLE_CN\" TEXT NOT NULL ," + // 3: TitleCN
                "\"TITLE_EN\" TEXT NOT NULL ," + // 4: TitleEN
                "\"ICON\" TEXT," + // 5: Icon
                "\"CTYPE\" INTEGER," + // 6: CType
                "\"CFILE\" TEXT," + // 7: CFile
                "\"ZIP_DATE_TIME\" TEXT," + // 8: ZipDateTime
                "\"IS_HIDDEN\" INTEGER," + // 9: IsHidden
                "\"SEQ\" INTEGER," + // 10: SEQ
                "\"CREATE_DATE_TIME\" TEXT NOT NULL ," + // 11: CreateDateTime
                "\"UPDATE_DATE_TIME\" TEXT NOT NULL ," + // 12: UpdateDateTime
                "\"RECORD_TIME_STAMP\" TEXT NOT NULL ," + // 13: RecordTimeStamp
                "\"IS_DOWN\" INTEGER," + // 14: IsDown
                "\"BAI_DU__TJ\" TEXT," + // 15: BaiDu_TJ
                "\"GOOGLE__TJ\" TEXT);"); // 16: Google_TJ
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MAIN_ICON\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MainIcon entity) {
        stmt.clearBindings();
 
        String IconID = entity.getIconID();
        if (IconID != null) {
            stmt.bindString(1, IconID);
        }
 
        Boolean IsDelete = entity.getIsDelete();
        if (IsDelete != null) {
            stmt.bindLong(2, IsDelete ? 1L: 0L);
        }
        stmt.bindString(3, entity.getTitleTW());
        stmt.bindString(4, entity.getTitleCN());
        stmt.bindString(5, entity.getTitleEN());
 
        String Icon = entity.getIcon();
        if (Icon != null) {
            stmt.bindString(6, Icon);
        }
 
        Integer CType = entity.getCType();
        if (CType != null) {
            stmt.bindLong(7, CType);
        }
 
        String CFile = entity.getCFile();
        if (CFile != null) {
            stmt.bindString(8, CFile);
        }
 
        String ZipDateTime = entity.getZipDateTime();
        if (ZipDateTime != null) {
            stmt.bindString(9, ZipDateTime);
        }
 
        Integer IsHidden = entity.getIsHidden();
        if (IsHidden != null) {
            stmt.bindLong(10, IsHidden);
        }
 
        Integer SEQ = entity.getSEQ();
        if (SEQ != null) {
            stmt.bindLong(11, SEQ);
        }
        stmt.bindString(12, entity.getCreateDateTime());
        stmt.bindString(13, entity.getUpdateDateTime());
        stmt.bindString(14, entity.getRecordTimeStamp());
 
        Integer IsDown = entity.getIsDown();
        if (IsDown != null) {
            stmt.bindLong(15, IsDown);
        }
 
        String BaiDu_TJ = entity.getBaiDu_TJ();
        if (BaiDu_TJ != null) {
            stmt.bindString(16, BaiDu_TJ);
        }
 
        String Google_TJ = entity.getGoogle_TJ();
        if (Google_TJ != null) {
            stmt.bindString(17, Google_TJ);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public MainIcon readEntity(Cursor cursor, int offset) {
        MainIcon entity = new MainIcon( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // IconID
            cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0, // IsDelete
            cursor.getString(offset + 2), // TitleTW
            cursor.getString(offset + 3), // TitleCN
            cursor.getString(offset + 4), // TitleEN
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Icon
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // CType
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // CFile
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // ZipDateTime
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // IsHidden
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // SEQ
            cursor.getString(offset + 11), // CreateDateTime
            cursor.getString(offset + 12), // UpdateDateTime
            cursor.getString(offset + 13), // RecordTimeStamp
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // IsDown
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // BaiDu_TJ
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16) // Google_TJ
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MainIcon entity, int offset) {
        entity.setIconID(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setIsDelete(cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0);
        entity.setTitleTW(cursor.getString(offset + 2));
        entity.setTitleCN(cursor.getString(offset + 3));
        entity.setTitleEN(cursor.getString(offset + 4));
        entity.setIcon(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCType(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setCFile(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setZipDateTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setIsHidden(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setSEQ(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setCreateDateTime(cursor.getString(offset + 11));
        entity.setUpdateDateTime(cursor.getString(offset + 12));
        entity.setRecordTimeStamp(cursor.getString(offset + 13));
        entity.setIsDown(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setBaiDu_TJ(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setGoogle_TJ(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(MainIcon entity, long rowId) {
        return entity.getIconID();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(MainIcon entity) {
        if(entity != null) {
            return entity.getIconID();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
