package com.adsale.HEATEC.activity;

import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.adapter.NewsAdapter;
import com.adsale.HEATEC.base.BaseActivity;
import com.adsale.HEATEC.dao.News;
import com.adsale.HEATEC.dao.NewsDao;
import com.adsale.HEATEC.dao.NewsLink;
import com.adsale.HEATEC.dao.NewsLinkDao;
import com.adsale.HEATEC.data.DataRepository;
import com.adsale.HEATEC.databinding.FNewsBinding;
import com.adsale.HEATEC.util.LogUtil;

import java.util.ArrayList;

public class NewsActivity extends BaseActivity {
    public static final String TAG = "NewsActivity";
    private RecyclerView recyclerView;

    public final ObservableArrayList<News> newsList = new ObservableArrayList<>();

    private DataRepository mDataRepository;
    private NewsDao mNewsDao;


    @Override
    protected void initView() {
        FNewsBinding binding = FNewsBinding.inflate(getLayoutInflater(), mBaseFrameLayout, true);
        binding.setNews(this);
        recyclerView = binding.rvNews;
    }

    @Override
    protected void initData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        NewsAdapter adapter = new NewsAdapter(this, new ArrayList<News>(0));
        recyclerView.setAdapter(adapter);

        mNewsDao = App.mDBHelper.mNewsDao;

        getNewsList();


    }

    //String newsID, String title
    public void onItemClick(News news) {
        LogUtil.i(TAG, "news=" + news.getTitle());
        Bundle bundle = new Bundle();
        bundle.putParcelable("News", news);
        bundle.putString(TITLE, barTitle.get());
        intent(NewsDetailActivity.class, bundle);
    }

    public ArrayList<News> getNewsList() {
        return (ArrayList<News>) mNewsDao.queryBuilder().orderDesc(NewsDao.Properties.PublishDate).list();
    }


}
