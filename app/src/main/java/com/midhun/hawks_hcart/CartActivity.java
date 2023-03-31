package com.midhun.hawks_hcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.midhun.hawks_hcart.Util.MidhunUtils;

public class CartActivity extends AppCompatActivity {
FrameLayout fragment_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        fragment_container=findViewById(R.id.fragment_container);

        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(CartActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(CartActivity.this, true);
    //add fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, CartFragment.class, null)
                    .commit();
        }
    }
}