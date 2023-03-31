package com.midhun.hawks_hcart.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.midhun.hawks_hcart.ProductActivity;
import com.midhun.hawks_hcart.R;
import com.midhun.hawks_hcart.View.FavModel;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavAdapterViewHolder> {
    Context ctx;

    public FavAdapter(Context ctx, List<FavModel> favModelList) {
        this.ctx = ctx;
        this.favModelList = favModelList;
    }

    List<FavModel> favModelList;

    @NonNull
    @Override
    public FavAdapter.FavAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.wishlist_custom_item, parent, false);
        return new FavAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.FavAdapterViewHolder holder, int position) {
        FavModel favModel = favModelList.get(position);

        String base = "https://apihcart.hawkssolutions.com/public/";

        String img_url = base.concat(favModel.getImage());
        Glide.with(ctx)
                .load(img_url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)
                        .placeholder(R.drawable.background_color_black)
                        .error(R.drawable.background_color_black).centerCrop()
                )
                .into(holder.img);

        holder.price.setText(favModel.getCategory());
        holder.qty.setText(favModel.getTax_id());
        holder.name.setText(favModel.getName());

        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.putExtra("product_id", favModel.getId());
                in.putExtra("product_name",favModel.getName());
                in.setClass(ctx, ProductActivity.class);
                ctx.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favModelList.size();
    }

    class FavAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, qty;
        ImageView img;
        CardView base;

        public FavAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.img);
            qty = itemView.findViewById(R.id.qty);
            base = itemView.findViewById(R.id.base);
        }
    }
}
