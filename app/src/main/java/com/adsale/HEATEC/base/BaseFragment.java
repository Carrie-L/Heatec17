package com.adsale.HEATEC.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {
    public View view;
    public Context mContext;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    /**
     * setContentView;
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = initView(inflater);

        return view;
    }

    public View getRootView() {
        return view;
    }

    /**
     * 初始化view
     */
    public abstract View initView(LayoutInflater inflater);

    /**
     * 初始化数�?
     */
    public abstract void initData(Bundle savedInstanceState);


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
