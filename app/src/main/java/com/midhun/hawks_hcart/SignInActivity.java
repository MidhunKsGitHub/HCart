package com.midhun.hawks_hcart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.Util.MidhunUtils;
import com.midhun.hawks_hcart.View.UserModelSignIn;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {
    LinearLayout bg_email, bg_password;
    TextView login, btn,forget;
    EditText username, password;
    List<UserModelSignIn> userModelSignInList;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bg_email = findViewById(R.id.bg_email);
        bg_password = findViewById(R.id.bg_password);
        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.btn);
        forget=findViewById(R.id.forget);
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(SignInActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(SignInActivity.this, true);
        MidhunUtils.round(bg_email, 0xffeceff1, 0xffeceff1, 15f);
        MidhunUtils.round(bg_password, 0xffeceff1, 0xffeceff1, 15f);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.putExtra("forget","yes");
                in.setClass(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(in);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty()) {
                    username.requestFocus();
                    username.setError("Required");
                } else if (password.getText().toString().isEmpty()) {
                    password.requestFocus();
                    password.setError("Requiored");
                } else {
                    progress = ProgressDialog.show(SignInActivity.this, null, null, true);
                    progress.setContentView(R.layout.progress_layout);
                    progress.setCancelable(false);
                    progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    progress.show();
                    SignIn();
                }
            }
        });
    }

    private void SignIn() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<UserModelSignIn>> call = api.userSignIn("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", username.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<List<UserModelSignIn>>() {
            @Override
            public void onResponse(Call<List<UserModelSignIn>> call, Response<List<UserModelSignIn>> response) {
                progress.dismiss();
                if (response.code() == 200) {
                    userModelSignInList = response.body();

                    if (userModelSignInList.get(0).getType().equalsIgnoreCase("invalid")) {
                        Toast.makeText(SignInActivity.this, userModelSignInList.get(0).getError(), Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("UID", userModelSignInList.get(0).getId());
                        myEdit.putString("USERNAMER", userModelSignInList.get(0).getUsername());
                        myEdit.commit();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        Toast.makeText(SignInActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(SignInActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserModelSignIn>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(SignInActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}