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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataMataPelajaran;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterNamaMapel;
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
public class DataMataPelajaranAdmin extends Fragment {

    RecyclerView recycler;
    AutoCompleteTextView NamaMapel;
    Spinner tingkatkelas;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;
    Button cari;
    String idMapel;
    public DataMataPelajaranAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_mata_pelajaran_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NamaMapel = (AutoCompleteTextView) view.findViewById(R.id.etNamaMapel);
        tingkatkelas = (Spinner)view.findViewById(R.id.spinnerTingkatKelas);
        cari = (Button)view.findViewById(R.id.btnInput);
        recycler = (RecyclerView)view.findViewById(R.id.recycler);
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(mManager);
        getAllMapel();
        Auto();
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchData();
            }
        });
    }
    private void Auto(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> AutoText = api.getAllMapel();
        AutoText.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterNamaMapel adapter = new AdapterNamaMapel(getActivity(),mItems);
                NamaMapel.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SearchData(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> SearchData = api.searchMapel(NamaMapel.getText().toString(),
                tingkatkelas.getSelectedItem().toString());
        SearchData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterDataMataPelajaran(getActivity(),mItems);
                recycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAllMapel(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataGuru = api.getAllMapel();
        GetAllDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterDataMataPelajaran(getActivity(),mItems);
                recycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
