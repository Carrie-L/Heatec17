package com.adsale.HEATEC.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.adapter.PadNavListAdapter.NavHolder;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.view.MenuView;
import com.adsale.HEATEC.viewmodel.MainViewModel;

import java.util.ArrayList;

public class PadNavListAdapter extends RecyclerView.Adapter<NavHolder> {

    private static final String TAG = "PadNavListAdapter";
    private MainViewModel.OnMainIntentCallback mCallback;
    private ArrayList<MainIcon> mColMainIcons;
    private MainIcon mainIcon;

    public class NavHolder extends RecyclerView.ViewHolder {
        private MenuView menuView;

        public NavHolder(View itemView) {
            super(itemView);
            menuView = (MenuView) itemView.findViewById(R.id.pad_menuview);
            menuView.setOnMenuClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.intent(mColMainIcons.get(getAdapterPosition()+8));
                }
            });
        }
    }

    public PadNavListAdapter(ArrayList<MainIcon> mainIcons, MainViewModel.OnMainIntentCallback callback) {
        this.mCallback = callback;
        this.mColMainIcons = mainIcons;
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public void onBindViewHolder(NavHolder holder, int position) {
        mainIcon = mColMainIcons.get(position + 8);
        LogUtil.i(TAG, "mainIcon.getIcon()=" + (position + 8) + ":" + mainIcon.getBaiDu_TJ() + "," + mainIcon.getIcon());

        if (mainIcon != null) {
            holder.menuView.setPadNormalIcon();
            holder.menuView.setBackground(mainIcon, false);
        }
    }


    @Override
    public NavHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.pad_view_nav_menu, parent, false);
        return new NavHolder(view);
    }

}
