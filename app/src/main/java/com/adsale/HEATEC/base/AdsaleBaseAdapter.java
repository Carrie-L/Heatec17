package com.adsale.HEATEC.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Carrie on 2017/8/3.
 *
 */
public abstract class AdsaleBaseAdapter<T> extends RecyclerView.Adapter<AdsaleBaseViewHolder> {

    @Override
    public AdsaleBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding  binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),viewType,parent,false);
        bindVariable(binding);
        return new AdsaleBaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AdsaleBaseViewHolder holder, int position) {
        Object object = getObjForPosition(position);
        holder.bind(object);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    protected abstract Object getObjForPosition(int position);

    protected abstract int getLayoutIdForPosition(int position);

    protected void bindVariable(ViewDataBinding binding){

    }

    public void setList(ArrayList<T> list){
        notifyDataSetChanged();
    }

}
