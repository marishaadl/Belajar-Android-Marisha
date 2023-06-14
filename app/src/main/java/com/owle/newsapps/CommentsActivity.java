package com.owle.newsapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
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

public class CommentsActivity extends AppCompatActivity {

    ProgressDialog pd;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initToolbar();
    }

    private void storeKomentar(){
        Intent data = getIntent();
        sm = new SessionManager(CommentsActivity.this);
        HashMap<String, String> map = sm.getDetailLogin();

        pd = new ProgressDialog(this);
        pd.setMessage("Tunggu Yaa...");
        pd.setCancelable(false);
        pd.show();

        AppCompatEditText isi_komentar = findViewById(R.id.isi_komentar);
        AppCompatEditText rating_komentar = findViewById(R.id.rating_komentar);
        rating_komentar.setEnabled(false);

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseBody> act = api.storeKomentar(data.getStringExtra("id_berita"), map.get(sm.KEY_ID_PENGUNJUNG), isi_komentar.getText().toString(), rating_komentar.getText().toString());

        act.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pd.dismiss();
                try {
                    String res = response.body().string();

                    CommentsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(res);

                                String code = json.getString("code");
                                if (code.equals("200")){
                                    Toast.makeText(CommentsActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                    isi_komentar.getText().clear();
                                    rating_komentar.getText().clear();
                                } else {
                                    Toast.makeText(CommentsActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CommentsActivity.this, "Server Maintenance", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        Intent data = getIntent();
        TextView judul_berita = findViewById(R.id.judul_berita);
        judul_berita.setText(data.getStringExtra("judul_berita"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_save){
            storeKomentar();
        }
        return super.onOptionsItemSelected(item);
    }
}