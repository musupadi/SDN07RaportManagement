package com.destinyapp.aplikasisdn07.Siswa.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterFinalRaport;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;
import com.destinyapp.aplikasisdn07.Session.DB_Helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RaportSiswaFragment extends Fragment {

    DB_Helper dbHelper;
    TextView NamaSiswa,NIS,Kelas,tgl,Sakit,Izin,Alpa;
    ImageView profile;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;

    public RaportSiswaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_raport_siswa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NamaSiswa = (TextView)view.findViewById(R.id.tvNamaSiswaFinalGuru);
        NIS = (TextView)view.findViewById(R.id.tvNISFinalGuru);
        Kelas = (TextView)view.findViewById(R.id.tvKelasFinalGuru);
        tgl = (TextView)view.findViewById(R.id.tvTglSekarangFinal);
        Sakit = (TextView)view.findViewById(R.id.tvSakitSiswa);
        Izin = (TextView)view.findViewById(R.id.tvIzinSiswa);
        Alpa = (TextView)view.findViewById(R.id.tvAlpaSiswa);
        profile = (ImageView)view.findViewById(R.id.ivPhotoSiswaFinal);
        mRecycler = (RecyclerView)view.findViewById(R.id.recyclerFinalNilai);
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);
        String nis = "";
        dbHelper = new DB_Helper(getActivity());
        Cursor cursor = dbHelper.checkSession();
        while (cursor.moveToNext()){
            nis=cursor.getString(0);
        }
        getNamaFromNIS(nis);
        getStatusFromNIS(nis);
        getNilaiFromNIS(nis);
    }
    private void getStatusFromNIS(String nis){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getRaport = api.getRaportData(nis);
        getRaport.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String izin = response.body().getIzin();
                String alpa = response.body().getAlpa();
                String sakit = response.body().getSakit();
                Izin.setText(izin);
                Alpa.setText(alpa);
                Sakit.setText(sakit);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getNamaFromNis Method",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNamaFromNIS(String nis){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getNama = api.getSiswaData(nis);
        getNama.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String nama = response.body().getNama_siswa();
                NamaSiswa.setText(nama);
                NIS.setText(response.body().getNis());
                Kelas.setText(response.body().getNama_kelas());
                String BASE_URL = getString(R.string.base_url);
                String URL = BASE_URL+"ProfilePicture/Siswa/"+response.body().getProfile_siswa();
                Glide.with(getActivity())
                        .load(URL)
                        .into(profile);
                tgl.setText(getToday());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getNamaFromNis Method",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static String dayName(String inputDate, String format){
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
    }
    private String getToday(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String thisDay = dateFormat.format(date);
        String today = dayName(thisDay,"dd/MM/yyyy");
        String HariIni = "Senin";
        if(today.equals("Monday")){
            HariIni = "Senin";
        }else if(today.equals("Tuesday")){
            HariIni = "Selasa";
        }else if(today.equals("Wednesday")){
            HariIni = "Rabu";
        }else if(today.equals("Thursday")){
            HariIni = "Kamis";
        }else if(today.equals("Friday")){
            HariIni = "Jumat";
        }else if(today.equals("Saturday")){
            HariIni = "Sabtu";
        }else if(today.equals("Sunday")){
            HariIni = "Minggu";
        }
        String Tgl = HariIni+","+thisDay;
        return Tgl;
    }
    private void getNilaiFromNIS(String nis){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getNilai = api.getNilaiSiswa(nis);
        getNilai.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d("RETRO","RESPONSE : "+response.body().getKode());
                mItems=response.body().getResult();

                mAdapter = new AdapterFinalRaport(getActivity(),mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Kelas Tidak Ditemukan",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
