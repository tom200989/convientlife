package com.convientlife.convientlife.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.convientlife.convientlife.R;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class TrainHolder extends RecyclerView.ViewHolder {

    public TextView tvClass;
    public TextView tvFromPlace;
    public TextView tvToPlace;
    public TextView tvFromTime;
    public TextView tvToTime;
    public TextView tvDuration;

    public TrainHolder(@NonNull View itemView) {
        super(itemView);
        tvClass = itemView.findViewById(R.id.tv_class);
        tvFromPlace = itemView.findViewById(R.id.tv_fromPlace);
        tvToPlace = itemView.findViewById(R.id.tv_toPlace);
        tvFromTime = itemView.findViewById(R.id.tv_fromTime);
        tvToTime = itemView.findViewById(R.id.tv_toTime);
        tvDuration = itemView.findViewById(R.id.tv_duration);
    }
}
