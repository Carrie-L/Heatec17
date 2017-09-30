package com.adsale.HEATEC.dao;

import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库管理器
 * Created by Administrator on 2016/6/23 0023.
 */
public class DBHelper {
    private static final String TAG = "DBHelper";

    private DaoSession mDaoSession;
    private DaoMaster mDaoMaster;
    public SQLiteDatabase db;

    public MainIconDao mIconDao;
    public CountryDao mCountryDao;
    public ExhibitorDao mExhibitorDao;
    public ExhibitorIndustryDtlDao mIndustryDtlDao;
    public FloorDao mFloorDao;
    public IndustryDao mIndustryDao;
    public MapFloorDao mapFloorDao;
    public NewsDao mNewsDao;
    public NewsLinkDao mLinkDao;
    public ScheduleInfoDao mScheduleInfoDao;
    public UpdateDateDao mUpdateDateDao;
    public WebContentDao mWebContentDao;


    private DBHelper(Builder builder) {
        mDaoSession = builder.mDaoSession;
        mDaoMaster = builder.mDaoMaster;
        db = builder.mDB;
        initDao();
    }

    public static final class Builder {
        private DaoSession mDaoSession;
        private DaoMaster mDaoMaster;
        private SQLiteDatabase mDB;

        public Builder(DaoSession daoSession, DaoMaster daoMaster, SQLiteDatabase db) {
            this.mDaoSession = daoSession;
            this.mDaoMaster = daoMaster;
            this.mDB = db;
        }

        public DBHelper build() {
            return new DBHelper(this);
        }
    }

    public void initDao() {
        mIconDao = mDaoSession.getMainIconDao();
        mCountryDao = mDaoSession.getCountryDao();
        mExhibitorDao = mDaoSession.getExhibitorDao();
        mIndustryDtlDao = mDaoSession.getExhibitorIndustryDtlDao();
        mFloorDao = mDaoSession.getFloorDao();
        mIndustryDao = mDaoSession.getIndustryDao();
        mapFloorDao = mDaoSession.getMapFloorDao();
        mLinkDao = mDaoSession.getNewsLinkDao();
        mNewsDao = mDaoSession.getNewsDao();
        mScheduleInfoDao = mDaoSession.getScheduleInfoDao();
        mUpdateDateDao = mDaoSession.getUpdateDateDao();
        mWebContentDao = mDaoSession.getWebContentDao();
    }

    /**
     * 当有更新的时候，将oldDB的这些数据清空。不清空的话仍保存原来的数据，不是新的。
     */
    public void setToNull() {
        mDaoMaster = null;
        mDaoSession = null;
        db = null;
    }


}