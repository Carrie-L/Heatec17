package com.adsale.HEATEC.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.adsale.HEATEC.adapter.ExhibitorAdapter;
import com.adsale.HEATEC.base.BaseActivity;
import com.adsale.HEATEC.dao.Exhibitor;
import com.adsale.HEATEC.data.ExhibitorRepository;
import com.adsale.HEATEC.databinding.ActivityExhibitorsBinding;
import com.adsale.HEATEC.util.Constant;
import com.adsale.HEATEC.util.RecyclerItemClickListener;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.SideLetter;
import com.adsale.HEATEC.viewmodel.ExhibitorListViewModel;

import java.util.ArrayList;

import static com.adsale.HEATEC.util.Constant.INTENT_DATE_INDEX;
import static com.adsale.HEATEC.util.Constant.INTENT_EXHIBITOR;

/**
 * ① when back to schedule edit, receive [index]
 */
public class ExhibitorListActivity extends BaseActivity {

    public static String TAG = "ExhibitorListActivity";
    private ExhibitorRepository repository;
    private ActivityExhibitorsBinding binding;
    private ExhibitorListViewModel mExhibitorModel;
    private RecyclerView rvExhibitors;
    private int dateIndex = -1; // Schedule 传递过来的

    @Override
    protected void initView() {
        binding = ActivityExhibitorsBinding.inflate(getLayoutInflater(), mBaseFrameLayout, true);

    }

    @Override
    protected void initData() {
        int language = SystemMethod.getCurLanguage(getApplicationContext());
        repository = ExhibitorRepository.getInstance(language);
        mExhibitorModel = new ExhibitorListViewModel(getApplicationContext(), repository);
        binding.setExhibitorModel(mExhibitorModel);

        dateIndex = getIntent().getIntExtra(INTENT_DATE_INDEX, 0);

        initExhibitorList();
        initSideLetterList();

        search();
    }

    private void initExhibitorList() {
        rvExhibitors = binding.exhibitorAllRecyclerView;
        rvExhibitors.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvExhibitors.setLayoutManager(layoutManager);

        ExhibitorAdapter adapter = new ExhibitorAdapter(this, new ArrayList<Exhibitor>(0));
        rvExhibitors.setAdapter(adapter);

        mExhibitorModel.getAllExhibitors();
        mExhibitorModel.setLayoutManager(layoutManager, rvExhibitors);

        onItemClick();

    }

    private void initSideLetterList() {
        SideLetter sideLetter = binding.sideLetter;
        mExhibitorModel.setupSideLetter(sideLetter);
    }

    private void onItemClick() {
        rvExhibitors.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvExhibitors, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemLongClick(View view, int position) {
            }

            @Override
            public void onItemClick(View view, int position) {
                if (dateIndex!=-1) {
                    Intent intent = new Intent(ExhibitorListActivity.this,ScheduleEditActivity.class);
                    intent.putExtra(INTENT_EXHIBITOR, mExhibitorModel.exhibitors.get(position));
                    intent.putExtra(INTENT_DATE_INDEX,dateIndex);
                    startActivity(intent);
                    finish();
                }

            }
        }));
    }

    private void search() {
        binding.editFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mExhibitorModel.resetList();
                } else {
                    mExhibitorModel.search(s.toString().trim());
                }
            }
        });
    }



}
