package com.adsale.HEATEC.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.SettingActivity;
import com.adsale.HEATEC.activity.WebContentActivity;
import com.adsale.HEATEC.adapter.NavListADT;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.database.ExhibitorDBHelper;
import com.adsale.HEATEC.database.ScheduleInfoDBHelper;
import com.adsale.HEATEC.util.DialogUtil;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.PermissionUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.TitleView;


public class SettingPadFragment extends BaseFragment {
    public static final String TAG = "SettingFragment";
    private Context mContext;

    private ListView mListView;

    private Integer[] mIconList;
    private String[] mNavList;
    private int mCurLanguage;
    private View mBaseView;

    private String mTitle;

    public static final int CHANGE = 0;

    @Override
    public View initView(LayoutInflater inflater) {
        mBaseView = inflater.inflate(R.layout.pad_f_setting, null);
        mContext = getActivity();

        findView();

        return mBaseView;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mCurLanguage = SystemMethod.getCurLanguage(mContext);

        LogUtil.i(TAG, "mCurLanguage=" + mCurLanguage);

        setupView();
    }

    private void findView() {
        mListView = (ListView) mBaseView.findViewById(R.id.listView1);

        TextView txtVersion = (TextView) mBaseView.findViewById(R.id.txtVersion);

        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
            txtVersion.setText("V " + info.versionName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setupView() {


        if (App.isTablet) {
            setPadList();
            mListView.setOnItemClickListener(itemPadClickListener);
            mIconList = new Integer[]{R.drawable.set_01, R.drawable.set_02, R.drawable.set_03, R.drawable.set_04, R.drawable.set_05};
        } else {
            mNavList = mContext.getResources().getStringArray(R.array.setting_nav);
            mIconList = new Integer[]{R.drawable.set_01, R.drawable.set_02, R.drawable.set_03, R.drawable.set_04, R.drawable.set_05};
            mListView.setOnItemClickListener(itemPhoneClickListener);
        }
        mListView.setAdapter(new NavListADT(mContext, mIconList, mNavList, null));
    }

    private void setPadList() {
        if (mCurLanguage == 0) {
            mNavList = new String[]{"網站連結", "添加到日歷", "重置所有設定", "私隱政策聲明", "使用條款"};
        } else if (mCurLanguage == 1) {
            mNavList = new String[]{"Link to websites", "Add to calendar", "Reset All Data", "Privacy Policy Statement", "Terms of Use"};
        } else {
            mNavList = new String[]{"网站连结", "添加到日历", "重置所有设定", "私隐政策声明", "使用条款"};
        }
    }

    private Intent intent;
    MainIcon oclsMainIcon;
    OnItemClickListener itemPadClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position + 1) {
                case 1:
                    linkWebsite();
                    break;
                case 2:
                    addToCalendar();
                    break;
                case 3:
                    resetAll();
                    break;
                case 4:
                    privacy(position);
                    break;
                case 5:
                    userItem(position);
                    break;
                default:
                    break;
            }
        }
    };

    OnItemClickListener itemPhoneClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    linkWebsite();
                    break;
                case 1:
                    addToCalendar();
                    break;
                case 2:
                    resetAll();
                    break;
                case 3:
                    privacy(position);
                    break;
                case 4:
                    userItem(position);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 网站连结
     */
    private void linkWebsite() {
        // <item>缍茬珯閫ｇ祼</item>
        final String[] items = mContext.getResources().getStringArray(R.array.url_ver);
        final String[] urls = mContext.getResources().getStringArray(R.array.urls);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.select_url);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(urls[item]);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    /**
     * 添加到日历
     */
    private void addToCalendar() {
        // <item>娣诲姞鍒版棩鏇�/item>
        boolean hasCalPermission = PermissionUtil.checkPermission(getActivity(), PermissionUtil.PERMISSION_READ_CALENDAR);
        if (hasCalPermission) {
            DialogUtil.showAlertDialog(mContext, R.string.ask_addToCalendar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SystemMethod.addToCalendar(mContext);
                }
            });
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, PermissionUtil.PMS_CODE_READ_CALENDAR);
//			PermissionUtil.requestPermission(getActivity(),PermissionUtil.PERMISSION_READ_CALENDAR,PermissionUtil.PMS_CODE_READ_CALENDAR);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermission = PermissionUtil.getGrantResults(grantResults);
        LogUtil.i(TAG, "hasPermission=" + hasPermission + ",requestCode=" + requestCode);

        if (PermissionUtil.getGrantResults(grantResults) && requestCode == PermissionUtil.PMS_CODE_READ_CALENDAR) {
            DialogUtil.showAlertDialog(mContext, R.string.ask_addToCalendar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SystemMethod.addToCalendar(mContext);
                }
            });
        }
    }

    /**
     * 重置所有设定
     */
    private void resetAll() {
        // <item>閲嶇疆鎵�湁瑷�?/item>
        DialogUtil.showAlertDialog(mContext, R.string.ask_clear, R.string.yes, R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ExhibitorDBHelper(mContext).clearFavourite();
                new ScheduleInfoDBHelper(mContext).chearAll();
            }
        });
    }

    /**
     * 私隐政策
     */
    private void privacy(int position) {
        intent = new Intent(mContext, WebContentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        oclsMainIcon = new MainIcon();
        oclsMainIcon.setIconID(99 + "");
        oclsMainIcon.setTitle(mCurLanguage, mNavList[position]);
        intent.putExtra("clsMainIcon", oclsMainIcon);
        intent.putExtra("Title", mNavList[position]);
        startActivity(intent);
        if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
            ((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
        }
    }

    /**
     * 使用条款
     */
    private void userItem(int position) {
        intent = new Intent(mContext, WebContentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        oclsMainIcon = new MainIcon();
        oclsMainIcon.setIconID(100 + "");
        oclsMainIcon.setTitle(mCurLanguage, mNavList[position]);
        intent.putExtra("clsMainIcon", oclsMainIcon);

        intent.putExtra("Title", mNavList[position]);
        startActivity(intent);
        if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
            ((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CHANGE:
                    SettingActivity.mTitleView.setTitle(getString(R.string.title_setting));

                    mCurLanguage = (Integer) msg.obj;
                    LogUtil.i(TAG, "mCurLanguage=" + mCurLanguage);

                    if (mCurLanguage == 1) {
                        TitleView.txtBack.setImageResource(R.drawable.btn_back);
                    } else {
                        TitleView.txtBack.setImageResource(R.drawable.bttn_back_cn);
                    }

                    break;

                default:
                    break;
            }

        }

        ;
    };
    private NavListADT adapter;

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
