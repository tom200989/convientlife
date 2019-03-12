package com.convientlife.convientlife.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.convientlife.convientlife.R;

import java.util.List;

/*
 * Created by Administrator on 2019/3/11 0011.
 */
public class XingzuoAdapter extends RecyclerView.Adapter<XingzuoHolder> {

    private Context context;
    private List<String> xingzuos;

    public XingzuoAdapter(Context context, List<String> xingzuos) {
        this.context = context;
        this.xingzuos = xingzuos;
    }

    @NonNull
    @Override
    public XingzuoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new XingzuoHolder(LayoutInflater.from(context).inflate(R.layout.item_xingzuo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull XingzuoHolder xingzuoHolder, int i) {
        String xingzuo = xingzuos.get(i);
        xingzuoHolder.tvXingzuo.setText(xingzuo);
        xingzuoHolder.tvXingzuo.setOnClickListener(v -> clickXingzuoNext(i));
    }

    @Override
    public int getItemCount() {
        return xingzuos.size();
    }

    private OnClickXingzuoListener onClickXingzuoListener;

    // Inteerface--> 接口OnClickXingzuoListener
    public interface OnClickXingzuoListener {
        void clickXingzuo(int position);
    }

    // 对外方式setOnClickXingzuoListener
    public void setOnClickXingzuoListener(OnClickXingzuoListener onClickXingzuoListener) {
        this.onClickXingzuoListener = onClickXingzuoListener;
    }

    // 封装方法clickXingzuoNext
    private void clickXingzuoNext(int position) {
        if (onClickXingzuoListener != null) {
            onClickXingzuoListener.clickXingzuo(position);
        }
    }
}
