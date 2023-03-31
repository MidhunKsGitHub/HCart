package com.midhun.hawks_hcart.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.ProductActivity;
import com.midhun.hawks_hcart.R;
import com.midhun.hawks_hcart.Util.MidhunUtils;
import com.midhun.hawks_hcart.View.CategoryProducts;
import com.midhun.hawks_hcart.View.WishList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.HomeCategoryViewHolder> {
    Context ctx;
    List<CategoryProducts> productsList;
    String UID;
    Activity activity;

    public CategoryProductAdapter(Context ctx, List<CategoryProducts> productsList) {
        this.ctx = ctx;
        this.productsList = productsList;
    }


    @NonNull
    @Override
    public CategoryProductAdapter.HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.productcategory_custom_item, parent, false);
        return new HomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductAdapter.HomeCategoryViewHolder holder, int position) {

        CategoryProducts products = productsList.get(position);
        if (MidhunUtils.localDataCtx(ctx, "login", "UID") != null) {
            UID = MidhunUtils.localDataCtx(ctx, "login", "UID");
        }
        if (MidhunUtils.localDataCtx(ctx, "isHeart", products.getId()).equalsIgnoreCase("yes")) {
            holder.heart.setImageResource(R.drawable.heart_full);

        } else {
            holder.heart.setImageResource(R.drawable.heart_outline);

        }
        String base = "https://apihcart.hawkssolutions.com/public/";

        String img_url = base.concat(products.getImage());
        MidhunUtils.colorFilterContex(ctx, holder.heart, R.color.grey_white);
        holder.name.setText(products.getName());
        holder.category.setText(products.getCategory());
        if (products.getPriceAttribute() == null) {
            holder.price.setText("₹ 50");
        } else {
            holder.price.setText("₹ " + products.getPriceAttribute());
        }
        Log.d("is image", img_url);

        Glide.with(ctx)
                .load(img_url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)
                        .placeholder(R.drawable.background_color_black)
                        .error(R.drawable.background_color_black).centerCrop()
                )
                .into(holder.img);


        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UID.isEmpty()) {
                    MidhunUtils.showSnackBarMsg(ctx, holder.heart, "Login first to continue", "Close");

                } else {
                    if (!MidhunUtils.localDataCtx(ctx, "isHeart", products.getId()).equalsIgnoreCase("yes")) {
                        holder.heart.setImageResource(R.drawable.heart_full);
                        MidhunUtils.showSnackBarMsg(ctx, holder.heart, "Product added to wishlist", "Close");
                        MidhunUtils.addLocalData(ctx, "isHeart", products.getId(), "yes");
                        addHeart(holder.getAdapterPosition());
                    } else {
                        MidhunUtils.addLocalData(ctx, "isHeart", products.getId(), "no");
                        holder.heart.setImageResource(R.drawable.heart_outline);
                        MidhunUtils.showSnackBarMsg(ctx, holder.heart, "Product removed from wishlist", "Close");
                        removeHeart(holder.getAdapterPosition());
                    }
                }

            }
        });

        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.putExtra("product_id", products.getId());
                in.putExtra("product_name", products.getName());
                in.setClass(ctx, ProductActivity.class);
                ctx.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class HomeCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name, price,category;
        ImageView img, heart;
        CardView base;

        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            heart = itemView.findViewById(R.id.heart);
            img = itemView.findViewById(R.id.img);
            base=itemView.findViewById(R.id.base);
            category=itemView.findViewById(R.id.category);
        }
    }

    private void addHeart(int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<WishList> call = api.addWishList("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", UID, productsList.get(pos).getId());
        call.enqueue(new Callback<WishList>() {
            @Override
            public void onResponse(Call<WishList> call, Response<WishList> response) {
                Toast.makeText(ctx, "Success added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WishList> call, Throwable t) {
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeHeart(int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<WishList> call = api.removeWishList("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", UID, productsList.get(pos).getId());
        call.enqueue(new Callback<WishList>() {
            @Override
            public void onResponse(Call<WishList> call, Response<WishList> response) {
                Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WishList> call, Throwable t) {
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

