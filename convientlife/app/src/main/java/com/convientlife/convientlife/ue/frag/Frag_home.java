package com.convientlife.convientlife.ue.frag;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.convientlife.convientlife.R;
import com.convientlife.convientlife.adapter.HomeAdapter;
import com.convientlife.convientlife.bean.HomeBean;
import com.convientlife.convientlife.bean.WeatherBean;
import com.example.administrator.gps.BaiduUtils;
import com.hiber.hiber.RootFrag;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Frag_home extends RootFrag {

    @BindView(R.id.rcv_home)
    RecyclerView rcvHome;
    @BindView(R.id.tv_home_weather)
    TextView tvWeather;
    @BindView(R.id.tv_home_quit)
    TextView tvQuit;

    private List<Drawable> iconList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private List<HomeBean> homeBeans = new ArrayList<>();
    private Class[] clickFrags = {// all
            Frag_Fanyi.class,// 翻译      
            Frag_dianyingpiao.class,// 电影票     
            Frag_huochepiao.class,// 火车票      
            Frag_kuaidichaxun.class,// 快递查询    
            Frag_weixinjinxuan.class,// 微信精选   
            Frag_xingzuoyunshi.class,// 星座运势    
            Frag_xinwentoutiao.class// 新闻头条
    };

    @Override
    public int onInflateLayout() {
        return R.layout.frag_home;
    }

    @Override
    public void initViewFinish() {
        initRes();
        setAdapter();
        initClick();
    }

    @Override
    public String[] initPermissed() {
        String[] permissions = {//
                Manifest.permission.ACCESS_FINE_LOCATION, //
                Manifest.permission.READ_PHONE_STATE,//
                Manifest.permission.WRITE_EXTERNAL_STORAGE};//
        return permissions;
    }

    private void getWeathter() {

        BaiduUtils baiduUtils = new BaiduUtils();
        baiduUtils.setOnGetLocationSuccessListener(bdLocation -> {

            String city = bdLocation.getCity().replace("市", "");
            String district = bdLocation.getDistrict();
            String street = bdLocation.getStreet();

            Log.v("ma_baidu", "city: " + city);
            Log.v("ma_baidu", "district: " + district);
            Log.v("ma_baidu", "street: " + street);

            RequestParams rp = new RequestParams("http://apis.juhe.cn/simpleWeather/query");
            rp.addQueryStringParameter("key", "dadad1162c26df95628cccb4d5960bb6");
            rp.addQueryStringParameter("city", city);
            x.http().get(rp, new Callback.CommonCallback<String>() {
                @Override
                public void responseBody(UriRequest uriRequest) {

                }

                @Override
                public void onSuccess(String json) {
                    WeatherBean weatherBean = JSONObject.parseObject(json, WeatherBean.class);
                    if (weatherBean.getError_code() == 0) {
                        String city = weatherBean.getResult().getCity();
                        WeatherBean.ResultBean.RealtimeBean realtime = weatherBean.getResult().getRealtime();
                        String info = realtime.getInfo();// 多云
                        String temperature = realtime.getTemperature();

                        String content = city + district + street + " " + info + " " + temperature + "℃";
                        tvWeather.setText(content);
                    } else {
                        int error_code = weatherBean.getError_code();
                        Log.e("ma_baidu", "error: " + error_code);
                    }
                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        });
        baiduUtils.init(activity);


    }

    private void initClick() {
        tvQuit.setOnClickListener(v -> kill());
    }

    @Override
    public void onNexts(Object o, View view, String s) {
        getWeathter();
    }

    private void initRes() {

        iconList = getIcon(R.drawable.icon_fanyi,// 翻译
                R.drawable.icon_h5dianyingpiao,// 电影票 
                R.drawable.icon_huochepiao,// 火车票
                R.drawable.icon_kuaidichaxun, // 快递查询
                R.drawable.icon_weixinjingxuan, // 微信精选
                R.drawable.icon_xingzuoyunshi,// 星座运势
                R.drawable.icon_xinwentoutiao// 新闻头条
        );
        titleList = getTitles(R.string.home_fanyi,// 翻译
                R.string.home_dianyingpiao,// 电影票
                R.string.home_huochepiao,// 火车票
                R.string.home_kuaidichaxun,// 快递查询
                R.string.home_weixinjingxuan,// 微信精选
                R.string.home_xingzuoyunshi,// 星座运势
                R.string.home_xinwentoutiao// 新闻头条
        );

        // 封装整合
        for (int i = 0; i < iconList.size(); i++) {
            HomeBean homeBean = new HomeBean();
            homeBean.setIcon(iconList.get(i));
            homeBean.setTitle(titleList.get(i));
            homeBeans.add(homeBean);
        }

    }

    /**
     * 获取图标
     *
     * @param resids 图标集合
     * @return 图标IV
     */
    private List<Drawable> getIcon(@DrawableRes int... resids) {
        List<Drawable> icons = new ArrayList<>();
        for (int resid : resids) {
            icons.add(getResources().getDrawable(resid));
        }
        return icons;
    }

    /**
     * 获取标题
     *
     * @param strids 标题资源
     * @return 标题TV
     */
    private List<String> getTitles(@StringRes int... strids) {
        List<String> titles = new ArrayList<>();
        for (int strid : strids) {
            titles.add(getString(strid));
        }
        return titles;
    }

    private void setAdapter() {
        GridLayoutManager gm = new GridLayoutManager(activity, 3, GridLayout.VERTICAL, false);
        rcvHome.setLayoutManager(gm);
        HomeAdapter adapter = new HomeAdapter(activity, homeBeans);
        setItemClickListener(adapter);
        rcvHome.setAdapter(adapter);
    }

    private void setItemClickListener(HomeAdapter adapter) {
        adapter.setOnItemClickRequestListener((homeBean, position) -> toFrag(getClass(), clickFrags[position], null, true));
    }

    @Override
    public boolean onBackPresss() {
        finishActivity();
        return true;
    }
}
