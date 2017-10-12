package com.adsale.HEATEC.data;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.dao.Exhibitor;
import com.adsale.HEATEC.dao.ExhibitorDao;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.view.SideLetter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Locale;

import de.greenrobot.dao.query.WhereCondition;

import static com.adsale.HEATEC.App.mDBHelper;
import static com.adsale.HEATEC.R.drawable.booth;
import static com.adsale.HEATEC.R.drawable.language;

/**
 * Created by Carrie on 2017/10/12.
 */

public class ExhibitorRepository {
    private final String TAG = "ExhibitorRepository";
    private Integer mLanguage;
    private ExhibitorDao mExhibitorDao;

    @Nullable
    private static ExhibitorRepository INSTANCE = null;

    public static ExhibitorRepository getInstance(int language) {
        if (INSTANCE == null) {
            return new ExhibitorRepository(language);
        }
        return INSTANCE;
    }

    private ExhibitorRepository(int language) {
        mLanguage = language;
        mExhibitorDao = mDBHelper.mExhibitorDao;
    }

    /**
     * TW: select * from EXHIBITOR where SORT_TW!="#" order by CAST(SORT_TW AS INT) ASC
     * CN: select * from EXHIBITOR where SORT_CN!="#" order by SORT_CN ASC
     *
     * @return ArrayList<Exhibitor>
     */
    public ArrayList<Exhibitor> getAllExhibitors(ArrayList<Exhibitor> exhibitors, ArrayList<String> letters) {
        ArrayList<Exhibitor> list0;// 获取所有【SORT != #】的数据
        ArrayList<Exhibitor> list1;// 获取所有【SORT == #】的数据
        boolean hasSign = true;// 有#

        if (mLanguage == 0) {
            list0 = (ArrayList<Exhibitor>) mExhibitorDao.queryBuilder().where(new WhereCondition.StringCondition("SORT_TW!=\"#\" order by CAST(SORT_TW AS INT) ASC ")).list();
            list1 = (ArrayList<Exhibitor>) mExhibitorDao.queryBuilder().where(ExhibitorDao.Properties.SortTW.eq("#")).orderAsc(ExhibitorDao.Properties.SortTW).list();
        } else if (mLanguage == 1) {
            list0 = (ArrayList<Exhibitor>) mExhibitorDao.queryBuilder().where(ExhibitorDao.Properties.SortEN.notEq("#")).orderAsc(ExhibitorDao.Properties.SortEN).list();
            list1 = (ArrayList<Exhibitor>) mExhibitorDao.queryBuilder().where(ExhibitorDao.Properties.SortEN.eq("#")).orderAsc(ExhibitorDao.Properties.SortEN).list();
        } else {
            list0 = (ArrayList<Exhibitor>) mExhibitorDao.queryBuilder().where(ExhibitorDao.Properties.SortCN.notEq("#")).orderAsc(ExhibitorDao.Properties.SortCN).list();
            list1 = (ArrayList<Exhibitor>) mExhibitorDao.queryBuilder().where(ExhibitorDao.Properties.SortCN.eq("#")).orderAsc(ExhibitorDao.Properties.SortCN).list();
        }
        if (list1.isEmpty()) {// 这个列表为空时，说明SORT不含有#
            hasSign = false;
        }
        exhibitors.addAll(list0);
        exhibitors.addAll(list1);
        list0.clear();
        list1.clear();

        LogUtil.i(TAG, "mLanguage=" + mLanguage + ", hasSign=" + hasSign);
        Cursor cursor = App.mDBHelper.db.rawQuery(getAllLettersSql(), null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                letters.add(cursor.getString(0));
            }
            cursor.close();
        }
        if (hasSign) {
            letters.add("#");
        }

        return exhibitors;
    }

    private String getAllLettersSql() {
        if (mLanguage == 0) {
            return "select distinct SORT_TW from EXHIBITOR where SORT_TW!=\"#\" order by CAST(SORT_TW AS INT) ASC";
        } else if (mLanguage == 1) {
            return "select distinct SORT_EN from EXHIBITOR where SORT_EN!=\"#\" order by SORT_EN ASC";
        } else {
            return "select distinct SORT_CN from EXHIBITOR where SORT_CN!=\"#\" order by SORT_CN ASC";
        }
    }

    private ArrayList<String> getLetters(ArrayList<String> letters, String sql, boolean hasSign) {
        LogUtil.i(TAG, "mLanguage=" + mLanguage + ", hasSign=" + hasSign);
        Cursor cursor = App.mDBHelper.db.rawQuery(getAllLettersSql(), null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                letters.add(cursor.getString(0));
            }
            cursor.close();
        }
        if (hasSign) {
            letters.add("#");
        }
        return letters;
    }


    public ArrayList<Exhibitor> getExhibitorSearchResults(ArrayList<Exhibitor> exhibitors, ArrayList<String> letters,
                                                          String keyword) {
        ArrayList<Exhibitor> exhibitorsTemps = new ArrayList<>();
        ArrayList<String> lettersTemps = new ArrayList<>();
        int size = exhibitors.size();
        Exhibitor exhibitor;
        keyword = keyword.toLowerCase(Locale.getDefault());
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                exhibitor = exhibitors.get(i);
                if (exhibitor.getCompanyNameCN().toLowerCase(Locale.getDefault()).contains(keyword)
                        || exhibitor.getCompanyNameEN().toLowerCase(Locale.getDefault()).contains(keyword)
                        || exhibitor.getCompanyNameTW().toLowerCase(Locale.getDefault()).contains(keyword)
                        || exhibitor.getExhibitorNO().toLowerCase(Locale.getDefault()).contains(keyword)) {
                    lettersTemps.add(exhibitor.getSort(mLanguage));
                    exhibitorsTemps.add(exhibitor);
                }
            }
        }
        LogUtil.i(TAG, "exhibitorsTemps=" + exhibitorsTemps.size());
        //去重
        lettersTemps = new ArrayList<>(new LinkedHashSet<>(lettersTemps));
        LogUtil.i(TAG, "去重后 -：lettersTemps=" + lettersTemps.size() + "," + lettersTemps.toString());

        letters.clear();
        letters.addAll(lettersTemps);// 要用 addAll 或 add，那边的 mLetters 才能收到数据。用 = 是不行的。

        return exhibitorsTemps;
    }

    /**
     * Used to force {@link #getInstance(int language)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

}
