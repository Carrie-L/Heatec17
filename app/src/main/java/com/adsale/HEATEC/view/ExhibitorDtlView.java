package com.adsale.HEATEC.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.App;
import com.adsale.HEATEC.activity.ExhibitorListActivity;
import com.adsale.HEATEC.database.ExhibitorDBHelper;
import com.adsale.HEATEC.database.model.clsExhibitor;
import com.adsale.HEATEC.database.model.clsIndustry;
import com.adsale.HEATEC.util.SystemMethod;

public class ExhibitorDtlView extends ScrollView {
	public static final String TAG = "ExhibitorDtlView";

	private Context mContext;

	private LinearLayout lyScroll;
	private DtlItemView dtlBooth, dtlAddress, dtlEmail, dtlTel, dtlTel1, dtlFax, dtlWebsite;
	private TextView txtIndustryTop;

	private clsExhibitor mClsExhibitor;
	private int mCurLanguage;

	private Fragment mFragment;

	// private OnClickListener mIndustryItemClickListener;

	// public void setIndustryItemClickListener(OnClickListener listener) {
	// mIndustryItemClickListener = listener;
	// }

	private void findView() {
		lyScroll = (LinearLayout) findViewById(R.id.lyScroll);

		dtlBooth = (DtlItemView) findViewById(R.id.dtlBooth);
		dtlAddress = (DtlItemView) findViewById(R.id.dtlAddress);
		dtlEmail = (DtlItemView) findViewById(R.id.dtlEmail);
		dtlTel = (DtlItemView) findViewById(R.id.dtlTel);
		dtlTel1 = (DtlItemView) findViewById(R.id.dtlTel1);
		dtlFax = (DtlItemView) findViewById(R.id.dtlFax);
		dtlWebsite = (DtlItemView) findViewById(R.id.dtlWebsite);

		txtIndustryTop = (TextView) findViewById(R.id.txtIndustryTop);
	}

	public ExhibitorDtlView(Context context) {
		super(context);
		setupView();
	}

	public ExhibitorDtlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupView();
	}

	public ExhibitorDtlView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupView();
	}

	private void setupView() {
		mContext = getContext();
		LayoutInflater.from(mContext).inflate(R.layout.f_exhibitor_dtl, this);
		this.setBackgroundColor(Color.WHITE);
		findView();
	}

	public void setData(clsExhibitor exhibitor) {
		mClsExhibitor = exhibitor;
		mCurLanguage = SystemMethod.getCurLanguage(mContext);

		dtlBooth.setData(0, R.drawable.booth, R.string.booth, mClsExhibitor.getExhibitorNO());
		dtlAddress.setData(0, R.drawable.address, R.string.address, mClsExhibitor.getAddress(mCurLanguage));
		dtlEmail.setData(1, R.drawable.mail, R.string.email, mClsExhibitor.getEmail());
		dtlTel.setData(2, R.drawable.telephone, R.string.tel, mClsExhibitor.getTel());
		dtlTel1.setData(2, R.drawable.telephone, R.string.tel, mClsExhibitor.getTel1());
		dtlFax.setData(2, R.drawable.fax, R.string.fax, mClsExhibitor.getFax());

		dtlWebsite.setFragment(mFragment);
		dtlWebsite.setData(3, R.drawable.website, R.string.website, mClsExhibitor.getWebsite());

		List<clsIndustry> industries = new ExhibitorDBHelper(mContext).getExhibitorIndustryList(mClsExhibitor.getCompanyID());
		if (!industries.isEmpty()) {
			txtIndustryTop.setVisibility(View.VISIBLE);
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			for (final clsIndustry oclsIndustry : industries) {
				View itemView = inflater.inflate(R.layout.view_list_item, null);
				TextView txtIndustryName = (TextView) itemView.findViewById(R.id.textView1);
				txtIndustryName.setText(oclsIndustry.getCategoryName(mCurLanguage));
				lyScroll.addView(itemView);
				itemView.setTag(oclsIndustry);
				itemView.setOnClickListener(new ListItemClick(oclsIndustry));
			}
		}
	}

	public void setFragment(Fragment mFragment) {
		this.mFragment = mFragment;
	}

	public class ListItemClick implements View.OnClickListener {
		clsIndustry mClsIndustry;

		public ListItemClick(clsIndustry oclsIndustry) {
			mClsIndustry = oclsIndustry;
		}

		@Override
		public void onClick(View v) {
			if (App.SearchIndustryCount >= 3)
				return;
			Intent intent = new Intent(mContext, ExhibitorListActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			String strCatergoryID = mClsIndustry.getCategoryID();
			intent.putExtra("Industry", strCatergoryID);
			intent.putExtra("Title", mClsIndustry.getCategoryName(mCurLanguage));
			mContext.startActivity(intent);
			
			if(SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")){
				((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
			}
		}
	}

}
