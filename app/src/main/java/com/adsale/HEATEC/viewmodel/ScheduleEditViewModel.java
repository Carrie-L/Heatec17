package com.adsale.HEATEC.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.data.DataRepository;
import com.adsale.HEATEC.util.Constant;
import com.adsale.HEATEC.util.LogUtil;

/**
 * Created by Carrie on 2017/10/11.
 */

public class ScheduleEditViewModel {
    private final String TAG = "ScheduleEditViewModel";
    public final ObservableField<String> etTitle = new ObservableField<>("");
    public final ObservableField<String> etLocation = new ObservableField<>("");
    public final ObservableField<String> etStartDate = new ObservableField<>("");
    public final ObservableField<String> etStartTime = new ObservableField<>("09:00");
    public final ObservableField<String> etNote = new ObservableField<>("");
    public final ObservableField<String> etHour = new ObservableField<>("0");
    public final ObservableField<String> etMinute = new ObservableField<>("15");
    /*是 edit 还是 add ,true: edit; false: add */
    public final ObservableBoolean isEdit = new ObservableBoolean();
    public final ObservableInt dateIndex = new ObservableInt(0);

    private Context mContext;
    private DataRepository mRepository;
    private long mId;
    private ScheduleInfo mScheduleInfo;
    private String mCompanyId;

    public ScheduleEditViewModel(Context mContext, long mId) {
        this.mContext = mContext;
        this.mId = mId;
        LogUtil.i(TAG, "mId=" + mId);
        mRepository = DataRepository.getInstance(mContext);
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public void initData() {
        etStartDate.set(getStartDate());


    }

    private String getStartDate() {
        if (dateIndex.get() == 0) {
            return Constant.SCHEDULE_MONTH.concat("-").concat(Constant.SCHEDULE_DAY0);
        } else if (dateIndex.get() == 1) {
            return Constant.SCHEDULE_MONTH.concat("-").concat(Constant.SCHEDULE_DAY1);
        } else {
            return Constant.SCHEDULE_MONTH.concat("-").concat(Constant.SCHEDULE_DAY2);
        }
    }

    public void onDelete() {
        mRepository.deleteItemData(mId);
    }

    /**
     * ScheduleInfo: [Long ScheduleID, String CompanyID, String Title, String Location, Integer Day_Index, String StartTime, Integer Length (use minute), Integer AllDay (use hour), String Note]
     */
    public void onSave() {
        ScheduleInfo scheduleInfo = new ScheduleInfo(mId, mCompanyId, etTitle.get(), etLocation.get(), dateIndex.get(), etStartTime.get(), Integer.valueOf(etMinute.get()), Integer.valueOf(etHour.get()), "");
        LogUtil.i(TAG, "onSave::scheduleInfo= " + scheduleInfo.toString());
        mRepository.insertItemData(scheduleInfo);
    }


}
