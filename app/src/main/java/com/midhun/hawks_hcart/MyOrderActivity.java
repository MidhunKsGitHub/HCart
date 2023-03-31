package com.midhun.hawks_hcart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawks_hcart.Adapter.MyOrderAdapter;
import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.Model.MyOrderApiModel;
import com.midhun.hawks_hcart.Util.MidhunUtils;
import com.midhun.hawks_hcart.View.MyOrder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView1;
    String UID;
    List<MyOrder> myOrderList;
    MyOrderAdapter myOrderAdapter;
   private LinearLayout loading,base;
   ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        recyclerView1 = findViewById(R.id.recyclerview1);
        loading=findViewById(R.id.loading);
        base=findViewById(R.id.base);
        base.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(MyOrderActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(MyOrderActivity.this, true);
        UID = MidhunUtils.localData(MyOrderActivity.this, "login", "UID");
        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView1.setHasFixedSize(true);
        myOrderList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyOrderActivity.this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager);
        myOrderAdapter = new MyOrderAdapter(myOrderList, MyOrderActivity.this);
        recyclerView1.setAdapter(myOrderAdapter);
        myOrder();
    }

    private void myOrder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<MyOrderApiModel> call = api.myOrder("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", UID, "0", "100");
        myOrderList = new ArrayList<>();
        call.enqueue(new Callback<MyOrderApiModel>() {
            @Override
            public void onResponse(Call<MyOrderApiModel> call, Response<MyOrderApiModel> response) {
                myOrderList = response.body().OList();
                myOrderAdapter = new MyOrderAdapter(myOrderList, MyOrderActivity.this);
                recyclerView1.setAdapter(myOrderAdapter);
                loading.setVisibility(View.GONE);
                base.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MyOrderApiModel> call, Throwable t) {
                Toast.makeText(MyOrderActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}