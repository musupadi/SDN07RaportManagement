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
import com.destinyapp.aplikasisdn07.GlobalAdapter.AdapterAutoCompleteNIP;
import com.destinyapp.aplikasisdn07.GlobalAdapter.AdapterAutoCompleteNamaGuru;
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
public class DataGuruAdmin extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;
    private AutoCompleteTextView NIP,NamaGuru;
    private Button btnCari;
    RecyclerView DataGuru;
    public DataGuruAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_guru_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataGuru = (RecyclerView)view.findViewById(R.id.recycler);
        NIP = (AutoCompleteTextView)view.findViewById(R.id.etNIP);
        NamaGuru = (AutoCompleteTextView)view.findViewById(R.id.etNamaGuru);
        btnCari = (Button)view.findViewById(R.id.btnCari);
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        DataGuru.setLayoutManager(mManager);
        getAllGuru();
        getAutoNIP();
        getAutoNama();
        NIP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    getAuto("",NIP.getEditableText().toString());
                }
            }
        });
        NamaGuru.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                   getAuto(NamaGuru.getEditableText().toString(),"");
                }
            }
        });
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchData();
            }
        });

    }

    private void SearchData(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataGuru = api.SearchGuru(NIP.getEditableText().toString());
        GetAllDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterDataGuruAdmin(getActivity(),mItems);
                DataGuru.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada Method getAllGuru",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAuto(final String Nama,final String nip){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataGuru = api.getGuruComplete(Nama,nip);
        GetAllDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (Nama == ""){
                    NamaGuru.setText(response.body().getNama());
                }else if(nip == ""){
                    NIP.setText(response.body().getNip());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada Method getAllGuru",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAllGuru(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataGuru = api.getAllDataGuru();
        GetAllDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterDataGuruAdmin(getActivity(),mItems);
                DataGuru.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada Method getAllGuru",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoNama(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> getData= api.getAllDataGuru();
        getData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterAutoCompleteNamaGuru adapter = new AdapterAutoCompleteNamaGuru(getActivity(),mItems);
                NamaGuru.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoNIP(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> getData= api.getAllDataGuru();
        getData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterAutoCompleteNIP adapter = new AdapterAutoCompleteNIP(getActivity(),mItems);
                NIP.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
