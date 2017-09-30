package com.adsale.HEATEC.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.WebContentActivity;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.util.network.Configure;
import com.adsale.HEATEC.view.MenuView;
import com.adsale.HEATEC.viewmodel.MainViewModel;

import java.util.ArrayList;

/**
 * Created by Carrie on 2017/9/26.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private ArrayList<MainIcon> mainIcons;
    private MainViewModel.OnMainIntentCallback mCallback;

    public MenuAdapter(ArrayList<MainIcon> mainIcons, MainViewModel.OnMainIntentCallback mCallback) {
        this.mainIcons = mainIcons;
        this.mCallback = mCallback;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        if (position == 1) {
            holder.menuView.setPhoneMenu2();
        } else {
            holder.menuView.setPhoneMenu3();
        }
        holder.menuView.setBackground(mainIcons.get(position), false);
    }

    @Override
    public int getItemCount() {
        return mainIcons.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private MenuView menuView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            menuView = (MenuView) itemView.findViewById(R.id.menuView);
            menuView.initSize();
            menuView.setOnMenuClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.intent(mainIcons.get(getAdapterPosition()));
                }
            });

        }

    }
}
