package com.adsale.HEATEC.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.adsale.HEATEC.activity.ExhibitorListActivity;
import com.adsale.HEATEC.adapter.ScheduleAdapter;
import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.data.DataRepository;
import com.adsale.HEATEC.util.Constant;
import com.adsale.HEATEC.util.LogUtil;

import java.util.ArrayList;

import static com.adsale.HEATEC.R.drawable.date;


/**
 * ScheduleList 和 ScheduleEdit 共同使用。
 * todo 当insert时插入到日历，delete时也从日历删除。
 * Created by Carrie on 2017/8/11.
 */

public class ScheduleViewModel extends BaseObservable {
    private static final String TAG = "ScheduleViewModel";

    public final ObservableArrayList<ScheduleInfo> scheduleInfos = new ObservableArrayList<>();
    public final ObservableBoolean noSchedules = new ObservableBoolean(true);
    public final ObservableBoolean isEmpty = new ObservableBoolean();
    public final ObservableInt dateIndex = new ObservableInt(0);

    private ArrayList<ScheduleInfo> schedulesCache0 = new ArrayList<>();
    private ArrayList<ScheduleInfo> schedulesCache1 = new ArrayList<>();
    private ArrayList<ScheduleInfo> schedulesCache2 = new ArrayList<>();


    private Context mContext;
    private DataRepository mDataRepository;
    private long mId;
    private ScheduleInfo mScheduleInfo;
    private String mCompanyId;
    /**
     * 列表是否有更新
     */
    private boolean updated = false;
    private ScheduleAdapter adapter;

    public ScheduleViewModel(Context context) {
        this.mContext = context.getApplicationContext();
        mDataRepository = DataRepository.getInstance(context);
    }

    public void initScheduleList(Activity aty,RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayout.VERTICAL));

        adapter = new ScheduleAdapter(aty,new ArrayList<ScheduleInfo>(0));
        recyclerView.setAdapter(adapter);
    }

    public void showItemPageList(int index, boolean updated) {
        dateIndex.set(index);
        this.updated = updated;
        getItemPageList();
        isEmpty.set(scheduleInfos.isEmpty());
        LogUtil.i(TAG, "showItemPageList: index=" + index + ", scheduleInfos.size = " + scheduleInfos.size());
        if (updated) {
            adapter.setList(scheduleInfos);
            adapter.notifyDataSetChanged();
        }
    }

    public void onDateClick(int date) {
        showItemPageList(date, false);
    }

    private ArrayList<ScheduleInfo> getSchedules() {
        return mDataRepository.getDateSchedules(dateIndex.get());
    }

    private void getItemPageList() {
        scheduleInfos.clear();
        if (dateIndex.get() == 0) {
            getList0();
        } else if (dateIndex.get() == 1) {
            getList1();
        } else {
            getList2();
        }
    }

    private void getList0() {
        if (schedulesCache0.isEmpty() || updated) {
            schedulesCache0.clear();
            schedulesCache0 = getSchedules();
        }
        scheduleInfos.addAll(schedulesCache0);
    }

    private void getList1() {
        if (schedulesCache1.isEmpty() || updated) {
            schedulesCache1.clear();
            schedulesCache1 = getSchedules();
        }
        scheduleInfos.addAll(schedulesCache1);
    }

    private void getList2() {
        if (schedulesCache2.isEmpty() || updated) {
            schedulesCache2.clear();
            schedulesCache2 = getSchedules();
        }
        scheduleInfos.addAll(schedulesCache2);
    }

    public void onAdd() {
        LogUtil.i(TAG, "onAdd: dateIndex=" + dateIndex.get());
        Intent intent = new Intent(mContext, ExhibitorListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constant.INTENT_DATE_INDEX, dateIndex.get());
        mContext.startActivity(intent);
    }


}
