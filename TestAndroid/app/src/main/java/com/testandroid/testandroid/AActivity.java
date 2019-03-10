package com.testandroid.testandroid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        // Log.v("trace", "A onCreated");
        // String action = getIntent().getAction();
        // Log.v("trace", "A action is: " + action);
        check();
        checklist();
    }

    private void checklist() {
        Integer[] qun = {1, 2, 3};
        List<Integer> qun1 = new ArrayList<>(Arrays.asList(qun));
        qun1.add(4);
        
    }

    private void check() {
        ComponentName componentName = getComponentName();
        String className = componentName.getClassName();
        ActivityInfo activityInfo;
        try {
            activityInfo = getPackageManager().getActivityInfo(componentName, PackageManager.GET_META_DATA);
            int launchMode = activityInfo.launchMode;
            Log.i("testmkn", "launchMode: " + launchMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("trace", "A onResume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v("trace", "A onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("trace", "A onDestroy");
    }

    public void toB(View view) {
        Intent intent = new Intent("com.testandroid.testandroid.BActivity");
        startActivity(intent);
    }
}
