package com.owle.newsapps.api;

import com.owle.newsapps.model.ResponseBerita;
import com.owle.newsapps.model.ResponseSubKategori;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequest {

    @GET("api/sub-kategori/kategori/3")
    Call<ResponseSubKategori> allSubKategori();

    @GET("api/berita/{id_sub_kategori}")
    Call<ResponseBerita> showBeritaSubKategori(@Path("id_sub_kategori")String id_sub_kategori);

    @GET("api/berita/show/{id_berita}")
    Call<ResponseBody> showBerita(@Path("id_berita")String id_berita);

    @FormUrlEncoded
    @POST("api/pengunjung/store")
    Call<ResponseBody> storePengunjung(@Field("nama_pengunjung") String nama_pengunjung,
                                       @Field("telepon_pengunjung") String telepon_pengunjung,
                                       @Field("email_pengunjung") String email_pengunjung,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("api/komentar/store")
    Call<ResponseBody> storeKomentar(@Field("id_berita") String id_berita,
                                     @Field("id_pengunjung") String id_pengunjung,
                                     @Field("isi_komentar") String isi_komentar,
                                     @Field("rating_komentar") String rating_komentar);

    @FormUrlEncoded
    @POST("api/pengunjung/update/{id_pengunjung}")
    Call<ResponseBody> updatePengunjung(@Field("nama_pengunjung") String nama_pengunjung,
                                        @Field("telepon_pengunjung") String telepon_pengunjung,
                                        @Field("email_pengunjung") String email_pengunjung,
                                        @Field("password") String password,
                                        @Path("id_pengunjung")String id_pengunjung);

    @FormUrlEncoded
    @POST("api/pengunjung/auth")
    Call<ResponseBody> authPengunjung(@Field("email_pengunjung") String email_pengunjung,
                                      @Field("password") String password);

    @GET("api/pengunjung/show/{id_pengunjung}")
    Call<ResponseBody> showPengunjung(@Path("id_pengunjung")String id_pengunjung);


}
