package com.testandroid.testandroid;

import android.Manifest;
import android.view.View;

import com.hiber.bean.PermissBean;
import com.hiber.bean.StringBean;
import com.hiber.hiber.RootFrag;
import com.hiber.impl.PermissedListener;

/*
 * Created by qianli.ma on 2019/3/6 0006.
 */
public class Fragment_A extends RootFrag {

    @Override
    public int onInflateLayout() {
        return R.layout.frag_a;
    }

    @Override
    public void onNexts(Object o, View view, String s) {
        view.findViewById(R.id.bt_toPermiss).setOnClickListener(v -> {
            setPermissedListener(new PermissedListener() {
                @Override
                public void permissionResult(boolean isAllPass, String[] denyPermisson) {

                }
            });
            
            PermissBean permissBean = new PermissBean();
            permissBean.setPermissView(null);
            StringBean stringBean = new StringBean();
            stringBean.setTitle("This is title");
            stringBean.setContent("no content to show");
            stringBean.setCancel("cancel 1");
            stringBean.setOk("ok 1");
            permissBean.setStringBean(stringBean);
            setPermissView(permissBean);
            clickPermissed(new String[]{Manifest.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND, Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND});
        });
    }

    @Override
    public boolean onBackPresss() {
        return false;
    }
}
