package com.destinyapp.aplikasisdn07.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

public class AdapterDataGuruAdmin extends RecyclerView.Adapter<AdapterDataGuruAdmin.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterDataGuruAdmin (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_data_guru_admin,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataGuruAdmin.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.nip.setText(dm.getNip());
        holderData.nama.setText(dm.getNama());
        holderData.ttl.setText(dm.getTempatlahir()+","+dm.getTanggalahir());
        holderData.agama.setText(dm.getAgama());
        holderData.notelp.setText(dm.getNotelp());
        holderData.jabatan.setText(dm.getJabatan());
        holderData.jk.setText(dm.getJk());
        holderData.alamat.setText(dm.getAlamat());
        String BASE_URL = "http://192.168.0.23/AplikasiSDN07/";
        String URL = BASE_URL+"ProfilePicture/Guru/"+dm.getPictureguru();
        Glide.with(ctx)
                .load(URL)
                .into(holderData.PhotoAdmin);
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        ImageView PhotoAdmin;
        TextView nip,password,nama,ttl,agama,notelp,jabatan,pendidikan,jk,pictureguru,alamat;
        DataModel dm;
         HolderData(View v){
            super(v);
            nip = (TextView)v.findViewById(R.id.tvNipGuruAdmin);
            nama = (TextView)v.findViewById(R.id.tvNamaGuruAdmin);
            ttl = (TextView)v.findViewById(R.id.tvTTLGuruAdmin);
            agama = (TextView)v.findViewById(R.id.tvAgamaGuruAdmin);
            notelp = (TextView)v.findViewById(R.id.tvNoTelpGuruAdmin);
            jabatan = (TextView)v.findViewById(R.id.tvJabatanGuruAdmin);
            pendidikan = (TextView)v.findViewById(R.id.tvPendidikanGuruAdmin);
            jk = (TextView)v.findViewById(R.id.tvJKGuruAdmin);
            PhotoAdmin = (ImageView)v.findViewById(R.id.ivPhotoGuruAdmin);
            alamat = (TextView)v.findViewById(R.id.tvAlamatGuruAdmin);
        }
    }
    private void getImageFromURL(String URL){

    }

}