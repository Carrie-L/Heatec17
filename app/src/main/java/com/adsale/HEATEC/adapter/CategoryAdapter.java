package com.adsale.HEATEC.adapter;

import java.util.List;

import com.adsale.HEATEC.database.model.ICategory;
import com.adsale.HEATEC.database.model.clsSection;
import com.adsale.HEATEC.view.CategoryView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

public class CategoryAdapter extends BaseAdapter implements SectionIndexer {
	public static String TAG = "CategoryAdapter";

	private Context mContext;
	private int curLanguage;
	private List<ICategory> mocolCategorys;
	private List<clsSection> mSectionList;

	public CategoryAdapter(Context context, int pLanguage, List<ICategory> pcolCategorys) {
		super();
		mContext = context;
		curLanguage = pLanguage;
		mocolCategorys = pcolCategorys;
	}

	@Override
	public int getCount() {
		if (null != mocolCategorys)
			return mocolCategorys.size();
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setListData(List<ICategory> pocolCategorys, int pLanguage) {
		this.mocolCategorys = pocolCategorys;
		curLanguage = pLanguage;
	}

	@Override
	public ICategory getItem(int position) {
		return mocolCategorys.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ICategory oclsCategory = mocolCategorys.get(position);
		boolean showCategory = false;
		if (position == 0) {
			showCategory = true;
		} else {
			String lastCatalog = mocolCategorys.get(position - 1).getSort(curLanguage);
			if (oclsCategory.getSort(curLanguage).equals(lastCatalog)) {
				showCategory = false;
			} else {
				showCategory = true;
			}
		}

		CategoryView holder = null;
		if (convertView != null) {
			CategoryView oCategoryView = (CategoryView) convertView;
			oCategoryView.setData(oclsCategory, curLanguage, showCategory);
			holder = oCategoryView;
		} else {
			holder = new CategoryView(mContext);
			holder.setData(oclsCategory, curLanguage, showCategory);

		}

		return holder;

	}

	@Override
	public int getPositionForSection(int section) {
		int intPosition = -1;

		ICategory oclsCategory;
		clsSection oSection = mSectionList.get(section);

		for (int i = 0; i < mocolCategorys.size(); i++) {
			oclsCategory = mocolCategorys.get(i);
			if (oclsCategory.getSort(curLanguage).equals(oSection.getLable()))
				intPosition = i;
			if (intPosition != -1) {
				break;
			}
		}
		return intPosition;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	public void setSectionList(List<clsSection> list) {
		mSectionList = list;
	}

}