package com.adsale.HEATEC.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.adsale.HEATEC.activity.ExhibitorListActivity;
import com.adsale.HEATEC.adapter.ScheduleAdapter;
import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.data.DataRepository;
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


    //   \\\\\\\\\\\\\\\\\ Schedule Edit \\\\\\\\\\\\\\\\\
    public final ObservableField<String> etTitle = new ObservableField<>();
    public final ObservableField<String> etLocation = new ObservableField<>();
    public final ObservableField<String> etStartDate = new ObservableField<>();
    public final ObservableField<String> etStartTime = new ObservableField<>("09:00");
    public final ObservableField<String> etNote = new ObservableField<>();
    public final ObservableField<String> etHour = new ObservableField<>("0");
    public final ObservableField<String> etMinute = new ObservableField<>("15");
    /*是 edit 还是 add ,true: edit; false: add */
    public final ObservableBoolean isEdit = new ObservableBoolean();

    private Context mContext;
    private DataRepository mDataRepository;
    private long mId;
    private ScheduleInfo mScheduleInfo;
    private String mCompanyId;

    public ScheduleViewModel(Context context) {
        this.mContext = context.getApplicationContext();
        mDataRepository = DataRepository.getInstance(context);
    }

//    private static ScheduleViewModel INSTANCE;
//    public static ScheduleViewModel getInstance(){
//        if(INSTANCE==null){
//            LogUtil.e(TAG,"INSTANCE==null");
//            return new ScheduleViewModel();
//        }
//        LogUtil.e(TAG,"getInstance()");
//        return INSTANCE;
//    }

    public void initScheduleList(RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayout.VERTICAL));

        ScheduleAdapter adapter = new ScheduleAdapter(new ArrayList<ScheduleInfo>(0));
        recyclerView.setAdapter(adapter);
    }

    public void setCompanyId(String companyId){
        mCompanyId=companyId;
    }

    //由 list 的 item 点击而来
    public ScheduleViewModel(Context context, long id) {
        this.mContext = context.getApplicationContext();
        this.mId = id;
        mDataRepository = DataRepository.getInstance(context);
        mScheduleInfo = mDataRepository.getItemData(mId);
        setupEditView();
        isEdit.set(true);
    }

    //由 add 按钮点击而来
    public ScheduleViewModel(Context context, int index) {
        this.mContext = context.getApplicationContext();
        dateIndex.set(index);
        mDataRepository = DataRepository.getInstance(context);
//        etStartDate.set(toStrDate());
        isEdit.set(false);
        LogUtil.i(TAG, " etStartDate=" + etStartDate.get());
    }

    private void setupEditView() {
        LogUtil.i(TAG, "mScheduleInfo=" + mScheduleInfo.toString());
        etTitle.set(mScheduleInfo.getTitle());
        etLocation.set(mScheduleInfo.getLocation());
//        etStartDate.set(mScheduleInfo.getStartDate());
        etStartTime.set(mScheduleInfo.getStartTime());
//        etMinute.set(mScheduleInfo.getMinute() + "");
//        etHour.set(mScheduleInfo.getHour() + "");
        etNote.set(mScheduleInfo.getNote());
    }

    public void onStart(int date) {
        dateIndex.set(date);
        scheduleInfos.clear();
        ArrayList<ScheduleInfo> list = getSchedules();
        scheduleInfos.addAll(list);
        isEmpty.set(scheduleInfos.isEmpty());
        LogUtil.i(TAG, "onDateClick: date=" + date + ", list.size = " + list.size() + ", etStartDate=" + etStartDate.get());
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
        mContext.startActivity(intent);
    }


}
