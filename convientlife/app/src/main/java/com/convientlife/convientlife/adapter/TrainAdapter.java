package com.convientlife.convientlife.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.convientlife.convientlife.R;
import com.convientlife.convientlife.bean.TrainSimpleBean;

import java.util.List;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class TrainAdapter extends RecyclerView.Adapter<TrainHolder> {

    private Context context;
    private List<TrainSimpleBean> trainSimpleBeans;

    public TrainAdapter(Context context, List<TrainSimpleBean> trainSimpleBeans) {
        this.context = context;
        this.trainSimpleBeans = trainSimpleBeans;
    }

    @NonNull
    @Override
    public TrainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TrainHolder(LayoutInflater.from(context).inflate(R.layout.item_train, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrainHolder trainHolder, int position) {
        TrainSimpleBean trainSimpleBean = trainSimpleBeans.get(position);
        trainHolder.tvClass.setText(trainSimpleBean.getTrainClass());
        trainHolder.tvFromPlace.setText(trainSimpleBean.getTrainFromPlace());
        trainHolder.tvToPlace.setText(trainSimpleBean.getTrainToPlace());
        trainHolder.tvFromTime.setText(trainSimpleBean.getTrainFromTime());
        trainHolder.tvToTime.setText(trainSimpleBean.getTrainToTime());
        trainHolder.tvDuration.setText(trainSimpleBean.getTrainDuration());
    }

    @Override
    public int getItemCount() {
        return trainSimpleBeans.size();
    }
}
