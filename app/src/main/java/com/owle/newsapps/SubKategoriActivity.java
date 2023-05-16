package com.owle.newsapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.owle.newsapps.adapter.SubKategoriAdapter;
import com.owle.newsapps.api.ApiRequest;
import com.owle.newsapps.api.Retroserver;
import com.owle.newsapps.model.ResponseSubKategori;
import com.owle.newsapps.model.SubKategori;
import com.owle.newsapps.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubKategoriActivity extends AppCompatActivity {

    ProgressDialog pd;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<SubKategori> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_kategori);
        View parent_view = findViewById(R.id.parent_view);

        initToolbar();
        initContent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Entertain");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_1000);
    }

    private void initContent(){
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
        Call<ResponseSubKategori> getdata;

        getdata = api.allSubKategori();
        getdata.enqueue(new Callback<ResponseSubKategori>() {
            @Override
            public void onResponse(Call<ResponseSubKategori> call, Response<ResponseSubKategori> response) {
                pd.dismiss();
                mItems = response.body().getData();
                mAdapter = new SubKategoriAdapter(SubKategoriActivity.this,mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseSubKategori> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(SubKategoriActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();

            }
        });

    }
}