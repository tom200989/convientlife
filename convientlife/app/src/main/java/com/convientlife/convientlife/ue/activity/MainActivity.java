package com.convientlife.convientlife.ue.activity;

import com.convientlife.convientlife.R;
import com.convientlife.convientlife.ue.frag.Frag_Fanyi;
import com.convientlife.convientlife.ue.frag.Frag_dianyingpiao;
import com.convientlife.convientlife.ue.frag.Frag_guide;
import com.convientlife.convientlife.ue.frag.Frag_home;
import com.convientlife.convientlife.ue.frag.Frag_huochepiao;
import com.convientlife.convientlife.ue.frag.Frag_kuaidichaxun;
import com.convientlife.convientlife.ue.frag.Frag_splash;
import com.convientlife.convientlife.ue.frag.Frag_weixinjinxuan;
import com.convientlife.convientlife.ue.frag.Frag_xingzuoyunshi;
import com.convientlife.convientlife.ue.frag.Frag_xinwentoutiao;
import com.hiber.bean.RootProperty;
import com.hiber.hiber.RootMAActivity;

public class MainActivity extends RootMAActivity {


    Class[] frags = {// 集合
            Frag_splash.class,// 启动
            Frag_guide.class,// 引导
            Frag_home.class,// 主页
            Frag_Fanyi.class,// 翻译      
            Frag_dianyingpiao.class,// 电影票     
            Frag_huochepiao.class,// 火车票      
            Frag_kuaidichaxun.class,// 快递查询    
            Frag_weixinjinxuan.class,// 微信精选   
            Frag_xingzuoyunshi.class,// 星座运势    
            Frag_xinwentoutiao.class// 新闻头条
    };

    @Override
    public RootProperty initProperty() {
        RootProperty rootProperty = new RootProperty();
        rootProperty.setColorStatusBar(R.color.colorPrimary);
        rootProperty.setContainId(R.id.fl_main);
        rootProperty.setFullScreen(true);
        rootProperty.setLayoutId(R.layout.activity_main);
        rootProperty.setSaveInstanceState(false);
        rootProperty.setFragmentClazzs(frags);
        return rootProperty;
    }

    @Override
    public void onNexts() {

    }

    @Override
    public boolean onBackClick() {
        return false;
    }
}
