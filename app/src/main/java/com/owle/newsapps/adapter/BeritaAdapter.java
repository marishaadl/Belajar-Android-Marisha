package com.owle.newsapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.owle.newsapps.R;
import com.owle.newsapps.model.Berita;
import com.owle.newsapps.model.SubKategori;

import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.HolderData> {

    private List<Berita> mList;
    private Context ctx;

    public BeritaAdapter(Context ctx, List<Berita> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public BeritaAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        BeritaAdapter.HolderData holder = new BeritaAdapter.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaAdapter.HolderData holder, int position) {
        Berita dm = mList.get(position);
        holder.judul_berita.setText(dm.getJudul_berita());
        holder.lokasi_berita.setText(dm.getLokasi_berita());
        holder.nama_sub_kategori.setText(dm.getNama_sub_kategori());

        final String url_images = "https://ujk.yoabsen.com/images/";
        final String dataImage = url_images.concat(dm.getGambar());
        Glide.with(ctx).load(dataImage).apply(new RequestOptions()).into(holder.gambar);

        holder.dm = dm;

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        ImageView gambar;
        TextView lokasi_berita, judul_berita, nama_sub_kategori;
        MaterialRippleLayout lyt_parent;

        Berita dm;

        public HolderData(@NonNull View itemView) {

            super(itemView);
            gambar = itemView.findViewById(R.id.gambar);
            lokasi_berita = itemView.findViewById(R.id.lokasi_berita);
            judul_berita = itemView.findViewById(R.id.judul_berita);
            nama_sub_kategori = itemView.findViewById(R.id.nama_sub_kategori);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
        }
    }
}
