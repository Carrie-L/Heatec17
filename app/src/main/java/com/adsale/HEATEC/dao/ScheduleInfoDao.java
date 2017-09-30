package com.adsale.HEATEC.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.adsale.HEATEC.dao.ScheduleInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SCHEDULE_INFO".
*/
public class ScheduleInfoDao extends AbstractDao<ScheduleInfo, Long> {

    public static final String TABLENAME = "SCHEDULE_INFO";

    /**
     * Properties of entity ScheduleInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ScheduleID = new Property(0, Long.class, "ScheduleID", true, "SCHEDULE_ID");
        public final static Property Title = new Property(1, String.class, "Title", false, "TITLE");
        public final static Property Note = new Property(2, String.class, "Note", false, "NOTE");
        public final static Property Location = new Property(3, String.class, "Location", false, "LOCATION");
        public final static Property CompanyID = new Property(4, String.class, "CompanyID", false, "COMPANY_ID");
        public final static Property StartTime = new Property(5, String.class, "StartTime", false, "START_TIME");
        public final static Property Length = new Property(6, Integer.class, "Length", false, "LENGTH");
        public final static Property Allday = new Property(7, Integer.class, "Allday", false, "ALLDAY");
    };


    public ScheduleInfoDao(DaoConfig config) {
        super(config);
    }
    
    public ScheduleInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SCHEDULE_INFO\" (" + //
                "\"SCHEDULE_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: ScheduleID
                "\"TITLE\" TEXT," + // 1: Title
                "\"NOTE\" TEXT," + // 2: Note
                "\"LOCATION\" TEXT," + // 3: Location
                "\"COMPANY_ID\" TEXT," + // 4: CompanyID
                "\"START_TIME\" TEXT," + // 5: StartTime
                "\"LENGTH\" INTEGER," + // 6: Length
                "\"ALLDAY\" INTEGER);"); // 7: Allday
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SCHEDULE_INFO\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ScheduleInfo entity) {
        stmt.clearBindings();
 
        Long ScheduleID = entity.getScheduleID();
        if (ScheduleID != null) {
            stmt.bindLong(1, ScheduleID);
        }
 
        String Title = entity.getTitle();
        if (Title != null) {
            stmt.bindString(2, Title);
        }
 
        String Note = entity.getNote();
        if (Note != null) {
            stmt.bindString(3, Note);
        }
 
        String Location = entity.getLocation();
        if (Location != null) {
            stmt.bindString(4, Location);
        }
 
        String CompanyID = entity.getCompanyID();
        if (CompanyID != null) {
            stmt.bindString(5, CompanyID);
        }
 
        String StartTime = entity.getStartTime();
        if (StartTime != null) {
            stmt.bindString(6, StartTime);
        }
 
        Integer Length = entity.getLength();
        if (Length != null) {
            stmt.bindLong(7, Length);
        }
 
        Integer Allday = entity.getAllday();
        if (Allday != null) {
            stmt.bindLong(8, Allday);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ScheduleInfo readEntity(Cursor cursor, int offset) {
        ScheduleInfo entity = new ScheduleInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ScheduleID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // Title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Note
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Location
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // CompanyID
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // StartTime
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // Length
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7) // Allday
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ScheduleInfo entity, int offset) {
        entity.setScheduleID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNote(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLocation(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCompanyID(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setStartTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLength(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setAllday(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ScheduleInfo entity, long rowId) {
        entity.setScheduleID(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ScheduleInfo entity) {
        if(entity != null) {
            return entity.getScheduleID();
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