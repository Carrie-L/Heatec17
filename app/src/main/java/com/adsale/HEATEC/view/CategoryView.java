package com.adsale.HEATEC.view;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.database.model.ICategory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryView extends LinearLayout {
	private Context mContext;
	public TextView txtGroupName, txtCategory;

	public CategoryView(Context context) {
		super(context);
		setupView();
	}

	public CategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupView();
	}

	public CategoryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupView();
	}

	private void setupView() {
		mContext = getContext();
		LayoutInflater.from(mContext).inflate(R.layout.view_category_item, this);

		txtCategory = (TextView) findViewById(R.id.txtCategoryName);
		txtGroupName = (TextView) findViewById(R.id.txtGroupName);
	}

	public void setData(ICategory oclsCategory, int curLanguage, boolean showCategry) {
		if (showCategry) {
			txtGroupName.setVisibility(View.VISIBLE);
			txtGroupName.setText(oclsCategory.getSort(curLanguage));

		} else {
			txtGroupName.setVisibility(View.GONE);
		}
		txtCategory.setText(oclsCategory.getCategoryName(curLanguage));
	}

}
