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
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataGuruAdmin;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataSiswaAdmin;
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

        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(mManager);
        getAllSiswa();
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
