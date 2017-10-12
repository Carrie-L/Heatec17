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
        public final static Property CompanyID = new Property(1, String.class, "CompanyID", false, "COMPANY_ID");
        public final static Property Title = new Property(2, String.class, "Title", false, "TITLE");
        public final static Property Location = new Property(3, String.class, "Location", false, "LOCATION");
        public final static Property Day_Index = new Property(4, Integer.class, "Day_Index", false, "DAY__INDEX");
        public final static Property StartTime = new Property(5, String.class, "StartTime", false, "START_TIME");
        public final static Property Hour = new Property(6, Integer.class, "Hour", false, "HOUR");
        public final static Property Minute = new Property(7, Integer.class, "Minute", false, "MINUTE");
        public final static Property Note = new Property(8, String.class, "Note", false, "NOTE");
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
                "\"COMPANY_ID\" TEXT," + // 1: CompanyID
                "\"TITLE\" TEXT," + // 2: Title
                "\"LOCATION\" TEXT," + // 3: Location
                "\"DAY__INDEX\" INTEGER," + // 4: Day_Index
                "\"START_TIME\" TEXT," + // 5: StartTime
                "\"HOUR\" INTEGER," + // 6: Hour
                "\"MINUTE\" INTEGER," + // 7: Minute
                "\"NOTE\" TEXT);"); // 8: Note
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
 
        String CompanyID = entity.getCompanyID();
        if (CompanyID != null) {
            stmt.bindString(2, CompanyID);
        }
 
        String Title = entity.getTitle();
        if (Title != null) {
            stmt.bindString(3, Title);
        }
 
        String Location = entity.getLocation();
        if (Location != null) {
            stmt.bindString(4, Location);
        }
 
        Integer Day_Index = entity.getDay_Index();
        if (Day_Index != null) {
            stmt.bindLong(5, Day_Index);
        }
 
        String StartTime = entity.getStartTime();
        if (StartTime != null) {
            stmt.bindString(6, StartTime);
        }
 
        Integer Hour = entity.getHour();
        if (Hour != null) {
            stmt.bindLong(7, Hour);
        }
 
        Integer Minute = entity.getMinute();
        if (Minute != null) {
            stmt.bindLong(8, Minute);
        }
 
        String Note = entity.getNote();
        if (Note != null) {
            stmt.bindString(9, Note);
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
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // CompanyID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Location
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // Day_Index
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // StartTime
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // Hour
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // Minute
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // Note
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ScheduleInfo entity, int offset) {
        entity.setScheduleID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCompanyID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLocation(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDay_Index(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setStartTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setHour(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setMinute(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setNote(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
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
