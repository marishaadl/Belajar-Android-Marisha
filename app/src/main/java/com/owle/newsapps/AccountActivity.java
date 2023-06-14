package com.owle.newsapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.owle.newsapps.api.ApiRequest;
import com.owle.newsapps.api.Retroserver;
import com.owle.newsapps.utils.SessionManager;
import com.owle.newsapps.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    ProgressDialog pd;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initToolbar();
        initSession();

        FloatingActionButton edit_account = findViewById(R.id.edit_account);
        edit_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, FormUpdateAccountActivity.class));
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.purple_600);
    }

    private void initSession(){
        pd = new ProgressDialog(this);
        pd.setMessage("Tunggu Yaa...");
        pd.setCancelable(false);
        pd.show();

        sm = new SessionManager(AccountActivity.this);
        HashMap<String, String> map = sm.getDetailLogin();

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseBody> getdata = api.showPengunjung(map.get(sm.KEY_ID_PENGUNJUNG));
        getdata.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                pd.dismiss();
                try {
                    String res = response.body().string();

                    AccountActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(res);

                                TextView nama_pengunjung = findViewById(R.id.nama_pengunjung);
                                nama_pengunjung.setText(json.getJSONObject("data").getString("nama_pengunjung"));

                                TextView telepon_pengunjung = findViewById(R.id.telepon_pengunjung);
                                telepon_pengunjung.setText(json.getJSONObject("data").getString("telepon_pengunjung"));

                                TextView email_pengunjung = findViewById(R.id.email_pengunjung);
                                email_pengunjung.setText(json.getJSONObject("data").getString("email_pengunjung"));

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
                Toast.makeText(AccountActivity.this, "Kamu Belum Login Nih", Toast.LENGTH_SHORT).show();
            }
        });
    }
}