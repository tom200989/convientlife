package com.convientlife.convientlife.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.convientlife.convientlife.R;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class FanyiHolder extends RecyclerView.ViewHolder {

    public RelativeLayout rlItemFanyi;
    public TextView tvItemFanyi;

    public FanyiHolder(@NonNull View itemView) {
        super(itemView);
        rlItemFanyi = itemView.findViewById(R.id.rl_item_fanyi);
        tvItemFanyi = itemView.findViewById(R.id.tv_item_fanyi);
    }
}
