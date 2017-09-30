package com.adsale.HEATEC.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.adsale.ChinaPlas.BR;

/**
 * ViewHolder基类
 * Created by Carrie on 2017/8/3.
 */

public class AdsaleBaseViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public AdsaleBaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public void bind(Object obj){
        binding.setVariable(BR.obj, obj);
        binding.executePendingBindings();
    }



}
