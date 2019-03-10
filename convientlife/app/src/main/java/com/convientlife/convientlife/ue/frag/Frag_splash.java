package com.convientlife.convientlife.ue.frag;

import android.view.View;
import android.view.WindowManager;

import com.convientlife.convientlife.R;
import com.convientlife.convientlife.cons.SPCons;
import com.hiber.hiber.RootFrag;
import com.hiber.hiber.language.Sgg;
import com.hiber.tools.Lgg;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Frag_splash extends RootFrag {
    @Override
    public int onInflateLayout() {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏 
        return R.layout.frag_splash;
    }

    @Override
    public void onNexts(Object o, View view, String s) {
        if (Sgg.getInstance(activity).getBoolean(SPCons.IS_GUIDE, false)) {
            new android.os.Handler().postDelayed(() -> toFrag(getClass(), Frag_home.class, null, true), 2000);
        } else {
            new android.os.Handler().postDelayed(() -> toFrag(getClass(), Frag_guide.class, null, true), 2000);
        }

    }

    @Override
    public boolean onBackPresss() {
        Lgg.t(getClass().getSimpleName()).ii(getClass().getSimpleName() + ": click back");
        return true;
    }
}
