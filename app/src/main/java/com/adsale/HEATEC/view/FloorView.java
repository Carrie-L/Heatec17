package com.adsale.HEATEC.view;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.database.model.ICategory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FloorView extends LinearLayout {
	private Context mContext;
	public TextView txtCategory;

	public FloorView(Context context) {
		super(context);
		setupView();
	}

	public FloorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupView();
	}

	public FloorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupView();
	}

	private void setupView() {
		mContext = getContext();
		LayoutInflater.from(mContext).inflate(R.layout.view_floor_item, this);
		txtCategory = (TextView) findViewById(R.id.txtCategoryName);
	}

	public void setData(ICategory oclsCategory, int curLanguage) {

		txtCategory.setText(oclsCategory.getCategoryName(curLanguage));
	}

}
