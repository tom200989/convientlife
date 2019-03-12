package com.convientlife.convientlife.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.convientlife.convientlife.R;

/*
 * Created by Administrator on 2019/3/11 0011.
 */
public class WeixinHolder extends RecyclerView.ViewHolder {

    public ImageView ivImg;
    public TextView tvTitle;

    public WeixinHolder(@NonNull View itemView) {
        super(itemView);
        ivImg = itemView.findViewById(R.id.iv_img);
        tvTitle = itemView.findViewById(R.id.tv_weixin_title);
    }
}
