package com.adsale.HEATEC.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;


import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.ScheduleActivity;
import com.adsale.HEATEC.base.AdsaleBaseAdapter;
import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.util.network.Configure;

import java.util.ArrayList;

/**
 * Created by Carrie on 2017/8/11.
 */

public class ScheduleAdapter extends AdsaleBaseAdapter<ScheduleInfo> {
    private ArrayList<ScheduleInfo> list;

    public ScheduleAdapter(ArrayList<ScheduleInfo> list) {
        this.list = list;
    }

    @Override
    public void setList(ArrayList<ScheduleInfo> list) {
        this.list=list;
        super.setList(list);
    }

    @Override
    protected Object getObjForPosition(int position) {
        return list.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.view_scheduleinfo_item;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
