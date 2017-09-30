package com.adsale.HEATEC.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.ExhibitorActivity;
import com.adsale.HEATEC.activity.MyExhibitorListActivity;
import com.adsale.HEATEC.activity.NewsActivity;
import com.adsale.HEATEC.activity.RegisterActivity;
import com.adsale.HEATEC.activity.ScheduleActivity;
import com.adsale.HEATEC.activity.SettingActivity;
import com.adsale.HEATEC.activity.SubscribeActivity;
import com.adsale.HEATEC.activity.WebContentActivity;
import com.adsale.HEATEC.adapter.MenuAdapter;
import com.adsale.HEATEC.adapter.PadNavListAdapter;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.data.DataRepository;
import com.adsale.HEATEC.database.model.ftpInformation;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Carrie on 2017/9/26.
 */

public class MainViewModel {
    public final ObservableBoolean isShowBtnTW = new ObservableBoolean();
    public final ObservableBoolean isShowBtnEN = new ObservableBoolean();
    public final ObservableBoolean isShowBtnCN = new ObservableBoolean();

    private Context mContext;
    private int mLanguage;
    private final DataRepository mDataRepository;
    private ArrayList<MainIcon> mainIcons;
    private MenuAdapter adapter;
    private PadNavListAdapter mPadAdapter;

    private SimpleDraweeView mTopBG;
    private RecyclerView mRV;
    private final boolean isPadDevice;

    private ftpInformation information;
    private Intent intent;

    public MainViewModel(Context mContext, OnMainIntentCallback callback) {
        this.mContext = mContext;
        this.mCallback = callback;
        mDataRepository = DataRepository.getInstance(mContext);
        isPadDevice = SystemMethod.isPadDevice(mContext);
        mLanguage = SystemMethod.getCurLanguage(mContext);
    }

    public void init(SimpleDraweeView topBG, RecyclerView recyclerView) {
        mTopBG = topBG;
        mRV = recyclerView;
        mainIcons = mDataRepository.getMainIconList();
        refreshView();
    }

    public void initPad(SimpleDraweeView topBG, RecyclerView recyclerView, ArrayList<MainIcon> list) {
        mTopBG = topBG;
        mRV = recyclerView;
        mainIcons = list;
        refreshView();
    }

    public void setAdapter() {
        if (adapter == null) {
            adapter = new MenuAdapter(mainIcons, mCallback);
            mRV.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void setPadAdapter() {
        if (mPadAdapter == null) {
            mPadAdapter = new PadNavListAdapter(mainIcons, mCallback);
            mRV.setAdapter(mPadAdapter);
        } else {
            mPadAdapter.notifyDataSetChanged();
        }
    }


    public void clickBtn(int index) {
        if (index == 3) {
            mCallback.setting();
            return;
        }
        mLanguage = index;
        showLangBtn(index);
        SystemMethod.switchLanguage(mContext, index);
        refreshView();
    }

    public void showLangBtn(int language) {
        isShowBtnTW.set(language != 0);
        isShowBtnEN.set(language != 1);
        isShowBtnCN.set(language != 2);
    }

    public void refreshView() {
        mTopBG.setImageDrawable(mContext.getResources().getDrawable(R.drawable.main_banner));
        if (isPadDevice) {
            setPadAdapter();
        } else {
            setAdapter();
        }
    }

    public ArrayList<MainIcon> getPadMainIcons() {
        return mDataRepository.getPadMainIconList();
    }

    public void clickPadIcon(int index) {
        if (index == 15 || index == 16) {
            LogUtil.i("clickPadIcon", "index=" + index + ", bdtj= " + mainIcons.get(index).getBaiDu_TJ());
            mCallback.intent(mainIcons.get(index));
        }

    }

    public interface OnMainIntentCallback {
        void intent(MainIcon mainIcon);

        void setting();
    }

    public OnMainIntentCallback mCallback;

    public Intent menuIntent(String strBaiDu_tj) {
        intent = null;
        if (strBaiDu_tj.equals("VisitorPreRegistration")) {// PreRegister
            toRegister();
        } else if (strBaiDu_tj.equals("ExhibitorListText")) {
            intent = new Intent(mContext, ExhibitorActivity.class);
        } else if (strBaiDu_tj.equals("ExhibitorList")) {
            intent = new Intent(mContext, ExhibitorActivity.class);
        } else if (strBaiDu_tj.equals("News")) {
            intent = new Intent(mContext, NewsActivity.class);
        } else if (strBaiDu_tj.equals("SubscribeeNewsletter")) {
            intent = new Intent(mContext, SubscribeActivity.class);
        } else if (strBaiDu_tj.equals("MyExhibitor")) {
            intent = new Intent(mContext, MyExhibitorListActivity.class);
        } else if (strBaiDu_tj.equals("Schedule")) {
            intent = new Intent(mContext, ScheduleActivity.class);
        } else if (strBaiDu_tj.equals("Settings")) {
            intent = new Intent(mContext, SettingActivity.class);
        } else {
            intent = new Intent(mContext, WebContentActivity.class);
        }

        if (!strBaiDu_tj.equals("-")) {
            return intent;
        }
        return null;
    }

    private Intent toRegister() {
        if (SystemMethod.isRegister(mContext)) {
            intent = new Intent(mContext, RegisterActivity.class);
        } else {
            information = SystemMethod.getInformation(mContext);
            if (information != null && SystemMethod.PreRegClose(information)) {
                toastRegisterCloseDialog();
                return null;
            } else {
                intent = new Intent(mContext, RegisterActivity.class);
            }
        }
        return intent;
    }

    private void toastRegisterCloseDialog() {
        new AlertDialog.Builder(mContext)
                .setMessage(information.getRegEndMessage().getRegMessage(mLanguage))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse("http://".concat(information.getRegiterLink()));
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        mContext.startActivity(i);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


}
