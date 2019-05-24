package com.destinyapp.aplikasisdn07.Guru.Adapter;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterGetAllSiswa extends RecyclerView.Adapter<AdapterGetAllSiswa.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;

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
        String BASE_URL = ctx.getString(R.string.base_url);
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
        Button Update,Delete;
        public HolderData(View v){
            super(v);
            myDialog = new Dialog(ctx);
            myDialog.setContentView(R.layout.layout_dialog);
            Update = (Button)myDialog.findViewById(R.id.btnUpdate);
            Delete = (Button)myDialog.findViewById(R.id.btnDelete);
            nis = (TextView)v.findViewById(R.id.tv_NamaAllSiswaGuru);
            nama = (TextView)v.findViewById(R.id.tv_NISAllSiswa);
            profile = (ImageView)v.findViewById(R.id.ivPhotoAllSiswaGuru);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.show();
                }
            });
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nis = dm.getNis();
                    Intent goInput = new Intent(ctx, MainAdminActivity.class);
                    goInput.putExtra("UPDATE_SISWA","update_siswa");
                    goInput.putExtra("ID_SISWA",nis);
                    ctx.startActivities(new Intent[]{goInput});
                }
            });
            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String nis = dm.getNis();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("Anda Ingin menghapus Data Ini ? ? ")
                            .setCancelable(false)
                            .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DeleteData(nis);
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            //Set your icon here
                            .setTitle("Delete Siswa")
                            .setIcon(R.drawable.icon);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }
    private void DeleteData(String nis){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Delete = api.DeleteSiswa(nis);
        Delete.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(ctx,response.body().getResponse(),Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(ctx, MainAdminActivity.class);
                goInput.putExtra("OUTPUT_SISWA","output_siswa");
                ctx.startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Data Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}