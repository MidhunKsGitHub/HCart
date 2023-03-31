package com.midhun.hawks_hcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.midhun.hawks_hcart.Util.MidhunUtils;

public class MainActivity extends AppCompatActivity {
FrameLayout fragment_container;
ImageView img_home,img_cart,img_heart,img_user;
String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment_container=findViewById(R.id.fragment_container);
        img_home=findViewById(R.id.img_home);
        img_heart=findViewById(R.id.img_heart);
        img_cart=findViewById(R.id.img_cart);
        img_user=findViewById(R.id.img_user);

        //Hide action bar
        getSupportActionBar().hide();

        //strict mode vm policy
      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());
        //statusbar
        MidhunUtils.setStatusBarIcon(MainActivity.this,true);
        MidhunUtils.changeStatusBarColor(MainActivity.this,R.color.white);
        UID = MidhunUtils.localData(MainActivity.this, "login", "UID");

        //image filter
        MidhunUtils.colorFilter(MainActivity.this,img_home, R.color.purple);
        MidhunUtils.colorFilter(MainActivity.this,img_cart, R.color.grey_black);
        MidhunUtils.colorFilter(MainActivity.this,img_heart, R.color.grey_black);
        MidhunUtils.colorFilter(MainActivity.this,img_user, R.color.grey_black);

        img_home.setImageResource(R.drawable.home);
        img_cart.setImageDrawable(getDrawable(R.drawable.cart_outline));
        img_heart.setImageDrawable(getDrawable(R.drawable.heart_outline));
        img_user.setImageDrawable(getDrawable(R.drawable.user_outline));
        //add fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, HomeFragment.class, null)
                    .commit();
        }

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container, HomeFragment.class, null)
                        .addToBackStack(null)
                        .commit();

                MidhunUtils.colorFilter(MainActivity.this,img_home,R.color.purple);
                MidhunUtils.colorFilter(MainActivity.this,img_cart, R.color.grey_black);
                MidhunUtils.colorFilter(MainActivity.this,img_heart, R.color.grey_black);
                MidhunUtils.colorFilter(MainActivity.this,img_user, R.color.grey_black);


                img_home.setImageResource(R.drawable.home);
                img_cart.setImageDrawable(getDrawable(R.drawable.cart_outline));
                img_heart.setImageDrawable(getDrawable(R.drawable.heart_outline));
                img_user.setImageDrawable(getDrawable(R.drawable.user_outline));

            }
        });

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UID.isEmpty()) {
                    MidhunUtils.showSnackBarMsg(getApplicationContext(), img_cart, "Login first to continue", "Close");

                } else {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, CartFragment.class, null)
                            .addToBackStack(null)
                            .commit();

                    MidhunUtils.colorFilter(MainActivity.this, img_cart, R.color.purple);
                    MidhunUtils.colorFilter(MainActivity.this, img_home, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_heart, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_user, R.color.grey_black);


                    img_home.setImageResource(R.drawable.home_outline);
                    img_cart.setImageDrawable(getDrawable(R.drawable.cart));
                    img_heart.setImageDrawable(getDrawable(R.drawable.heart_outline));
                    img_user.setImageDrawable(getDrawable(R.drawable.user_outline));
                }
            }
        });

        img_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UID.isEmpty()) {
                    MidhunUtils.showSnackBarMsg(getApplicationContext(), img_heart, "Login first to continue", "Close");

                } else {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, FavFragment.class, null)
                            .addToBackStack(null)
                            .commit();

                    MidhunUtils.colorFilter(MainActivity.this, img_heart, R.color.purple);
                    MidhunUtils.colorFilter(MainActivity.this, img_cart, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_home, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_user, R.color.grey_black);

                    img_home.setImageResource(R.drawable.home_outline);
                    img_cart.setImageDrawable(getDrawable(R.drawable.cart_outline));
                    img_heart.setImageDrawable(getDrawable(R.drawable.heart));
                    img_user.setImageDrawable(getDrawable(R.drawable.user_outline));
                }
            }
        });

        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MidhunUtils.localData(MainActivity.this,"login","UID").isEmpty()){
                  startActivity(new Intent(MainActivity.this,SignInActivity.class));
                }
                else {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, UserFragment.class, null)
                            .addToBackStack(null)
                            .commit();

                    MidhunUtils.colorFilter(MainActivity.this, img_user, R.color.purple);
                    MidhunUtils.colorFilter(MainActivity.this, img_cart, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_heart, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_home, R.color.grey_black);

                    img_home.setImageResource(R.drawable.home_outline);
                    img_cart.setImageDrawable(getDrawable(R.drawable.cart_outline));
                    img_heart.setImageDrawable(getDrawable(R.drawable.heart_outline));
                    img_user.setImageDrawable(getDrawable(R.drawable.user));
                }
            }
        });

    }
    @Override
    public void  onBackPressed(){

    }
}