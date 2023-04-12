package com.owle.newsapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.owle.newsapps.utils.Tools;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
    }

    private void initToolbar() {

        Tools.setSystemBarColor(this, R.color.blue_500);
    }
}