package com.adsale.HEATEC.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.database.ExhibitorDBHelper;
import com.adsale.HEATEC.database.model.clsExhibitor;
import com.adsale.HEATEC.database.model.clsIndustry;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.DtlItemView;

public class ExhibitorDtlFragment extends BaseFragment {
	public static final String TAG = "ExhibitorDtlFragment";

	private Context mContext;
	private View mBaseView;

	private LinearLayout lyScroll;
	private DtlItemView dtlBooth, dtlAddress, dtlEmail, dtlTel, dtlTel1, dtlFax, dtlWebsite;
	private TextView txtIndustryTop;

	private clsExhibitor mClsExhibitor;
	private int mCurLanguage;

	@Override
	public View initView(LayoutInflater inflater) {
		mBaseView = inflater.inflate(R.layout.f_exhibitor_dtl, null);
		mContext = getActivity();
		findView();
		
		return mBaseView;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mClsExhibitor = ((Activity) mContext).getIntent().getParcelableExtra("Exhibitor");
		mCurLanguage = SystemMethod.getCurLanguage(mContext);
		
		setupView();
	}

	private void findView() {
		lyScroll = (LinearLayout) mBaseView.findViewById(R.id.lyScroll);

		dtlBooth = (DtlItemView) mBaseView.findViewById(R.id.dtlBooth);
		dtlAddress = (DtlItemView) mBaseView.findViewById(R.id.dtlAddress);
		dtlEmail = (DtlItemView) mBaseView.findViewById(R.id.dtlEmail);
		dtlTel = (DtlItemView) mBaseView.findViewById(R.id.dtlTel);
		dtlTel1 = (DtlItemView) mBaseView.findViewById(R.id.dtlTel1);
		dtlFax = (DtlItemView) mBaseView.findViewById(R.id.dtlFax);
		dtlWebsite = (DtlItemView) mBaseView.findViewById(R.id.dtlWebsite);

		txtIndustryTop = (TextView) mBaseView.findViewById(R.id.txtIndustryTop);
	}

	private void setupView() {
		dtlBooth.setData(0, R.drawable.booth, R.string.booth, mClsExhibitor.getExhibitorNO());
		dtlAddress.setData(0, R.drawable.address, R.string.address, mClsExhibitor.getAddress(mCurLanguage));
		dtlEmail.setData(1, R.drawable.mail, R.string.email, mClsExhibitor.getEmail());
		dtlTel.setData(2, R.drawable.telephone, R.string.tel, mClsExhibitor.getTel());
		dtlTel1.setData(2, R.drawable.telephone, R.string.tel, mClsExhibitor.getTel1());
		dtlFax.setData(2, R.drawable.fax, R.string.fax, mClsExhibitor.getFax());
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
			
			}
		}
	}


}
