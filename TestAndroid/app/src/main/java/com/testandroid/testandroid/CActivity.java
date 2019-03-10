package com.testandroid.testandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        Log.v("trace", "C onCreated");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("trace", "C onResume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v("trace", "C onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("trace", "C onDestroy");
    }

    public void toA(View view) {
        Intent intent = new Intent(this, AActivity.class);
        startActivity(intent);
    }

    public void toB(View view) {
        Intent intent = new Intent(this, BActivity.class);
        startActivity(intent);
    }
}
