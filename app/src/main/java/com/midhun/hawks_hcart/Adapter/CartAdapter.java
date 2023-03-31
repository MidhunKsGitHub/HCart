package com.midhun.hawks_hcart.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import com.midhun.hawks_hcart.Model.CartApiModel;
import com.midhun.hawks_hcart.R;
import com.midhun.hawks_hcart.View.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder> {
    Context ctx;
    List<Cart> cartList;
    ProgressDialog progress;
    int qty1;
    int price;
    int total;
    String PID, SIZE;

    public CartAdapter(Context ctx, List<Cart> cartList) {
        this.ctx = ctx;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartAdapter.CartAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.cart_custom_item, parent, false);
        return new CartAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartAdapterViewHolder holder, int position) {
        Cart cart = cartList.get(position);

        String base = "https://apihcart.hawkssolutions.com/public/";

        String img_url = base.concat(cart.getImage());


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
        price = Integer.parseInt(cart.getPrice());
        qty1 = Integer.parseInt(cart.getQuantity());
        total = price * qty1;
        holder.name.setText(cart.getProduct());
        holder.price.setText("₹ " + total);
        holder.qty.setText(String.valueOf(qty1));
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int value = Integer.parseInt(holder.qty.getText().toString());
                value++;
                holder.qty.setText(String.valueOf(value));
                holder.price.setText("₹ " + value * Integer.parseInt(cart.getPrice()));
                progress = ProgressDialog.show(ctx, null, null, true);
                progress.setContentView(R.layout.progress_layout);
                progress.setCancelable(false);
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                progress.show();
                pluscart(holder.getAdapterPosition());
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = Integer.parseInt(holder.qty.getText().toString());
                value--;
                if (value != 0) {
                    holder.qty.setText(String.valueOf(value));
                    holder.price.setText("₹ " + value * Integer.parseInt(cart.getPrice()));
                }

                progress = ProgressDialog.show(ctx, null, null, true);
                progress.setContentView(R.layout.progress_layout);
                progress.setCancelable(false);
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                progress.show();
                minusCart(holder.getAdapterPosition());
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = ProgressDialog.show(ctx, null, null, true);
                progress.setContentView(R.layout.progress_layout);
                progress.setCancelable(false);
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                progress.show();
                removeCart(holder.getAdapterPosition());
                //holder.base.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, qty;
        ImageView plus, minus;
        ImageView img;
        CardView remove, base;

        public CartAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            qty = itemView.findViewById(R.id.qty);
            img = itemView.findViewById(R.id.img);
            remove = itemView.findViewById(R.id.remove);
            base = itemView.findViewById(R.id.base);
        }
    }

    private void pluscart(int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<CartApiModel> call = api.plusCart("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", cartList.get(pos).getUserId(), cartList.get(pos).getId());
        call.enqueue(new Callback<CartApiModel>() {
            @Override
            public void onResponse(Call<CartApiModel> call, Response<CartApiModel> response) {
                Toast.makeText(ctx, "success", Toast.LENGTH_SHORT).show();
                progress.dismiss();

            }

            @Override
            public void onFailure(Call<CartApiModel> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void minusCart(int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<CartApiModel> call = api.minusCart("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", cartList.get(pos).getUserId(), cartList.get(pos).getId());
        call.enqueue(new Callback<CartApiModel>() {
            @Override
            public void onResponse(Call<CartApiModel> call, Response<CartApiModel> response) {

                progress.dismiss();

            }

            @Override
            public void onFailure(Call<CartApiModel> call, Throwable t) {
                progress.dismiss();

            }
        });
    }

    private void removeCart(int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<CartApiModel> call = api.removeCart("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", cartList.get(pos).getUserId(), cartList.get(pos).getId());
        call.enqueue(new Callback<CartApiModel>() {
            @Override
            public void onResponse(Call<CartApiModel> call, Response<CartApiModel> response) {
                PID = cartList.get(pos).getProductId();
                SIZE = cartList.get(pos).getSize();
                cartList.remove(pos);
                notifyDataSetChanged();
                SharedPreferences sharedPreferences = ctx.getSharedPreferences("iscart", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                myEdit.putString(PID + SIZE, "no");
                myEdit.commit();
                progress.dismiss();

            }

            @Override
            public void onFailure(Call<CartApiModel> call, Throwable t) {
                progress.dismiss();

            }
        });
    }
}
