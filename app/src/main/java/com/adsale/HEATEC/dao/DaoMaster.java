package com.adsale.HEATEC.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.dao.CountryDao;
import com.adsale.HEATEC.dao.ExhibitorDao;
import com.adsale.HEATEC.dao.ExhibitorIndustryDtlDao;
import com.adsale.HEATEC.dao.FloorDao;
import com.adsale.HEATEC.dao.IndustryDao;
import com.adsale.HEATEC.dao.MainIconDao;
import com.adsale.HEATEC.dao.MapFloorDao;
import com.adsale.HEATEC.dao.NewsDao;
import com.adsale.HEATEC.dao.NewsLinkDao;
import com.adsale.HEATEC.dao.ScheduleInfoDao;
import com.adsale.HEATEC.dao.UpdateDateDao;
import com.adsale.HEATEC.dao.WebContentDao;
import com.adsale.HEATEC.util.LogUtil;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        CountryDao.createTable(db, ifNotExists);
        ExhibitorDao.createTable(db, ifNotExists);
        ExhibitorIndustryDtlDao.createTable(db, ifNotExists);
        FloorDao.createTable(db, ifNotExists);
        IndustryDao.createTable(db, ifNotExists);
        MainIconDao.createTable(db, ifNotExists);
        MapFloorDao.createTable(db, ifNotExists);
        NewsDao.createTable(db, ifNotExists);
        NewsLinkDao.createTable(db, ifNotExists);
        ScheduleInfoDao.createTable(db, ifNotExists);
        UpdateDateDao.createTable(db, ifNotExists);
        WebContentDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        CountryDao.dropTable(db, ifExists);
        ExhibitorDao.dropTable(db, ifExists);
        ExhibitorIndustryDtlDao.dropTable(db, ifExists);
        FloorDao.dropTable(db, ifExists);
        IndustryDao.dropTable(db, ifExists);
        MainIconDao.dropTable(db, ifExists);
        MapFloorDao.dropTable(db, ifExists);
        NewsDao.dropTable(db, ifExists);
        NewsLinkDao.dropTable(db, ifExists);
        ScheduleInfoDao.dropTable(db, ifExists);
        UpdateDateDao.dropTable(db, ifExists);
        WebContentDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);

            LogUtil.i("DaoMaster_DevOpenHelper","DevOpenHelper(Context context, String name, CursorFactory factory)");

            App.mSPConfig.edit().putInt("DB_Version",SCHEMA_VERSION).apply();

            SQLiteDatabase db=App.openDatabase(App.DB_PATH.concat("/").concat(App.DATABASE_NAME));
            if(db!=null&&db.isOpen()){
                db.close();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            LogUtil.e("DaoMaster_DevOpenHelper","greenDAO"+"Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            App.mSPConfig.edit().putBoolean("DB_UPGRADE",true).apply();
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(CountryDao.class);
        registerDaoClass(ExhibitorDao.class);
        registerDaoClass(ExhibitorIndustryDtlDao.class);
        registerDaoClass(FloorDao.class);
        registerDaoClass(IndustryDao.class);
        registerDaoClass(MainIconDao.class);
        registerDaoClass(MapFloorDao.class);
        registerDaoClass(NewsDao.class);
        registerDaoClass(NewsLinkDao.class);
        registerDaoClass(ScheduleInfoDao.class);
        registerDaoClass(UpdateDateDao.class);
        registerDaoClass(WebContentDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}