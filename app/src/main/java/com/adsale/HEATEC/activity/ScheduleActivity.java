package com.adsale.HEATEC.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.adapter.SchedulePagerADT;
import com.adsale.HEATEC.base.BaseActivity;
import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.databinding.ActivityScheduleBinding;
import com.adsale.HEATEC.databinding.PageScheduleListBinding;
import com.adsale.HEATEC.util.Constant;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.viewmodel.ScheduleViewModel;

import java.util.ArrayList;
import java.util.List;

import m.framework.utils.Data;


public class ScheduleActivity extends BaseActivity {

    private ScheduleViewModel mScheduleModel;
    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private SchedulePagerADT mSchedulePagerADT;
    private List<View> views;

    @Override
    protected void initView() {
        ActivityScheduleBinding binding = ActivityScheduleBinding.inflate(getLayoutInflater(), mBaseFrameLayout, true);
        mScheduleModel = new ScheduleViewModel(getApplicationContext());
        binding.setModel(mScheduleModel);
        viewPager = binding.schedulePager;

        initListView();
    }

    @Override
    protected void initData() {
        mSchedulePagerADT = new SchedulePagerADT(views);
        viewPager.setAdapter(mSchedulePagerADT);
        viewPager.addOnPageChangeListener(pageChangeListener);

    }

    private void initListView() {
        views = new ArrayList<>();
//        mScheduleModel = new ScheduleViewModel(getApplicationContext());
        List<PageScheduleListBinding> bindings = new ArrayList<>();
        for (int i = 0; i < Constant.SCHEDULE_DAYS; i++) {
            PageScheduleListBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.page_schedule_list, null, true);
            views.add(binding.getRoot());
            bindings.add(binding);
            binding.setModel(mScheduleModel);
            mScheduleModel.initScheduleList(binding.scheduleRecyclerView);
        }


    }

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ScheduleInfo scheduleInfo = (ScheduleInfo) parent.getAdapter().getItem(position);
            LogUtil.i(TAG, "item " + position + " : " + scheduleInfo.getTitle());
        }
    };

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            //change btn ui
            mScheduleModel.dateIndex.set(position);

//            mScheduleModel.isCurrItem0.set(position==0);
//            mScheduleModel.isCurrItem1.set(position==1);
//            mScheduleModel.isCurrItem2.set(position==2);
//            LogUtil.i(TAG,"onPageSelected: position="+position+",isCurrItem1="+ mScheduleModel.isCurrItem1.get()+",dateIndexget="+ mScheduleModel.dateIndex.get());
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int position) {

        }
    };


}
