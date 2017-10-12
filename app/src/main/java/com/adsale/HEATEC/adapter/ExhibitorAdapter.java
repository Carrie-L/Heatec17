

package com.adsale.HEATEC.adapter;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.dao.Exhibitor;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;

import java.util.ArrayList;

import static com.adsale.HEATEC.R.id.sdv_logo;

public class ExhibitorAdapter extends RecyclerView.Adapter<ViewHolder>{
    private static final String TAG = "ExhibitorAdapter";
    private Context mContext;
    private ArrayList<Exhibitor> exhibitors;
    private int currLang;
    private LayoutInflater inflater;

    private final int TYPE_HEADER=0;
    private final int TYPE_ITEM=1;

    boolean showCategory = false;
    private Exhibitor exhibitor;
    private HeaderViewHolder headerViewHolder;
    private ExhibitorItemViewHolder itemViewHolder;
    private int language;

    public ExhibitorAdapter(Context context,ArrayList<Exhibitor> lists) {
        this.mContext=context;
        this.exhibitors=lists;
        inflater=LayoutInflater.from(mContext);
        language= SystemMethod.getCurLanguage(context);

        LogUtil.e(TAG, "ExhibitorAdapter");

    }

    public void setList(ArrayList<Exhibitor> lists){
        this.exhibitors=lists;
        LogUtil.e(TAG, "setList_"+exhibitors.size());
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
        if(viewType==TYPE_HEADER){
            View headerView=inflater.inflate(R.layout.item_exhi_detail_header, container,false);
            return new HeaderViewHolder(headerView);
        }else{
            View itemView=inflater.inflate(R.layout.item_exhi_detail_child, container,false);
            return new ExhibitorItemViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        exhibitor = exhibitors.get(position);

        if(holder.getItemViewType()==TYPE_HEADER){
            headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.tvGroupName.setText(exhibitor.getSort(language));
            showItemText(headerViewHolder);

        }else{
            itemViewHolder = (ExhibitorItemViewHolder) holder;
            showItemText(itemViewHolder);
        }
    }

    private void showItemText(ExhibitorItemViewHolder holder){
        holder.tvCompanyName.setText(exhibitor.getCompanyName(language));
        holder.tvExhibitorNo.setText(exhibitor.getExhibitorNO());
        if(exhibitor.getLogo()!=null&&!exhibitor.getLogo().trim().isEmpty()){
            LogUtil.i(TAG,"getPhotoFileName = "+exhibitor.getLogo().trim());
            holder.sdvLogo.setVisibility(View.VISIBLE);
            holder.sdvLogo.setImageURI(Uri.parse(exhibitor.getLogo()));
        }else{
            holder.sdvLogo.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEADER;
        }else if(exhibitors.get(position).getSort(language).equals(exhibitors.get(position-1).getSort(language))){
            return TYPE_ITEM;
        }else{
            return TYPE_HEADER;
        }
    }


    private class HeaderViewHolder extends ExhibitorItemViewHolder{
        private TextView tvGroupName;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvGroupName=(TextView) itemView.findViewById(R.id.txtGroupName);
        }

    }

    private class ExhibitorItemViewHolder extends ViewHolder{
        private TextView tvCompanyName;
        private TextView tvExhibitorNo;
        private ImageView sdvLogo;

        public ExhibitorItemViewHolder(View itemView) {
            super(itemView);
            tvCompanyName=(TextView) itemView.findViewById(R.id.txtCategoryName);
            tvExhibitorNo=(TextView) itemView.findViewById(R.id.txtBoothNo);
            sdvLogo=(ImageView) itemView.findViewById(R.id.sdv_logo);

//            sdvLogo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    LogUtil.i(TAG,"sdvLogo clicked");
//                    sdvLogo.setImageResource(R.drawable.btn_star);
//
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return exhibitors.size();
    }

}














//package com.adsale.HEATEC.adapter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import com.adsale.HEATEC.database.model.clsExhibitor;
//import com.adsale.HEATEC.database.model.clsSection;
//import com.adsale.HEATEC.view.ExhibitorView;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.SectionIndexer;
//
//public class ExhibitorAdapter extends BaseAdapter implements SectionIndexer, Filterable {
//	public static String TAG = "ExhibitorAdapter";
//
//	private Context mContext;
//	private int curLanguage;
//	private List<clsExhibitor> mocolExhibitors;
//	private List<clsExhibitor> mOriginalDataList;
//	private List<clsSection> mSectionList;
//	private ArrayFilter filter;
//
//	public ExhibitorAdapter(Context context, int pLanguage, List<clsExhibitor> pcolExhibitors) {
//		super();
//		mContext = context;
//		curLanguage = pLanguage;
//		mOriginalDataList = pcolExhibitors;
//		if (mOriginalDataList == null)
//			mOriginalDataList = new ArrayList<clsExhibitor>();
//
//		mocolExhibitors = mOriginalDataList;
//	}
//
//	@Override
//	public int getCount() {
//		if (null != mocolExhibitors)
//			return mocolExhibitors.size();
//		return 0;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	public void setListData(List<clsExhibitor> pocolExhibitors, int pLanguage) {
//		this.mocolExhibitors = pocolExhibitors;
//		curLanguage = pLanguage;
//	}
//
//	@Override
//	public clsExhibitor getItem(int position) {
//		return mocolExhibitors.get(position);
//	}
//
//	public List<clsExhibitor> getData() {
//		return mocolExhibitors;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//
//		clsExhibitor oclsExhibitor = mocolExhibitors.get(position);
//		boolean showCategory = false;
//		if (position == 0) {
//			showCategory = true;
//		} else {
//			String lastCatalog = mocolExhibitors.get(position - 1).getSort(curLanguage);
//			if (oclsExhibitor.getSort(curLanguage).equals(lastCatalog)) {
//				showCategory = false;
//			} else {
//				showCategory = true;
//			}
//		}
//
//		ExhibitorView holder = null;
//		if (convertView != null) {
//			ExhibitorView oExhibitorView = (ExhibitorView) convertView;
//			oExhibitorView.setData(oclsExhibitor, curLanguage, showCategory);
//			holder = oExhibitorView;
//		} else {
//			holder = new ExhibitorView(mContext);
//			holder.setData(oclsExhibitor, curLanguage, showCategory);
//
//		}
//
//		return holder;
//
//	}
//
//	@Override
//	public int getPositionForSection(int section) {
//		int intPosition = -1;
//
//		clsExhibitor oclsExhibitor;
//		clsSection oSection = mSectionList.get(section);
//
//		for (int i = 0; i < mocolExhibitors.size(); i++) {
//			oclsExhibitor = mocolExhibitors.get(i);
//			if (oclsExhibitor.getSort(curLanguage).equals(oSection.getLable()))
//				intPosition = i;
//			if (intPosition != -1) {
//				break;
//			}
//		}
//		return intPosition;
//	}
//
//	@Override
//	public int getSectionForPosition(int position) {
//		return 0;
//	}
//
//	@Override
//	public Object[] getSections() {
//		return null;
//	}
//
//	public void setSectionList(List<clsSection> list) {
//		mSectionList = list;
//	}
//
//	@Override
//	public Filter getFilter() {
//		if (filter == null) {
//			filter = new ArrayFilter();
//		}
//		return filter;
//	}
//
//	class ArrayFilter extends Filter {
//		protected FilterResults performFiltering(CharSequence constraint) {
//			FilterResults results = new FilterResults();
//			List<clsExhibitor> fliterResult;
//			if (constraint == null || constraint.length() == 0) {
//				fliterResult = new ArrayList<clsExhibitor>(mOriginalDataList);
//			} else {
//				final int count = mOriginalDataList.size();
//				String filerValue = constraint.toString().toLowerCase(Locale.getDefault());
//				fliterResult = new ArrayList<clsExhibitor>(count);
//				for (int i = 0; i < count; i++) {
//					clsExhibitor contact = mOriginalDataList.get(i);
//					if (contact.getCompanyNameCN().toLowerCase(Locale.getDefault()).indexOf(filerValue) != -1
//							|| contact.getCompanyNameTW().toLowerCase(Locale.getDefault()).indexOf(filerValue) != -1
//							|| contact.getCompanyNameEN().toLowerCase(Locale.getDefault()).indexOf(filerValue) != -1
//							|| contact.getExhibitorNO().toLowerCase(Locale.getDefault()).indexOf(filerValue) != -1) {
//						fliterResult.add(contact);
//					}
//				}
//			}
//			results.count = fliterResult.size();
//			results.values = fliterResult;
//			return results;
//		}
//
//		@SuppressWarnings("unchecked")
//		@Override
//		protected void publishResults(CharSequence constraint, FilterResults results) {
//			mocolExhibitors = (List<clsExhibitor>) results.values;
//			notifyDataSetChanged();
//		}
//	}
//}