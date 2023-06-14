package com.owle.newsapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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

public class FormUpdateAccountActivity extends AppCompatActivity {

    ProgressDialog pd;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_update_account);

        initToolbar();
        initSession();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Account Pengunjung");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.blue_400);
    }

    private void initSession(){
        pd = new ProgressDialog(this);
        pd.setMessage("Tunggu Yaa...");
        pd.setCancelable(false);
        pd.show();

        sm = new SessionManager(FormUpdateAccountActivity.this);
        HashMap<String, String> map = sm.getDetailLogin();

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseBody> getdata = api.showPengunjung(map.get(sm.KEY_ID_PENGUNJUNG));
        getdata.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                pd.dismiss();
                try {
                    String res = response.body().string();

                    FormUpdateAccountActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(res);

                                AppCompatEditText nama_pengunjung = findViewById(R.id.nama_pengunjung);
                                nama_pengunjung.setText(json.getJSONObject("data").getString("nama_pengunjung"));

                                AppCompatEditText telepon_pengunjung = findViewById(R.id.telepon_pengunjung);
                                telepon_pengunjung.setText(json.getJSONObject("data").getString("telepon_pengunjung"));

                                AppCompatEditText email_pengunjung = findViewById(R.id.email_pengunjung);
                                email_pengunjung.setText(json.getJSONObject("data").getString("email_pengunjung"));

                                AppCompatEditText password = findViewById(R.id.password);
                                password.setText(json.getJSONObject("data").getString("password"));

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
                Toast.makeText(FormUpdateAccountActivity.this, "Kamu Belum Login Nih", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        sm = new SessionManager(FormUpdateAccountActivity.this);
        HashMap<String, String> map = sm.getDetailLogin();

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
            Call<ResponseBody> act = api.updatePengunjung(nama_pengunjung.getText().toString(), telepon_pengunjung.getText().toString(), email_pengunjung.getText().toString(), password.getText().toString(), map.get(sm.KEY_ID_PENGUNJUNG));

            act.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    pd.dismiss();
                    try {
                        String res = response.body().string();

                        FormUpdateAccountActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject json = new JSONObject(res);

                                    String code = json.getString("code");
                                    if (code.equals("200")){
                                        Toast.makeText(FormUpdateAccountActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(FormUpdateAccountActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(FormUpdateAccountActivity.this, "Server Maintenance", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}