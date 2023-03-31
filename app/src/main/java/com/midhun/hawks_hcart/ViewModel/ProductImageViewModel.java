package com.midhun.hawks_hcart.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.Model.ProductImagesApiModel;
import com.midhun.hawks_hcart.View.Images;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductImageViewModel extends ViewModel {
    private MutableLiveData<List<Images>> ImagesList;

    //we will call this method to get the data
    public LiveData<List<Images>>getProductImage(String id) {
        //if the list is null
        if (ImagesList == null) {
            ImagesList = new MutableLiveData<List<Images>>();

            //we will load it asynchronously from server in this method
            loadProducts(id);
        }

        //finally we will return the list
        return ImagesList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProducts(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProductImagesApiModel> call = api.getProductImage("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "products",id);
        Log.e("Product response",""+call.toString());

       call.enqueue(new Callback<ProductImagesApiModel>() {
           @Override
           public void onResponse(Call<ProductImagesApiModel> call, Response<ProductImagesApiModel> response) {
               ImagesList.setValue(response.body().getImages());
           }

           @Override
           public void onFailure(Call<ProductImagesApiModel> call, Throwable t) {

           }
       });
    }
}
