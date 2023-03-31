package com.midhun.hawks_hcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.midhun.hawks_hcart.Config.Api;
import com.midhun.hawks_hcart.Model.ChangePwdApiModel;
import com.midhun.hawks_hcart.Util.MidhunUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {
    TextView btn;
    EditText edt_pwd, edt_cpwd;
    String UID;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edt_cpwd = findViewById(R.id.edt_cpwd);
        edt_pwd = findViewById(R.id.edt_pwd);
        btn = findViewById(R.id.btn);
        img_back = findViewById(R.id.img_back);
        getSupportActionBar().hide();
        UID = MidhunUtils.localData(ChangePasswordActivity.this, "login", "UID");

        MidhunUtils.setStatusBarIcon(ChangePasswordActivity.this, true);
        MidhunUtils.changeStatusBarColor(ChangePasswordActivity.this, R.color.white);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_pwd.getText().toString().isEmpty()) {
                    edt_pwd.setError("Required");
                } else if (edt_cpwd.getText().toString().isEmpty()) {
                    edt_cpwd.setError("Required");
                } else if (!edt_cpwd.getText().toString().equals(edt_pwd.getText().toString())) {
                    Toast.makeText(ChangePasswordActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                } else {
                    ProgressDialog progress = ProgressDialog.show(ChangePasswordActivity.this, null, null, true);
                    progress.setContentView(R.layout.progress_layout);
                    progress.setCancelable(false);
                    progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    progress.show();
                    changePassword();
                }
            }
        });
    }

    private void changePassword() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ChangePwdApiModel> call = api.changePassword("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", UID, edt_cpwd.getText().toString());
        call.enqueue(new Callback<ChangePwdApiModel>() {
            @Override
            public void onResponse(Call<ChangePwdApiModel> call, Response<ChangePwdApiModel> response) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(ChangePasswordActivity.this, "Password Update succesfully", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ChangePwdApiModel> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Cannot update password", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

}