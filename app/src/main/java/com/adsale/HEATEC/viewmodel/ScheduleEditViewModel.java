package com.adsale.HEATEC.viewmodel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.data.DataRepository;
import com.adsale.HEATEC.util.Constant;
import com.adsale.HEATEC.util.DatePickerDialogFragment;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;

import java.util.Calendar;

import static com.adsale.HEATEC.R.drawable.date;

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
    /**
     * id starts from 1, so if mId == 0, means adding a new schedule.
     */
    private long mId = 0;
    private String mCompanyId;
    private OnBackListener mListener;

    public ScheduleEditViewModel(Context mContext) {
        this.mContext = mContext;

        LogUtil.i(TAG, "mId=" + mId);
        mRepository = DataRepository.getInstance(mContext);
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public void initData(OnBackListener listener) {
        etStartDate.set(getStartDate());
        mListener = listener;
    }

    /**
     * dateIndex -> startDate  e.g.:[0 -> "2017-10-10"]
     *
     * @return startDate
     */
    private String getStartDate() {
        if (dateIndex.get() == 0) {
            return Constant.SCHEDULE_MONTH.concat("-").concat(Constant.SCHEDULE_DAY0);
        } else if (dateIndex.get() == 1) {
            return Constant.SCHEDULE_MONTH.concat("-").concat(Constant.SCHEDULE_DAY1);
        } else {
            return Constant.SCHEDULE_MONTH.concat("-").concat(Constant.SCHEDULE_DAY_END);
        }
    }

    /**
     * startDate -> dateIndex  e.g.:["2017-10-10" -> 0]
     */
    private void toDateIndex(String startDate) {
        if (startDate.equals(Constant.SCHEDULE_MONTH.concat("-").concat(Constant.SCHEDULE_DAY0))) {
            dateIndex.set(0);
        } else if (startDate.equals(Constant.SCHEDULE_MONTH.concat("-").concat(Constant.SCHEDULE_DAY1))) {
            dateIndex.set(1);
        } else if (startDate.equals(Constant.SCHEDULE_MONTH.concat("-").concat(Constant.SCHEDULE_DAY_END))) {
            dateIndex.set(2);
        }
    }

    public void onDelete() {
        mRepository.deleteItemData(mId);
        if (mListener != null) {
            mListener.back(true);
        }
    }

    /**
     * ScheduleInfo: [Long ScheduleID, String CompanyID, String Title, String Location, Integer DayIndex, String StartTime, Integer Hour, Integer Minute, String Note]
     * todo 相同时间已储存了另一个议程，继续储存?
     */
    public void onSave() {
        if (etTitle.get().isEmpty()) {
            Toast.makeText(mContext, mContext.getString(R.string.not_title), Toast.LENGTH_LONG).show();
            return;
        }
        ScheduleInfo scheduleInfo = new ScheduleInfo(isEdit.get() ? mId : null, mCompanyId, etTitle.get(), etLocation.get(), dateIndex.get(), SystemMethod.formatStartTime(etStartTime.get()), Integer.valueOf(etHour.get()), Integer.valueOf(etMinute.get()), "");
        LogUtil.i(TAG, "onSave::scheduleInfo= " + scheduleInfo.toString());
        mRepository.insertOrReplaceItemData(scheduleInfo);
        if (mListener != null) {
            mListener.back(true);
        }
    }

    /**
     * todo 点击时，日期不是当前日期
     *
     * @param view
     */
    public void onDateClick(View view) {
        int year = Integer.valueOf(Constant.SCHEDULE_MONTH.split("-")[0]);
        int month = Integer.valueOf(Constant.SCHEDULE_MONTH.split("-")[1]);
        int minDay = Integer.valueOf(Constant.SCHEDULE_DAY0); // 开始日
        int maxDay = Integer.valueOf(Constant.SCHEDULE_DAY_END); // 结束日
        int currDay = Integer.valueOf(etStartDate.get().split("-")[2]);
        LogUtil.e(TAG, "minDay=" + minDay);
        LogUtil.e(TAG, "maxDay=" + maxDay);
        LogUtil.e(TAG, "year=" + year);
        LogUtil.e(TAG, "month=" + month);
        LogUtil.e(TAG, "currDay=" + currDay);

        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                etStartDate.set(SystemMethod.dateToString(calendar.getTime()));
                toDateIndex(etStartDate.get());
                LogUtil.i(TAG, "dateIndex=" + dateIndex.get() + ", datePicker: " + etStartDate.get());
            }
        }, year, month, currDay);

        //set minDate and maxDate
        DatePicker datePicker = dateDialog.getDatePicker();
        calendar.set(year, month - 1, minDay, 0, 0);//月份需减1
        long minDate = calendar.getTimeInMillis();
        calendar.set(year, month - 1, maxDay, 23, 59, 59);
        long maxDate = calendar.getTimeInMillis();
        datePicker.setMinDate(minDate);
        datePicker.setMaxDate(maxDate);
        calendar.set(year, month - 1, currDay, 0, 0);
        dateDialog.show();

//        DatePickerDialogFragment fragment=new DatePickerDialogFragment();
//        fragment.setDateDialog(view.getContext(), currDay, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                Calendar calendar=Calendar.getInstance();
//                calendar.set(year, month, dayOfMonth);
//                etStartDate.set(SystemMethod.dateToString(calendar.getTime()));
//                toDateIndex(etStartDate.get());
//                LogUtil.i(TAG, "dateIndex=" + dateIndex.get() + ", datePicker: " + etStartDate.get());
//            }
//        });
//        fragment.show();

    }

    public void onTimeClick(View view) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(SystemMethod.stringToDate(etStartTime.get()));
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timeDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                etStartTime.set(SystemMethod.timeToString(calendar.getTime()));
                LogUtil.i(TAG, "timePicker: " + etStartTime.get());
            }
        }, mHour, mMinute, true);
        timeDialog.show();
    }

    public interface OnBackListener {
        void back(boolean change);
    }


}
