package com.convientlife.convientlife.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.convientlife.convientlife.R;

/*
 * Created by Administrator on 2019/3/11 0011.
 */
public class XingzuoHolder extends RecyclerView.ViewHolder {

    public TextView tvXingzuo;

    public XingzuoHolder(@NonNull View itemView) {
        super(itemView);
        tvXingzuo = itemView.findViewById(R.id.tv_xingzuo);
    }
}
