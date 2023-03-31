package com.midhun.hawks_hcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.midhun.hawks_hcart.Adapter.ProfileAdapter;
import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.Model.ProfileApiModel;
import com.midhun.hawks_hcart.Util.MidhunUtils;
import com.midhun.hawks_hcart.View.Profile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserFragment extends Fragment {
    List<Profile> profileList;
    RecyclerView recyclerView1;
     ProfileAdapter profileAdapter;
     String UID;
ShimmerFrameLayout shimmerFrameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
       recyclerView1=view.findViewById(R.id.recyclerview1);
       shimmerFrameLayout=view.findViewById(R.id.shimmer_view_container);
       UID = MidhunUtils.localData(getActivity(), "login", "UID");
        recyclerView1.setHasFixedSize(true);
        profileList=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager);
        profileAdapter=new ProfileAdapter(getActivity(),profileList);
        recyclerView1.setAdapter(profileAdapter);
        LoadProfileList();
        return view;
    }
    private void LoadProfileList(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api= retrofit.create(Api.class);

        Call<ProfileApiModel> call=api.getProfile("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",UID);

        call.enqueue(new Callback<ProfileApiModel>() {
            @Override
            public void onResponse(Call<ProfileApiModel> call, Response<ProfileApiModel> response) {
                profileList.addAll(response.body().getData());
                profileAdapter=new ProfileAdapter(getActivity(),profileList);
                recyclerView1.setAdapter(profileAdapter);
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ProfileApiModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}