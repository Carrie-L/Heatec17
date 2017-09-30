package com.adsale.HEATEC.view;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.database.model.clsExhibitor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExhibitorView extends LinearLayout {
	private Context mContext;

	public TextView txtExhibitor, txtCategory, txtExhibitorNO;

	public ExhibitorView(Context context) {
		super(context);
		setupView();
	}

	public ExhibitorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupView();
	}

	public ExhibitorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupView();
	}

	private void setupView() {
		mContext = getContext();
		LayoutInflater.from(mContext).inflate(R.layout.view_exhibitor_item, this);

		txtCategory = (TextView) findViewById(R.id.txtcatalog);
		txtExhibitor = (TextView) findViewById(R.id.txtCompanyName);
		txtExhibitorNO = (TextView) findViewById(R.id.txtExhibitorNO);
	}

	public void setData(clsExhibitor oclsExhibitor, int curLanguage, boolean showCategry) {
		if (showCategry) {
			txtCategory.setVisibility(View.VISIBLE);
			txtCategory.setText(oclsExhibitor.getSort(curLanguage));
		} else {
			txtCategory.setVisibility(View.GONE);
		}
		txtExhibitorNO.setText(oclsExhibitor.getExhibitorNO());
		txtExhibitor.setText(oclsExhibitor.getCompanyName(curLanguage));
	}

}
