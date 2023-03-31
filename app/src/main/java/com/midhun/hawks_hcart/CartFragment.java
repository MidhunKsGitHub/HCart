package com.midhun.hawks_hcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawks_hcart.Adapter.CartAdapter;
import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.Model.CartApiModel;
import com.midhun.hawks_hcart.Util.MidhunUtils;
import com.midhun.hawks_hcart.View.Cart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartFragment extends Fragment {

    RecyclerView recyclerView1;
    List<Cart> cartList;
    CartAdapter cartAdapter;
    String UID;
    TextView total,order;
    ImageView img_back;
   private LinearLayout loading,base;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView1 = view.findViewById(R.id.recyclerview1);
        total = view.findViewById(R.id.total);
        order=view.findViewById(R.id.order);
        loading=view.findViewById(R.id.loading);
        base=view.findViewById(R.id.base);
        base.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        img_back=view.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClass(getActivity(), ShowAddressActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });
        UID = MidhunUtils.localData(getActivity(), "login", "UID");
        recyclerView1.setHasFixedSize(true);
        cartList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(getActivity(), cartList);
        recyclerView1.setAdapter(cartAdapter);
        loadCart();
        return view;
    }

    private void loadCart() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<CartApiModel> call = api.getCart("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", UID);
        call.enqueue(new Callback<CartApiModel>() {
            @Override
            public void onResponse(Call<CartApiModel> call, Response<CartApiModel> response) {
                try {
                   // cartList = response.body().getCart();
                    cartList.addAll(response.body().getCart());
                    cartAdapter = new CartAdapter(getActivity(), cartList);
                    recyclerView1.setAdapter(cartAdapter);
                    loading.setVisibility(View.GONE);
                    base.setVisibility(View.VISIBLE);
                    int sum = 0;
                    for (int i = 0; i < cartList.size(); i++) {
                        //  sum = sum + Integer.parseInt(cartList.get(i).getPrice());
                        sum = i;

                    }
                    total.setText(String.valueOf(cartList.size()) + " Cart items");

                }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CartApiModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}