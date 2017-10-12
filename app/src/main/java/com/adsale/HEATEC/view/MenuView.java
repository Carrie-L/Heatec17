package com.adsale.HEATEC.view;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.App;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.database.model.clsMainIcon;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.util.TextMeshPostProcessor;
import com.adsale.HEATEC.util.network.Configure;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class MenuView extends RelativeLayout {

	private static final String TAG = "MenuView";
	private TextView textview;
	private Context mContext;
	private SharedPreferences sp;
	private int mScreenWidth;
	private SimpleDraweeView sdv_menu;
	private RelativeLayout rl_menu;
	private LayoutParams param;
	private MainIcon mainIcon;
	private TextMeshPostProcessor mPostProcessor;
	private ImageRequest imageRequest;
	private PipelineDraweeControllerBuilder controllerBuilder;

	private String mBaseUrl = Configure.DOWNLOAD_PATH + "WebContent/";
	private StringBuilder mUrlBuilder;

	public MenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MenuView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_menu_item, this);
		textview = (TextView) view.findViewById(R.id.tv_menu);
		sdv_menu = (SimpleDraweeView) view.findViewById(R.id.sdv_menu);
		rl_menu = (RelativeLayout) view.findViewById(R.id.rl_menu);

		mContext = context;
		sp = mContext.getSharedPreferences("ScreenSize", 0);
		mScreenWidth = sp.getInt("ScreenWidth", 0);

	}

	public void setPhoneMenu1() {
		setSize(mScreenWidth / 3, mScreenWidth / 3);
	}

	public void setPhoneMenu2() {
		setSize(mScreenWidth - mScreenWidth / 3, mScreenWidth / 3);
	}

	public void setPhoneMenu3() {
		setSize(mScreenWidth / 3, mScreenWidth / 3);
	}
	
	public void setPadLargeIcon(){
		setSize(2*mScreenWidth / 7, 2*mScreenWidth / 7);
	}
	
	public void setPadNormalIcon(){
		setSize(mScreenWidth / 7, mScreenWidth / 7);
	}
	
	private void setSize(int width, int height) {
		param = (LayoutParams) sdv_menu.getLayoutParams();
		param.width = width;
		param.height = height;
		sdv_menu.setLayoutParams(param);
	}

	public void setBackground(MainIcon entity, boolean large) {
		mainIcon = entity;

		if (entity.getGoogle_TJ().contains("NULL")) {
			sdv_menu.setImageURI(getUri());
		} else {
			mPostProcessor = new TextMeshPostProcessor(mContext, entity, large);
			imageRequest = ImageRequestBuilder.newBuilderWithSource(getUri())
					.setPostprocessor(mPostProcessor).build();
			controllerBuilder = Fresco.newDraweeControllerBuilder()
					.setImageRequest(imageRequest)
					.setOldController(sdv_menu.getController());
			sdv_menu.setController(controllerBuilder.build());
		}

	}

	private Uri getUri() {
		mUrlBuilder = new StringBuilder();

		if (App.isNetworkAvailable) {
			mUrlBuilder.append(mBaseUrl);
		} else {
			mUrlBuilder.append(App.RootDir).append("WebContent/");
		}

		if (mainIcon.getIcon().contains("|")) {
			if (App.isTablet) {
				mUrlBuilder.append(mainIcon.getIcon().split("\\|")[1]);
			} else {
				mUrlBuilder.append(mainIcon.getIcon().split("\\|")[0]);
			}
		} else {
			mUrlBuilder.append(mainIcon.getIcon());
		}
		
		return App.isNetworkAvailable?Uri.parse(mUrlBuilder.toString()):Uri.fromFile(new File(mUrlBuilder.toString()));
	}

	public void setData(clsMainIcon entity) {

	}

	public void initSize() {
		mScreenWidth = mContext.getSharedPreferences("ScreenSize", 0).getInt(
				"ScreenWidth", 0);
	}

	public void setOnMenuClickListener(OnClickListener listener) {
		rl_menu.setOnClickListener(listener);
	}

}
