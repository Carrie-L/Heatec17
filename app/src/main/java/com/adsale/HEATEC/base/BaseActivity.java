package com.adsale.HEATEC.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.BR;
import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.MainActivity;
import com.adsale.HEATEC.dao.DBHelper;
import com.adsale.HEATEC.databinding.BaseBinding;
import com.adsale.HEATEC.util.SystemMethod;

/**
 * Created by Carrie on 2017/9/27.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected static String TAG;
    //title bar
    public final ObservableField<String> barTitle = new ObservableField<>();
    public final ObservableFloat topViewAspectRatio = new ObservableFloat();
    private BaseBinding mBaseBinding;
    protected FrameLayout mBaseFrameLayout;
    protected DBHelper mDBHelper;
    protected String mDeviceType;
    protected static final String TITLE="title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseBinding = DataBindingUtil.setContentView(this, R.layout.base);
        mBaseFrameLayout = mBaseBinding.contentFrame;
        mBaseBinding.setVariable(BR.activity, this);

        mDBHelper = App.mDBHelper;
        TAG = getClass().getSimpleName();
        mDeviceType= SystemMethod.getSharedPreferences(getApplicationContext(), "DeviceType");

        preView();

        initView();

        if(TextUtils.isEmpty(barTitle.get())){
            barTitle.set(getIntent().getStringExtra(TITLE));
        }


        initData();
    }

    protected void preView() {

    }

    protected abstract void initView();

    protected abstract void initData();

    protected <T> void intent(Class<T> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected <T> void intent(Class<T> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void back() {
        finish();
    }

    public void home() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDeviceType.equals("Pad")) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


}
