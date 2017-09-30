package com.adsale.HEATEC.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.adsale.HEATEC.dao.NewsLink;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NEWS_LINK".
*/
public class NewsLinkDao extends AbstractDao<NewsLink, String> {

    public static final String TABLENAME = "NEWS_LINK";

    /**
     * Properties of entity NewsLink.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property LinkID = new Property(0, String.class, "LinkID", true, "LINK_ID");
        public final static Property IsDelete = new Property(1, Boolean.class, "IsDelete", false, "IS_DELETE");
        public final static Property NewsID = new Property(2, String.class, "NewsID", false, "NEWS_ID");
        public final static Property Photo = new Property(3, String.class, "Photo", false, "PHOTO");
        public final static Property Title = new Property(4, String.class, "Title", false, "TITLE");
        public final static Property Link = new Property(5, String.class, "Link", false, "LINK");
        public final static Property SEQ = new Property(6, Integer.class, "SEQ", false, "SEQ");
        public final static Property CreateDateTime = new Property(7, String.class, "CreateDateTime", false, "CREATE_DATE_TIME");
        public final static Property UpdateDateTime = new Property(8, String.class, "UpdateDateTime", false, "UPDATE_DATE_TIME");
        public final static Property RecordTimeStamp = new Property(9, String.class, "RecordTimeStamp", false, "RECORD_TIME_STAMP");
    };


    public NewsLinkDao(DaoConfig config) {
        super(config);
    }
    
    public NewsLinkDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NEWS_LINK\" (" + //
                "\"LINK_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: LinkID
                "\"IS_DELETE\" INTEGER," + // 1: IsDelete
                "\"NEWS_ID\" TEXT," + // 2: NewsID
                "\"PHOTO\" TEXT," + // 3: Photo
                "\"TITLE\" TEXT," + // 4: Title
                "\"LINK\" TEXT," + // 5: Link
                "\"SEQ\" INTEGER," + // 6: SEQ
                "\"CREATE_DATE_TIME\" TEXT NOT NULL ," + // 7: CreateDateTime
                "\"UPDATE_DATE_TIME\" TEXT NOT NULL ," + // 8: UpdateDateTime
                "\"RECORD_TIME_STAMP\" TEXT NOT NULL );"); // 9: RecordTimeStamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NEWS_LINK\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, NewsLink entity) {
        stmt.clearBindings();
 
        String LinkID = entity.getLinkID();
        if (LinkID != null) {
            stmt.bindString(1, LinkID);
        }
 
        Boolean IsDelete = entity.getIsDelete();
        if (IsDelete != null) {
            stmt.bindLong(2, IsDelete ? 1L: 0L);
        }
 
        String NewsID = entity.getNewsID();
        if (NewsID != null) {
            stmt.bindString(3, NewsID);
        }
 
        String Photo = entity.getPhoto();
        if (Photo != null) {
            stmt.bindString(4, Photo);
        }
 
        String Title = entity.getTitle();
        if (Title != null) {
            stmt.bindString(5, Title);
        }
 
        String Link = entity.getLink();
        if (Link != null) {
            stmt.bindString(6, Link);
        }
 
        Integer SEQ = entity.getSEQ();
        if (SEQ != null) {
            stmt.bindLong(7, SEQ);
        }
        stmt.bindString(8, entity.getCreateDateTime());
        stmt.bindString(9, entity.getUpdateDateTime());
        stmt.bindString(10, entity.getRecordTimeStamp());
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public NewsLink readEntity(Cursor cursor, int offset) {
        NewsLink entity = new NewsLink( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // LinkID
            cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0, // IsDelete
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // NewsID
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Photo
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Title
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Link
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // SEQ
            cursor.getString(offset + 7), // CreateDateTime
            cursor.getString(offset + 8), // UpdateDateTime
            cursor.getString(offset + 9) // RecordTimeStamp
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, NewsLink entity, int offset) {
        entity.setLinkID(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setIsDelete(cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0);
        entity.setNewsID(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPhoto(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTitle(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLink(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSEQ(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setCreateDateTime(cursor.getString(offset + 7));
        entity.setUpdateDateTime(cursor.getString(offset + 8));
        entity.setRecordTimeStamp(cursor.getString(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(NewsLink entity, long rowId) {
        return entity.getLinkID();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(NewsLink entity) {
        if(entity != null) {
            return entity.getLinkID();
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