package com.adsale.HEATEC.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.database.model.clsCountry;
import com.adsale.HEATEC.util.SystemMethod;

public class CountryAdapter extends BaseAdapter {
	private List<clsCountry> mclsCountries;
	private TextView text;
	private int mLang;
	private Context mContext;
	private int mDropDownResource;
	private LayoutInflater inflater;
	private int mResource;

	public CountryAdapter(Context context, List<clsCountry> clsCountries,
			int language,int resource) {
		this.mContext=context;
		this.mclsCountries = clsCountries;
		this.mResource=resource;
		this.mLang = language;
	}

	@Override
	public int getCount() {
		return mclsCountries.size();
	}

	@Override
	public Object getItem(int position) {
		return mclsCountries.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater=(LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		LinearLayout ll=new LinearLayout(mContext);
		
		convertView = inflater.inflate(R.layout.view_spinner_item,null);
		text = (TextView) convertView.findViewById(R.id.text1);

		// int language=SystemMethod.getCurLanguage(mContext);
		// 0:TW; 1:EN; 2:SC;
		if (mLang == 0) {
			text.setText(mclsCountries.get(position).getCountryNameTW());
		} else if (mLang == 1) {
			text.setText(mclsCountries.get(position).getCountryNameEN());
		} else if (mLang == 2)
			text.setText(mclsCountries.get(position).getCountryNameCN());
		text.setGravity(Gravity.CENTER_VERTICAL);
		return convertView;
	}

	
	

}
