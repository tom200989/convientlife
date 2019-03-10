package com.convientlife.convientlife.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.convientlife.convientlife.R;

import java.util.List;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class FanyiAdapter extends RecyclerView.Adapter<FanyiHolder> {

    private Context context;
    private List<String> languages;

    public FanyiAdapter(Context context, List<String> languages) {
        this.context = context;
        this.languages = languages;
    }

    @NonNull
    @Override
    public FanyiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FanyiHolder(LayoutInflater.from(context).inflate(R.layout.item_fanyi, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FanyiHolder fanyiHolder, int i) {
        fanyiHolder.tvItemFanyi.setText(languages.get(i));
        fanyiHolder.rlItemFanyi.setOnClickListener(v -> FanYiItemClickNext(i));
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    private OnFanYiItemClickListener onFanYiItemClickListener;

    // Inteerface--> 接口OnFanYiItemClickListener
    public interface OnFanYiItemClickListener {
        void FanYiItemClick(int position);
    }

    // 对外方式setOnFanYiItemClickListener
    public void setOnFanYiItemClickListener(OnFanYiItemClickListener onFanYiItemClickListener) {
        this.onFanYiItemClickListener = onFanYiItemClickListener;
    }

    // 封装方法FanYiItemClickNext
    private void FanYiItemClickNext(int position) {
        if (onFanYiItemClickListener != null) {
            onFanYiItemClickListener.FanYiItemClick(position);
        }
    }
}
