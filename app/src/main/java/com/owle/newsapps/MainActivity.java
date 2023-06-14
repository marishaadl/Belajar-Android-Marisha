package com.owle.newsapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.owle.newsapps.adapter.SubKategoriAdapter;
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

public class MainActivity extends AppCompatActivity {

    ProgressDialog pd;
    SessionManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();

        sm = new SessionManager(MainActivity.this);
        HashMap<String, String> map = sm.getDetailLogin();

        ImageView account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
            }
        });

        Button btn_login = findViewById(R.id.btn_login);

        if (map.get(SessionManager.KEY_ID_PENGUNJUNG)== null){
            btn_login.setText("Sign In");
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });

        } else {
            initSession();
            btn_login.setText("Sign Out");
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sm.logout();
                    sm.checkLogin();
                }
            });
        }

        LinearLayout akademik = findViewById(R.id.akademik);
        akademik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(MainActivity.this, NewsActivity.class);
                data.putExtra("id_sub_kategori", "15");
                data.putExtra("nama_sub_kategori", "Informasi Akademik");
                startActivity(data);
            }
        });

        LinearLayout informatika = findViewById(R.id.informatika);
        informatika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(MainActivity.this, NewsActivity.class);
                data.putExtra("id_sub_kategori", "14");
                data.putExtra("nama_sub_kategori", "Pendidikan Informatika");
                startActivity(data);
            }
        });

        LinearLayout olahraga = findViewById(R.id.olahraga);
        olahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(MainActivity.this, NewsActivity.class);
                data.putExtra("id_sub_kategori", "11");
                data.putExtra("nama_sub_kategori", "Pendidikan Olahraga");
                startActivity(data);
            }
        });

        LinearLayout pgsd = findViewById(R.id.pgsd);
        pgsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(MainActivity.this, NewsActivity.class);
                data.putExtra("id_sub_kategori", "13");
                data.putExtra("nama_sub_kategori", "Pendidikan Guru Sekolah Dasar");
                startActivity(data);
            }
        });

        LinearLayout seni = findViewById(R.id.seni);
        seni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(MainActivity.this, NewsActivity.class);
                data.putExtra("id_sub_kategori", "12");
                data.putExtra("nama_sub_kategori", "Pendidikan Seni Pertunjukan");
                startActivity(data);
            }
        });

        LinearLayout btn_entertain = findViewById(R.id.btn_entertain);
        btn_entertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SubKategoriActivity.class));
            }
        });

        LinearLayout about = findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });
    }


    private void initToolbar() {

        Tools.setSystemBarColor(this, R.color.blue_500);
    }

    private void initSession(){
        pd = new ProgressDialog(this);
        pd.setMessage("Tunggu Yaa...");
        pd.setCancelable(false);
        pd.show();

        sm = new SessionManager(MainActivity.this);
        HashMap<String, String> map = sm.getDetailLogin();

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseBody> getdata = api.showPengunjung(map.get(sm.KEY_ID_PENGUNJUNG));
        getdata.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                pd.dismiss();
                try {
                    String res = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(res);

                                TextView nama_pengunjung = findViewById(R.id.nama_pengunjung);
                                nama_pengunjung.setText(json.getJSONObject("data").getString("nama_pengunjung"));

                                TextView telepon_pengunjung = findViewById(R.id.telepon_pengunjung);
                                telepon_pengunjung.setText(json.getJSONObject("data").getString("telepon_pengunjung"));


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
                Toast.makeText(MainActivity.this, "Kamu Belum Login Nih", Toast.LENGTH_SHORT).show();
            }
        });
    }
}