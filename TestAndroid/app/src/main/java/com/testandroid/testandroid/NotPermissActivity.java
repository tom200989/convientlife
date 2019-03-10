package com.testandroid.testandroid;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;

import com.strongview.strongview.StrongView;

import java.util.ArrayList;
import java.util.List;

public class NotPermissActivity extends Activity {


    private StrongView wdNotPermiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_permiss);
        wdNotPermiss = findViewById(R.id.wd_not_permiss);

        List<String> permisss = new ArrayList<>();
        List<Drawable> draws = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            permisss.add("wtf num : " + i);
            Drawable ic = getResources().getDrawable(R.drawable.error);
            draws.add(ic);
        }

        wdNotPermiss.createDefault(draws, permisss);

    }
}
