package com.owle.newsapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.owle.newsapps.api.ApiRequest;
import com.owle.newsapps.api.Retroserver;
import com.owle.newsapps.utils.SessionManager;
import com.owle.newsapps.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog pd;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View parent_view = findViewById(android.R.id.content);

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

        pd = new ProgressDialog(LoginActivity.this);
        sm = new SessionManager(LoginActivity.this);
        pd.setMessage("Tunggu Yaa ...");

        TextInputEditText email_pengunjung = findViewById(R.id.email_pengunjung);
        TextInputEditText password = findViewById(R.id.password);

        Button btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<ResponseBody> login = api.authPengunjung(email_pengunjung.getText().toString(), password.getText().toString());

                login.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pd.dismiss();

                        try {
                            String res = response.body().string();

                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject json = new JSONObject(res);

                                        String code = json.getString("code");
                                        if (code.equals("200")){
                                            Toast.makeText(LoginActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();

                                            sm.storeLogin(json.getJSONObject("data").getString("id_pengunjung"));
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                        } else {
                                            Toast.makeText(LoginActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Server Maintenance", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        TextView btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}