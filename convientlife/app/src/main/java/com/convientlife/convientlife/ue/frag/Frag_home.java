package com.convientlife.convientlife.ue.frag;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;

import com.convientlife.convientlife.R;
import com.convientlife.convientlife.adapter.HomeAdapter;
import com.convientlife.convientlife.bean.HomeBean;
import com.hiber.hiber.RootFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Frag_home extends RootFrag {

    @BindView(R.id.rcv_home)
    RecyclerView rcvHome;

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
        super.initViewFinish();
        initRes();
        setAdapter();
    }

    @Override
    public void onNexts(Object o, View view, String s) {

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
