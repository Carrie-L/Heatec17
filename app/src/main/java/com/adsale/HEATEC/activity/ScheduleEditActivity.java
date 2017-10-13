package com.adsale.HEATEC.activity;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.base.BaseActivity;
import com.adsale.HEATEC.dao.Exhibitor;
import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.databinding.ActivityScheduleEditBinding;
import com.adsale.HEATEC.util.Constant;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.viewmodel.ScheduleEditViewModel;

import static android.R.attr.id;
import static com.adsale.HEATEC.util.Constant.INTENT_SCHEDULE;

/**
 * 日程表 add or edit
 * add:  [Constant.INTENT_EXHIBITOR](Exhibitor) & [Constant.INTENT_DATE_INDEX]
 * edit: [Constant.INTENT_SCHEDULE](ScheduleInfo)
 */
public class ScheduleEditActivity extends BaseActivity implements ScheduleEditViewModel.OnBackListener {

    private ScheduleEditViewModel mEditModel;
    private int language;
    private ActivityScheduleEditBinding binding;

    @Override
    protected void initView() {
        binding = ActivityScheduleEditBinding.inflate(getLayoutInflater(), mBaseFrameLayout, true);
        mEditModel = new ScheduleEditViewModel(getApplicationContext());
        binding.setModel(mEditModel);
    }

    @Override
    protected void initData() {
        language = SystemMethod.getCurLanguage(getApplicationContext());

        Exhibitor exhibitor = getIntent().getParcelableExtra(Constant.INTENT_EXHIBITOR);
        if (exhibitor != null) {
            mEditModel.isEdit.set(false);
            mEditModel.setCompanyId(exhibitor.getCompanyID());
            mEditModel.dateIndex.set(getIntent().getIntExtra(Constant.INTENT_DATE_INDEX, 0));
            mEditModel.etTitle.set(exhibitor.getCompanyName(language));
            mEditModel.etLocation.set(exhibitor.getExhibitorNO());
            mEditModel.etNote.set(exhibitor.getNote());
        }

        ScheduleInfo scheduleInfo = getIntent().getParcelableExtra(INTENT_SCHEDULE);
        if (scheduleInfo != null) {
            mEditModel.isEdit.set(true);
            mEditModel.setId(scheduleInfo.getScheduleID());
            mEditModel.setCompanyId(scheduleInfo.getCompanyID());
            mEditModel.dateIndex.set(scheduleInfo.getDayIndex());
            mEditModel.etTitle.set(scheduleInfo.getTitle());
            mEditModel.etLocation.set(scheduleInfo.getLocation());
            mEditModel.etNote.set(scheduleInfo.getNote());
            mEditModel.etHour.set(String.valueOf(scheduleInfo.getHour()));
            mEditModel.etMinute.set(String.valueOf(scheduleInfo.getMinute()));
            mEditModel.etStartTime.set(scheduleInfo.getStartTime());
        }

        mEditModel.initData(this);


    }


    @Override
    public void back(boolean change) {
        App.mSPConfig.edit().putBoolean("ScheduleListUpdate", change).apply();
        finish();
    }
}
