package com.adsale.HEATEC.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.MainActivity;
import com.adsale.HEATEC.activity.PadMainActivity;
import com.adsale.HEATEC.util.SystemMethod;
import com.facebook.drawee.view.SimpleDraweeView;

public class TitleView extends RelativeLayout {
	private Context mContext;
	private TextView txtBarTitle;
	public static ImageView txtBack;
	public static ImageView txtHome;
	private RelativeLayout rl_bar;
	private SimpleDraweeView imgTop;
	private RelativeLayout rl_header_bar;
	
	private String mDeviceType;

	public TitleView(Context context) {
		super(context);
		setupView();
	}

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupView();
	}

	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupView();
	}

	private void setupView() {
		mContext = getContext();
		
		mDeviceType=SystemMethod.getSharedPreferences(mContext, "DeviceType");
		
		LayoutInflater.from(mContext).inflate(R.layout.view_title, this);
		
		txtBarTitle = (TextView) findViewById(R.id.txtBarTitle);
		txtBack = (ImageView) findViewById(R.id.txtBarBack);
		txtHome = (ImageView) findViewById(R.id.txtBarHome);
		rl_bar = (RelativeLayout) findViewById(R.id.rl_header_bar);
		imgTop = (SimpleDraweeView) findViewById(R.id.imgBarTop);
		
		if(mDeviceType.equals("Phone")){
			
		}
		
//		if(mDeviceType.equals("Pad")){
//			RelativeLayout.LayoutParams paramsImg=new RelativeLayout.LayoutParams(App.mWidth,105);
//	//		RelativeLayout.LayoutParams paramsBar=new RelativeLayout.LayoutParams(App.mWidth,105);
//			paramsImg.addRule(Gravity.CENTER_HORIZONTAL);
//	//		paramsBar.addRule(Gravity.CENTER_HORIZONTAL);
//			imgTop.setLayoutParams(paramsImg);
//	//		rl_header_bar.setLayoutParams(paramsBar);
//		}
//		
		
		
//		Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.bar_001);
//		int height=(bitmap.getHeight()*App.mScreenWidth)/bitmap.getWidth();
//		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(App.mScreenWidth,height); 
//		rl_bar.setLayoutParams(params);
		
		if (SystemMethod.getCurLanguage(mContext) == 1) {
			txtBack.setImageResource(R.drawable.btn_back);
		} else {
			txtBack.setImageResource(R.drawable.bttn_back_cn);
		}

		txtHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mDeviceType.equals("Pad")) {
					Intent intent = new Intent(mContext, PadMainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					((Activity) mContext).startActivity(intent);
					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				} else {
					Intent intent = new Intent(mContext, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					((Activity) mContext).startActivity(intent);
					((Activity) mContext).overridePendingTransition(R.animator.slide_right_enter, R.animator.slide_right_exit);
				}
			}
		});
	}

	public void setTitle(int resID) {
		txtBarTitle.setText(resID);
	}

	public void setTitle(String title) {
		txtBarTitle.setText(title);
	}

	public void setOnBackListener(OnClickListener listener) {
		txtBack.setOnClickListener(listener);
	}

}
