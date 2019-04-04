package com.destinyapp.aplikasisdn07.Guru.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AbsensiAdapterGuru extends RecyclerView.Adapter<AbsensiAdapterGuru.HolderData> {

    private List<DataModel> mList;
    private Context ctx;
    private static String stat;
    private String[] listItems;

    public AbsensiAdapterGuru (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_absen_siswa_guru,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.nama.setText(dm.getNama_siswa());
        holderData.nis.setText(dm.getNis());


        String BASE_URL = "http://192.168.1.24/AplikasiSDN07/";
        String URL = BASE_URL+"ProfilePicture/Siswa/"+dm.getProfile_siswa();
        Glide.with(ctx)
                .load(URL)
                .into(holderData.PhotoSiswa);

        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nama,nis;
        ImageView PhotoSiswa;
        DataModel dm;
        Button status;
        public HolderData(View v){
            super(v);
            nama = (TextView)v.findViewById(R.id.tv_NamaSiswaGuru);
            nis = (TextView)v.findViewById(R.id.tv_NISGuru);
            PhotoSiswa = (ImageView)v.findViewById(R.id.ivPhotoSiswaGuru);
            status = (Button)v.findViewById(R.id.btnStatusGuru);
            //Toast.makeText(ctx,dm.getNis(),Toast.LENGTH_SHORT).show();
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setStatus(dm.getNip(),dm.getNis(),dm.getId_mapel(),dm.getId_kelas());
                    //CheckKeterangan(dm.getNis(),getThisDay(),dm.getId_kelas(),dm.getId_mapel());
                }
            });
        }
    }
    private String getThisDay(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String thisDay = dateFormat.format(date);
        return thisDay;
    }
    private void insertAbsensi(String nip, String nis, final String id_mapel, String tanggal_masuk, final String id_kelas, String status){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> insertAbsen = api.InsertAbsensi(nip,nis,id_mapel,status,tanggal_masuk,id_kelas);
        insertAbsen.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                String ress = response.body().getResponse();
                if (ress.equals("Insert")){
                    Toast.makeText(ctx,"Data Absensi Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                    succes(id_kelas,id_mapel);
                }else{
                    Toast.makeText(ctx,"Error Data Tidak berhasil Disimpan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Data Error When Insert Data",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateAbsensi(String nip, String nis, final String id_mapel, String tanggal_masuk, final String id_kelas, String status){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> updateAbsensi = api.UpdateSiswa(nip,nis,id_mapel,status,getThisDay(),id_kelas);
        updateAbsensi.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                String ress = response.body().getResponse();
                if (ress.equals("Update")){
                    Toast.makeText(ctx,"Data Absensi Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                    succes(id_kelas,id_mapel);
                }else{
                    Toast.makeText(ctx,"Error Data Tidak berhasil Disimpan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Data Error ketika Update Data",Toast.LENGTH_SHORT).show();;
            }
        });
    }
    private void getStatusInsert(final String nip, final String nis, final String id_mapel, final String tanggal_masuk, final String id_kelas){
        listItems = new String[]{"Masuk","Izin","Sakit","Tanpa Keterangan"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
        mBuilder.setTitle("Pilih Status Siswa");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String status = listItems[i];
                dialog.dismiss();
                insertAbsensi(nip,nis,id_mapel,tanggal_masuk,id_kelas,status);
            }
        });
        mBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
    private void getStatusUpdate(final String nip, final String nis, final String id_mapel, final String tanggal_masuk, final String id_kelas){
        listItems = new String[]{"Masuk","Izin","Sakit","Tanpa Keterangan"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
        mBuilder.setTitle("Pilih Status Siswa");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String status = listItems[i];
                dialog.dismiss();
                updateAbsensi(nip,nis,id_mapel,getThisDay(),id_kelas,status);
            }
        });
        mBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
    private void succes(String id_kelas,String id_mapel){
        Intent goInput = new Intent(ctx, MainGuruActivity.class);
        goInput.putExtra("id_kelas",id_kelas);
        goInput.putExtra("id_mapel",id_mapel);
        goInput.putExtra("Absensi","absensi");
        ctx.startActivities(new Intent[]{goInput});
    }
    private void setStatus(final String nis, final String nip, final String id_mapel, final String id_kelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> checkAbsensi = api.CheckAbsensi(nis,getThisDay(),id_mapel);
        checkAbsensi.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Ress = response.body().getResponse();
                if(Ress.equals("Iya")){
                    getStatusInsert(nip,nis,id_mapel,getThisDay(),id_kelas);
                }else{
                    getStatusUpdate(nip,nis,id_mapel,getThisDay(),id_kelas);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Check Data Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String CheckKeterangan(final String nis,final String tanggal_masuk,final String id_kelas,final String id_mapel){
        final String[] keterangan = {"Belum Diabsen"};
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> checkStatus = api.CheckStatusSiswa(nis,tanggal_masuk,id_mapel);
        checkStatus.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String ress = response.body().getResponse();
                if(ress.equals("succes")){
                    keterangan[0] = response.body().getStatus().toString();
                    setStat(keterangan[0]);

                }else{
                    keterangan[0] = null;
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                keterangan[0] = null;
                Toast.makeText(ctx,"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(ctx,getStat(),Toast.LENGTH_SHORT).show();
        return keterangan[0];
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
