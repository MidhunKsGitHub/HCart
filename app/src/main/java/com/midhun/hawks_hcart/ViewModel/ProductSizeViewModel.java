package com.midhun.hawks_hcart.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.Model.ProductSizeApiModel;
import com.midhun.hawks_hcart.View.ProductSize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductSizeViewModel extends ViewModel {
    private MutableLiveData<List<ProductSize>> ProductSizeList;

    //we will call this method to get the data
    public LiveData<List<ProductSize>>getProductSize(String id) {
        //if the list is null
        if (ProductSizeList == null) {
            ProductSizeList = new MutableLiveData<List<ProductSize>>();

            //we will load it asynchronously from server in this method
            loadProducts(id);
        }

        //finally we will return the list
        return ProductSizeList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProducts(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProductSizeApiModel> call = api.getProductSize("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "products",id);


       call.enqueue(new Callback<ProductSizeApiModel>() {
           @Override
           public void onResponse(Call<ProductSizeApiModel> call, Response<ProductSizeApiModel> response) {
               ProductSizeList.setValue(response.body().getProductSize());
               Log.d("size list", "onResponse: "+response.body().getProductSize().get(0).getPriceAttribute());
           }

           @Override
           public void onFailure(Call<ProductSizeApiModel> call, Throwable t) {

           }
       });
    }
}
