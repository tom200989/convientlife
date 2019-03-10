package com.convientlife.convientlife.bean;

import android.graphics.drawable.Drawable;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class HomeBean {
    private Drawable icon;
    private String title;

    public HomeBean() {
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
