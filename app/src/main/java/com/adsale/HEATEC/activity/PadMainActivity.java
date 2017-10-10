package com.adsale.HEATEC.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.database.model.ftpInformation;
import com.adsale.HEATEC.databinding.PadActivityMainBinding;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.util.network.Configure;
import com.adsale.HEATEC.view.MenuView;
import com.adsale.HEATEC.viewmodel.MainViewModel;
import com.facebook.drawee.view.SimpleDraweeView;

public class PadMainActivity extends AppCompatActivity implements MainViewModel.OnMainIntentCallback {
    protected static final String TAG = "PadMainActivity";

    private Context mContext;
    private ArrayList<MainIcon> mColMainIcons;
    private MainIcon mMainIcon;

    private PadActivityMainBinding binding;
    private MainViewModel mModel;

    private MenuView icon01;
    private MenuView icon02;
    private MenuView icon03;
    private MenuView icon04;
    private MenuView icon05;
    private MenuView icon06;
    private MenuView icon07;
    private MenuView icon08;
    private RecyclerView mRecyclerView;
    private SimpleDraweeView ivTop;
    private SimpleDraweeView ivLeft;
    private SimpleDraweeView ivRight;

    private String mDeviceType;
    private int mCurLanguage;
    private String strBaiDu_tj;
    private String strGoogle_tj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.pad_activity_main);
        mModel = new MainViewModel(getApplicationContext(), this);
        binding.setMainModel(mModel);

        mContext = getApplicationContext();
        mCurLanguage = SystemMethod.getCurLanguage(mContext);
        SystemMethod.switchLanguage(mContext, mCurLanguage);
        mDeviceType = SystemMethod.getSharedPreferences(mContext, "DeviceType");

        findView();
        initRecyclerView();
        setupView();

        mModel.initPad(ivTop, mRecyclerView, mColMainIcons);
        mModel.showLangBtn(mCurLanguage);


    }

    private void findView() {
        mRecyclerView = binding.recyclerView;
        ivTop = binding.imgTopBg;
        ivLeft = binding.ivLeft;
        ivRight = binding.ivRight;
        icon01 = binding.ivIcon01;
        icon02 = binding.ivIcon02;
        icon03 = binding.ivIcon03;
        icon04 = binding.ivIcon04;
        icon05 = binding.ivIcon05;
        icon06 = binding.ivIcon06;
        icon07 = binding.ivIcon07;
        icon08 = binding.ivIcon08;
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        LayoutParams params = (LayoutParams) mRecyclerView.getLayoutParams();
        mScreenWidth = mContext.getSharedPreferences("ScreenSize", 0).getInt("ScreenWidth", 0);
        params.height = mScreenWidth / 7;
        mRecyclerView.setLayoutParams(params);
        LogUtil.i(TAG, "mScreenWidth=" + mScreenWidth);
    }

    private void setupView() {
        mColMainIcons = mModel.getPadMainIcons();
        LogUtil.i(TAG, "mColMainIcons=" + mColMainIcons.size() + "," + mColMainIcons.toString());

        ivTop.setImageURI(Uri.parse("res:///" + R.drawable.main_banner));

        setTextName();
        setLeftRightIcon();


//        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, mRecyclerView, new OnItemClickListener() {
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//            }
//
//            @Override
//            public void onItemClick(View view, int position) {
//                position = position + 8;
//                String baiduTJ = mColMainIcons.get(position).getBaiDu_TJ();
//
//                if (!mColMainIcons.get(position).getGoogle_TJ().contains("NULL")) {
//                    if (baiduTJ.contains("AppWebPage")) {
//                        openUrl(position);
//                    } else {
//                        setIntent(position);
//                    }
//                }
//            }
//        }));
    }

    private void setTextName() {
        MainIcon mainIcon;
        if (icon08 != null) {
            menus = new MenuView[]{icon01, icon02, icon03, icon04, icon05, icon06, icon07, icon08};
            String googleTJ = "";
            int seq = 0;
            for (int i = 0; i < 8; i++) {
                mainIcon = mColMainIcons.get(i);
                LogUtil.i(TAG, "mainIcon=" + mainIcon.toString());
                googleTJ = mainIcon.getGoogle_TJ().split("\\|")[1];

                LogUtil.i(TAG, "googleTJ=" + googleTJ);

                if (googleTJ.contains("_")) {
                    googleTJ = googleTJ.split("_")[0];
                    seq = Integer.valueOf(googleTJ);
                    menus[seq - 1].setBackground(mainIcon, isLarge(seq - 1));

                    LogUtil.i(TAG, "seq=" + seq);

                    if (seq == 1 || seq == 4) {
                        menus[seq - 1].setPadLargeIcon();
                    } else {
                        menus[seq - 1].setPadNormalIcon();
                    }
                    onClick(menus[i], seq - 1);
                } else {
                    menus[i].setBackground(mainIcon, isLarge(i));
                    onClick(menus[i], i);
                }
            }
        }


    }

    /**
     * 底下左右两个长条形图片
     */
    private void setLeftRightIcon() {
        String baseIconUrl = Configure.DOWNLOAD_PATH.concat("WebContent/");
        int iconsSize = mColMainIcons.size();
        if (iconsSize > 15) {
            ivLeft.setImageURI(Uri.parse(baseIconUrl.concat(mColMainIcons.get(iconsSize - 2).getIcon())));
            ivRight.setImageURI(Uri.parse(baseIconUrl.concat(mColMainIcons.get(iconsSize - 1).getIcon())));
            setSize(ivLeft);
            setSize(ivRight);
        }
    }

    private boolean isLarge(int position) {
        if (position == 0 || position == 3) {
            return true;
        } else
            return false;
    }

    private void onClick(MenuView menuView, final int pos) {
        menuView.setOnMenuClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mModel.mCallback.intent(mColMainIcons.get(pos));
            }
        });
    }

    private void setSize(SimpleDraweeView sdv) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sdv.getLayoutParams();
        params.width = mScreenWidth / 2;
        sdv.setLayoutParams(params);
    }

    /**
     * MainIcon跳转
     *
     * @param position
     */
    private void setIntent(int position) {
        MainIcon mainIcon = mColMainIcons.get(position);
        String strBD_TJ = mainIcon.getBaiDu_TJ();
        LogUtil.i(TAG, "strBaiDu_TJ=" + strBD_TJ);

        LogUtil.i(TAG, "mDeviceType=" + mDeviceType);

        if (strBD_TJ.equals("VisitorPreRegistration")) {// 预先登记
//			intent2Register();
            intent = new Intent(mContext, RegisterActivity.class);
        } else if (strBD_TJ.equals("SubscribeeNewsletter")) {// 订阅电子快讯
            intent = new Intent(mContext, SubscribeActivity.class);

        } else if (strBD_TJ.equals("MyExhibitor")) {// 我的参展商
            intent = new Intent(mContext, MyExhibitorListActivity.class);

        } else if (strBD_TJ.equals("Schedule")) {// 议程
            intent = new Intent(mContext, ScheduleActivity.class);

        } else if (strBD_TJ.equals("Settings")) {// 设置
            intent = new Intent(mContext, SettingActivity.class);

        } else if (strBD_TJ.equals("ExhibitorList")) {// 参展商名单
            intent = new Intent(mContext, ExhibitorActivity.class);

        } else if (strBD_TJ.equals("News")) {// 新闻
            intent = new Intent(mContext, NewsActivity.class);

        } else {// Webcontent
            intent = new Intent(mContext, WebContentActivity.class);
        }

        if (intent != null) {
            intent.putExtra("MainIcon", mainIcon);
            intent.putExtra("Title", mainIcon.getTitle(mCurLanguage));
            intent.putExtra("baiduTJ", strBD_TJ);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    private Intent intent;
    private int mScreenWidth;

    private MenuView[] menus;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
            ad.setTitle(mContext.getString(R.string.EXIT));
            ad.setMessage(mContext.getString(R.string.EXIT_MSG));
            ad.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {// ??锟斤�?锟介�?

                @Override
                public void onClick(DialogInterface dialog, int i) {
                    ((Activity) mContext).finish();
                    final ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
                    am.killBackgroundProcesses(mContext.getPackageName());
                }
            });
            if (mDeviceType.equals("Pad")) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            ad.setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            ad.show();
        }
        return true;
    }


    private void openByWeb() {
        if (!strGoogle_tj.trim().contains("-")) {
            if (strBaiDu_tj.contains("InAppWebPage")) {//在APP内部打开链接
                Intent intent = new Intent(mContext, WebContentActivity.class);
                intent.putExtra("WebUrl", strGoogle_tj);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
            } else if (strBaiDu_tj.contains("OutAppWebPage")) {//用外部浏览器打开链接
                try {
                    Uri uri = Uri.parse(strGoogle_tj);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mContext = getApplicationContext();
        mCurLanguage = SystemMethod.getCurLanguage(mContext);

        boolean isChangeLanguage = getSharedPreferences("Config", 0).getBoolean("IsChangeLanguage", false);
        if (isChangeLanguage) {
            LogUtil.i(TAG, "mCurLanguage=" + mCurLanguage);

            setTextName();

            getSharedPreferences("Config", 0).edit().putBoolean("IsChangeLanguage", false).apply();
        }


    }

    @Override
    public void intent(MainIcon mainIcon) {
        mMainIcon = mainIcon;
        strBaiDu_tj = mMainIcon.getBaiDu_TJ().trim();
        strGoogle_tj = mMainIcon.getGoogle_TJ().trim();

        LogUtil.i(TAG, "intent: strBaiDu_tj="+strBaiDu_tj);

        if (!strGoogle_tj.contains("NULL")) {
            if (strBaiDu_tj.contains("AppWebPage")) {
                openByWeb();
            } else {
                intent = mModel.menuIntent(strBaiDu_tj);
                startIntent();
            }
        }
    }


    @Override
    public void setting() {
        intent = new Intent(mContext, SettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Title", getString(R.string.title_setting));
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void startIntent() {
        if (intent == null) {
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Title", mMainIcon.getTitle(mCurLanguage));
        if (mMainIcon != null) {
            intent.putExtra("clsMainIcon", mMainIcon);
        }
        startActivity(intent);
    }
}
