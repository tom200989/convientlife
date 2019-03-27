package com.convientlife.convientlife.ue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.convientlife.convientlife.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Button button = findViewById(R.id.bt_click_me);
        TextView textView = findViewById(R.id.tv_test_title);
        textView.setText("测试标题");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(SplashActivity.this, "点到我嗲", 3000).show();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
