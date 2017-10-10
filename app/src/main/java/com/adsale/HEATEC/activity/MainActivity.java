package com.adsale.HEATEC.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.text.TextUtils;
import android.view.View;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.database.MapFloorDBHelper;
import com.adsale.HEATEC.database.model.ftpInformation;
import com.adsale.HEATEC.databinding.ActivityMainBinding;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.viewmodel.MainViewModel;
import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity implements MainViewModel.OnMainIntentCallback {
    public static final String TAG = "MainActivity";
    private Context mContext;
    private MainViewModel mModel;
    private ActivityMainBinding binding;
    private SimpleDraweeView imgTopBg;
    private RecyclerView recyclerView;

    private int mCurLanguage;
    private String title;
    private String strBaiDu_tj;
    private MainIcon mMainIcon;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mModel = new MainViewModel(getApplicationContext(), this);
        binding.setMainModel(mModel);

        mContext = getApplicationContext();
        mCurLanguage = SystemMethod.getCurLanguage(mContext);
        SystemMethod.switchLanguage(getApplicationContext(), mCurLanguage);// must!!

        findView();
        initRecyclerView();
        mModel.init(imgTopBg, recyclerView);
        mModel.showLangBtn(mCurLanguage);

    }

    private void findView() {
        imgTopBg = binding.imgTopBg;
        recyclerView = binding.recyclerviewMenu;
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        layoutManager.setSpanSizeLookup(new SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (position == 1) {// 第二个位置 预登记 横跨两个格子
                    return 2; // 表示占几个格子
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view,
                                       RecyclerView parent, State state) {
                outRect.left = 0;// 左间距
                outRect.bottom = 0;// 与底部的间距
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
    }

    @Override
    public void intent(MainIcon mainIcon) {
        mMainIcon = mainIcon;
        title = mainIcon.getTitle(mCurLanguage);
        strBaiDu_tj = mainIcon.getBaiDu_TJ();
        intent();
    }

    @Override
    public void setting() {
        intent = new Intent(this, SettingActivity.class);
        title = getString(R.string.title_setting);
        startIntent();
    }

    private void intent() {
        if (strBaiDu_tj.contains("Phone_") || strBaiDu_tj.contains("AppWebPage")) {
            openByWeb();
        } else {
            intent = mModel.menuIntent(strBaiDu_tj);
            startIntent();
        }
    }

    private void openByWeb() {
        if (!mMainIcon.getGoogle_TJ().trim().equals("-")) {
            if (strBaiDu_tj.contains("InAppWebPage")) {// 在APP内部打开链接
                Intent intent = new Intent(mContext, WebContentActivity.class);
                intent.putExtra("WebUrl", mMainIcon.getGoogle_TJ());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if (strBaiDu_tj.contains("OutAppWebPage")) {// 用外部浏览器打开链接
                try {
                    Uri uri = Uri.parse(mMainIcon.getGoogle_TJ());
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startIntent() {
        if (intent == null) {
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Title", title);
        if (mMainIcon != null) {
            intent.putExtra("clsMainIcon", mMainIcon);
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        mContext = getApplicationContext();
        mCurLanguage = SystemMethod.getCurLanguage(mContext);
        boolean isChangeLanguage = getSharedPreferences("Config", 0).getBoolean("IsChangeLanguage", false);
        if (isChangeLanguage) {
            LogUtil.i(TAG, "mCurLanguage=" + mCurLanguage);
            mModel.refreshView();
            getSharedPreferences("Config", 0).edit().putBoolean("IsChangeLanguage", false).apply();
        }
        super.onResume();
    }

}
