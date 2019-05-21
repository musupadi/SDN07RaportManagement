package com.destinyapp.aplikasisdn07.Admin.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataGuruAdmin;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataSiswaAdmin;
import com.destinyapp.aplikasisdn07.GlobalAdapter.AdapterNIS;
import com.destinyapp.aplikasisdn07.GlobalAdapter.AdapterNamaSiswa;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterGetAllSiswa;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataSiswaAdmin extends Fragment {

    RecyclerView recycler;
    AutoCompleteTextView NIS,nama;
    Button cari;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;
    public DataSiswaAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_siswa_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = (RecyclerView)view.findViewById(R.id.recycler);
        NIS = (AutoCompleteTextView)view.findViewById(R.id.etNIS);
        nama = (AutoCompleteTextView)view.findViewById(R.id.etNamaSiswa);
        cari = (Button)view.findViewById(R.id.btnCari);
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(mManager);
        getAllSiswa();
        getAutoNama();
        getAutoNIS();
        NIS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    getAuto("",NIS.getEditableText().toString());
                }
            }
        });
        nama.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    getAuto(nama.getEditableText().toString(),"");
                }
            }
        });
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchData();
            }
        });
    }
    private void SearchData(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getSiswaData = api.getSiswaDataSiswa(NIS.getEditableText().toString(),nama.getEditableText().toString());
        getSiswaData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterGetAllSiswa(getActivity(),mItems);
                recycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Internet Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAuto(final String Nama,final String nis){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataSiswa = api.getSiswaFrom(Nama,nis);
        GetAllDataSiswa.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (Nama == ""){
                    //Toast.makeText(getActivity(),response.body().getNama_siswa(),Toast.LENGTH_SHORT).show();
                    nama.setText(response.body().getNama_siswa());
                }else if(nis == ""){
                    //Toast.makeText(getActivity(),response.body().getNis(),Toast.LENGTH_SHORT).show();
                    NIS.setText(response.body().getNis());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Internet Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoNama(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> getData= api.getAllDataSiswa();
        getData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterNIS adapter = new AdapterNIS(getActivity(),mItems);
                NIS.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoNIS(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> getData= api.getAllDataSiswa();
        getData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterNamaSiswa adapter = new AdapterNamaSiswa(getActivity(),mItems);
                nama.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAllSiswa(){
            ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponseModel> GetAllDataSiswa = api.getAllDataSiswa();
            GetAllDataSiswa.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    mItems=response.body().getResult();
                    mAdapter = new AdapterGetAllSiswa(getActivity(),mItems);
                    recycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(getActivity(),"Data Error pada Method getAllGuru",Toast.LENGTH_SHORT).show();
                }
            });
    }
}
