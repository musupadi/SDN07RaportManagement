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

public class AdapterGetAllSiswa extends RecyclerView.Adapter<AdapterGetAllSiswa.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterGetAllSiswa (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_all_siswa_from_class,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.nis.setText(dm.getNis());
        holderData.nama.setText(dm.getNama_siswa());
        String BASE_URL = "http://192.168.0.3/AplikasiSDN07/";
        String URL = BASE_URL+"ProfilePicture/Siswa/"+dm.getProfile_siswa();
        Glide.with(ctx)
                .load(URL)
                .into(holderData.profile);
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class HolderData extends RecyclerView.ViewHolder{
        TextView nis,nama;
        ImageView profile;
        DataModel dm;
        public HolderData(View v){
            super(v);
            nis = (TextView)v.findViewById(R.id.tv_NamaAllSiswaGuru);
            nama = (TextView)v.findViewById(R.id.tv_NISAllSiswa);
            profile = (ImageView)v.findViewById(R.id.ivPhotoAllSiswaGuru);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, MainGuruActivity.class);
                    goInput.putExtra("nama_siswa",dm.getNama_siswa());
                    goInput.putExtra("nis",dm.getNis());
                    goInput.putExtra("Penilaian","penilaian");
                    goInput.putExtra("id_kelas",dm.getId_kelas());
                    ctx.startActivities(new Intent[]{goInput});
                }
            });
        }
    }
}