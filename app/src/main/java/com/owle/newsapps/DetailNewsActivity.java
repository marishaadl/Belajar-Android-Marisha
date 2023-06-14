package com.owle.newsapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.owle.newsapps.api.ApiRequest;
import com.owle.newsapps.api.Retroserver;
import com.owle.newsapps.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailNewsActivity extends AppCompatActivity {

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        initToolbar();
        initContent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    private void initContent() {
        Intent data = getIntent();

        pd = new ProgressDialog(this);
        pd.setMessage("Tunggu Yaa...");
        pd.setCancelable(false);
        pd.show();

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseBody> getdata = api.showBerita(data.getStringExtra("id_berita"));
        getdata.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                pd.dismiss();
                try {
                    String res = response.body().string();

                    DetailNewsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(res);

                                TextView nama_sub_kategori = findViewById(R.id.nama_sub_kategori);
                                nama_sub_kategori.setText(json.getJSONObject("data").getString("nama_sub_kategori"));

                                TextView nama_kategori = findViewById(R.id.nama_kategori);
                                nama_kategori.setText(json.getJSONObject("data").getString("nama_kategori"));

                                TextView judul_berita = findViewById(R.id.judul_berita);
                                judul_berita.setText(json.getJSONObject("data").getString("judul_berita"));

                                TextView short_desc_berita = findViewById(R.id.short_desc_berita);
                                short_desc_berita.setText(json.getJSONObject("data").getString("short_desc_berita"));

                                TextView isi_berita = findViewById(R.id.isi_berita);
                                isi_berita.setText(json.getJSONObject("data").getString("isi_berita"));

                                final String url_image = "https://apps.stkiprosaliametro.ac.id/images/";
                                final String dataImage = url_image.concat(json.getJSONObject("data").getString("gambar"));

                                new  DownloadImageFromInternet((ImageView) findViewById(R.id.gambar)).execute(dataImage);

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
                Toast.makeText(DetailNewsActivity.this, "Data Tidak Ditemukan Nih", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent data = getIntent();

        if (item.getItemId()==R.id.action_comment){
            Intent data_intent = new Intent(DetailNewsActivity.this, CommentsActivity.class);
            data_intent.putExtra("id_berita", data.getStringExtra("id_berita"));
            data_intent.putExtra("judul_berita", data.getStringExtra("judul_berita"));
            startActivity(data_intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            //Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}