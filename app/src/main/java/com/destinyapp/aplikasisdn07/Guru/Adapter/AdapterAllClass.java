package com.destinyapp.aplikasisdn07.Guru.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

public class AdapterAllClass extends RecyclerView.Adapter<AdapterAllClass.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterAllClass (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pilih_kelas_guru,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.namaKelas.setText(dm.getNama_kelas());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView namaKelas;
        DataModel dm;
        public HolderData(View v){
            super(v);
            namaKelas = (TextView)v.findViewById(R.id.tvNamaKelasPilihGuru);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, MainGuruActivity.class);
                    goInput.putExtra("id_kelas",dm.getId_kelas());
                    goInput.putExtra("Kelas","kelas");
                    ctx.startActivities(new Intent[]{goInput});
                }
            });
        }
    }

}