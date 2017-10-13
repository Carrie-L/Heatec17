package com.adsale.HEATEC.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.adapters.TextViewBindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.widget.EditText;
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
 * <p>
 * 所有列表
 */

public class ListBindings {
    private static final String TAG = "ListBindings";

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
    public static void setViewAspectRatio(SimpleDraweeView view, float aspectRatio) {
        view.setAspectRatio(aspectRatio);
    }

    @BindingAdapter({"circle"})
    public static void setCircle(TextView tv, boolean currItem) {
        LogUtil.i(TAG, "setBackground:currItem=" + currItem);
        tv.setBackgroundResource(currItem ? R.drawable.oval_black : R.drawable.oval_white);
        tv.setTextColor(currItem ? tv.getResources().getColor(R.color.white) : tv.getResources().getColor(R.color.black));
    }


    /**
     * {@link TextViewBindingAdapter}  仿照setText()方法，避免死循环绑定
     *
     * @param editText
     * @param text
     */
    @BindingAdapter("android:text")
    public static void setCustomEditText(EditText editText, String text) {
        final CharSequence oldText = editText.getText();
        LogUtil.i(TAG, "setCustomEditText:: text=" + text + ", oldText=" + oldText);

        if (text == oldText) {
            LogUtil.e(TAG, "text == oldText");
            return;
        }
        if (text == null && oldText.length() == 0) {
            LogUtil.e(TAG, "text == null && oldText.length() == 0)");
            return;
        }
        if (!haveContentsChanged(text, oldText)) {
            LogUtil.e(TAG, "!haveContentsChanged");
            return; // No content changes, so don't set anything.
        }
        editText.setText(text);
        editText.setSelection(editText.getText().length());
    }

    private static boolean haveContentsChanged(CharSequence str1, CharSequence str2) {
        if ((str1 == null) != (str2 == null)) {
            return true;
        } else if (str1 == null) {
            return false;
        }
        final int length = str1.length();
        if (length != str2.length()) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return true;
            }
        }
        return false;
    }


}
