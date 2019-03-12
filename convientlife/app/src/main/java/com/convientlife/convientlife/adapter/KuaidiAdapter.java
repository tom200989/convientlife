package com.convientlife.convientlife.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.convientlife.convientlife.R;
import com.convientlife.convientlife.bean.KuaidiSimplebean;

import java.util.List;

/*
 * Created by Administrator on 2019/3/11 0011.
 */
public class KuaidiAdapter extends RecyclerView.Adapter<KuaidiHolder> {


    private Context context;
    private List<KuaidiSimplebean> kuaidiSimplebeans;

    public KuaidiAdapter(Context context, List<KuaidiSimplebean> kuaidiSimplebeans) {
        this.context = context;
        this.kuaidiSimplebeans = kuaidiSimplebeans;
    }

    @NonNull
    @Override
    public KuaidiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new KuaidiHolder(LayoutInflater.from(context).inflate(R.layout.item_kuaidichaxun, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KuaidiHolder kuaidiHolder, int i) {
        KuaidiSimplebean kuaidiSimplebean = kuaidiSimplebeans.get(i);
        kuaidiHolder.tvCompany.setVisibility(i == 0 ? View.VISIBLE : View.INVISIBLE);
        kuaidiHolder.tvCompany.setText(kuaidiSimplebean.getCompany());
        kuaidiHolder.tvDate.setText(kuaidiSimplebean.getDate());
        kuaidiHolder.tvCntent.setText(kuaidiSimplebean.getContent());
    }

    @Override
    public int getItemCount() {
        return kuaidiSimplebeans.size();
    }
}
