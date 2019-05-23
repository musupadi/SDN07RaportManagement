package com.destinyapp.aplikasisdn07.Siswa.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterJadwalBelajarSiswa extends RecyclerView.Adapter<com.destinyapp.aplikasisdn07.Siswa.Adapter.AdapterJadwalBelajarSiswa.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;
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
        Button Update,Delete;
        public HolderData(View v){
            super(v);
            myDialog = new Dialog(ctx);
            myDialog.setContentView(R.layout.layout_dialog);
            Update = (Button)myDialog.findViewById(R.id.btnUpdate);
            Delete = (Button)myDialog.findViewById(R.id.btnDelete);
            namaKelas = (TextView)v.findViewById(R.id.tvNamaKelas);
            mataPelajaran = (TextView)v.findViewById(R.id.tvMataPelajaranGuru);
            dariJam = (TextView)v.findViewById(R.id.tvDariJam);
            sampaiJam = (TextView)v.findViewById(R.id.tvSampaiJam);
            guru = (TextView)v.findViewById(R.id.tvNamaGuruSiswa);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.show();
                }
            });
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String jadwal = dm.getId_jadwal();
                    Intent goInput = new Intent(ctx, MainAdminActivity.class);
                    goInput.putExtra("UPDATE_JADWAL","update_jadwal");
                    goInput.putExtra("ID_JADWAL",jadwal);
                    ctx.startActivities(new Intent[]{goInput});
                }
            });
            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String jadwal = dm.getId_jadwal();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("Anda Ingin menghapus Data Ini ? ? ")
                            .setCancelable(false)
                            .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DeleteData(jadwal);
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            //Set your icon here
                            .setTitle("Data Kelas")
                            .setIcon(R.drawable.classroom);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }
    private void DeleteData(String jadwal){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Delete = api.DeleteJadwal(jadwal);
        Delete.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(ctx,response.body().getResponse(),Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(ctx, MainAdminActivity.class);
                goInput.putExtra("OUTPUT_JADWAL","output_jadwal");
                ctx.startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Data Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
