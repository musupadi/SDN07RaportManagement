package com.destinyapp.aplikasisdn07.Siswa.Adapter;

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

public class AdapterJadwalBelajarSiswa extends RecyclerView.Adapter<com.destinyapp.aplikasisdn07.Siswa.Adapter.AdapterJadwalBelajarSiswa.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterJadwalBelajarSiswa (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public com.destinyapp.aplikasisdn07.Siswa.Adapter.AdapterJadwalBelajarSiswa.HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_jadwal_belajar_siswa,viewGroup,false);
        com.destinyapp.aplikasisdn07.Siswa.Adapter.AdapterJadwalBelajarSiswa.HolderData holder = new com.destinyapp.aplikasisdn07.Siswa.Adapter.AdapterJadwalBelajarSiswa.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.destinyapp.aplikasisdn07.Siswa.Adapter.AdapterJadwalBelajarSiswa.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.namaKelas.setText(dm.getNama_kelas());
        holderData.mataPelajaran.setText(dm.getNama_mapel());
        holderData.guru.setText(dm.getNama());
        holderData.dariJam.setText(dm.getDari_jam());
        holderData.sampaiJam.setText(dm.getSampai_jam());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView namaKelas,mataPelajaran,dariJam,sampaiJam,guru;
        DataModel dm;
        public HolderData(View v){
            super(v);
            namaKelas = (TextView)v.findViewById(R.id.tvNamaKelas);
            mataPelajaran = (TextView)v.findViewById(R.id.tvMataPelajaranGuru);
            dariJam = (TextView)v.findViewById(R.id.tvDariJam);
            sampaiJam = (TextView)v.findViewById(R.id.tvSampaiJam);
            guru = (TextView)v.findViewById(R.id.tvNamaGuruSiswa);
        }
    }

}
