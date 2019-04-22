package com.destinyapp.aplikasisdn07.Admin.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputDataMapel extends Fragment {

    EditText NamaMapel;
    Spinner tingkatKelas;
    Button Insert;
    public InputDataMapel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_data_mapel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NamaMapel = (EditText)view.findViewById(R.id.etNamaMapel);
        tingkatKelas = (Spinner)view.findViewById(R.id.spinnerTingkatKelas);
        Insert = (Button)view.findViewById(R.id.btnInput);
        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });
    }
    private void InsertData(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> insertDataMapel = api.insertDataMapel(NamaMapel.getText().toString(),
                tingkatKelas.getSelectedItem().toString());
        insertDataMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body().getResponse().equals("Insert")){
                    Toast.makeText(getActivity(),"Data Berhasil Terinput",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Data Mata Pelajaran Sudah Terisi",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Gagal Silahkan Coba lagi",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
