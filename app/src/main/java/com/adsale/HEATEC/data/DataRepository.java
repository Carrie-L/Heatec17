package com.adsale.HEATEC.data;

import android.content.Context;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.dao.DBHelper;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.dao.MainIconDao;
import com.adsale.HEATEC.dao.MapFloorDao;
import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.dao.ScheduleInfoDao;

import java.util.ArrayList;

/**
 * Created by Carrie on 2017/9/25.
 */

public class DataRepository {
    private static final String TAG = "DataRepository";
    private Context mContext;
    private final DBHelper mDBHelper;
    private MainIconDao mainIconDao;
    private MapFloorDao mapFloorDao;
    private ScheduleInfoDao mScheduleInfoDao;

    public static DataRepository getInstance(Context context) {
        return new DataRepository(context);
    }

    private DataRepository(Context context) {
        mContext = context;
        mDBHelper = App.mDBHelper;
        mainIconDao = mDBHelper.mIconDao;
        mapFloorDao = mDBHelper.mapFloorDao;
        mScheduleInfoDao = mDBHelper.mScheduleInfoDao;
    }

    // \\\\\\\\\\\\\\\\\\\   MainIcon  \\\\\\\\\\\\\\\
    public ArrayList<MainIcon> getMainIconList() {
        return (ArrayList<MainIcon>) mainIconDao.queryRaw(" where 1=1 AND [IS_HIDDEN] = 0 and [IS_DELETE]==0 and [GOOGLE__TJ] not like '%-%' order by [SEQ] desc");
    }

    public ArrayList<MainIcon> getPadMainIconList() {
        return (ArrayList<MainIcon>) mainIconDao.queryRaw(" where 1=1  AND [IS_HIDDEN] = 0 and [IS_DELETE]==0 order by [SEQ] desc");
    }

    // \\\\\\\\\\\\\\\\\\\\\   MapFloor   \\\\\\\\\\\\\\\\\\\\\\\\\
    public boolean checkMapFloorParentID() {
//        List<MapFloor> list = mapFloorDao.queryRaw(" Where PARENT_ID='' AND TYPE=1 AND (select count(*) from MAP_FLOOR Where PARENT_ID='')=1");
        return mapFloorDao.queryBuilder().where(MapFloorDao.Properties.ParentID.isNotNull()).list().isEmpty();
    }

    // \\\\\\\\\\\\\\\\\\\\\   ScheduleInfo   \\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * 获取某一天的日程列表
     * @param dayIndex start from 0. 第0天，第1天，第2天
     * @return ArrayList
     */
    public ArrayList<ScheduleInfo> getDateSchedules(int dayIndex){
        return (ArrayList<ScheduleInfo>) mScheduleInfoDao.queryBuilder().where(ScheduleInfoDao.Properties.DayIndex.eq(dayIndex)).list();
    }

    public void insertItemData(ScheduleInfo entity) {
        mScheduleInfoDao.insert(entity);
    }

    public void insertOrReplaceItemData(ScheduleInfo entity) {
        mScheduleInfoDao.insertOrReplace(entity);
    }

    public void deleteItemData(long scheduleId) {
        mScheduleInfoDao.deleteByKey(scheduleId);
    }

    public ScheduleInfo getItemData(long id){
        return mScheduleInfoDao.load(id);
    }

    public void updateItemData(ScheduleInfo entity) {
        mScheduleInfoDao.update(entity);
    }

    public boolean isSameTimeSchedule(int dayIndex,String startTime){
        return !mScheduleInfoDao.queryBuilder().where(ScheduleInfoDao.Properties.DayIndex.eq(dayIndex),ScheduleInfoDao.Properties.StartTime.eq(startTime)).limit(1).list().isEmpty();
    }

}
