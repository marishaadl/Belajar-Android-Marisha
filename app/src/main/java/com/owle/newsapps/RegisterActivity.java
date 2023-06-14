package com.owle.newsapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.owle.newsapps.api.ApiRequest;
import com.owle.newsapps.api.Retroserver;
import com.owle.newsapps.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolbar();


    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registrasi Pengunjung");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.blue_400);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AppCompatEditText nama_pengunjung = findViewById(R.id.nama_pengunjung);
        AppCompatEditText telepon_pengunjung = findViewById(R.id.telepon_pengunjung);
        AppCompatEditText email_pengunjung = findViewById(R.id.email_pengunjung);
        AppCompatEditText password = findViewById(R.id.password);

        if (item.getItemId()==R.id.action_save){
            pd = new ProgressDialog(this);
            pd.setMessage("Tunggu Yaa...");
            pd.setCancelable(false);
            pd.show();

            ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
            Call<ResponseBody> act = api.storePengunjung(nama_pengunjung.getText().toString(), telepon_pengunjung.getText().toString(), email_pengunjung.getText().toString(), password.getText().toString());

            act.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    pd.dismiss();
                    try {
                        String res = response.body().string();

                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject json = new JSONObject(res);

                                    String code = json.getString("code");
                                    if (code.equals("200")){
                                        Toast.makeText(RegisterActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                        nama_pengunjung.getText().clear();
                                        telepon_pengunjung.getText().clear();
                                        email_pengunjung.getText().clear();
                                        password.getText().clear();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
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
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Server Maintenance", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

}