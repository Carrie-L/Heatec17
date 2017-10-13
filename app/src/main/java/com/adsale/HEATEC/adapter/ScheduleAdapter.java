package com.adsale.HEATEC.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;


import com.adsale.HEATEC.BR;
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
    private Activity activity;
    private ScheduleInfo scheduleInfo;

    public ScheduleAdapter(Activity activity, ArrayList<ScheduleInfo> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public void setList(ArrayList<ScheduleInfo> list) {
        this.list = list;
        super.setList(list);
    }

    @Override
    protected Object getObjForPosition(int position) {
        scheduleInfo = list.get(position);
        scheduleInfo.position = position;
        return scheduleInfo;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.view_scheduleinfo_item;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void bindVariable(ViewDataBinding binding) {
        super.bindVariable(binding);
        binding.setVariable(BR.activity, activity);
    }
}
