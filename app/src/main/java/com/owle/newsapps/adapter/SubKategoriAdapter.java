package com.owle.newsapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.owle.newsapps.DetailNewsActivity;
import com.owle.newsapps.NewsActivity;
import com.owle.newsapps.R;
import com.owle.newsapps.model.SubKategori;

import java.util.List;

public class SubKategoriAdapter extends RecyclerView.Adapter<SubKategoriAdapter.HolderData> {

    private List<SubKategori> mList;
    private Context ctx;

    public SubKategoriAdapter(Context ctx, List<SubKategori> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public SubKategoriAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_category_image,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubKategoriAdapter.HolderData holder, int position) {
        SubKategori dm = mList.get(position);
        holder.nama_sub_kategori.setText(dm.getNama_sub_kategori());
        holder.keterangan_sub_kategori.setText(dm.getKeterangan_sub_kategori());
        holder.dm = dm;
    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView nama_sub_kategori;
        Button keterangan_sub_kategori;
        LinearLayout lyt_parent;

        SubKategori dm;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            nama_sub_kategori = itemView.findViewById(R.id.nama_sub_kategori);
            keterangan_sub_kategori = itemView.findViewById(R.id.keterangan_sub_kategori);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);

            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data = ((Activity) ctx).getIntent();
                    Intent goInput = new Intent(ctx, NewsActivity.class);
                    goInput.putExtra("id_sub_kategori", dm.getId_sub_kategori());
                    goInput.putExtra("nama_sub_kategori", dm.getNama_sub_kategori());
                    ctx.startActivity(goInput);
                }
            });
        }
    }
}
