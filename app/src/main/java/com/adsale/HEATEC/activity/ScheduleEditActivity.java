package com.adsale.HEATEC.activity;

import com.adsale.HEATEC.base.BaseActivity;
import com.adsale.HEATEC.dao.Exhibitor;
import com.adsale.HEATEC.databinding.ActivityScheduleEditBinding;
import com.adsale.HEATEC.util.Constant;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.viewmodel.ScheduleEditViewModel;

/**
 * 日程表 add or edit
 * add: [Constant.INTENT_EXHIBITOR]
 * edit: [id] [Constant.INTENT_EXHIBITOR] [Constant.INTENT_DATE_INDEX]
 */
public class ScheduleEditActivity extends BaseActivity implements ScheduleEditViewModel.OnBackListener{

    private ScheduleEditViewModel mEditModel;
    private int language;
    private long id;
    private ActivityScheduleEditBinding binding;

    @Override
    protected void initView() {
        binding = ActivityScheduleEditBinding.inflate(getLayoutInflater(), mBaseFrameLayout, true);
        id = getIntent().getLongExtra("id", 0);
        mEditModel = new ScheduleEditViewModel(getApplicationContext(), id);
        binding.setModel(mEditModel);
    }

    @Override
    protected void initData() {
        language = SystemMethod.getCurLanguage(getApplicationContext());

        // id starts from 1, so if id == 0, means adding a new schedule.
        mEditModel.isEdit.set(id != 0);
        Exhibitor exhibitor = getIntent().getParcelableExtra(Constant.INTENT_EXHIBITOR);
        if (exhibitor != null) {
            mEditModel.setCompanyId(exhibitor.getCompanyID());
            mEditModel.dateIndex.set(getIntent().getIntExtra(Constant.INTENT_DATE_INDEX, 0));
            mEditModel.etTitle.set(exhibitor.getCompanyName(language));
            mEditModel.etLocation.set(exhibitor.getExhibitorNO());
            mEditModel.etNote.set(exhibitor.getNote());
        }
        mEditModel.initData(this);



    }


    @Override
    public void back(boolean change) {
        finish();
    }
}
