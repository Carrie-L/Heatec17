package com.adsale.HEATEC.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adsale.HEATEC.dao.Exhibitor;
import com.adsale.HEATEC.data.ExhibitorRepository;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.RecyclerViewScrollTo;
import com.adsale.HEATEC.view.SideLetter;

import java.util.ArrayList;

import static com.adsale.HEATEC.R.drawable.language;

/**
 * Created by Carrie on 2017/10/12.
 * Exhibitor List Activity
 */

public class ExhibitorListViewModel extends BaseObservable implements SideLetter.OnLetterClickListener {
    private static String TAG = "ExhibitorListViewModel";
    private Context mContext;
    private ExhibitorRepository mRepository;
    private int language;


    /**
     * 全部展商数据缓存列表
     */
    private ArrayList<Exhibitor> mExhibitorCaches = new ArrayList<>();
    /**
     * 全部排序字母缓存列表
     */
    private ArrayList<String> mLetterCaches = new ArrayList<>();
    /**
     * 右侧字母列表
     */
    public ArrayList<String> mLetters = new ArrayList<>();

    public final ObservableArrayList<Exhibitor> exhibitors = new ObservableArrayList<>();
    public final ObservableField<String> dialogLetter = new ObservableField<>("");

    private ArrayList<Exhibitor> searchTemps = new ArrayList<>();
    private RecyclerViewScrollTo mRVScrollTo;

    public ExhibitorListViewModel(Context context, ExhibitorRepository repository) {
        mContext = context.getApplicationContext();
        mRepository = repository;
        language = SystemMethod.getCurLanguage(context);
    }

    public void setLayoutManager(LinearLayoutManager layoutManager, RecyclerView recyclerView) {
        mRVScrollTo = new RecyclerViewScrollTo(layoutManager, recyclerView);
    }

    public ArrayList<Exhibitor> getAllExhibitors() {
        exhibitors.clear();
        mLetters.clear();
        LogUtil.i("---> getAllExhibitors before ", "mExhibitorCaches=" + mExhibitorCaches.size() + ",exhibitors=" + exhibitors.size());

        if (mExhibitorCaches.isEmpty()) {
            mExhibitorCaches = mRepository.getAllExhibitors(mExhibitorCaches, mLetters);
        }
        exhibitors.addAll(mExhibitorCaches);
        LogUtil.i(TAG, "mLetters=" + mLetters.toString());
        LogUtil.i("getAllExhibitors after <---- ", "mExhibitorCaches=" + mExhibitorCaches.size() + ",exhibitors=" + exhibitors.size());

        if (mLetterCaches.isEmpty()) {
            mLetterCaches.addAll(mLetters);
        } else {
            mLetters.addAll(mLetterCaches);
        }

        return exhibitors;
    }

    public void search(String text) {
        exhibitors.clear();
        mLetters.clear();
        searchTemps.clear();

        searchTemps.addAll(mExhibitorCaches);
        exhibitors.addAll(mRepository.getExhibitorSearchResults(searchTemps, mLetters, text));

        LogUtil.i(TAG, "search -：mLetters=" + mLetters.size() + "," + mLetters.toString());
    }

    public void resetList() {
        exhibitors.clear();
        exhibitors.addAll(mExhibitorCaches);

        mLetters.clear();
        mLetters.addAll(mLetterCaches);
    }


    public void setupSideLetter(SideLetter sideLetter) {
        sideLetter.setList(mLetters);
        sideLetter.setOnLetterClickListener(this);
    }

    private void scrollTo(String letter) {
        int size = exhibitors.size();
        for (int j = 0; j < size; j++) {
            if (exhibitors.get(j).getSort(language).equals(letter)) {
                mRVScrollTo.scroll(j);
                break;
            }
        }
    }

    @Override
    public void onClick(String letter) {
        dialogLetter.set(letter);
        scrollTo(letter);
    }

}
