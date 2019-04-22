package com.destinyapp.aplikasisdn07.Models;

import java.util.List;

public class ResponseModel {
    String response,kode;
    String nip,password,nama,tempatlahir,tanggalahir,agama,notelp,jabatan,pendidikan,jk,pictureguru,alamat;
    String id_jadwal,id_kelas,id_mapel,hari,dari_jam,sampai_jam;
    String nama_kelas,tingkat_kelas;
    String nama_mapel,kelas;
    String status,tanggal_masuk;
    String nis,nama_siswa,jk_siswa,tahunajaran,namaibu,namaayah,pekerjaanibu,pekerjaanayah,profile_siswa;
    String nilai,verif;
    String izin,alpa,sakit;
    String username,nama_admin,profile_admin;


    List<DataModel> result;


    public List<DataModel> getResult(){
        return result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTempatlahir() {
        return tempatlahir;
    }

    public void setTempatlahir(String tempatlahir) {
        this.tempatlahir = tempatlahir;
    }

    public String getTanggallahir() {
        return tanggalahir;
    }

    public void setTanggallahir(String tanggallahir) {
        this.tanggalahir = tanggallahir;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getPictureguru() {
        return pictureguru;
    }

    public void setPictureguru(String pictureguru) {
        this.pictureguru = pictureguru;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setResult(List<DataModel> result) {
        this.result = result;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getId_mapel() {
        return id_mapel;
    }

    public void setId_mapel(String id_mapel) {
        this.id_mapel = id_mapel;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getDari_jam() {
        return dari_jam;
    }

    public void setDari_jam(String dari_jam) {
        this.dari_jam = dari_jam;
    }

    public String getSampai_jam() {
        return sampai_jam;
    }

    public void setSampai_jam(String sampai_jam) {
        this.sampai_jam = sampai_jam;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    public String getNama_mapel() {
        return nama_mapel;
    }

    public void setNama_mapel(String nama_mapel) {
        this.nama_mapel = nama_mapel;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(String tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public String getNama_siswa() {
        return nama_siswa;
    }

    public void setNama_siswa(String nama_siswa) {
        this.nama_siswa = nama_siswa;
    }

    public String getJk_siswa() {
        return jk_siswa;
    }

    public void setJk_siswa(String jk_siswa) {
        this.jk_siswa = jk_siswa;
    }

    public String getTahunajaran() {
        return tahunajaran;
    }

    public void setTahunajaran(String tahunajaran) {
        this.tahunajaran = tahunajaran;
    }

    public String getNamaibu() {
        return namaibu;
    }

    public void setNamaibu(String namaibu) {
        this.namaibu = namaibu;
    }

    public String getNamaayah() {
        return namaayah;
    }

    public void setNamaayah(String namaayah) {
        this.namaayah = namaayah;
    }

    public String getPekerjaanibu() {
        return pekerjaanibu;
    }

    public void setPekerjaanibu(String pekerjaanibu) {
        this.pekerjaanibu = pekerjaanibu;
    }

    public String getPekerjaanayah() {
        return pekerjaanayah;
    }

    public void setPekerjaanayah(String pekerjaanayah) {
        this.pekerjaanayah = pekerjaanayah;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getTanggalahir() {
        return tanggalahir;
    }

    public void setTanggalahir(String tanggalahir) {
        this.tanggalahir = tanggalahir;
    }

    public String getTingkat_kelas() {
        return tingkat_kelas;
    }

    public void setTingkat_kelas(String tingkat_kelas) {
        this.tingkat_kelas = tingkat_kelas;
    }

    public String getProfile_siswa() {
        return profile_siswa;
    }

    public void setProfile_siswa(String profile_siswa) {
        this.profile_siswa = profile_siswa;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getIzin() {
        return izin;
    }

    public void setIzin(String izin) {
        this.izin = izin;
    }

    public String getAlpa() {
        return alpa;
    }

    public void setAlpa(String alpa) {
        this.alpa = alpa;
    }

    public String getSakit() {
        return sakit;
    }

    public void setSakit(String sakit) {
        this.sakit = sakit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama_admin() {
        return nama_admin;
    }

    public void setNama_admin(String nama_admin) {
        this.nama_admin = nama_admin;
    }

    public String getProfile_admin() {
        return profile_admin;
    }

    public void setProfile_admin(String profile_admin) {
        this.profile_admin = profile_admin;
    }

    public String getVerif() {
        return verif;
    }

    public void setVerif(String verif) {
        this.verif = verif;
    }
}
