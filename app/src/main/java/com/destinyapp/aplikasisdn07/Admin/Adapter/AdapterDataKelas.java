package com.destinyapp.aplikasisdn07.Admin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

public class AdapterDataKelas extends RecyclerView.Adapter<AdapterDataKelas.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterDataKelas(Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_kelas_row,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataKelas.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.NamaKelas.setText(dm.getNama_kelas());
        holderData.TingkatKelas.setText(dm.getTingkat_kelas());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView NamaKelas,TingkatKelas;
        DataModel dm;
        HolderData(View v){
            super(v);
            NamaKelas = (TextView)v.findViewById(R.id.tvNamaKelas);
            TingkatKelas = (TextView)v.findViewById(R.id.tvTingkatKelas);
        }
    }
}

