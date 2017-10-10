package com.adsale.HEATEC.adapter;

import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.widget.ImageView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.NewsActivity;
import com.adsale.HEATEC.base.AdsaleBaseAdapter;
import com.adsale.HEATEC.base.AdsaleBaseViewHolder;
import com.adsale.HEATEC.dao.News;
import com.adsale.HEATEC.databinding.ItemNewsBinding;
import com.adsale.HEATEC.util.network.Configure;

import java.util.ArrayList;

/**
 * Created by Carrie on 2017/8/15.
 */

public class NewsAdapter extends AdsaleBaseAdapter<News> {
	private ArrayList<News> list;
	private ImageView ivLogo;
	private NewsActivity activity;

	public NewsAdapter(NewsActivity activity, ArrayList<News> list) {
		this.list = list;
		this.activity=activity;
	}

	@Override
	public void setList(ArrayList<News> list) {
		this.list = list;
		super.setList(list);
	}

	@Override
	public void onBindViewHolder(AdsaleBaseViewHolder holder, int position) {
		super.onBindViewHolder(holder, position);
		ivLogo.setImageURI(Uri.parse(Configure.DOWNLOAD_PATH + "News/" + list.get(position).getLogo()));
	}

	@Override
	protected void bindVariable(ViewDataBinding binding) {
		super.bindVariable(binding);
		ItemNewsBinding newsBinding = (ItemNewsBinding) binding;
		ivLogo = newsBinding.ivNewsPic;
		newsBinding.setActivity(activity);
	}

	@Override
	protected Object getObjForPosition(int position) {
		return list.get(position);
	}

	@Override
	protected int getLayoutIdForPosition(int position) {
		return R.layout.item_news;
	}

	@Override
	public int getItemCount() {
		return list.size();
	}
}
