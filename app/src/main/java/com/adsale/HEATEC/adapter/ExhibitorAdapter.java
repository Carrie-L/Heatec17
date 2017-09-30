package com.adsale.HEATEC.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.adsale.HEATEC.database.model.clsExhibitor;
import com.adsale.HEATEC.database.model.clsSection;
import com.adsale.HEATEC.view.ExhibitorView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;

public class ExhibitorAdapter extends BaseAdapter implements SectionIndexer, Filterable {
	public static String TAG = "ExhibitorAdapter";

	private Context mContext;
	private int curLanguage;
	private List<clsExhibitor> mocolExhibitors;
	private List<clsExhibitor> mOriginalDataList;
	private List<clsSection> mSectionList;
	private ArrayFilter filter;

	public ExhibitorAdapter(Context context, int pLanguage, List<clsExhibitor> pcolExhibitors) {
		super();
		mContext = context;
		curLanguage = pLanguage;
		mOriginalDataList = pcolExhibitors;
		if (mOriginalDataList == null)
			mOriginalDataList = new ArrayList<clsExhibitor>();

		mocolExhibitors = mOriginalDataList;
	}

	@Override
	public int getCount() {
		if (null != mocolExhibitors)
			return mocolExhibitors.size();
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setListData(List<clsExhibitor> pocolExhibitors, int pLanguage) {
		this.mocolExhibitors = pocolExhibitors;
		curLanguage = pLanguage;
	}

	@Override
	public clsExhibitor getItem(int position) {
		return mocolExhibitors.get(position);
	}

	public List<clsExhibitor> getData() {
		return mocolExhibitors;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		clsExhibitor oclsExhibitor = mocolExhibitors.get(position);
		boolean showCategory = false;
		if (position == 0) {
			showCategory = true;
		} else {
			String lastCatalog = mocolExhibitors.get(position - 1).getSort(curLanguage);
			if (oclsExhibitor.getSort(curLanguage).equals(lastCatalog)) {
				showCategory = false;
			} else {
				showCategory = true;
			}
		}

		ExhibitorView holder = null;
		if (convertView != null) {
			ExhibitorView oExhibitorView = (ExhibitorView) convertView;
			oExhibitorView.setData(oclsExhibitor, curLanguage, showCategory);
			holder = oExhibitorView;
		} else {
			holder = new ExhibitorView(mContext);
			holder.setData(oclsExhibitor, curLanguage, showCategory);

		}

		return holder;

	}

	@Override
	public int getPositionForSection(int section) {
		int intPosition = -1;

		clsExhibitor oclsExhibitor;
		clsSection oSection = mSectionList.get(section);

		for (int i = 0; i < mocolExhibitors.size(); i++) {
			oclsExhibitor = mocolExhibitors.get(i);
			if (oclsExhibitor.getSort(curLanguage).equals(oSection.getLable()))
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

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new ArrayFilter();
		}
		return filter;
	}

	class ArrayFilter extends Filter {
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			List<clsExhibitor> fliterResult;
			if (constraint == null || constraint.length() == 0) {
				fliterResult = new ArrayList<clsExhibitor>(mOriginalDataList);
			} else {
				final int count = mOriginalDataList.size();
				String filerValue = constraint.toString().toLowerCase(Locale.getDefault());
				fliterResult = new ArrayList<clsExhibitor>(count);
				for (int i = 0; i < count; i++) {
					clsExhibitor contact = mOriginalDataList.get(i);
					if (contact.getCompanyNameCN().toLowerCase(Locale.getDefault()).indexOf(filerValue) != -1
							|| contact.getCompanyNameTW().toLowerCase(Locale.getDefault()).indexOf(filerValue) != -1
							|| contact.getCompanyNameEN().toLowerCase(Locale.getDefault()).indexOf(filerValue) != -1
							|| contact.getExhibitorNO().toLowerCase(Locale.getDefault()).indexOf(filerValue) != -1) {
						fliterResult.add(contact);
					}
				}
			}
			results.count = fliterResult.size();
			results.values = fliterResult;
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			mocolExhibitors = (List<clsExhibitor>) results.values;
			notifyDataSetChanged();
		}
	}
}