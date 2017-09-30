package com.adsale.HEATEC.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * ftp控制的展商列表
 * @author Carrie
 *
 */
public class FtpExhibitorAdapter extends BaseAdapter {
	private ArrayList<String> iconUrl;
	private ArrayList<String> name;
	
	private LayoutInflater inflater;
	private Context mContext;
	private ViewHolder holder;
	private DisplayImageOptions imgOptions;
	private ImageLoader imgloader;
	

	public FtpExhibitorAdapter(Context context,ArrayList<String> icon,
			 ArrayList<String> name) {
		super();
		this.mContext=context;
		this.iconUrl = icon;
		this.name = name;
		
		inflater=LayoutInflater.from(mContext);
		imgOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		imgloader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return name.size();
	}

	@Override
	public Object getItem(int position) {
		return name.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=inflater.inflate(R.layout.view_exhibitor_nav_item, null);
			holder = new ViewHolder();
			holder.icon=(ImageView) convertView.findViewById(R.id.imgNavIcon);
			holder.name=(TextView) convertView.findViewById(R.id.textNvaName);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		imgloader.displayImage(iconUrl.get(position), holder.icon,imgOptions);
		holder.name.setText(name.get(position));
		return convertView;
	}
	
	public class ViewHolder{
		public ImageView icon;
		public TextView name;
	}

}
