package com.adsale.HEATEC.fragment;

import java.util.Calendar;

import sanvio.libs.util.DateUtils;
import sanvio.libs.util.InputFilterMinMax;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.AlarmReceiver;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.database.ScheduleInfoDBHelper;
import com.adsale.HEATEC.util.DialogUtil;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;

public class ScheduleEditFragment extends BaseFragment {
    public static final String TAG = "ScheduleEditFragment";

    private Context mContext;
    private View mBaseView;

    private EditText editTitle, editLocation, editHour, editMinute;
    private EditText editStartTime, editStartTime2;
    private Button btnSave, btnDel;

    private String mExhibitorName, mCompanyID;
    private ScheduleInfo mScheduleInfo;
    private ScheduleInfoDBHelper mDbHelper;
    private Calendar mSelectDate;
    public Boolean mIsFragmentMode = false;
    private String mTitle;

    private AlarmManager alarmManager;

    private int currentItem;

    private static final int YEAR = 2017; //2017.10.10-12
    private static final int MONTH = 9;//月份-1
    private static final int DAY01 = 10;
    private static final int DAY02 = 11;
    private static final int DAY03 = 12;
    private static final int HOUR_OF_DAY = 9;
    private static final int MINUTE = 0;
    private static final int SECOND = 0;


    @Override
    public View initView(LayoutInflater inflater) {
        mBaseView = inflater.inflate(R.layout.f_schedule_edit, null);
        mContext = getActivity();

        findView();

        return mBaseView;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        if (savedInstanceState != null) {
            mCompanyID = savedInstanceState.getString("CompanyID");
            mExhibitorName = savedInstanceState.getString("ExhibitorName");
            mScheduleInfo = savedInstanceState.getParcelable("ScheduleInfo");
            currentItem = savedInstanceState.getInt("currentItem");
        } else {
            Intent intent = ((Activity) mContext).getIntent();
            mCompanyID = intent.getStringExtra("CompanyID");
            mExhibitorName = intent.getStringExtra("ExhibitorName");
            mScheduleInfo = intent.getParcelableExtra("ScheduleInfo");
            currentItem = intent.getIntExtra("currentItem", 0);
        }

        setupView();

        LogUtil.i(TAG, "name=" + mExhibitorName + ",CompanyID=" + mCompanyID);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CompanyID", mCompanyID);
        outState.putString("ExhibitorName", mExhibitorName);
        outState.putParcelable("ScheduleInfo", mScheduleInfo);
    }

    private void findView() {
        editTitle = (EditText) mBaseView.findViewById(R.id.editTitle);
        editLocation = (EditText) mBaseView.findViewById(R.id.editLocation);
        editHour = (EditText) mBaseView.findViewById(R.id.editHour);
        editMinute = (EditText) mBaseView.findViewById(R.id.editMinute);
        editStartTime = (EditText) mBaseView.findViewById(R.id.editStartTime);
        editStartTime2 = (EditText) mBaseView.findViewById(R.id.editStartTime2);
        btnSave = (Button) mBaseView.findViewById(R.id.btnSave);
        btnDel = (Button) mBaseView.findViewById(R.id.btnDel);
    }

    private void setupView() {

        mDbHelper = new ScheduleInfoDBHelper(mContext);
        InputFilter[] hourFilters = {new InputFilterMinMax(0, 99)};
        editHour.setFilters(hourFilters);
        InputFilter[] minuteFilters = {new InputFilterMinMax(0, 99)};
        editMinute.setFilters(minuteFilters);

        editMinute.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int hour = 0, minute = 0;
                try {
                    hour = Integer.valueOf(editHour.getText().toString().trim());
                    minute = Integer.valueOf(editMinute.getText().toString().trim());

                } catch (Exception e) {

                }

                if (minute > 60) {
                    hour = hour + minute / 60;
                    minute = minute % 60;
                    editHour.setText("" + hour);
                    editMinute.setText("" + minute);
                }

            }
        });

        if (mScheduleInfo == null) {
            editTitle.setText(mExhibitorName);
            editHour.setText(0 + "");
            editMinute.setText(15 + "");

            mSelectDate = Calendar.getInstance();
            if (currentItem == 0) {
                mSelectDate.set(YEAR, MONTH, DAY01, HOUR_OF_DAY, MINUTE, SECOND);
            } else if (currentItem == 1) {
                mSelectDate.set(YEAR, MONTH, DAY02, HOUR_OF_DAY, MINUTE, SECOND);
            } else if (currentItem == 2) {
                mSelectDate.set(YEAR, MONTH, DAY03, HOUR_OF_DAY, MINUTE, SECOND);
            }
            editStartTime.setText(DateUtils.FormatDate("yyyy-MM-dd", mSelectDate));
            editStartTime2.setText(DateUtils.FormatDate("hh:mm a", mSelectDate));
        } else {
            editTitle.setText(mScheduleInfo.getTitle());
            editLocation.setText(mScheduleInfo.getLocation());

            int length = mScheduleInfo.getLength();
            editHour.setText("" + length / 60);
            editMinute.setText("" + length % 60);

            mSelectDate = Calendar.getInstance();
            mSelectDate.setTime(DateUtils.String2Date("yyyy-MM-dd HH:mm", mScheduleInfo.getStartTime()));
            editStartTime.setText(DateUtils.FormatDate("yyyy-MM-dd", mSelectDate));
            editStartTime2.setText(DateUtils.FormatDate("hh:mm a", mSelectDate));
        }

        editStartTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDateFragment oCalendarDateFragment;
                oCalendarDateFragment = new CalendarDateFragment();
                oCalendarDateFragment.SetDialog(mSelectDate, R.string.startTime, oOnDateSetListener);
                oCalendarDateFragment.show(getFragmentManager(), "");
            }
        });

        editStartTime2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarTimeFragment oCalendarTimeFragment;
                oCalendarTimeFragment = new CalendarTimeFragment();
                oCalendarTimeFragment.SetDialog(mSelectDate, R.string.startTime, oOnTimeSetListener);
                oCalendarTimeFragment.show(getFragmentManager(), "");
            }
        });

        btnSave.setOnClickListener(btnSaveClickListener);

        if (mScheduleInfo == null) {
            btnDel.setVisibility(View.GONE);
        } else {
            btnDel.setVisibility(View.VISIBLE);
            btnDel.setOnClickListener(btnDelClickListener);
        }
    }

    OnDateSetListener oOnDateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mSelectDate.set(Calendar.YEAR, year);
            mSelectDate.set(Calendar.MONTH, monthOfYear);
            mSelectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editStartTime.setText(DateUtils.FormatDate("yyyy-MM-dd", mSelectDate));
        }
    };

    OnTimeSetListener oOnTimeSetListener = new OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mSelectDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mSelectDate.set(Calendar.MINUTE, minute);
            editStartTime2.setText(DateUtils.FormatDate("hh:mm a", mSelectDate));
        }
    };

    OnClickListener btnSaveClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (editTitle.getText().toString().trim().equals("")) {
                DialogUtil.showConfirmAlertDialog(mContext, getString(R.string.not_title));
                return;
            }

            if (mScheduleInfo == null) {
                String strDate = DateUtils.FormatDate("yyyy-MM-dd HH:mm", mSelectDate.getTime());
                if (mDbHelper.check(mCompanyID, strDate)) {
                    DialogUtil.showAlertDialog(mContext, R.string.ask_schedule,  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveScedule();
                        }
                    });
                } else {
                    saveScedule();
                }
            } else {
                saveScedule();
            }
        }
    };

    private void saveScedule() {
        int mScheduleID = (int) mSelectDate.getTimeInMillis();
        if (mScheduleInfo == null) {
            mScheduleInfo = new ScheduleInfo();
            mScheduleInfo.setCompanyID(mCompanyID);
//            mScheduleInfo.setScheduleID(mScheduleID);
        }

        mScheduleInfo.setTitle(editTitle.getText().toString().trim());
        mScheduleInfo.setLocation(editLocation.getText().toString().trim());
        int hour = 0, minute = 0;
        try {
            hour = Integer.valueOf(editHour.getText().toString().trim());
            minute = Integer.valueOf(editMinute.getText().toString().trim());
        } catch (Exception e) {

        }
        int length = hour * 60 + minute;
        mScheduleInfo.setLength(length);
//        mScheduleInfo.setAllday(length == 24 * 60 ? 1 : 0);

        mScheduleInfo.setStartTime(DateUtils.FormatDate("yyyy-MM-dd HH:mm", mSelectDate.getTime()));
        setResult(mDbHelper.modify(mScheduleInfo));

        // 设置提醒
        Intent intent = new Intent(AlarmReceiver.ACTION);
        intent.putExtra("ScheduleID", mScheduleID);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(mContext, mScheduleID, intent, 0);
        // Calendar setCal = Calendar.getInstance();
        // setCal.add(Calendar.SECOND, 10);
        // alarmManager.set(AlarmManager.RTC_WAKEUP, setCal.getTimeInMillis(),
        // alarmIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, mSelectDate.getTimeInMillis(), alarmIntent);
    }

    OnClickListener btnDelClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
//            setResult(mDbHelper.Delete(mScheduleInfo.getScheduleID()));
            // 取消提醒
//            Intent intent = new Intent(AlarmReceiver.ACTION);
//            PendingIntent alarmIntent = PendingIntent.getBroadcast(mContext, mScheduleInfo.getScheduleID(), intent, 0);
//            alarmManager.cancel(alarmIntent);
        }
    };

    private void setResult(boolean ok) {
        SystemMethod.hideSoftInput(mContext);
        Intent returnIntent = new Intent();
        if (ok) {
            getActivity().setResult(Activity.RESULT_OK, returnIntent);

        } else {
            getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        getActivity().finish();
        if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
            ((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
        }
    }

}
