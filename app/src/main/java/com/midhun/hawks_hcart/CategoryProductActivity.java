package com.midhun.hawks_hcart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawks_hcart.Adapter.CategoryProductAdapter;
import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.Util.MidhunUtils;
import com.midhun.hawks_hcart.View.CategoryProducts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryProductActivity extends AppCompatActivity {
    RecyclerView recyclerView1;
    List<CategoryProducts> categoryProductsList;
    CategoryProductAdapter categoryProductAdapter;
    TextView catname;
    ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);
        recyclerView1 = findViewById(R.id.recyclerview1);
        catname = findViewById(R.id.catname);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(CategoryProductActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(CategoryProductActivity.this, true);
        recyclerView1.setHasFixedSize(true);
        categoryProductsList = new ArrayList<>();
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(CategoryProductActivity.this);
        layoutManager2.setOrientation(layoutManager2.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager2);
        categoryProductAdapter = new CategoryProductAdapter(CategoryProductActivity.this, categoryProductsList);
        recyclerView1.setAdapter(categoryProductAdapter);
        catname.setText(getIntent().getExtras().getString("category_name"));
        CategoryProductssPopulate(getIntent().getExtras().getString("category_id"));

    }

    private void CategoryProductssPopulate(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<CategoryProducts>> call = api.getCategoryProductsList("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", id);
        call.enqueue(new Callback<List<CategoryProducts>>() {
            @Override
            public void onResponse(Call<List<CategoryProducts>> call, Response<List<CategoryProducts>> response) {
                categoryProductsList.addAll(response.body());
                categoryProductAdapter = new CategoryProductAdapter(CategoryProductActivity.this, categoryProductsList);
                recyclerView1.setAdapter(categoryProductAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoryProducts>> call, Throwable t) {
                Toast.makeText(CategoryProductActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}