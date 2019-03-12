package com.convientlife.convientlife.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.convientlife.convientlife.R;
import com.convientlife.convientlife.bean.WeixinSimpleBean;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/*
 * Created by Administrator on 2019/3/11 0011.
 */
public class WeixinAdapter extends RecyclerView.Adapter<WeixinHolder> {


    private Context context;
    private List<WeixinSimpleBean> wss;
    private ImageOptions imageOptions;

    public WeixinAdapter(Context context, List<WeixinSimpleBean> wss) {
        this.context = context;
        this.wss = wss;
        setImageOption();
    }

    private void setImageOption() {
        imageOptions = new ImageOptions.Builder()//
                               //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                               .setImageScaleType(ImageView.ScaleType.FIT_XY)
                               //设置加载过程中的图片
                               .setLoadingDrawableId(R.drawable.ic_launcher)
                               //设置加载失败后的图片
                               .setFailureDrawableId(R.drawable.ic_launcher)
                               //设置使用缓存
                               .setUseMemCache(true)
                               //设置支持gif
                               .setIgnoreGif(false)
                               //设置显示圆形图片
                               .setCircular(false)
                               // 显示方形
                               .setSquare(true).build();
    }

    @NonNull
    @Override
    public WeixinHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WeixinHolder(LayoutInflater.from(context).inflate(R.layout.item_weixin, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeixinHolder weixinHolder, int i) {
        WeixinSimpleBean wx = wss.get(i);
        x.image().bind(weixinHolder.ivImg, wx.getFirstImg(), imageOptions);
        weixinHolder.tvTitle.setText(wx.getTitle());
        weixinHolder.ivImg.setOnClickListener(v -> clickImgNext(wx.getUrl()));
    }

    @Override
    public int getItemCount() {
        return wss.size();
    }

    private OnClickImgListener onClickImgListener;

    // Inteerface--> 接口OnClickImgListener
    public interface OnClickImgListener {
        void clickImg(String url);
    }

    // 对外方式setOnClickImgListener
    public void setOnClickImgListener(OnClickImgListener onClickImgListener) {
        this.onClickImgListener = onClickImgListener;
    }

    // 封装方法clickImgNext
    private void clickImgNext(String url) {
        if (onClickImgListener != null) {
            onClickImgListener.clickImg(url);
        }
    }
}
