package com.testandroid.testandroid;

import android.view.View;

import com.hiber.hiber.RootFrag;

/*
 * Created by qianli.ma on 2019/3/6 0006.
 */
public class Fragment_B extends RootFrag {
    @Override
    public int onInflateLayout() {
        return R.layout.frag_b;
    }

    @Override
    public void onNexts(Object o, View view, String s) {

    }

    @Override
    public boolean onBackPresss() {
        return false;
    }
}
