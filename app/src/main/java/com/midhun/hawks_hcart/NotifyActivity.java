package com.midhun.hawks_hcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.midhun.hawks_hcart.Util.MidhunUtils;

public class NotifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(NotifyActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(NotifyActivity.this, true);
    }
}