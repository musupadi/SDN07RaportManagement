package com.destinyapp.aplikasisdn07.Guru.Adapter;

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

public class AdapterMengajarGuru extends RecyclerView.Adapter<AdapterMengajarGuru.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterMengajarGuru (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_jadwal_mengajar_guru,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.namaKelas.setText(dm.getNama_kelas());
        holderData.mataPelajaran.setText(dm.getNama_mapel());
        holderData.dariJam.setText(dm.getDari_jam());
        holderData.sampaiJam.setText(dm.getSampai_jam());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView namaKelas,mataPelajaran,dariJam,sampaiJam;
        DataModel dm;
        public HolderData(View v){
            super(v);
            namaKelas = (TextView)v.findViewById(R.id.tvNamaKelas);
            mataPelajaran = (TextView)v.findViewById(R.id.tvMataPelajaranGuru);
            dariJam = (TextView)v.findViewById(R.id.tvDariJam);
            sampaiJam = (TextView)v.findViewById(R.id.tvSampaiJam);
        }
    }

}
