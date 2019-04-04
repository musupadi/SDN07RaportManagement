package com.destinyapp.aplikasisdn07.Guru.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

public class AdapterFinalRaport extends RecyclerView.Adapter<AdapterFinalRaport.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterFinalRaport (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_nilai,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.nilai.setText(dm.getNilai());
        int nilai = Integer.parseInt(dm.getNilai());
        String grade = "E";
        if(nilai >= 90){
            grade = "A";
        }else if(nilai>=80 && nilai<90){
            grade = "B";
        }else if(nilai>=70 && nilai<80){
            grade = "C";
        }else if(nilai>=60 && nilai<70){
            grade = "D";
        }else{
            grade = "E";
        }
        holderData.grade.setText(grade);
        holderData.mapel.setText(dm.getNama_mapel());
        holderData.namaGuru.setText(dm.getNama());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class HolderData extends RecyclerView.ViewHolder{
        TextView nilai,grade,namaGuru,mapel;
        DataModel dm;
        public HolderData(View v){
            super(v);
            nilai = (TextView)v.findViewById(R.id.tvNilaiFinalGuru);
            grade = (TextView)v.findViewById(R.id.tvGradeFinalGuru);
            namaGuru = (TextView)v.findViewById(R.id.tvNamaGuruFinalGuru);
            mapel = (TextView)v.findViewById(R.id.tvMapelFinalGuru);
        }
    }

}
