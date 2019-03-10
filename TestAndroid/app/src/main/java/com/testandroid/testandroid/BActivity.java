package com.testandroid.testandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class BActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Log.v("trace", "B onCreated");
        String action = getIntent().getAction();
        Log.v("trace", "B action is: " + action);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("trace", "B onResume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v("trace", "B onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("trace", "B onDestroy");
    }


    public void toA(View view) {
        Intent intent = new Intent("com.testandroid.testandroid.AActivity");
        startActivity(intent);
    }
}
