package com.midhun.hawks_hcart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawks_hcart.FavActivity;
import com.midhun.hawks_hcart.MainActivity;
import com.midhun.hawks_hcart.MyOrderActivity;
import com.midhun.hawks_hcart.R;
import com.midhun.hawks_hcart.SettingsActivity;
import com.midhun.hawks_hcart.View.Profile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileAdapterViewHolder> {
    Context ctx;
    List<Profile> profileList;

    public ProfileAdapter(Context ctx, List<Profile> profileList) {
        this.ctx = ctx;
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public ProfileAdapter.ProfileAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.profile_list_view, parent, false);

        return new ProfileAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ProfileAdapterViewHolder holder, int position) {
        Profile profile = profileList.get(position);
        holder.name.setText(profile.getFirst_name());
        holder.email.setText(profile.getEmail());
        holder.img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, MainActivity.class));
            }
        });
        holder.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, SettingsActivity.class));
            }
        });

        holder.myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, MyOrderActivity.class));
            }
        });
        holder.myads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, FavActivity.class));
            }
        });
        holder.helpsupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
                selectorIntent.setData(Uri.parse("mailto:"));

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"address@mail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "The subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "The email body");
                emailIntent.setSelector( selectorIntent );

                ctx.startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    class ProfileAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name, email;
        ImageView img_back;

        LinearLayout settings,myorders,myads,helpsupport;

        public ProfileAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            img_back = itemView.findViewById(R.id.img_back);
            settings = itemView.findViewById(R.id.settings);
            myads=itemView.findViewById(R.id.myads);
            myorders=itemView.findViewById(R.id.myorders);
            helpsupport=itemView.findViewById(R.id.helpsupport);
        }
    }
}
