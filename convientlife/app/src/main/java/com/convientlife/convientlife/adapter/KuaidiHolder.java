package com.convientlife.convientlife.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.convientlife.convientlife.R;

/*
 * Created by Administrator on 2019/3/11 0011.
 */
public class KuaidiHolder extends RecyclerView.ViewHolder {

    public TextView tvCompany;
    public TextView tvDate;
    public TextView tvCntent;

    public KuaidiHolder(@NonNull View itemView) {
        super(itemView);
        tvCompany = itemView.findViewById(R.id.tv_company);
        tvDate = itemView.findViewById(R.id.tv_date);
        tvCntent = itemView.findViewById(R.id.tv_content);
    }
}
