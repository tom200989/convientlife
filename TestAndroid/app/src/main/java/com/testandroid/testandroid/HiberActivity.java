package com.testandroid.testandroid;

import com.hiber.bean.RootProperty;
import com.hiber.hiber.RootMAActivity;

public class HiberActivity extends RootMAActivity {

    private Class[] frags = {// 全部
            Fragment_A.class, // A界面
            Fragment_B.class // B界面
    };

    @Override
    public RootProperty initProperty() {
        RootProperty rootProperty = new RootProperty();
        rootProperty.setColorStatusBar(R.color.colorPrimary);
        rootProperty.setContainId(R.id.fl_containh);
        rootProperty.setFragmentClazzs(frags);
        rootProperty.setFullScreen(true);
        rootProperty.setLayoutId(R.layout.activity_hiberh);
        rootProperty.setSaveInstanceState(false);
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
