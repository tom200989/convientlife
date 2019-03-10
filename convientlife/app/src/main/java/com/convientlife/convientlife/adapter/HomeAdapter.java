package com.convientlife.convientlife.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.convientlife.convientlife.R;
import com.convientlife.convientlife.bean.HomeBean;

import java.util.List;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeHolder> {

    private Context context;
    private List<HomeBean> homeBeans;

    public HomeAdapter(Context context, List<HomeBean> homeBeans) {
        this.context = context;
        this.homeBeans = homeBeans;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HomeHolder(LayoutInflater.from(context).inflate(R.layout.item_home, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder homeHolder, int position) {
        HomeBean homeBean = homeBeans.get(position);
        homeHolder.ivItemIcon.setImageDrawable(homeBean.getIcon());
        homeHolder.tvItemTitle.setText(homeBean.getTitle());
        homeHolder.rlItemAll.setOnClickListener(v -> itemClickRequestNext(homeBean, position));
    }

    @Override
    public int getItemCount() {
        return homeBeans.size();
    }

    private OnItemClickRequestListener onItemClickRequestListener;

    // Inteerface--> 接口OnItemClickRequestListener
    public interface OnItemClickRequestListener {
        void itemClickRequest(HomeBean homeBean, int position);
    }

    // 对外方式setOnItemClickRequestListener
    public void setOnItemClickRequestListener(OnItemClickRequestListener onItemClickRequestListener) {
        this.onItemClickRequestListener = onItemClickRequestListener;
    }

    // 封装方法itemClickRequestNext
    private void itemClickRequestNext(HomeBean homeBean, int position) {
        if (onItemClickRequestListener != null) {
            onItemClickRequestListener.itemClickRequest(homeBean, position);
        }
    }

}
