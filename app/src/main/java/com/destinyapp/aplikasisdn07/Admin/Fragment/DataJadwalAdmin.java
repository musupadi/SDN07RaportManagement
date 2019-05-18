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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataKelas;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterGuruAutoNama;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterGuruAutoNip;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterKelasSpinner;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterNamaMapel;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;
import com.destinyapp.aplikasisdn07.Siswa.Adapter.AdapterJadwalBelajarSiswa;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataJadwalAdmin extends Fragment {

    Spinner Kelas,Hari;
    Button cari;
    RecyclerView recycler;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;
    private AdapterKelasSpinner aSpinner;
    String idKelas;

    public DataJadwalAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_jadwal_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = (RecyclerView)view.findViewById(R.id.recycler);
        Kelas = (Spinner)view.findViewById(R.id.spinnerNamaKelas);

        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(mManager);
        getAllJadwal();
        getKelas();
        aSpinner = new AdapterKelasSpinner(getActivity(),mItems);
        Kelas.setAdapter(aSpinner);
        Kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataModel clickedItem = (DataModel) parent.getItemAtPosition(position);
                String clickedItemNamaKelas = clickedItem.getNama_kelas();
                GetIDKelas(clickedItemNamaKelas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void GetIDKelas(String NamaKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getIDKelas = api.GetIDKelas(NamaKelas);
        getIDKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                idKelas = response.body().getId_kelas();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam GetIDKelas",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getKelas(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetKelas = api.getKelas();
        GetKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterKelasSpinner adapter = new AdapterKelasSpinner(getActivity(),mItems);
                Kelas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Kelas Tidak Ditemukan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAllJadwal(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataGuru = api.getAllDataJadwal();
        GetAllDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterJadwalBelajarSiswa(getActivity(),mItems);
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
