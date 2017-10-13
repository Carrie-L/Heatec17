package com.adsale.HEATEC.adapter;

import java.util.ArrayList;
import java.util.List;

import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.util.Constant;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.view.SchedulePagerItem;
import com.adsale.HEATEC.viewmodel.ScheduleViewModel;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SchedulePagerADT extends PagerAdapter {
    private final String TAG = "SchedulePagerADT";
    private List<View> views;

    public SchedulePagerADT(List<View> list) {
        super();
        this.views = list;
    }

    @Override
    public int getCount() {
        return Constant.SCHEDULE_DAYS;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtil.i(TAG, "instantiateItem:" + position);
        container.addView(views.get(position), 0);
        return views.get(position);
    }


}
