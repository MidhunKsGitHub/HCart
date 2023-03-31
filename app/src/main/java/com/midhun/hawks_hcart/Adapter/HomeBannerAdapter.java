package com.midhun.hawks_hcart.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.midhun.hawks_hcart.CategoryProductActivity;
import com.midhun.hawks_hcart.R;
import com.midhun.hawks_hcart.View.Category;

import java.util.List;

public class HomeBannerAdapter extends RecyclerView.Adapter<HomeBannerAdapter.HomeCategoryViewHolder> {
    Context ctx;
   private List<Category> bannerList;

    public HomeBannerAdapter(Context ctx, List<Category> bannerList) {
        this.ctx = ctx;
        this.bannerList = bannerList;
    }


    @NonNull
    @Override
    public HomeBannerAdapter.HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.banner_custom_item, parent, false);
        return new HomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBannerAdapter.HomeCategoryViewHolder holder, int position) {

        Category category = bannerList.get(position);


        String base = "https://apihcart.hawkssolutions.com/public/";

        String img_url = base.concat(category.getImage());


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
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.putExtra("category_id",category.getId());
                in.setClass(ctx, CategoryProductActivity.class);
                ctx.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {

        return bannerList.size();
    }

    class HomeCategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img);
        }
    }
}
