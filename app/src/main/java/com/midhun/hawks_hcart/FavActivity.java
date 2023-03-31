package com.midhun.hawks_hcart;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.midhun.hawks_hcart.Util.MidhunUtils;

public class FavActivity extends AppCompatActivity {
FrameLayout fragment_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        fragment_container=findViewById(R.id.fragment_container);

        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(FavActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(FavActivity.this, true);
    //add fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, FavFragment.class, null)
                    .commit();
        }
    }
}