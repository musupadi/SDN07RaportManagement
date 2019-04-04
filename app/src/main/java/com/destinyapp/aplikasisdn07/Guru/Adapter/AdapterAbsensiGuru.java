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

public class AdapterAbsensiGuru extends RecyclerView.Adapter<AdapterAbsensiGuru.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterAbsensiGuru (Context ctx,List<DataModel> mList){
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

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, MainGuruActivity.class);
                    String idjadwal= dm.getId_jadwal();
                    goInput.putExtra("id_kelas",dm.getId_kelas());
                    goInput.putExtra("id_mapel",dm.getId_mapel());
                    goInput.putExtra("Absensi","absensi");
                    ctx.startActivities(new Intent[]{goInput});
                }
            });
        }
    }

}
