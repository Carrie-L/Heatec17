package com.adsale.HEATEC.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.adapter.ExhibitorAdapter;
import com.adsale.HEATEC.base.AdsaleBaseAdapter;
import com.adsale.HEATEC.dao.Exhibitor;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import static android.R.attr.y;

/**
 * Created by Carrie on 2017/8/11.
 * 加上前缀（ @BindingAdapter("app:items")  ）就会出现：
 * Error:(23, 28) 警告: Application namespace for attribute app:items will be ignored.
 * 因此，去掉前缀，改为： @BindingAdapter({"items"})
 *
 * 所有列表
 */

public class ListBindings {
    private static final String TAG="ListBindings";

//    @BindingAdapter("app:items")
    @BindingAdapter({"items"})
    public static <T> void setItems(RecyclerView recyclerView, ArrayList<T> list) {
        AdsaleBaseAdapter<T> adapter = (AdsaleBaseAdapter<T>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setList(list);
        }
    }

//    @BindingAdapter("app:exhibitors")
    @BindingAdapter({"exhibitors"})
    public static void setExhibitors(RecyclerView recyclerView, ArrayList<Exhibitor> list) {
        ExhibitorAdapter adapter = (ExhibitorAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setList(list);
        }
    }

    @BindingAdapter({"viewAspectRatio"})
    public static void setViewAspectRatio(SimpleDraweeView view,float aspectRatio){
        view.setAspectRatio(aspectRatio);
    }

    @BindingAdapter({"circle"})
    public static void setCircle(TextView tv, boolean currItem){
        LogUtil.i(TAG,"setBackground:currItem="+currItem);
        tv.setBackgroundResource(currItem? R.drawable.oval_black:R.drawable.oval_white);
        tv.setTextColor(currItem? tv.getResources().getColor(R.color.white):tv.getResources().getColor(R.color.black));
    }



}
