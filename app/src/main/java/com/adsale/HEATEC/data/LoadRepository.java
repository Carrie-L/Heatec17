package com.adsale.HEATEC.data;

import android.content.Context;
import android.content.SharedPreferences;


import com.adsale.HEATEC.App;
import com.adsale.HEATEC.dao.Exhibitor;
import com.adsale.HEATEC.dao.ExhibitorDao;
import com.adsale.HEATEC.dao.ExhibitorIndustryDtl;
import com.adsale.HEATEC.dao.ExhibitorIndustryDtlDao;
import com.adsale.HEATEC.dao.Floor;
import com.adsale.HEATEC.dao.FloorDao;
import com.adsale.HEATEC.dao.Industry;
import com.adsale.HEATEC.dao.IndustryDao;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.dao.MainIconDao;
import com.adsale.HEATEC.dao.MapFloor;
import com.adsale.HEATEC.dao.MapFloorDao;
import com.adsale.HEATEC.dao.News;
import com.adsale.HEATEC.dao.NewsDao;
import com.adsale.HEATEC.dao.NewsLink;
import com.adsale.HEATEC.dao.NewsLinkDao;
import com.adsale.HEATEC.dao.UpdateDate;
import com.adsale.HEATEC.dao.UpdateDateDao;
import com.adsale.HEATEC.dao.WebContent;
import com.adsale.HEATEC.dao.WebContentDao;
import com.adsale.HEATEC.util.LogUtil;

import java.util.ArrayList;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;


/**
 * Created by Carrie on 2017/9/12.
 */

public class LoadRepository {
    private WebContentDao mWebContentDao = App.mDBHelper.mWebContentDao;
    private MainIconDao mMainIconDao = App.mDBHelper.mIconDao;
    private NewsDao mNewsDao = App.mDBHelper.mNewsDao;
    private NewsLinkDao mNewsLinkDao = App.mDBHelper.mLinkDao;
    private MapFloorDao mMapFloorDao = App.mDBHelper.mapFloorDao;
    private ExhibitorDao mExhibitorDao = App.mDBHelper.mExhibitorDao;
    private ExhibitorIndustryDtlDao mIndustryDtlDao = App.mDBHelper.mIndustryDtlDao;
    private IndustryDao mIndustryDao = App.mDBHelper.mIndustryDao;
    private FloorDao mFloorDao = App.mDBHelper.mFloorDao;
    private UpdateDateDao mUpdateDateDao = App.mDBHelper.mUpdateDateDao;

    private Context mContext;
    private boolean isFirstGetMaster;
    private final String TAG = "LoadRepository";
    private SharedPreferences mSP_lut;


    public static LoadRepository getInstance(Context context) {
        return new LoadRepository(context);
    }

    private LoadRepository(Context context) {
        mContext = context;
    }

    public void prepareInsertXmlData() {
        isFirstGetMaster = mContext.getSharedPreferences("config", Context.MODE_PRIVATE).getBoolean("isFirstGetMaster", false);
        LogUtil.i("LoadRepo", "isFirstGetMaster=" + isFirstGetMaster);
        mSP_lut = App.mSP_updateTime;
    }

    private <T> boolean deleteAll(ArrayList<T> list) {
        return isFirstGetMaster && list.size() > 0;
    }

    private <T> void insertAll(ArrayList<T> list, AbstractDao<T, String> dao, String maxUT) {
        if (list.size() == 0) {
            return;
        }
        long startTime = System.currentTimeMillis();
        if (deleteAll(list)) {
            LogUtil.e(TAG, dao.getTablename() + " --->>> clear all data");
            dao.deleteAll();
        }
        dao.insertOrReplaceInTx(list);
        LogUtil.i(TAG, "insertAll：" + dao.getTablename()+(System.currentTimeMillis() - startTime) + "ms");
        mSP_lut.edit().putString(dao.getTablename(), maxUT).apply();

        UpdateDate updateDate = new UpdateDate(dao.getTablename(), maxUT);
        mUpdateDateDao.insertOrReplaceInTx(updateDate);
    }

    public void insertMainIconAll(ArrayList<MainIcon> list) {
        if (list.size() == 0) {
            return;
        }
        insertAll(list, mMainIconDao, getMaxUT(MainIconDao.Properties.UpdateDateTime, mMainIconDao).getUpdateDateTime());
    }

    public void insertNewsAll(ArrayList<News> list) {
        if (list.size() == 0) {
            return;
        }
        insertAll(list, mNewsDao, getMaxUT(NewsDao.Properties.UpdateDateTime, mNewsDao).getUpdateDateTime());
    }

    public void insertNewsLinkAll(ArrayList<NewsLink> list) {
        if (list.size() == 0) {
            return;
        }
        insertAll(list, mNewsLinkDao, getMaxUT(NewsLinkDao.Properties.UpdateDateTime, mNewsLinkDao).getUpdateDateTime());
    }

    public void insertWebContentAll(ArrayList<WebContent> list) {
        if (list.size() == 0) {
            return;
        }
        insertAll(list, mWebContentDao,getMaxUT(WebContentDao.Properties.UpdateDateTime, mWebContentDao).getUpdateDateTime());
    }

    public void insertMapFloorAll(ArrayList<MapFloor> list) {
        if (list.size() == 0) {
            return;
        }
        insertAll(list, mMapFloorDao, getMaxUT(MapFloorDao.Properties.UpdateDateTime, mMapFloorDao).getUpdateDateTime());
    }

    public void insertExhibitorAll(ArrayList<Exhibitor> list) {
        if (list.size() == 0) {
            return;
        }
        insertAll(list, mExhibitorDao, getMaxUT(ExhibitorDao.Properties.UpdateDateTime, mExhibitorDao).getUpdateDateTime());
    }

    public void insertIndustryDtlAll(ArrayList<ExhibitorIndustryDtl> list) {
        if (list.size() == 0) {
            return;
        }
        insertAll(list, mIndustryDtlDao, getMaxUT(ExhibitorIndustryDtlDao.Properties.UpdateDateTime, mIndustryDtlDao).getUpdateDateTime());
    }

    public void insertIndustryAll(ArrayList<Industry> list) {
        if (list.size() == 0) {
            return;
        }
        insertAll(list, mIndustryDao, getMaxUT(IndustryDao.Properties.UpdateDateTime, mIndustryDao).getUpdateDateTime());
    }

    public void insertFloorAll(ArrayList<Floor> list) {
        if (list.size() == 0) {
            return;
        }
        insertAll(list, mFloorDao, getMaxUT(FloorDao.Properties.UpdateDateTime, mFloorDao).getUpdateDateTime());
    }

    /**
     * 获取最大更新时间
     */
    private <T, String> T getMaxUT(Property property, AbstractDao<T, String> dao) {
        return dao.queryBuilder().orderDesc(property).limit(1).list().get(0);
    }


}
