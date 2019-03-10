package com.convientlife.convientlife.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.convientlife.convientlife.R;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class HomeHolder extends RecyclerView.ViewHolder {

    public RelativeLayout rlItemAll;
    public TextView tvItemTitle;
    public ImageView ivItemIcon;

    public HomeHolder(@NonNull View itemView) {
        super(itemView);
        rlItemAll = itemView.findViewById(R.id.rl_item_all);
        tvItemTitle = itemView.findViewById(R.id.tv_item_title);
        ivItemIcon = itemView.findViewById(R.id.iv_item_icon);
    }
}
