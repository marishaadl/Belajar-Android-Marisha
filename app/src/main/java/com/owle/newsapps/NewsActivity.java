package com.owle.newsapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.owle.newsapps.adapter.BeritaAdapter;
import com.owle.newsapps.adapter.SubKategoriAdapter;
import com.owle.newsapps.api.ApiRequest;
import com.owle.newsapps.api.Retroserver;
import com.owle.newsapps.model.Berita;
import com.owle.newsapps.model.ResponseBerita;
import com.owle.newsapps.model.ResponseSubKategori;
import com.owle.newsapps.model.SubKategori;
import com.owle.newsapps.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {


    ProgressDialog pd;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<Berita> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initToolbar();
        initContent();

    }

    private void initToolbar() {
        Intent data = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(data.getStringExtra("nama_sub_kategori"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_1000);
    }

    private void initContent() {
        Intent data = getIntent();
        pd = new ProgressDialog(this);
        mRecycler = findViewById(R.id.recyclerView);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(false);

        pd.setMessage("Tunggu Yaa...");
        pd.setCancelable(false);
        pd.show();

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseBerita> getdata = api.showBeritaSubKategori(data.getStringExtra("id_sub_kategori"));
        getdata.enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                pd.dismiss();
                mItems = response.body().getData();
                mAdapter = new BeritaAdapter(NewsActivity.this,mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(NewsActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_searchs, menu);
        Tools.changeMenuIconColor(menu, Color.WHITE);
        return true;
    }
}