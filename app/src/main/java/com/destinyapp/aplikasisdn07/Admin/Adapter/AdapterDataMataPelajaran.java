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

public class AdapterDataMataPelajaran extends RecyclerView.Adapter<AdapterDataMataPelajaran.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterDataMataPelajaran(Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pelajaran_row,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataMataPelajaran.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.NamaMapel.setText(dm.getNama_mapel());
        holderData.TingkatMapel.setText(dm.getTingkat_kelas());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView NamaMapel,TingkatMapel;
        DataModel dm;
        HolderData(View v){
            super(v);
            NamaMapel = (TextView)v.findViewById(R.id.tvNamaMapel);
            TingkatMapel = (TextView)v.findViewById(R.id.tvTingkatPelajaran);
        }
    }
}
