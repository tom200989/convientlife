package com.convientlife.convientlife.app;

import com.hiber.hiber.language.RootApp;

import org.xutils.x;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class ConvientApp extends RootApp {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
