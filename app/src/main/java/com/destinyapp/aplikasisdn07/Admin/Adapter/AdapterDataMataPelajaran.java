package com.destinyapp.aplikasisdn07.Admin.Adapter;

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

public class AdapterDataMataPelajaran extends RecyclerView.Adapter<AdapterDataMataPelajaran.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;
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
        Button Update,Delete;
        HolderData(View v){
            super(v);
            myDialog = new Dialog(ctx);
            myDialog.setContentView(R.layout.layout_dialog);
            Update = (Button)myDialog.findViewById(R.id.btnUpdate);
            Delete = (Button)myDialog.findViewById(R.id.btnDelete);
            NamaMapel = (TextView)v.findViewById(R.id.tvNamaMapel);
            TingkatMapel = (TextView)v.findViewById(R.id.tvTingkatPelajaran);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.show();
                }
            });
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ctx,"Update",Toast.LENGTH_SHORT).show();
                    String mapel = dm.getId_mapel();
                    Intent goInput = new Intent(ctx, MainAdminActivity.class);
                    goInput.putExtra("UPDATE_MAPEL","update_mapel");
                    goInput.putExtra("ID_MAPEL",mapel);
                    ctx.startActivities(new Intent[]{goInput});
                }
            });
            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   final String idMapel = dm.getId_mapel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("Anda Ingin menghapus Data Ini ? ? ")
                            .setCancelable(false)
                            .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DeleteData(idMapel);
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
    private void DeleteData(String idMapel){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Delete = api.DeleteMapel(idMapel);
        Delete.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(ctx,response.body().getResponse(),Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(ctx, MainAdminActivity.class);
                goInput.putExtra("OUTPUT_MAPEL","output_mapel");
                ctx.startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Data Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
