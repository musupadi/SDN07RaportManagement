package com.destinyapp.aplikasisdn07.Admin.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Fragment.DataKelasAdmin;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.Dialog.UpdateDeleteDialog;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDataKelas extends RecyclerView.Adapter<AdapterDataKelas.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;
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
        Button Update,Delete;
        HolderData(View v){
            super(v);
            myDialog = new Dialog(ctx);
            myDialog.setContentView(R.layout.layout_dialog);
            Update = (Button)myDialog.findViewById(R.id.btnUpdate);
            Delete = (Button)myDialog.findViewById(R.id.btnDelete);
            NamaKelas = (TextView)v.findViewById(R.id.tvNamaKelas);
            TingkatKelas = (TextView)v.findViewById(R.id.tvTingkatKelas);
            //final String kelas = dm.getId_kelas();
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.show();
                }
            });
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ctx,"Update",Toast.LENGTH_SHORT).show();
                    String kelas = dm.getId_kelas();
                    Intent goInput = new Intent(ctx, MainAdminActivity.class);
                    goInput.putExtra("UPDATE_KELAS","update_kelas");
                    goInput.putExtra("ID_KELAS",kelas);
                    ctx.startActivities(new Intent[]{goInput});
                }
            });
            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String kelas = dm.getId_kelas();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("Anda Ingin menghapus Data Ini ? ? ")
                            .setCancelable(false)
                            .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DeleteData(kelas);
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            //Set your icon here
                            .setTitle("Absensi")
                            .setIcon(R.drawable.icon);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }
    private void DeleteData(String idKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Delete = api.DeleteKelas(idKelas);
        Delete.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(ctx,response.body().getResponse(),Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(ctx, MainAdminActivity.class);
                goInput.putExtra("OUTPUT_KELAS","output_kelas");
                ctx.startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Data Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

