package com.destinyapp.aplikasisdn07.Admin.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class AdapterDataGuruAdmin extends RecyclerView.Adapter<AdapterDataGuruAdmin.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;
    public AdapterDataGuruAdmin (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_data_guru_admin,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataGuruAdmin.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.nip.setText(dm.getNip());
        holderData.nama.setText(dm.getNama());
        holderData.notelp.setText(dm.getNotelp());
        holderData.jabatan.setText(dm.getJabatan());
        holderData.jk.setText(dm.getJk());
        holderData.alamat.setText(dm.getAlamat());
        String BASE_URL = ctx.getString(R.string.base_url);
        String URL = BASE_URL+"ProfilePicture/Guru/"+dm.getPictureguru();
        Glide.with(ctx)
                .load(URL)
                .into(holderData.PhotoAdmin);
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        ImageView PhotoAdmin;
        TextView nip,nama,notelp,jabatan,jk,alamat;
        Button Update,Delete;
        DataModel dm;
         HolderData(View v){
            super(v);
            myDialog = new Dialog(ctx);
            myDialog.setContentView(R.layout.layout_dialog);
            Update = (Button)myDialog.findViewById(R.id.btnUpdate);
            Delete = (Button)myDialog.findViewById(R.id.btnDelete);
            nip = (TextView)v.findViewById(R.id.tvNipGuruAdmin);
            nama = (TextView)v.findViewById(R.id.tvNamaGuruAdmin);
            notelp = (TextView)v.findViewById(R.id.tvNoTelpGuruAdmin);
            jabatan = (TextView)v.findViewById(R.id.tvJabatanGuruAdmin);
            jk = (TextView)v.findViewById(R.id.tvJKGuruAdmin);
            PhotoAdmin = (ImageView)v.findViewById(R.id.ivPhotoGuruAdmin);
            alamat = (TextView)v.findViewById(R.id.tvAlamatGuruAdmin);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.show();
                }
            });
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nip = dm.getNip();
                    Intent goInput = new Intent(ctx, MainAdminActivity.class);
                    goInput.putExtra("UPDATE_GURU","update_guru");
                    goInput.putExtra("ID_GURU",nip);
                    ctx.startActivities(new Intent[]{goInput});
                }
            });
            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String nip = dm.getNip();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("Anda Ingin menghapus Data Ini ? ? ")
                            .setCancelable(false)
                            .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DeleteData(nip);
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            //Set your icon here
                            .setTitle("Delete Data Guru")
                            .setIcon(R.drawable.classroom);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }
    private void DeleteData(String nip){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Delete = api.DeleteGuru(nip);
        Delete.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(ctx,response.body().getResponse(),Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(ctx, MainAdminActivity.class);
                goInput.putExtra("OUTPUT_GURU","output_guru");
                ctx.startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Data Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

}