package com.adsale.HEATEC.viewmodel;

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


/**
 * ScheduleList 和 ScheduleEdit 共同使用。
 * todo 当insert时插入到日历，delete时也从日历删除。
 * Created by Carrie on 2017/8/11.
 */

public class ScheduleViewModel extends BaseObservable {
    private static final String TAG = "ScheduleViewModel";

    //   \\\\\\\\\\\\\\\\\ Schedule List \\\\\\\\\\\\\\\\\
    public final ObservableArrayList<ScheduleInfo> scheduleInfos = new ObservableArrayList<>();
    public final ObservableBoolean noSchedules = new ObservableBoolean(true);
    public final ObservableBoolean isEmpty = new ObservableBoolean();
    public final ObservableInt dateIndex = new ObservableInt(0);




    private Context mContext;
    private DataRepository mDataRepository;
    private long mId;
    private ScheduleInfo mScheduleInfo;
    private String mCompanyId;

    public ScheduleViewModel(Context context) {
        this.mContext = context.getApplicationContext();
        mDataRepository = DataRepository.getInstance(context);
    }

    public void initScheduleList(RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayout.VERTICAL));

        ScheduleAdapter adapter = new ScheduleAdapter(new ArrayList<ScheduleInfo>(0));
        recyclerView.setAdapter(adapter);
    }


    //由 list 的 item 点击而来
    public ScheduleViewModel(Context context, long id) {
        this.mContext = context.getApplicationContext();
        this.mId = id;
        mDataRepository = DataRepository.getInstance(context);
        mScheduleInfo = mDataRepository.getItemData(mId);

    }




    public void onStart(int date) {
        dateIndex.set(date);
        scheduleInfos.clear();
        ArrayList<ScheduleInfo> list = getSchedules();
        scheduleInfos.addAll(list);
        isEmpty.set(scheduleInfos.isEmpty());
        LogUtil.i(TAG, "onDateClick: date=" + date + ", list.size = " + list.size());
    }


    public void onDateClick(int date) {
        onStart(date);
    }

    private ArrayList<ScheduleInfo> getSchedules() {
        return mDataRepository.getDateSchedules(dateIndex.get());
    }






    public void onAdd(){
        LogUtil.i(TAG,"onAdd: dateIndex="+dateIndex.get());
        Intent intent=new Intent(mContext, ExhibitorListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constant.INTENT_DATE_INDEX,dateIndex.get());
        mContext.startActivity(intent);
    }


}
