package com.adsale.HEATEC.fragment;

import java.util.Calendar;

import com.adsale.HEATEC.R;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CalendarTimeFragment extends DialogFragment {

	private int mresTitleID;
	private Calendar mSelectDate;
	private Context mContext;
	private OnTimeSetListener mOnTimeSetListener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		mContext = getActivity();
		TimePickerDialog oTimePickerDialog = new TimePickerDialog(mContext, R.style.HoloLightDialog_No_Border, mOnTimeSetListener,
				mSelectDate.get(Calendar.HOUR_OF_DAY), mSelectDate.get(Calendar.MINUTE), false);
		oTimePickerDialog.setTitle(mresTitleID);

		return oTimePickerDialog;

	}

	public void SetDialog(Calendar pSelectDate, int presTitleID, OnTimeSetListener pOnTimeSetListener) {
		mresTitleID = presTitleID;
		if (pSelectDate != null) {
			mSelectDate = pSelectDate;
		} else {
			mSelectDate = Calendar.getInstance();
		}
		mOnTimeSetListener = pOnTimeSetListener;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getDialog() == null)
			return;
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

}
