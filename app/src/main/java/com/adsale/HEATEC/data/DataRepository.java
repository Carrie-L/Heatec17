package com.adsale.HEATEC.data;

import android.content.Context;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.dao.DBHelper;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.dao.MainIconDao;
import com.adsale.HEATEC.dao.MapFloor;
import com.adsale.HEATEC.dao.MapFloorDao;
import com.adsale.HEATEC.database.model.clsMainIcon;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Carrie on 2017/9/25.
 */

public class DataRepository {
    private static final String TAG = "DataRepository";
    private Context mContext;
    private final DBHelper mDBHelper;
    private MainIconDao mainIconDao;
    private MapFloorDao mapFloorDao;

    public static DataRepository getInstance(Context context) {
        return new DataRepository(context);
    }

    private DataRepository(Context context) {
        mContext = context;
        mDBHelper = App.mDBHelper;
        mainIconDao = mDBHelper.mIconDao;
        mapFloorDao = mDBHelper.mapFloorDao;
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

}
