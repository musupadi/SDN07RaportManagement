package com.destinyapp.aplikasisdn07.API;

import com.destinyapp.aplikasisdn07.Models.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface  ApiRequest {
    @FormUrlEncoded
    @POST("GuruLogin.php")
    Call<ResponseModel> loginGuru(@Field("username") String username,
                                  @Field("password") String password);
    @FormUrlEncoded
    @POST("DataGuru.php")
    Call<ResponseModel> dataGuru(@Field("username") String username);

    @FormUrlEncoded
    @POST("JadwalMengajarGuru.php")
    Call<ResponseModel> getJadwalMengajarGuru(@Field("username") String username,
                                              @Field("hari_ini") String hari_ini);

    @FormUrlEncoded
    @POST("jadwalBelajarSiswa.php")
    Call<ResponseModel> getJadwalBelajarSiswa(@Field("username") String username,
                                              @Field("hari_ini") String hari_ini);

    @FormUrlEncoded
    @POST("GetDataSiswaKelasAbsensi.php")
    Call<ResponseModel> getKelas(@Field("id_jadwal") String id_jadwal);

    @FormUrlEncoded
    @POST("getDataSiswaAbsensi.php")
    Call<ResponseModel> getSiswa(@Field("id_kelas") String id_kelas,
                                 @Field("id_mapel") String id_mapel);

    @FormUrlEncoded
    @POST("CheckAbsen.php")
    Call<ResponseModel> CheckAbsensi(@Field("nis") String nis,
                                 @Field("tanggal_masuk") String tanggal_masuk,
                                     @Field("id_mapel") String id_mapel);

    @FormUrlEncoded
    @POST("InsertAbsensiSiswa.php")
    Call<ResponseModel> InsertAbsensi(@Field("nip") String nip,
                                     @Field("nis") String nis,
                                      @Field("id_mapel") String id_mapel,
                                      @Field("status") String status,
                                      @Field("tanggal_masuk") String tanggal_masuk,
                                      @Field("id_kelas") String id_kelas);

    @FormUrlEncoded
    @POST("CheckStatusSiswa.php")
    Call<ResponseModel> CheckStatusSiswa(@Field("nis") String nis,
                                     @Field("tanggal_masuk") String tanggal_masuk,
                                     @Field("id_mapel") String id_mapel);

    @FormUrlEncoded
    @POST("UpdateAbsensiSiswa.php")
    Call<ResponseModel> UpdateSiswa(@Field("nip") String nip,
                                    @Field("nis") String nis,
                                    @Field("id_mapel") String id_mapel,
                                    @Field("status") String status,
                                    @Field("tanggal_masuk") String tanggal_masuk,
                                    @Field("id_kelas") String id_kelas);


    @GET("getAllClass.php")
    Call<ResponseModel> getKelas();

    @GET("getAllDataGuru.php")
    Call<ResponseModel> getAllDataGuru();

    @FormUrlEncoded
    @POST("getAllSiswaFromClassGuru.php")
    Call<ResponseModel> getAllSiswaFromGuru(@Field("id_kelas") String id_kelas);

    @FormUrlEncoded
    @POST("getNamaKelas.php")
    Call<ResponseModel> getNamaKelas(@Field("id_kelas") String id_kelas);

    @FormUrlEncoded
    @POST("getMapel.php")
    Call<ResponseModel> getMapel(@Field("tingkat_kelas") String tingkat_kelas);

    @FormUrlEncoded
    @POST("getIDMapel.php")
    Call<ResponseModel> getIDMapel(@Field("nama_mapel") String nama_mapel);

    @FormUrlEncoded
    @POST("CheckNilaiSiswa.php")
    Call<ResponseModel> checkNilaiSiswa(@Field("nis") String nis,
                                        @Field("id_mapel") String id_mapel);

    @FormUrlEncoded
    @POST("InsertNilaiSiswa.php")
    Call<ResponseModel> insertNilaiSiswa(@Field("nis") String nis,
                                        @Field("nip") String nip,
                                         @Field("nilai") String nilai,
                                         @Field("id_mapel") String id_mapel);

    @FormUrlEncoded
    @POST("UpdateNilaiSiswa.php")
    Call<ResponseModel> updateNilaiSiswa(@Field("nis") String nis,
                                         @Field("nilai") String nilai,
                                         @Field("id_mapel") String id_mapel);

    @FormUrlEncoded
    @POST("getNilai.php")
    Call<ResponseModel> GetNilai(@Field("nis") String nis,
                                         @Field("id_mapel") String id_mapel);

    @FormUrlEncoded
    @POST("getIDKelas.php")
    Call<ResponseModel> GetIDKelas(@Field("nama_kelas") String nama_kelas);

    @FormUrlEncoded
    @POST("getDataSiswa.php")
    Call<ResponseModel> getDataSiswa(@Field("nis") String nis);

    @FormUrlEncoded
    @POST("getSiswa.php")
    Call<ResponseModel> getSiswaData(@Field("nis") String nis);

    @FormUrlEncoded
    @POST("getNilaiSiswa.php")
    Call<ResponseModel> getNilaiSiswa(@Field("nis") String nis);

    @FormUrlEncoded
    @POST("getDataSiswa.php")
    Call<ResponseModel> getLoginSiswa(@Field("nis") String nis);

    @FormUrlEncoded
    @POST("getRaport.php")
    Call<ResponseModel> getRaportData(@Field("nis") String nis);

    @FormUrlEncoded
    @POST("AdminLogin.php")
    Call<ResponseModel> getAdminLogin(@Field("username") String username,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("getDataAdmin.php")
    Call<ResponseModel> getDataAdmin(@Field("username") String username);

    @FormUrlEncoded
    @POST("InsertDataGuru.php")
    Call<ResponseModel> insrertDataGuru(@Field("nip") String nip,
                                        @Field("password") String password,
                                        @Field("nama") String nama,
                                        @Field("tempatlahir") String tempatlahir,
                                        @Field("tanggalahir") String tanggalahir,
                                        @Field("agama") String agama,
                                        @Field("notelp") String notelp,
                                        @Field("jabatan") String jabatan,
                                        @Field("pendidikan") String pendidikan,
                                        @Field("jk") String jk,
                                        @Field("pictureguru") String pictureguru,
                                        @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("InsertDataSiswa.php")
    Call<ResponseModel> insertDataSiswa(@Field("nis") String nis,
                                        @Field("nama_siswa") String nama_siswa,
                                        @Field("jk_siswa") String jk_siswa,
                                        @Field("tahunajaran") String tahunajaran,
                                        @Field("namaibu") String namaibu,
                                        @Field("namaayah") String namaayah,
                                        @Field("pekerjaanayah") String pekerjaanayah,
                                        @Field("pekerjaanibu") String pekerjaanibu,
                                        @Field("id_kelas") String id_kelas,
                                        @Field("profile_siswa") String profile_siswa);

    @FormUrlEncoded
    @POST("InsertDataJadwal.php")
    Call<ResponseModel> insertDataJadwal(@Field("nip") String nip,
                                        @Field("id_kelas") String id_kelas,
                                        @Field("id_mapel") String id_mapel,
                                        @Field("hari") String hari,
                                        @Field("dari_jam") String dari_jam,
                                        @Field("sampai_jam") String sampai_jam);

    @FormUrlEncoded
    @POST("InsertDataKelas.php")
    Call<ResponseModel> insertDataKelas(@Field("nama_kelas") String nama_kelas,
                                        @Field("tingkat_kelas") String tingkat_kelas);

    @FormUrlEncoded
    @POST("InsertDataMapel.php")
    Call<ResponseModel> insertDataMapel(@Field("nama_mapel") String nama_mapel,
                                        @Field("tingkat_kelas") String tingkat_kelas);

    @FormUrlEncoded
    @POST("getGuruFromNama.php")
    Call<ResponseModel> getGuru(@Field("nama") String nama,
                                        @Field("nip") String nip);
}
