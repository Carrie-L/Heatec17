package com.adsale.HEATEC.view;

import sanvio.libs.util.DisplayUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.WebContentActivity;
import com.adsale.HEATEC.util.SystemMethod;

public class DtlItemView extends LinearLayout {
	private Context mContext;
	private ImageView imgIcon;
	private TextView txtLable;
	private TextView txtContent;

	private Fragment mFragment;

	public DtlItemView(Context context) {
		super(context);
		setupView();
	}

	public DtlItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupView();
	}

	public DtlItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupView();
	}

	private void setupView() {
		mContext = getContext();
		LayoutInflater.from(mContext).inflate(R.layout.view_exhibitor_dtl_item, this);
		int top = DisplayUtils.dip2px(mContext, 5);
		int left = DisplayUtils.dip2px(mContext, 10);
		this.setBackgroundResource(R.drawable.bg_bottom_line_gray);
		this.setGravity(Gravity.CENTER_VERTICAL);
		this.setPadding(left, top, left, top);

		imgIcon = (ImageView) findViewById(R.id.imgIcon);
		txtLable = (TextView) findViewById(R.id.txtLable);
		txtContent = (TextView) findViewById(R.id.txtContent);
	}

	/**
	 * @param type
	 *            0,null;1,email;2,phone;3,web;
	 */
	public void setData(int type, int imgResID, int lableResID, final String content) {
		if (TextUtils.isEmpty(content)) {
			this.setVisibility(View.GONE);
			return;
		}
		imgIcon.setImageResource(imgResID);
		txtLable.setText(lableResID);
		txtContent.setText(content);

		if (type == 1) {
			txtContent.setTextColor(Color.BLUE);
			txtContent.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						Intent i = new Intent(Intent.ACTION_SEND);
						i.setType("plain/text").putExtra(Intent.EXTRA_EMAIL, new String[] { content });
						mContext.startActivity(i);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if (type == 2) {
			txtContent.setTextColor(Color.BLUE);
			txtContent.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						Intent callIntent = new Intent(Intent.ACTION_DIAL);
						callIntent.setData(Uri.parse("tel:" + content.replaceAll("[^0-9]", "")));
						mContext.startActivity(callIntent);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if (type == 3) {
			txtContent.setTextColor(Color.BLUE);
			txtContent.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String url = content;
					url = url.startsWith("http://") ? url : "http://" + url;
					Intent intent = new Intent(mContext, WebContentActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("WebUrl", url);
					mContext.startActivity(intent);

					if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
						((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
					}
				}
			});
		}
	}

	public void setFragment(Fragment mFragment) {
		this.mFragment = mFragment;
	}

}
