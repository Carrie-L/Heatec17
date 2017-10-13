package com.adsale.HEATEC.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.R;
import com.adsale.HEATEC.adapter.ScheduleAdapter;
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

import static com.adsale.HEATEC.util.Constant.INTENT_DATE_INDEX;
import static com.adsale.HEATEC.util.Constant.INTENT_EXHIBITOR;
import static com.adsale.HEATEC.util.Constant.INTENT_SCHEDULE;

/**
 * todo
 * 每个页面显示单独的数据
 * item click
 * 列表界面正常显示
 * circle btn click event
 */
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
        initViewPager();

    }

    @Override
    protected void initData() {


    }

    private void initViewPager() {
        mSchedulePagerADT = new SchedulePagerADT(views);
        viewPager.setAdapter(mSchedulePagerADT);
        viewPager.addOnPageChangeListener(pageChangeListener);
    }

    /**
     * {@link ScheduleAdapter}
     */
    private void initListView() {
        views = new ArrayList<>();
        List<PageScheduleListBinding> bindings = new ArrayList<>();
        for (int i = 0; i < Constant.SCHEDULE_DAYS; i++) {
            PageScheduleListBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.page_schedule_list, null, true);
            views.add(binding.getRoot());
            bindings.add(binding);
            binding.setModel(mScheduleModel);
            mScheduleModel.initScheduleList(this,binding.scheduleRecyclerView);
        }
    }

    public void onItemClick(ScheduleInfo scheduleInfo) {
        Intent intent = new Intent(this, ScheduleEditActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(INTENT_SCHEDULE, scheduleInfo);
        startActivity(intent);
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            //change btn ui
            mScheduleModel.showItemPageList(position, false);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int position) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        boolean updated = App.mSPConfig.getBoolean("ScheduleListUpdate", false);
        LogUtil.i(TAG, "onResume::updated=" + updated);

        viewPager.setCurrentItem(mScheduleModel.dateIndex.get());
        mScheduleModel.showItemPageList(mScheduleModel.dateIndex.get(), updated);
        App.mSPConfig.edit().putBoolean("ScheduleListUpdate", false).apply();// must.

    }
}
