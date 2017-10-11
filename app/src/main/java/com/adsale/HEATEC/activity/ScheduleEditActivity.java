//package com.adsale.HEATEC.activity;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.adsale.HEATEC.R;
//import com.adsale.HEATEC.base.BaseActivity;
//import com.adsale.HEATEC.dao.Exhibitor;
//import com.adsale.HEATEC.util.Constant;
//import com.adsale.HEATEC.viewmodel.ScheduleViewModel;
//
///**
// * 日程表 add or edit
// */
//public class ScheduleEditActivity extends BaseActivity implements ScheduleViewModel.ScheduleEditListenr {
//
//    private ScheduleViewModel mScheduleModel;
//
//    @Override
//    protected void initView() {
//        ActivityScheduleItemBinding binding = ActivityScheduleItemBinding.inflate(getLayoutInflater(), mBaseFrameLayout, true);
//
//        Bundle bundle = getIntent().getExtras();
//        long id = bundle.getLong("id");
//        int dateIndex = bundle.getInt("dateIndex");
//
//        if (id == 0) {
//            mScheduleModel = new ScheduleViewModel(getApplicationContext(), dateIndex);
//        } else {
//            mScheduleModel = new ScheduleViewModel(getApplicationContext(), id);
//        }
//        binding.setScheduleModel(mScheduleModel);
//    }
//
//    @Override
//    protected void initData() {
//        mScheduleModel.setScheduleEditListener(this);
//
//        Intent data=getIntent();
//        Exhibitor exhibitor = data.getParcelableExtra(Constant.EXHIBITOR);
//        if(exhibitor!=null){
//            mScheduleModel.dateIndex.set(data.getIntExtra("dateIndex",0));
//            mScheduleModel.setCompanyId(exhibitor.getCompanyID());
//            mScheduleModel.etTitle.set(exhibitor.getCompanyName());
//            mScheduleModel.etLocation.set(exhibitor.getBoothNo());
//            mScheduleModel.etNote.set(exhibitor.getNote());
//        }
//
//    }
//
//    @Override
//    public void onSameTimeSave() {
//        AppUtil.showAlertDialog(this, getString(R.string.ask_schedule), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mScheduleModel.insert();
//            }
//        });
//    }
//
//    @Override
//    public void toExhibitorDtl(String companyId) {
//        Bundle bundle = new Bundle();
//        bundle.putString(Constant.COMPANY_ID, companyId);
//        intent(ExhibitorDtlActivity.class, bundle);
//    }
//
//
//    @Override
//    public void onFinish() {
//        finish();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mScheduleModel.onActivityDestroyed();
//    }
//}
