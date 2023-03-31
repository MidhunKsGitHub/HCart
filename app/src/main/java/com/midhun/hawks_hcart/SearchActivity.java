package com.midhun.hawks_hcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawks_hcart.Adapter.SearchAdapter;
import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.Model.ProductApiModel;
import com.midhun.hawks_hcart.Util.MidhunUtils;
import com.midhun.hawks_hcart.View.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    AutoCompleteTextView edt;
    ImageView ic_search, img_back;
    List<Products> productsList;
    RecyclerView recyclerView1;
    SearchAdapter searchAdapter;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edt = findViewById(R.id.edt);
        ic_search = findViewById(R.id.ic_search);
        img_back = findViewById(R.id.img_back);
        recyclerView1 = findViewById(R.id.recyclerview1);
        getSupportActionBar().hide();
        MidhunUtils.setStatusBarIcon(SearchActivity.this, true);
        MidhunUtils.changeStatusBarColor(SearchActivity.this, R.color.white);
        ProductssPopulate2("0");
        recyclerView1.setHasFixedSize(true);
        productsList = new ArrayList<>();
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(SearchActivity.this);
        layoutManager2.setOrientation(layoutManager2.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager2);
        searchAdapter = new SearchAdapter(SearchActivity.this, productsList);
        recyclerView1.setAdapter(searchAdapter);

        if (getIntent().getExtras().getString("name").isEmpty()) {

        } else {
            edt.setText(getIntent().getExtras().getString("name"));
            SearchProduct(getIntent().getExtras().getString("name"));
            progress = ProgressDialog.show(SearchActivity.this, null, null, true);
            progress.setContentView(R.layout.progress_layout);
            progress.setCancelable(true);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            progress.show();
        }

        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (edt.getText().toString().isEmpty()) {
                        Toast.makeText(SearchActivity.this, "Enter search item", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent in = new Intent();
                        in.setClass(SearchActivity.this, SearchActivity.class);
                        in.putExtra("name", edt.getText().toString());
                        startActivity(in);
                        finish();

                    }
                    return true;
                }
                return false;
            }
        });

    }

    private void SearchProduct(String search) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProductApiModel> call = api.searchProduct("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", "0", "10", search, "", "");
        call.enqueue(new Callback<ProductApiModel>() {
            @Override
            public void onResponse(Call<ProductApiModel> call, Response<ProductApiModel> response) {
                productsList.addAll(response.body().PList());
                searchAdapter = new SearchAdapter(SearchActivity.this, productsList);
                recyclerView1.setAdapter(searchAdapter);
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<ProductApiModel> call, Throwable t) {
                Toast.makeText(SearchActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ProductssPopulate2(String offset) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProductApiModel> call = api.getProductList("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "10", offset, "products");
        call.enqueue(new Callback<ProductApiModel>() {
            @Override
            public void onResponse(Call<ProductApiModel> call, Response<ProductApiModel> response) {

                addToDropDown(response.body().PList());

            }

            @Override
            public void onFailure(Call<ProductApiModel> call, Throwable t) {

            }
        });
    }


    private void addToDropDown(List<Products> productsList) {
        ArrayList arrayList = new ArrayList<>();
        for (int i = 0; i < productsList.size(); i++) {
            arrayList.add(productsList.get(i).getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        edt.setAdapter(arrayAdapter);
        edt.setThreshold(1);
        edt.requestFocus();
        edt.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                Intent in = new Intent();
                in.setClass(SearchActivity.this, SearchActivity.class);
                in.putExtra("name", edt.getText().toString());
                startActivity(in);
                finish();
            }
        });

    }
}