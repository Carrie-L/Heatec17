package com.adsale.HEATEC.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Carrie on 2017/10/13.
 */

public class DatePickerDialogFragment extends DialogFragment {
    private final String TAG = "DatePickerDialogFragment";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Calendar calendar;
    private int currDay;
    private Context mContext;

    public void setDateDialog(Context context, int currDay, DatePickerDialog.OnDateSetListener listener) {
        this.mContext = context;
        this.currDay = currDay;
        mDateSetListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = Integer.valueOf(Constant.SCHEDULE_MONTH.split("-")[0]);
        int month = Integer.valueOf(Constant.SCHEDULE_MONTH.split("-")[1]);
        int minDay = Integer.valueOf(Constant.SCHEDULE_DAY0); // 开始日
        int maxDay = Integer.valueOf(Constant.SCHEDULE_DAY_END); // 结束日
//        int currDay = 11;
//        LogUtil.e(TAG, "minDay=" + minDay);
//        LogUtil.e(TAG, "maxDay=" + maxDay);
//        LogUtil.e(TAG, "currDay=" + currDay);
//        LogUtil.e(TAG, "year=" + year);
//        LogUtil.e(TAG, "month=" + month);

        DatePickerDialog dateDialog = new DatePickerDialog(mContext, mDateSetListener, year, month, currDay);
        //set minDate and maxDate
        calendar=Calendar.getInstance();
        DatePicker datePicker = dateDialog.getDatePicker();
        calendar.set(year, month - 1, minDay, 0, 0);//月份需减1
        long minDate = calendar.getTimeInMillis();
        calendar.set(year, month - 1, maxDay, 23, 59, 59);
        long maxDate = calendar.getTimeInMillis();
        datePicker.setMinDate(minDate);
        datePicker.setMaxDate(maxDate);
//        calendar.set(year, month - 1, currDay, 0, 0);

        return dateDialog;
    }
}
