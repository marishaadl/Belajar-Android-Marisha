package com.owle.newsapps.model;

public class SubKategori {

    String id_sub_kategori, id_kategori, nama_sub_kategori, keterangan_sub_kategori, created_at, updated_at;

    public String getId_sub_kategori() {
        return id_sub_kategori;
    }

    public void setId_sub_kategori(String id_sub_kategori) {
        this.id_sub_kategori = id_sub_kategori;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_sub_kategori() {
        return nama_sub_kategori;
    }

    public void setNama_sub_kategori(String nama_sub_kategori) {
        this.nama_sub_kategori = nama_sub_kategori;
    }

    public String getKeterangan_sub_kategori() {
        return keterangan_sub_kategori;
    }

    public void setKeterangan_sub_kategori(String keterangan_sub_kategori) {
        this.keterangan_sub_kategori = keterangan_sub_kategori;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
