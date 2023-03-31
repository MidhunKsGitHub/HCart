package com.midhun.hawks_hcart.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.Model.ProductApiModel;
import com.midhun.hawks_hcart.View.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductsViewModel extends ViewModel {
    private MutableLiveData<List<Products>> productsList;

    //we will call this method to get the data
    public LiveData<List<Products>>getProducts(String offset) {
        //if the list is null
        if (productsList == null) {
            productsList = new MutableLiveData<List<Products>>();

            //we will load it asynchronously from server in this method
            loadProducts(offset);
        }

        //finally we will return the list
        return productsList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProducts(String offset) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProductApiModel> call = api.getProductList("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "10",offset,"products");

        Log.e("Product response",""+call.toString());
        Log.d("offset", "loadProducts: "+offset);
        call.enqueue(new Callback<ProductApiModel>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(Call<ProductApiModel> call, Response<ProductApiModel> response) {

                Log.e("ProductsApi response",""+response.toString());
                //finally we are setting the list to our MutableLiveData
                if(response.code()==200) {
                    productsList.setValue(response.body().PList());
//                    brandList.setValue(response.body().getPageData());
                }
                else if(response.code()==503) {

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<ProductApiModel> call, Throwable t) {

            }
        });
    }
}
