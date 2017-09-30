package com.adsale.HEATEC.fragment;

import java.util.Calendar;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.App;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class CalendarDateFragment extends DialogFragment {

	private int mresTitleID;
	private Calendar oCalendar;
	private Context mContext;
	private OnDateSetListener mOnDateSetListener;
	
	private final int YEAR=2017;
	private final int MONTH=9;//月份-1
	private final int DAY_0=10;
	private final int DAY_1=12;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		mContext = getActivity();

		int intYear = oCalendar.get(Calendar.YEAR);
		if (intYear < 1900)
			intYear += 1900;

		DatePickerDialog oDatePickerDialog = new DatePickerDialog(mContext, R.style.HoloLightDialog_No_Border, mOnDateSetListener, intYear,
				oCalendar.get(Calendar.MONTH), oCalendar.get(Calendar.DAY_OF_MONTH));
		oDatePickerDialog.setTitle(mresTitleID);
		DatePicker datePicker = oDatePickerDialog.getDatePicker();
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR, MONTH, DAY_0, 0, 0);//月份减1
		long minDate = calendar.getTimeInMillis();
		calendar.set(YEAR, MONTH, DAY_1, 23, 59, 59);
		long maxDate = calendar.getTimeInMillis();
		
		if(App.IsBlackoutDate){
			//限制最大最小日期
			datePicker.setMinDate(minDate);
			datePicker.setMaxDate(maxDate);
		}
	

		// 4.0 设置不显示日历
		oDatePickerDialog.getDatePicker().setCalendarViewShown(false);
		return oDatePickerDialog;

	}

	public void SetDialog(Calendar pCalendar, int presTitleID, OnDateSetListener pOnDateSetListener) {

		mresTitleID = presTitleID;
		mOnDateSetListener = pOnDateSetListener;
		if (pCalendar != null) {
			oCalendar = pCalendar;
		} else {
			oCalendar = Calendar.getInstance();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getDialog() == null)
			return;
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

}
