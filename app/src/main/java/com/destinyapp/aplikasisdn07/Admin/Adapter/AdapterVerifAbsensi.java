package com.destinyapp.aplikasisdn07.Admin.Adapter;

import android.app.AlertDialog;
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
import com.destinyapp.aplikasisdn07.MainActivity;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;
import com.destinyapp.aplikasisdn07.Session.DB_Helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterVerifAbsensi extends RecyclerView.Adapter<AdapterVerifAbsensi.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    private String[] listItems;

    public AdapterVerifAbsensi(Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_absensi,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVerifAbsensi.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.NIS.setText(dm.getNis());
        holderData.NamaSiswa.setText(dm.getNama_siswa());
        holderData.sakit.setText(dm.getSakit());
        holderData.izin.setText(dm.getIzin());
        holderData.alpa.setText(dm.getAlpa());
        if (dm.getVerif().equals("sudah")){
            holderData.verif.setText("Update");
        }else{
            holderData.verif.setText(dm.getVerif()+" Terverivikasi");
        }

        String url = dm.getProfile_siswa();
        String BASE_URL = ctx.getString(R.string.base_url);
        String URL = BASE_URL+"ProfilePicture/Siswa/"+url;
        Glide.with(ctx)
                .load(URL)
                .into(holderData.Profile);
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView NIS,NamaSiswa,sakit,izin,alpa;
        Button verif;
        ImageView Profile;
        DataModel dm;
        HolderData(View v){
            super(v);
            NIS = (TextView)v.findViewById(R.id.tv_NIS);
            NamaSiswa = (TextView)v.findViewById(R.id.tv_NamaSiswa);
            sakit = (TextView)v.findViewById(R.id.tvSakit);
            izin = (TextView)v.findViewById(R.id.tvIzin);
            alpa = (TextView)v.findViewById(R.id.tvAlpa);
            verif = (Button)v.findViewById(R.id.btnStatus);
            Profile = (ImageView)v.findViewById(R.id.ivPhotoSiswa);
            verif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Verif=dm.getVerif();
                    String NIS=dm.getNis();
                    if (Verif.equals("belum")){
                        Sure(NIS,Verif);
                    }else if(Verif.equals("sudah")){
                        UpdateAbsensi(NIS);
                    }
                }
            });
        }
    }
    private void UpdatePilih(String NIS,String status){
        if (status.equals("Delete")){

        }else if(status.equals("Update")){
            Intent goInput = new Intent(ctx, MainAdminActivity.class);
            goInput.putExtra("ABSENSI","update");
            goInput.putExtra("NIS",NIS);
            ctx.startActivities(new Intent[]{goInput});
        }
    }
    private void UpdateAbsensi(final String NIS){
        listItems = new String[]{"Update","Delete"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
        mBuilder.setTitle("Pilih Status Siswa");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String status = listItems[i];
                dialog.dismiss();
                UpdatePilih(NIS,status);
            }
        });
        mBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
    private void VerivikasiBelumAbsen(String NIS,String verif){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> VerifAbsen = api.updateVerifAbsen(NIS,"sudah");
        VerifAbsen.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Response = response.body().getResponse();
                if(Response.equals("Update")){
                    Toast.makeText(ctx,"Data Berhasil Terverifikasi",Toast.LENGTH_SHORT).show();
                    Intent goInput = new Intent(ctx, MainAdminActivity.class);
                    goInput.putExtra("ABSEN","sudah");
                    ctx.startActivities(new Intent[]{goInput});
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Koneksi gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Sure(final String NIS, final String Verif){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Anda Ingin memverivikasi Absensi ini ? ")
                .setCancelable(false)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        VerivikasiBelumAbsen(NIS,Verif);
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
}
